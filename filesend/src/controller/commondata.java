package controller;

import android.R.string;

public class commondata {
	private static final int SIZE = 1024;
	private static String ip = "192.168.1.101";
	private static int port = 6633;
	private static String filePath = null;
	private static int time = -1;
	private static final String spilt = "::";
	private static String allPath = null;
	
	public static void setallPath(String ap){
		allPath = ap;
	}
	
	public static String getallPath(){
		return allPath;
	}
	
	public static final String getSpilt(){
		return spilt;
	}
	
	public static int getTime(){
		return time;
	}
	
	public static void resetTime(){
		time = 0;
	}
	
	public static void plusTime(){
		time++;
	}
	public static String getFilePath(){
		return filePath;
	}
	
	public static void setFilePath(String fp){
		filePath = fp;
	}
	
	public static final int getSize(){
		return SIZE;
	}
	
	public static void setIp(String IP){
		ip = IP;
	}
	public static String getIp(){
		return ip;
	}
	
	public static void setPort(int PORT){
		port = PORT;
	}
	public static int getPort(){
		return port;
	}
}
