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
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class mainView extends JFrame implements ActionListener {
	private JLabel filePathLab;
	private JTextField textField;
	private JButton sendBtn, fileChoseBtn;
	private JPanel jPanel;
	private JFileChooser jFileChooser;
	private mainView myMainView = this;
	
	public mainView() {
		commonfactory.setMainview(this);
		jFileChooser = new JFileChooser();
		filePathLab = new JLabel("文件的路径:");
		textField = new JTextField();
		sendBtn = new JButton("确定");
		fileChoseBtn = new JButton("文件选择");
		jPanel = new JPanel();

		sendBtn.addActionListener(this);
		fileChoseBtn.addActionListener(this);

		filePathLab.setBounds(20, 20, 130, 30);
		textField.setBounds(100, 20, 130, 30);
		fileChoseBtn.setBounds(240, 20, 100, 30);
		sendBtn.setBounds(130, 70, 70, 30);

		jPanel.setLayout(null);
		jPanel.add(filePathLab);
		jPanel.add(textField);
		jPanel.add(sendBtn);
		jPanel.add(fileChoseBtn);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(jPanel, BorderLayout.CENTER);

		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screenDim.width - 350) / 2,
				(screenDim.height - 250) / 2); // 屏幕居中
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setTitle("文件传输");
		this.setSize(400, 150);
	}

	public void notConnected() {
		textField.setEditable(false);
		sendBtn.setEnabled(false);
		fileChoseBtn.setEnabled(false);
		filePathLab.setText("还未连接");
	}

	public void connected() {
		textField.setEditable(true);
		sendBtn.setEnabled(true);
		fileChoseBtn.setEnabled(true);
		filePathLab.setText("文件的路径:");
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("确定")) {
			commonfactory.getFileThread().sendFile(textField.getText().trim());
//			commonfactory.getFileThread().sendTest(textField.getText().trim());
		} else if (arg0.getActionCommand().equals("文件选择")) {
			jFileChooser.showOpenDialog(commonfactory.getMainview());

			textField.setText(jFileChooser.getSelectedFile().toString());
		}
	}

	public String getFilePath() {
		return this.textField.getText().trim();
	}
}
