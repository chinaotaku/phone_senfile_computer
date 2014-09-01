package view;

import com.example.filesend.R;

import controller.commondata;
import controller.commonfactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class initHardDiskActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initharddisk);
		commonfactory.setinitHardDiskActivity(this);
		commonfactory.getFileThread().sendMsg("SEND" + commondata.getSpilt() + "INIT");
	}
	
	public void changeActivity(){
		Intent intent = new Intent(this, HardDiskFileExplorerActivity.class);
		this.startActivity(intent);
		this.finish();
		System.out.println("³õÊ¼»¯Íê±Ï");
	}
}
