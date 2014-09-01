package model.biz;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import view.uploadingActivity;
import android.content.Intent;
import controller.commondata;
import controller.commonfactory;

public class sendfile {
	private DataOutputStream dos;
	
	public sendfile(DataOutputStream dos) {
		this.dos = dos;
	}
	
	public boolean sendTest(String filePath){
		long fileLength;
		String filename;
		FileInputStream fis;
		int read = -1;
		byte[] buf = new byte[commondata.getSize()];

		// ��ȡ��Ҫ������ļ�
		File file = new File(filePath);
		try {
			fis = new FileInputStream(file);
			fileLength = file.length();
			filename = file.getName().substring(
					file.getName().lastIndexOf("\\") + 1);

			dos.writeUTF("RECEIVE");
			dos.flush();
			
			dos.writeUTF(filename);
			dos.flush();

			dos.writeLong(fileLength);
			dos.flush();
			
//			//�ж϶Է��Ƿ�ͬ������ļ�
//			if(dis.readUTF().equals("CANCEL")){
//				System.out.println("�յ�cancel");
//				return;
//			}

			System.out.println("��ʼ����");
			
			//�����Ҫ�����ļ����ͽ������л�
			Intent intent = new Intent(commonfactory
					.getSdCardFileExplorerActivity(),
					uploadingActivity.class);
			commonfactory.getSdCardFileExplorerActivity()
					.startActivity(intent);
			commonfactory.getSdCardFileExplorerActivity().finish();
			while ((read = fis.read(buf)) != -1) {
				dos.write(buf, 0, read);
			}
			
			dos.flush();
			System.out.println("�������");
			
			commonfactory.getUpLoadingActivity().changeActivity();
			return true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public long endTime(long last, int speed) {
		if (speed == 0) {
			return -1;
		}
		return last / speed;
	}
}
