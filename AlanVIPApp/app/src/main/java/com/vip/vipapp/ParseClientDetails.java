package com.vip.vipapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseClientDetails extends AsyncTask<Void, Void, Void> {

	String url;
	String maintxt, subtxt, uri, phone,search,email,shrt_name;
	Activity c;

	public ParseClientDetails(String s,Activity context) {
		// TODO Auto-generated constructor stub

		url = s;
		c=context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Showing progress dialog

	}

	public String[] getDetails() {
		String s[] = new String[10];
		s[0] = maintxt;
		s[1] = subtxt;
		s[2] = uri;
		s[3] = phone;
		if(search != null && search.contains("\r\n"))
		search=search.replaceAll("\r\n", "#");
		s[4]= search;
        s[5]=email;
        s[6]=shrt_name;
		return s;

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// Creating service handler class instance
		try{
			Parser parser = new Parser();
		JSONArray json = parser.getJSONData(url);
		// value = json.getJSONArray("Value");

			JSONObject c = json.getJSONObject(0);
			Log.d("","Json Object:"+c );
			maintxt = c.getString("main");
			subtxt = c.getString("sub");
			uri = c.getString("web");
			phone = c.getString("phone");
			search=c.getString("search");
			email=c.getString("email");
            shrt_name=c.getString("short_name");
			publishProgress();
			// onProgressUpdate();
			Log.d("YE h", "yo " + c);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*
		 * Parser parser = new Parser(); JSONObject json =
		 * parser.getJSONDat(url); // Log.d("","json:"+json); try { JSONArray
		 * hh=json.getJSONArray("messages"); JSONObject a=hh.getJSONObject(0);
		 * 
		 * 
		 * 
		 * 
		 * JSONObject jObject = a.getJSONObject("message");
		 * jObject.getString("body_rich");
		 * Log.d("","title:\n"+a.getJSONObject("message"));
		 * 
		 * } catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		return null;

	}

	@Override
	protected void onProgressUpdate(Void... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		
		
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		getDetails();
		((MainActivity)c).showDetails();
	}

}
