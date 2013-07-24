package com.alvin.contacto;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends BaseActivity implements NetworkActionResult {

	List<Contacts> contactList = new ArrayList<Contacts>();
	EditText txt_search;
	NetworkInterface ni;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ni = new NetworkInterface(MainActivity.this, MainActivity.this);
		txt_search = (EditText) findViewById(R.id.txtSearch);
		((ImageView) findViewById(R.id.addContact)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(getApplicationContext(), AddEditActivity.class).putExtra("action", "add"));
			}
		});
		((ImageView) findViewById(R.id.imgSearch)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//new AsyncCallSearch().execute();
				String search = txt_search.getText().toString().trim();
				showDialog(1);
				ni.getHttpRequest(2,"http://katibajyo.com/api/contacto.php/?action=search&q="+search);
			}
		});
		//new AsyncCall().execute();
		showDialog(1);
		ni.getHttpRequest(1,"http://katibajyo.com/api/contacto.php/?action=list");
	}
	/*
	public class AsyncCall extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			ServerRequest request = new ServerRequest();
			String response  = request.requestGetHttp("http://katibajyo.com/api/contacto.php/?action=list");
			return response;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissDialog(1);
			postResponse(result);
			
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(1);
		}
		
	}
	public class AsyncCallSearch extends AsyncTask<Void, Void, String>{
		@Override
		protected String doInBackground(Void... params) {
			String search = txt_search.getText().toString().trim();
			ServerRequest request = new ServerRequest();
			String response  = request.requestGetHttp("http://katibajyo.com/api/contacto.php/?action=search&q="+search);
			return response;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissDialog(1);
			postResponse(result);
			
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog(1);
		}
		
	}
	*/
	private void postResponse(String response) {
		printf("response = " + response);
		try {
			JSONObject jObj = new JSONObject(response);
			
			String res = jObj.getString("res");
			printf("res = " + res);
			JSONArray jArr = jObj.getJSONArray("data");
			contactList.clear();
			//contactList = new ArrayList<Contacts>();
			for (int i = 0; i < jArr.length(); i++) {
				JSONObject conObj = jArr.getJSONObject(i);
				int id  = Integer.valueOf(conObj.getString("id"));
				String username  = conObj.getString("username");
				String fname  = conObj.getString("c_fname");
				String lname  = conObj.getString("c_lname");
				String mobile  = conObj.getString("n_mobile");
				String home  = conObj.getString("n_home");
				String office  = conObj.getString("n_office");
				String timestamp  = conObj.getString("created");
				Contacts c = new Contacts(id, username, fname, lname, mobile, home, office, timestamp);
				contactList.add(c);
				traceLog("contact inserted with id = "+ id);
			}
		}
		catch (JSONException e) {e.printStackTrace();}
		
		printf("Total contact list is " + contactList.size());
		
		List<String> nameList = new ArrayList<String>();
		for (int i = 0; i < contactList.size(); i++) {
			Contacts c = contactList.get(i);
			nameList.add(c.getFname() + " " + c.getLname());
		}
		
		ListView lv = (ListView) findViewById(R.id.listView);
        // Binding Array to ListAdapter
        //lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, nameList));
		lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.row_contact, R.id.txt_name, nameList){
			@Override
			public View getView (int position, View v, ViewGroup parent){
				if(v == null)
					v = getLayoutInflater().inflate(R.layout.row_contact, null);
				Contacts c = contactList.get(position);
				TextView tvn = (TextView) v.findViewById(R.id.txt_name);
				tvn.setText(c.getFname() + " " + c.getLname());
				((TextView) v.findViewById(R.id.txt_number)).setText(c.getMobile());
				
				return v;
			}
		});
        
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				Contacts c = contactList.get(position);
				Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
				intent.putExtra("id", String.valueOf(c.getId()));
				intent.putExtra("fname", c.getFname());
				intent.putExtra("lname", c.getLname());
				intent.putExtra("mobile", c.getMobile());
				intent.putExtra("home", c.getHome());
				intent.putExtra("office", c.getOffice());
				startActivity(intent);
			}
		});
		
	}


	private void printf(String string) {
		Log.i("Contacto", string);
	}


	@SuppressWarnings("deprecation")
	@Override
	public void onComplete(int type, String result) {
		dismissDialog(1);
		postResponse(result);
	}

}
