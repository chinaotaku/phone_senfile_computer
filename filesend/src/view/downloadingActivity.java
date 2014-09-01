package view;

import com.example.filesend.R;

import controller.commonfactory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class downloadingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downloading);
		commonfactory.setdownloadingActivity(this);
	}

	public void changeActivity() {
		Intent intent = new Intent(this, SDCardFileExplorerActivity.class);
		this.startActivity(intent);
		this.finish();
		System.out.println("œ¬‘ÿπÿ±’");
	}

}
