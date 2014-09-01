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
 * �ļ������߳� 
 * @author xxk
 *
 */
public class FileThread extends Thread {
	private String ip;
	private int port;
	private Socket socket;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private boolean isConnected = false; //�����Ƿ��Ѿ�����
	private boolean isSendFinished = true; //�����ļ��Ƿ������
	private boolean isGetAllPath = false; //���ڼ����Ƿ��ȡ�����Զ˵�ǰ��Ŀ¼���

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
				System.err.println("��û������");
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
			// ���ӵ������
			socket = new Socket(ip, port);
			isConnected = true;
			dis = new DataInputStream(new BufferedInputStream(
					socket.getInputStream()));
			dos = new DataOutputStream(new BufferedOutputStream(
					socket.getOutputStream()));
			
			receivefile rf = new receivefile(dis);
			
			String[] sptmsg;
			while (dis != null) {
				//���յ��Զ˴������ź�
				msg = dis.readUTF();
				System.out.println(msg);
				
				sptmsg = msg.split(commondata.getSpilt());
				if(sptmsg[0].equals("RECEIVE")){
					//�����ļ� 
					rf.receiveTest();
				}else if(sptmsg[0].equals("SEND")){
					
				}else if(sptmsg[0].equals("GETALLPATH")){
					System.err.println("GETALLPATH");
					//���յ��˵��Զ˵�ǰ����·��(����windows����ϵͳ)
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
