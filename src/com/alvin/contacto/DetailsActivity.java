package com.alvin.contacto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends BaseActivity implements NetworkActionResult{

	String id = "", fname = "", lname = "", mobile = "", home = "", office = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		//String id = "", fname = "", lname = "", mobile = "", home = "", office ="";
		
		try{
			id = getIntent().getExtras().getString("id");
			fname = getIntent().getExtras().getString("fname");
			lname = getIntent().getExtras().getString("lname");
			mobile = getIntent().getExtras().getString("mobile");
			home = getIntent().getExtras().getString("home");
			office = getIntent().getExtras().getString("office");
			
		}
		catch(Exception e){}
		
		((TextView) findViewById(R.id.txt_name)).setText(fname + " " + lname);
		((TextView) findViewById(R.id.txt_mobile)).setText(mobile +" ");	
		((TextView) findViewById(R.id.txt_home)).setText(home +" ");	
		((TextView) findViewById(R.id.txt_office)).setText(office +" ");
		
		((Button) findViewById(R.id.btn_edit)).setOnClickListener(lis_edit);
		((Button) findViewById(R.id.btn_delete)).setOnClickListener(lis_delete);
	}
	OnClickListener lis_edit = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(getApplicationContext(), AddEditActivity.class);
			intent.putExtra("id", id);
			intent.putExtra("action", "edit");
			intent.putExtra("fname", fname);
			intent.putExtra("lname", lname);
			intent.putExtra("mobile", mobile);
			intent.putExtra("home", home);
			intent.putExtra("office", office);
			startActivity(intent);
			finish();
		}
	};
	OnClickListener lis_delete = new OnClickListener() {
		
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View arg0) {
			//new AsyncDelete().execute();
			NetworkInterface ni = new NetworkInterface(getApplicationContext(), DetailsActivity.this);
			String parameters = "id=" + id;
			ni.getHttpRequest(1, "http://katibajyo.com/api/contacto.php/?action=delete&"+parameters);
			showDialog(1);
			//finish();
		}
	};
	/*
	public class AsyncDelete extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			String parameters = "id=" + id;
			
			ServerRequest request = new ServerRequest();
			String response  = request.requestGetHttp("http://katibajyo.com/api/contacto.php/?action=delete&"+parameters);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			showToast(result);
			finish();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
	}
*/
	@SuppressWarnings("deprecation")
	@Override
	public void onComplete(int type, String result) {
		dismissDialog(1);
		showToast(result);
		finish();
	}

}
