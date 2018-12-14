package com.vip.vipapp;

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

public class SendIntialInfo {

	static String device_token;
	static String client_id;

	public SendIntialInfo(String device_token, String client_id) {
		SendIntialInfo.device_token = device_token;
		// TODO Auto-generated constructor stub
		SendIntialInfo.client_id = client_id;
//
		new HttpAsyncTask()
				.execute(GlobalArrayList.BASE_URL + "app_users/adduser.json?");
//		new HttpAsyncTask()
//				.execute("http://d9081a55.ngrok.io/app_users/adduser.json?");
//		new HttpAsyncTask()
//				.execute("http://192.168.1.31/app_users/adduser.json?");
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

				String jsonString = "";

				// 3. build jsonObject
				JSONObject jsonObject = new JSONObject();

				jsonObject.accumulate("devicetoken", device_token);
				jsonObject.accumulate("clientid", client_id);
				jsonObject.accumulate("is_ipad", "2");

				// 4. convert JSONObject to JSON to String
				jsonString = jsonObject.toString();

				// 5. set json to StringEntity
				StringEntity stringEntity = new StringEntity(jsonString);

				// 6. set httpPost Entity
				httpPost.setEntity(stringEntity);

				// 7. Set some headers to inform server about the type of the
				// content
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-type", "application/json");

				// 8. Execute POST request to the given URL
				HttpResponse httpResponse = httpclient.execute(httpPost);

				// 9. receive response as inputStream
				inputStream = httpResponse.getEntity().getContent();

				// 10. convert inputstream to string
				if (inputStream != null) {
					result = convertInputStreamToString(inputStream);
					Log.i("", "work for me" + result);
				} else
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
