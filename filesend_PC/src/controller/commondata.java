package controller;
public class commondata {
	private static final int SIZE = 1024;
//	private static String ip = "192.168.1.101";
	private static int port = 6633;
	private static int time = -1;
	
	public static final int getSize(){
		return SIZE;
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
	
//	public static void setIp(String IP){
//		ip = IP;
//	}
//	public static String getIp(){
//		return ip;
//	}
	
	public static void setPort(int PORT){
		port = PORT;
	}
	public static int getPort(){
		return port;
	}
}
