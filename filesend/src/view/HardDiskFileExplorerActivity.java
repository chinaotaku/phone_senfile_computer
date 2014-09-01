package view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.filesend.R;

import controller.commondata;
import controller.commonfactory;
import android.R.string;
import android.app.Activity;
import android.app.LauncherActivity.ListItem;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HardDiskFileExplorerActivity extends Activity {
	private HardDiskFileExplorerActivity hardDiskFileExplorerActivity = this;

	private TextView tvpath;
	private ListView lvFiles;
	private Button btnParent;
	private final String pathSplit = "||";
	private String[] path; // ����ϵͳ�ĸ�·��������windows ����ָ���Ǵ��̣�

	// ��¼��ǰ�ĸ��ļ���
	File currentParent;

	// ��¼��ǰ·���µ������ļ��е��ļ�����
	File[] currentFiles;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		commonfactory.setHardDiskFileExplorerActivity(this);
		lvFiles = (ListView) this.findViewById(R.id.files);

		tvpath = (TextView) this.findViewById(R.id.tvpath);
		btnParent = (Button) this.findViewById(R.id.btnParent);

		init();
		
		lvFiles.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// ����û��������Ƿ���
				if (currentFiles.length <= position) {
					returnParent();
					return;
				}

				// ����û��������ļ���ֱ�ӷ��أ������κδ���
				if (currentFiles[position].isFile()) {
					// Ҳ���Զ�����չ������ļ���
					commondata.setFilePath(currentFiles[position].getPath());
					commonfactory.getFileThread().sendFile(
							commondata.getFilePath());
					return;
				}
				// ��ȡ�û�������ļ��� �µ������ļ�
				File[] tem = currentFiles[position].listFiles();
				if (tem == null || tem.length == 0) {

					Toast.makeText(HardDiskFileExplorerActivity.this,
							"��ǰ·�����ɷ��ʻ��߸�·����û���ļ�", Toast.LENGTH_LONG).show();
				} else {
					// ��ȡ�û��������б����Ӧ���ļ��У���Ϊ��ǰ�ĸ��ļ���
					currentParent = currentFiles[position];
					// ���浱ǰ�ĸ��ļ����ڵ�ȫ���ļ����ļ���
					currentFiles = tem;
					// �ٴθ���ListView
					inflateListView(currentFiles);
				}

			}
		});
	}

	/**
	 * ��ʼ������
	 * 
	 */
	private void init() {
		path = commondata.getallPath().split(pathSplit);

		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (String str : path) {
			Map<String, Object> listItem = new HashMap<String, Object>();

			listItem.put("icon", R.drawable.folder);

			listItem.put("filename", str);

			// ����һ������޸�����
			listItem.put("modify", "�޸����ڣ�" + "");
			
			listItems.add(listItem);
		}
		
		// ����һ��SimpleAdapter
		SimpleAdapter adapter = new SimpleAdapter(
				HardDiskFileExplorerActivity.this, listItems,
				R.layout.list_item,
				new String[] { "filename", "icon", "modify" }, new int[] {
						R.id.file_name, R.id.icon, R.id.file_modify });

		// ������ݼ�
		lvFiles.setAdapter(adapter);
	}

	/**
	 * �����ļ������ListView
	 * 
	 * @param files
	 */
	private void inflateListView(File[] files) {

		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < files.length; i++) {

			Map<String, Object> listItem = new HashMap<String, Object>();

			if (files[i].isDirectory()) {
				// ������ļ��о���ʾ��ͼƬΪ�ļ��е�ͼƬ
				listItem.put("icon", R.drawable.folder);
			} else {
				listItem.put("icon", R.drawable.file);
			}
			// ����һ���ļ�����
			listItem.put("filename", files[i].getName());

			File myFile = new File(files[i].getName());

			// ��ȡ�ļ�������޸�����
			long modTime = myFile.lastModified();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			System.out.println(dateFormat.format(new Date(modTime)));

			// ����һ������޸�����
			listItem.put("modify",
					"�޸����ڣ�" + dateFormat.format(new Date(modTime)));

			listItems.add(listItem);

		}
		Map<String, Object> listItem = new HashMap<String, Object>();
		listItem.put("icon", R.drawable.back_48);

		listItems.add(listItem);
		// ����һ��SimpleAdapter
		SimpleAdapter adapter = new SimpleAdapter(
				HardDiskFileExplorerActivity.this, listItems,
				R.layout.list_item,
				new String[] { "filename", "icon", "modify" }, new int[] {
						R.id.file_name, R.id.icon, R.id.file_modify });

		// ������ݼ�
		lvFiles.setAdapter(adapter);

		try {
			tvpath.setText("��ǰ·��Ϊ:" + currentParent.getCanonicalPath());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void returnParent() {
		try {

			if (!currentParent.getCanonicalPath().equals("/mnt/sdcard")) {

				// ��ȡ��һ��Ŀ¼
				currentParent = currentParent.getParentFile();
				// �г���ǰĿ¼�µ������ļ�
				currentFiles = currentParent.listFiles();
				// �ٴθ���ListView
				inflateListView(currentFiles);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}