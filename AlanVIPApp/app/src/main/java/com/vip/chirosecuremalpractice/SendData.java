package com.vip.chirosecuremalpractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class SendData {

	static String name;
	static String phone;
	static String email;
	static String query;
	static String recp;

	public SendData(String name, String phone, String email, String query,String recp) {
		// TODO Auto-generated constructor stub
		SendData.name = name;
		SendData.phone = phone;
		SendData.email = email;
		
		SendData.query = query;
		SendData.recp=recp;
		//new HttpAsyncTask().execute("http://192.168.1.31/my_mailer/email_sent.json");
		new HttpAsyncTask().execute(GlobalArrayList.BASE_URL + "my_mailer/email_sent.json");
//		new HttpAsyncTask().execute("http://d9081a55.ngrok.io/my_mailer/email_sent.json");
	}

	

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			
			InputStream inputStream = null;
			String result = "";
			try {

				// 1. create HttpClient
				HttpClient httpclient = new DefaultHttpClient();

				// 2. make POST request to the given URL
				HttpPost httpPost = new HttpPost(urls[0]);

				String json = "";

				// 3. build jsonObject
				JSONObject jsonObject = new JSONObject();
				
					jsonObject.accumulate("recipient",recp);
				jsonObject.accumulate("subject", "Message");
				jsonObject.accumulate("fromemail", email);
				jsonObject.accumulate("message", query);
				// 4. convert JSONObject to JSON to String
				json = jsonObject.toString();

				// 5. set json to StringEntity
				StringEntity se = new StringEntity(json);

				// 6. set httpPost Entity
				httpPost.setEntity(se);

				// 7. Set some headers to inform server about the type of the
				// content
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-type", "application/json");

				// 8. Execute POST request to the given URL
				HttpResponse httpResponse = httpclient.execute(httpPost);
				
				// 9. receive response as inputStream
				inputStream = httpResponse.getEntity().getContent();

				// 10. convert inputstream to string
				if (inputStream != null)
					{result = convertInputStreamToString(inputStream);
					Log.d("","hfgdugdhgbdhb"+result);
					}
				else
					result = "Did not work!";

			} catch (Exception e) {
				Log.d("InputStream", "");
				e.printStackTrace();
				
			}
			return result;

			
		}

	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}
}
