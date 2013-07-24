package com.alvin.contacto;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class NetworkInterface {

	
	NetworkActionResult action;
	Context context;
	
	
	
	public NetworkInterface(Context context, NetworkActionResult action) {
		this.context = context;
		this.action = action;
		
	}
	
	public void getHttpRequest(int type,String url){
		//String result = request.requestGetHttp(url);
		//action.onComplete(0, result);
		
		Log.i("Contacto", "url is " + url);
		if(ServerRequest.isNetworkConnected(context)){
			new AsyncCall(type, url).execute();
		}
		else{
			Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
			action.onComplete(type, "{\"error\":\"no internet connection\"}");
		}
	}
	
	public class AsyncCall extends AsyncTask<Void, Void, String>{
		int type;
		String url;
		AsyncCall(int type, String url){
			this.type = type;
			this.url = url;
		}
		@Override
		protected String doInBackground(Void... params) {
			ServerRequest request = new ServerRequest();
			String response  = request.requestGetHttp(url);
			Log.i("Contacto", "result is " + response);
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			action.onComplete(type, result);
			
		}

		
	}
}
