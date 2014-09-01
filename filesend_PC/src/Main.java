import controller.*;

public class Main {
	public static void main(String[] args) {
		commonfactory.getFileThread().start();

		mainView myView = new mainView();
		myView.setVisible(true);

		myView.notConnected();
		try {
			while (!commonfactory.getFileThread().isConnected()) {
				Thread.currentThread().sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		myView.connected();
	}
}
