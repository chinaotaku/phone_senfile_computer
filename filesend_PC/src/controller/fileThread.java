package controller;
import java.awt.TrayIcon.MessageType;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;

import javax.swing.JOptionPane;

/**
 * 文件传输线程
 * @author xxk
 *
 */
public class fileThread extends Thread {
	private String ip;
	private int port;
	private ServerSocket serverSocket;
	private Socket socket;
	private File file;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private boolean isConnected = false;

	public fileThread() {
		try {
			serverSocket = new ServerSocket(commondata.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//文件接收
	public void sendTest(String filePath) {

		if (dos == null) {
			System.err.println("还没有连接");
			return;
		}

		long fileLength;
		long passedlen = 0;
		String filename;
		FileInputStream fis;
		int read = -1;
		byte[] buf = new byte[commondata.getSize()];

		// 获取需要传输的文件
		file = new File(filePath);
		try {
			fis = new FileInputStream(file);
			fileLength = file.length();
			filename = file.getName().substring(
					file.getName().lastIndexOf("\\") + 1);

			dos.writeUTF(filename);
			dos.flush();

			dos.writeLong(fileLength);
			dos.flush();

			progressview pv = new progressview("上载");

			Timer timer = new Timer();
			timerSpeedTask timerSpeedTask = new timerSpeedTask();
			timer.schedule(timerSpeedTask, 1000l, 1000l);
			int time = 0;

			while ((read = fis.read(buf)) != -1) {
				dos.write(buf, 0, read);

				passedlen += read;
				if (commondata.getTime() != 0) {
					time = commondata.getTime();
				} else {
					System.out.println("上载速度为：" + time * commondata.getSize()
							/ 1000 + "KB/s");
					System.out.println("文件发送了：" + passedlen * 100 / fileLength
							+ "%");
					if (endTime((fileLength - passedlen),
							(time * commondata.getSize() / 1000)) == -1) {
						System.out.println("还剩余：" + "--" + "s");
					} else {
						System.out.println("还剩余"
								+ endTime((fileLength - passedlen), (time
										* commondata.getSize() / 1000)) + "s");
						pv.setProgressValue(
								(int) (passedlen * 100 / fileLength),
								time * commondata.getSize() / 1000,
								endTime((fileLength - passedlen), (time
										* commondata.getSize() / 1000)));
					}
				}
				commondata.plusTime();
			}
			dos.flush();
			fileLength = 0;
			passedlen = 0;
			pv.dispose();
			System.out.println("文件发送完毕");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件接收线程
	 * @author xxk
	 *
	 */
	private class filesendThread extends Thread {
		private String filePath;

		public filesendThread(String filePath) {
			this.filePath = filePath;
		}

		@Override
		public void run() {
			sendTest(filePath);
		}
	}

	public void sendFile(String filePath) {
		new filesendThread(filePath).start();
	}

	@Override
	public void run() {
		String fileSavePath = "C:\\"; // 默认存放路径为C盘
		long fileLength;
		byte buf[] = new byte[commondata.getSize()];
		int read = -1;
		long passedlen = 0;
		String filename = null;

		FileOutputStream fos;

		try {

			// 连接到服务端
			socket = serverSocket.accept();
			isConnected = true;
			System.out.println("连接上了");
			dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			dos = new DataOutputStream(new BufferedOutputStream(
					socket.getOutputStream()));

			Timer timer = new Timer();
			timerSpeedTask timerSpeedTask = new timerSpeedTask();
			timer.schedule(timerSpeedTask, 1000l, 1000l);
			int time = 0;
			while (dis != null) {

				filename = dis.readUTF();

				fileLength = dis.readLong();
				System.out.println("文件的长度为:" + fileLength + "    KB");
				System.out.println("开始接收文件!");
				// JOptionPane.showMessageDialog(commonfactory.getMainview(),
				// "收到文件:" + filename + "\n文件大小为" + fileLength + "KB"
				// + "接收文件名：" + filename);

				// 新建一个文件路径选择窗口
				savePathView sv = new savePathView("收到文件:" + filename
						+ "\n文件大小为" + fileLength + "KB" + "接收文件名：" + filename);

				// 暂停一秒循环等待文件路径选择
				while (sv.choseState() == null) {
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (sv.choseState().equals("CANCEL")) {
					dos.writeUTF("CANCEL");//向对方发送不进行通信信号
					continue;
				}

				fileSavePath = sv.getFilePath(); //获取已经选择好的路径
				fos = new FileOutputStream(fileSavePath + filename);
				
//				dos.writeUTF("OK");//向对方发送确认通信信号
				
				progressview pv = new progressview("下载");
				while ((read = dis.read(buf)) != -1) {
					passedlen += read;

					if (commondata.getTime() != 0) {
						time = commondata.getTime();
					} else {
						System.out.println("下载速度为：" + time
								* commondata.getSize() / 1000 + "KB/s");
						System.out.println("文件接收了：" + passedlen * 100
								/ fileLength + "%");
						System.out.println("还剩余"
								+ endTime((fileLength - passedlen), (time
										* commondata.getSize() / 1000)) + "s");
						pv.setProgressValue(
								(int) (passedlen * 100 / fileLength),
								time * commondata.getSize() / 1000,
								endTime((fileLength - passedlen), (time
										* commondata.getSize() / 1000)));
					}
					commondata.plusTime();
					fos.write(buf, 0, read);

					if (passedlen == fileLength)
						break;
				}
				System.out.println("文件接收完毕!");
				fileLength = 0;
				passedlen = 0;
				fos.close();
				pv.dispose();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long endTime(long last, int speed) {
		if (speed == 0) {
			return -1;
		}
		return last / speed;
	}

	public boolean isConnected() {
		return isConnected;
	}
}
