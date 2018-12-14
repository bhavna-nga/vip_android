package com.vip.chirosecuremalpractice;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class CoBrandingDetails extends AsyncTask<Void, Void, Void> {

	String uri, link;
	Activity c;
	int dpi;
	public CoBrandingDetails(Activity a, String u,int di) {
		// TODO Auto-generated constructor stub
		uri = u;
		c = a;
		dpi=di;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Showing progress dialog

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// Creating service handler class instance
		Parser parser = new Parser();
		JSONObject json = parser.getJSONDat(uri);
if(json!=null)
{	//String g;
		try {
			if(dpi<=320)
				
			
			uri = json.getString("android_300");
			else if(dpi>320 && dpi<=480)
				uri = json.getString("android_460");
			else if(dpi>480&&dpi<=540)
				uri = json.getString("android_520");
			else if(dpi>540&&dpi<=600)
				uri = json.getString("android_580");
			else if(dpi>600&&dpi<=720)
				uri = json.getString("android_700");
			else if(dpi>720)
				uri = json.getString("android_1060");
			
			link = json.getString("link");
			Log.d("YE h", "yoyo " + json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		publishProgress();
}
		return null;

	}

	public String urii() {
		return uri;
	}

	public String linkk() {
		return link;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);

	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		urii();
		linkk();
		((MainActivity) c).showCobrand();
	}

}
