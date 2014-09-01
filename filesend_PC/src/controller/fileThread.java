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
 * �ļ������߳�
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

	//�ļ�����
	public void sendTest(String filePath) {

		if (dos == null) {
			System.err.println("��û������");
			return;
		}

		long fileLength;
		long passedlen = 0;
		String filename;
		FileInputStream fis;
		int read = -1;
		byte[] buf = new byte[commondata.getSize()];

		// ��ȡ��Ҫ������ļ�
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

			progressview pv = new progressview("����");

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
					System.out.println("�����ٶ�Ϊ��" + time * commondata.getSize()
							/ 1000 + "KB/s");
					System.out.println("�ļ������ˣ�" + passedlen * 100 / fileLength
							+ "%");
					if (endTime((fileLength - passedlen),
							(time * commondata.getSize() / 1000)) == -1) {
						System.out.println("��ʣ�ࣺ" + "--" + "s");
					} else {
						System.out.println("��ʣ��"
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
			System.out.println("�ļ��������");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ļ������߳�
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
		String fileSavePath = "C:\\"; // Ĭ�ϴ��·��ΪC��
		long fileLength;
		byte buf[] = new byte[commondata.getSize()];
		int read = -1;
		long passedlen = 0;
		String filename = null;

		FileOutputStream fos;

		try {

			// ���ӵ������
			socket = serverSocket.accept();
			isConnected = true;
			System.out.println("��������");
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
				System.out.println("�ļ��ĳ���Ϊ:" + fileLength + "    KB");
				System.out.println("��ʼ�����ļ�!");
				// JOptionPane.showMessageDialog(commonfactory.getMainview(),
				// "�յ��ļ�:" + filename + "\n�ļ���СΪ" + fileLength + "KB"
				// + "�����ļ�����" + filename);

				// �½�һ���ļ�·��ѡ�񴰿�
				savePathView sv = new savePathView("�յ��ļ�:" + filename
						+ "\n�ļ���СΪ" + fileLength + "KB" + "�����ļ�����" + filename);

				// ��ͣһ��ѭ���ȴ��ļ�·��ѡ��
				while (sv.choseState() == null) {
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				if (sv.choseState().equals("CANCEL")) {
					dos.writeUTF("CANCEL");//��Է����Ͳ�����ͨ���ź�
					continue;
				}

				fileSavePath = sv.getFilePath(); //��ȡ�Ѿ�ѡ��õ�·��
				fos = new FileOutputStream(fileSavePath + filename);
				
//				dos.writeUTF("OK");//��Է�����ȷ��ͨ���ź�
				
				progressview pv = new progressview("����");
				while ((read = dis.read(buf)) != -1) {
					passedlen += read;

					if (commondata.getTime() != 0) {
						time = commondata.getTime();
					} else {
						System.out.println("�����ٶ�Ϊ��" + time
								* commondata.getSize() / 1000 + "KB/s");
						System.out.println("�ļ������ˣ�" + passedlen * 100
								/ fileLength + "%");
						System.out.println("��ʣ��"
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
				System.out.println("�ļ��������!");
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
