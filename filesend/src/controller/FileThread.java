package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;

import android.R.string;

import model.biz.receivefile;
import model.biz.sendfile;

/**
 * 文件传输线程 
 * @author xxk
 *
 */
public class FileThread extends Thread {
	private String ip;
	private int port;
	private Socket socket;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private boolean isConnected = false; //监听是否已经连接
	private boolean isSendFinished = true; //监听文件是否发送完成
	private boolean isGetAllPath = false; //用于监听是否获取到电脑端当前的目录情况

	public FileThread(String ip, int port) {
		this.ip = ip;
		this.port = port;
		
		Timer timer = new Timer();
		timerSpeedTask timerSpeedTask = new timerSpeedTask();
		timer.schedule(timerSpeedTask, 1000l, 1000l);

	}

	public void close() {
		try {
			dis.close();
			dos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMsg(String path){
		try {
			dos.writeUTF(path);
			dos.flush();
			System.err.println("sendpath------>" + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendFile(String filename) {
		new sendThread(filename).start();
	}

	class sendThread extends Thread {
		private String filename;

		public sendThread(String fn) {
			filename = fn;
		}

		@Override
		public void run() {
			isSendFinished = false;
			if (dos == null) {
				System.err.println("还没有连接");
				return;
			}
			
			sendfile sf = new sendfile(dos);
			sf.sendTest(filename);
		}
	}

	@Override
	public void run() {
		String msg = null;
		try {
			// 连接到服务端
			socket = new Socket(ip, port);
			isConnected = true;
			dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			dos = new DataOutputStream(new BufferedOutputStream(
					socket.getOutputStream()));
			
			receivefile rf = new receivefile(dis);
			
			String[] sptmsg;
			while (dis != null) {
				//接收电脑端传来的信号
				msg = dis.readUTF();
				System.out.println(msg);
				
				sptmsg = msg.split(commondata.getSpilt());
				if(sptmsg[0].equals("RECEIVE")){
					//接收文件 
					rf.receiveTest();
				}else if(sptmsg[0].equals("SEND")){
					
				}else if(sptmsg[0].equals("GETALLPATH")){
					System.err.println("GETALLPATH");
					//接收到了电脑端当前所有路径(基于windows操作系统)
					commondata.setallPath(sptmsg[1]);
					commonfactory.getinitHardDiskActivity().changeActivity();
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isFileSendFinished() {
		return isSendFinished;
	}

	public boolean isConnected() {
		return isConnected;
	}
	
	public boolean isGetAllPath(){
		return isGetAllPath;
	}
}
