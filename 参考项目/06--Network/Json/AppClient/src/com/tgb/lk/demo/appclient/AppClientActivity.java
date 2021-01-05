package com.tgb.lk.demo.appclient;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.tgb.lk.demo.model.Student;
import com.tgb.lk.demo.util.NetTool;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AppClientActivity extends Activity {
	private TextView tvData = null;
	private Button btnTxt = null;
	private Button btnGet = null;
	private Button btnPost = null;
	private Button btnHttpClient = null;
	private Button btnUploadFile = null;
	private Button btnReadTxtFile = null;
	private Button btnDownloadFile = null;
	
	//需要将下面的IP改为服务器端IP
	private String txtUrl = "http://192.168.1.46:8080/AppServer/SynTxtDataServlet";
	private String url = "http://192.168.1.46:8080/AppServer/SynDataServlet";
	private String uploadUrl = "http://192.168.1.46:8080/AppServer/UploadFileServlet";
	private String fileUrl = "http://192.168.1.46:8080/AppServer/file.jpg";
	private String txtFileUrl = "http://192.168.1.46:8080/AppServer/txtFile.txt";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		tvData = (TextView) findViewById(R.id.tvData);
		btnTxt = (Button) findViewById(R.id.btnTxt);
		btnGet = (Button) findViewById(R.id.btnGet);
		btnPost = (Button) findViewById(R.id.btnPost);
		btnHttpClient = (Button) findViewById(R.id.btnHttpClient);
		btnUploadFile = (Button) findViewById(R.id.btnUploadFile);
		btnReadTxtFile = (Button) findViewById(R.id.btnReadTxtFile);
		btnDownloadFile = (Button) findViewById(R.id.btnDownloadFile);

		btnTxt.setOnClickListener(btnListener);
		btnGet.setOnClickListener(btnListener);
		btnPost.setOnClickListener(btnListener);
		btnHttpClient.setOnClickListener(btnListener);
		btnUploadFile.setOnClickListener(btnListener);
		btnReadTxtFile.setOnClickListener(btnListener);
		btnDownloadFile.setOnClickListener(btnListener);

	}

	OnClickListener btnListener = new OnClickListener() {
		String retStr = "";

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnTxt:
				Student student = new Student();
				student.setId(1);
				student.setName("李坤");
				student.setClasses("五期信息技术提高班");
				Gson gson = new Gson();
				String jsonTxt = gson.toJson(student);
				try {
					retStr = NetTool.sendTxt(txtUrl, jsonTxt,"UTF-8");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				break;
			case R.id.btnGet:
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", "李坤");
				map.put("age", "26");
				map.put("classes", "五期信息技术提高班");
				try {
					retStr = NetTool.sendGetRequest(url, map, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.btnPost:
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("name", "李坤");
				map2.put("age", "26");
				map2.put("classes", "五期信息技术提高班");
				try {
					retStr = NetTool.sendPostRequest(url, map2, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.btnHttpClient:
				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("name", "李坤");
				map3.put("age", "26");
				map3.put("classes", "五期信息技术提高班");
				try {
					retStr = NetTool.sendHttpClientPost(url, map3, "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.btnUploadFile:
				// 需要在sdcard中放一张image.jsp的图片,本例才能正确运行
				try {
					retStr = NetTool.sendFile(uploadUrl, "/sdcard/image.jpg",
							"image1.jpg");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.btnReadTxtFile:
				try {
					//本例中服务器端的文件类型是UTF-8
					retStr = NetTool.readTxtFile(txtFileUrl, "UTF-8");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			case R.id.btnDownloadFile:
				try {
					NetTool.downloadFile(fileUrl, "/download", "newfile.jpg");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
			tvData.setText(retStr);
		}
	};
}