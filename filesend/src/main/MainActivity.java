package main;

import com.example.filesend.R;

import view.SDCardFileExplorerActivity;
import view.SelectActivity;
import controller.FileThread;
import controller.commonfactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Files;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private String ip;
	private int port;
	private EditText ipText, portText;
	private Button startBtn;
	private MainActivity mainActivity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		ipText = (EditText) findViewById(R.id.ipText);
		portText = (EditText) findViewById(R.id.portText);

		startBtn = (Button) findViewById(R.id.button1);

		startBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// 创立线程进行连接
				FileThread fileThread = new FileThread(ipText.getText()
						.toString().trim(), Integer.valueOf(portText.getText()
						.toString().trim()));
				fileThread.start();

				// 循环等待连接
				try {
					while (!fileThread.isConnected()) {
						Thread.currentThread().sleep(1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 连接上了将其放入单例
				commonfactory.setFileThread(fileThread);

				// 转到文件浏览页面
				Intent intent = new Intent(mainActivity,
						SelectActivity.class);
				mainActivity.startActivity(intent);
				mainActivity.finish();
			}
		});

		// // 测试用
		// startBtn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // 开始线程(测试用)
		// FileThread fileThread = new FileThread(ipText.getText()
		// .toString().trim(), Integer.parseInt(portText.getText()
		// .toString().trim()));
		// fileThread.start();
		// System.out.println("休眠");
		// try {
		// Thread.currentThread().sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// System.out.println("休眠结束");
		// fileThread.sendFile(Environment.getExternalStorageDirectory()
		// .getPath()
		// + "/"
		// + "TV动画[命运石之门]OP Hacking to the Gate.mp4");
		// }
		// });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
