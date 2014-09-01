package controller;

public class commonfactory {
	private static fileThread fileThread = new fileThread();
	private static mainView mainView;
	private static savePathView savePathView;
	
	public static savePathView getSavePathView(){
		return savePathView;
	}
	
	public static void setSavePathView(savePathView pv){
		savePathView = pv;
	}
	
	public static fileThread getFileThread(){
		return fileThread;
	}
	
	public static void setMainview(mainView mv){
		mainView = mv;
	}
	
	public static mainView getMainview(){
		return mainView;
	}
}
