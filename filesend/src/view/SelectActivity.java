package view;

import com.example.filesend.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SelectActivity extends Activity {
	private ImageButton HardDiskBtn, SDCardBtn;
	private SelectActivity selectActivity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);

		HardDiskBtn = (ImageButton) findViewById(R.id.harddiskimageButton);
		SDCardBtn = (ImageButton) findViewById(R.id.sdimageButton);

		HardDiskBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//���Զ˵Ļ�ȡ
				Intent intent = new Intent(selectActivity, initHardDiskActivity.class);
				selectActivity.startActivity(intent);
				selectActivity.finish();
				
			}
		});

		SDCardBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//�ֻ������ļ��ķ���
				Intent intent = new Intent(selectActivity,
						SDCardFileExplorerActivity.class);
				selectActivity.startActivity(intent);
				selectActivity.finish();
				
			}
		});
	}
}
