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

		// 获取需要传输的文件
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
			
//			//判断对方是否同意接收文件
//			if(dis.readUTF().equals("CANCEL")){
//				System.out.println("收到cancel");
//				return;
//			}

			System.out.println("开始发送");
			
			//如果需要发送文件，就将界面切换
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
			System.out.println("发送完毕");
			
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
