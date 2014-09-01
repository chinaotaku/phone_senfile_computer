package model.biz;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import view.downloadingActivity;
import android.content.Intent;
import android.os.Environment;
import controller.commondata;
import controller.commonfactory;

public class receivefile {
	private DataInputStream dis;

	public receivefile(DataInputStream dis) {
		this.dis = dis;
	}

	public void receiveTest() {
		String fileSavePath = Environment.getExternalStorageDirectory()
				.getPath() + "/";
		System.out.println("保存路径：" + fileSavePath);

		long fileLength = 0;
		byte buf[] = new byte[commondata.getSize()];
		int read = -1, passedlen = 0;
		int time = 0;

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fileSavePath + dis.readUTF());
			fileLength = dis.readLong();
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}

		System.out.println("文件的长度为:" + fileLength + "    KB");
		System.out.println("开始接收文件!");
		// 如果要接收文件就将试图切换

		Intent intent = new Intent(
				commonfactory.getSdCardFileExplorerActivity(),
				downloadingActivity.class);
		commonfactory.getSdCardFileExplorerActivity().startActivity(intent);
		commonfactory.getSdCardFileExplorerActivity().finish();
		try {

			while ((read = dis.read(buf)) != -1) {
				passedlen += read;

				if (commondata.getTime() != 0) {
					time = commondata.getTime();
				} else {
					System.out.println("下载速度为：" + time * commondata.getSize()
							/ 1000 + "KB/s");
					System.out.println("文件接收了：" + passedlen * 100 / fileLength
							+ "%");
					System.out.println("还剩余"
							+ endTime((fileLength - passedlen), (time
									* commondata.getSize() / 1000)) + "s");
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
			commonfactory.getdownloadingActivity().changeActivity();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	public long endTime(long last, int speed) {
		if (speed == 0) {
			return -1;
		}
		return last / speed;
	}
}
