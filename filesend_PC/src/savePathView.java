import controller.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class savePathView extends JFrame implements ActionListener {
	private JLabel filePathLab, informLab;
	private JTextField textField;
	private JButton okBtn, cancelBtn, fileChoseBtn;
	private JPanel jPanel;
	private JFileChooser jFileChooser;
	private savePathView savePathView = this;
	private String state = null;

	public savePathView(String inform) {
		commonfactory.setSavePathView(savePathView);
		jFileChooser = new JFileChooser();
		informLab = new JLabel(inform);
		filePathLab = new JLabel("存放路径:");
		textField = new JTextField();
		okBtn = new JButton("确定");
		cancelBtn = new JButton("取消");

		fileChoseBtn = new JButton("路径选择");
		jPanel = new JPanel();

		okBtn.addActionListener(this);
		fileChoseBtn.addActionListener(this);

		informLab.setBounds(20, 20, 360, 30);
		filePathLab.setBounds(20, 80, 130, 30);
		textField.setBounds(100, 80, 130, 30);
		fileChoseBtn.setBounds(240, 80, 100, 30);
		okBtn.setBounds(80, 120, 80, 30);
		cancelBtn.setBounds(200, 120, 100, 30);

		jPanel.setLayout(null);
		jPanel.add(informLab);
		jPanel.add(filePathLab);
		jPanel.add(textField);
		jPanel.add(okBtn);
		jPanel.add(fileChoseBtn);
		jPanel.add(cancelBtn);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(jPanel, BorderLayout.CENTER);

		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenDim.width - 350) / 2,
				(screenDim.height - 250) / 2); // 屏幕居中

		this.setTitle("文件传输");
		this.setSize(400, 200);

		this.setVisible(true);
	}

	public String getFilePath() {
		return textField.getText();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("确定")) {
			state = "OK";
		} else if (arg0.getActionCommand().equals("路径选择")) {
			jFileChooser.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);  
			jFileChooser.showDialog(commonfactory.getMainview(), null);
			
			textField.setText(jFileChooser.getSelectedFile().toString());
		} else if (arg0.getActionCommand().equals("取消")) {
			state = "CANCEL";
		}
	}

	public String choseState() {
		return state;
	}

//	 测试用
//	 public static void main(String[] args){
//	 savePathView sv = new savePathView("测试");
//	 }
}
