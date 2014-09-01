import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class progressview extends JFrame{
	private JProgressBar progressBar;
	private JLabel progressLab, speedLab, lastTimeLab;
	
	public progressview(String title){
		progressBar = new JProgressBar();
		progressLab = new JLabel(title + "����");
		speedLab = new JLabel("�ٶȣ�" + "--KB/s");
		lastTimeLab = new JLabel("ʣ��ʱ�䣺" + "--s");
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
		this.setTitle("������");
		this.setVisible(true);
	}
	
	public void setProgressValue(int v, int speed, long lastTime){
		progressBar.setValue(v);
		speedLab.setText("�ٶȣ�" + speed +"KB/s");
		lastTimeLab.setText("ʣ��ʱ�䣺" + lastTime + "s");
		System.out.println("���ý���");
	}
	
//	//������
//	public static void main(String[] args){
//		progressview pv = new progressview("����");
//		pv.setProgressValue(50, 1202, 300);
//	}
}
