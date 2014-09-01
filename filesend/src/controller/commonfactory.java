package controller;

import view.HardDiskFileExplorerActivity;
import view.SDCardFileExplorerActivity;
import view.downloadingActivity;
import view.initHardDiskActivity;
import view.uploadingActivity;

public class commonfactory {
	private static FileThread fileThread;
	private static SDCardFileExplorerActivity sdCardFileExplorerActivity;
	private static downloadingActivity loadingActivity;
	private static uploadingActivity uploadingActivity;
	private static initHardDiskActivity initHardDiskActivity;
	private static HardDiskFileExplorerActivity hardDiskFileExplorerActivity;
	
	public static void setHardDiskFileExplorerActivity(HardDiskFileExplorerActivity hd){
		hardDiskFileExplorerActivity = hd;
	}
	
	public static HardDiskFileExplorerActivity getHardDiskFileExplorerActivity(){
		return hardDiskFileExplorerActivity;
	}
	
	public static void setinitHardDiskActivity(initHardDiskActivity ih){
		initHardDiskActivity = ih;
	}
	
	public static initHardDiskActivity getinitHardDiskActivity(){
		return initHardDiskActivity;
	}

	public static void setUpLoadingActivity(uploadingActivity ul) {
		uploadingActivity = ul;
	}

	public static uploadingActivity getUpLoadingActivity() {
		return uploadingActivity;
	}

	public static void setdownloadingActivity(downloadingActivity ld) {
		loadingActivity = ld;
	}

	public static downloadingActivity getdownloadingActivity() {
		return loadingActivity;
	}

	public static void setsdCardFileExplorerActivity(
			SDCardFileExplorerActivity sd) {
		sdCardFileExplorerActivity = sd;
	}

	public static SDCardFileExplorerActivity getSdCardFileExplorerActivity() {
		return sdCardFileExplorerActivity;
	}

	public static void setFileThread(FileThread ft) {
		fileThread = ft;
	}

	public static FileThread getFileThread() {
		return fileThread;
	}

}
