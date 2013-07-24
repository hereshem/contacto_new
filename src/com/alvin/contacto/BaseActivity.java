package com.alvin.contacto;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

public class BaseActivity extends Activity{

	public void showToast(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
		traceLog(text);
	}
	public void traceLog(String text){
		Log.i("Contacto", text);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		         ProgressDialog dialog = new ProgressDialog(this);
		           dialog.setMessage("Loading...");
		           dialog.setIndeterminate(true);
		           dialog.setCancelable(true);
		           return dialog;
	}
	
//	public void showAlert(String text){
//		AlertDialog.Builder 
//	}
}
