import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class progressview extends JFrame{
	private JProgressBar progressBar;
	private JLabel progressLab, speedLab, lastTimeLab;
	
	public progressview(String title){
		progressBar = new JProgressBar();
		progressLab = new JLabel(title + "进度");
		speedLab = new JLabel("速度：" + "--KB/s");
		lastTimeLab = new JLabel("剩余时间：" + "--s");
		JPanel panel = new JPanel();
		
		progressBar.setValue(0);
		
		progressBar.setBounds(20, 50, 200, 20);
		progressLab.setBounds(100, 20, 60, 20);
		speedLab.setBounds(20, 70, 100, 20);
		lastTimeLab.setBounds(120, 70, 100, 20);
		
		panel.setLayout(null);
		panel.add(progressBar);
		panel.add(progressLab);
		panel.add(speedLab);
		panel.add(lastTimeLab);
		
		this.getContentPane().add(panel);
		this.setSize(250, 150);
		this.setTitle("进度条");
		this.setVisible(true);
	}
	
	public void setProgressValue(int v, int speed, long lastTime){
		progressBar.setValue(v);
		speedLab.setText("速度：" + speed +"KB/s");
		lastTimeLab.setText("剩余时间：" + lastTime + "s");
		System.out.println("设置进程");
	}
	
//	//测试用
//	public static void main(String[] args){
//		progressview pv = new progressview("测试");
//		pv.setProgressValue(50, 1202, 300);
//	}
}
