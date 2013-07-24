package com.alvin.contacto;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddEditActivity extends BaseActivity {

	EditText ed_username, ed_fname, ed_lname, ed_mobile, ed_home, ed_office;
	String id = "", fname = "", lname = "", mobile = "", home = "", office ="", action = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		ed_username = (EditText) findViewById(R.id.txt_username);
		ed_fname = (EditText) findViewById(R.id.txt_fname);
		ed_lname = (EditText) findViewById(R.id.txt_lname);
		ed_mobile = (EditText) findViewById(R.id.txt_mobile);
		ed_home = (EditText) findViewById(R.id.txt_home);
		ed_office = (EditText) findViewById(R.id.txt_office);
		
		try{
			action = getIntent().getExtras().getString("action");
			if(action.equals("edit")){
				id = getIntent().getExtras().getString("id");
				fname = getIntent().getExtras().getString("fname");
				lname = getIntent().getExtras().getString("lname");
				mobile = getIntent().getExtras().getString("mobile");
				home = getIntent().getExtras().getString("home");
				office = getIntent().getExtras().getString("office");
			}
		}
		catch(Exception e){}
		
		ed_fname.setText(fname);
		ed_lname.setText(lname);
		ed_mobile.setText(mobile);
		ed_home.setText(home);
		ed_office.setText(office);
		
		((Button) findViewById(R.id.btn_save)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(action.equals("add"))
					new AsyncCallAdd().execute();
				else
					new AsyncCallEdit().execute();
			}
		});
	}

	public class AsyncCallAdd extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			String username = ed_username.getText().toString().trim();
			String fname = ed_fname.getText().toString().trim();
			String lname = ed_lname.getText().toString().trim();
			String mobile = ed_mobile.getText().toString().trim();
			String home = ed_home.getText().toString().trim();
			String office = ed_office.getText().toString().trim();
		
			String parameters = "username="+username+"&c_fname="+fname+"&c_lname="+lname+"&n_mobile="+mobile+"&n_home="+home+"&n_office="+office;
			
			ServerRequest request = new ServerRequest();
			String response  = request.requestGetHttp("http://katibajyo.com/api/contacto.php/?action=add&"+parameters);
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

	public class AsyncCallEdit extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			String username = ed_username.getText().toString().trim();
			String fname = ed_fname.getText().toString().trim();
			String lname = ed_lname.getText().toString().trim();
			String mobile = ed_mobile.getText().toString().trim();
			String home = ed_home.getText().toString().trim();
			String office = ed_office.getText().toString().trim();
		
			String parameters = "username="+username+"&c_fname="+fname+"&c_lname="+lname+"&n_mobile="+mobile+"&n_home="+home+"&n_office="+office;
			
			ServerRequest request = new ServerRequest();
			String response  = request.requestGetHttp("http://katibajyo.com/api/contacto.php/?action=edit&id="+id+"&"+parameters);
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
}
