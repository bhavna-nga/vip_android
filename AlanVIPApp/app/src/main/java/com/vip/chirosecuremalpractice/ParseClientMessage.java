package com.vip.chirosecuremalpractice;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseClientMessage extends AsyncTask<Void, Void, Void> {

	String url;
	int k = 0;int limit;
	String maintxt, subtxt, uri, phone;
	Activity c;
	List<String> msg_id = new ArrayList<String>();
	List<String> text_list = new ArrayList<String>();
	List<String> full_link = new ArrayList<String>();
	List<String> msg_list = new ArrayList<String>();
	List<String> video_type = new ArrayList<String>();

	public ParseClientMessage(String s, Activity context, int limit) {
		// TODO Auto-generated constructor stub
        this.limit=limit;
		url = s + "&startLimit=" + limit;
		Log.d("", "url" + url);
		c = context;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		// Showing progress dialog

	}

	public List<String> getVidDetails() {

		return msg_list;

	}
	public List<String> getMsgId() {

		return msg_id;

	}
	public List<String> getTextDetails() {

		return text_list;

	}

	public List<String> getFullLink() {

		return full_link;

	}

	// New Update
	public List<String> getVideoType() {

		return video_type;

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		// Creating service handler class instance
		/*
		 * Parser parser = new Parser(); JSONArray json =
		 * parser.getJSONData(url); // value = json.getJSONArray("Value"); try {
		 * JSONObject c = json.getJSONObject(0); maintxt = c.getString("main");
		 * subtxt = c.getString("sub"); uri = c.getString("web"); phone =
		 * c.getString("phone"); publishProgress(); // onProgressUpdate();
		 * Log.d("YE h", "yo " + c); } catch (JSONException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */

		try {
			Parser parser = new Parser();
		JSONObject json = parser.getJSONDat(url);
		Log.d("", "json:" + json);

			JSONArray hh = json.getJSONArray("messages");
			for (int i = 0; i < hh.length(); i++) {
				JSONObject a = hh.getJSONObject(i);
				JSONObject jObject = a.getJSONObject("message");
				// jObject.getString("body_rich");
				Log.d("","msg_id:"+jObject.getString("message_type_id"));
				msg_id.add(jObject.getString("message_type_id"));
				//New Update
				video_type.add(jObject.getString("video_type_id"));

				String g = jObject.getString("ref_url");
				if (g.length() > 0&&(jObject.getString("message_type_id").equals(GlobalArrayList.MSG_TYPE_VIDEO)||g.startsWith("http://www.youtube.com/watch?v="))) {
					full_link.add(jObject.getString("ref_url"));
					Log.d("","FULL LINK"+full_link.get(i));
					if (g.startsWith("http://www.youtube.com/watch?v="))
											msg_list.add(g.substring(g.indexOf('=') + 1, g.length()));
					else
						msg_list.add(g);
					
				} else
				{
					if(g.length()>0)
					full_link.add(jObject.getString("ref_url"));
					else
						full_link.add(jObject.getString("body_rich"));
					String h = jObject.getString("body_rich");
					h = h.replace("\n", "");
					h = h.replace("\r", "");
					h = h.replace("\t", "");
					msg_list.add(h);

				}

				String title = jObject.getString("title");
				text_list.add(title);
			}/*
			 * if (Integer.parseInt(jObject.getString("message_type_id")) == 3)
			 * { String g = jObject.getString("body_rich"); g = g.replace("\n",
			 * ""); g = g.replace("\r", ""); g = g.replace("\t", "");
			 * text_list.add(g); } msg_id_list.add(Integer.parseInt(jObject
			 * .getString("message_type_id")));
			 */
			// Log.d("", "title:\n"+vid_list.get(k++));

		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();

		}
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

		getVidDetails();
		getTextDetails();
		getFullLink();
		getMsgId();
		((MainActivity) c).getList();
		if(limit==0)
		((MainActivity) c).updateUI();

	}

}
