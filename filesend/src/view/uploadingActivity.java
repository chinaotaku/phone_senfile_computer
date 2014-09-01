package view;

import com.example.filesend.R;

import controller.commonfactory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class uploadingActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploading);
		commonfactory.setUpLoadingActivity(this);
	}

	public void changeActivity() {
		Intent intent = new Intent(this, SDCardFileExplorerActivity.class);
		this.startActivity(intent);
		this.finish();
		System.out.println("иоть╧ь╠у");
	}
}
