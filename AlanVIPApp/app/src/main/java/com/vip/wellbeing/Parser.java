package com.vip.wellbeing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Parser {
	

    static InputStream is = null;
    static JSONArray jObj = null;  
    static JSONObject jOb = null;
    static String json = "";
    public JSONArray getJSONData(String url){
            try{
                        DefaultHttpClient client = new DefaultHttpClient();
                        HttpPost post = new HttpPost(url);
                        HttpResponse response = client.execute(post);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
            }catch (Exception e) {
            e.printStackTrace();
        }
            try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
            try {
            jObj = new JSONArray(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObj;
    }
    public JSONObject getJSONDat(String url){
        try{
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    HttpResponse response = client.execute(post);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
        }catch (Exception e) {
        e.printStackTrace();
    }
        try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                is, "iso-8859-1"), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        json = sb.toString();
        
        //Log.d("","json:\n"+json);
    } catch (Exception e) {
        Log.e("Buffer Error", "Error converting result " + e.toString());
    }
        try {
        jOb = new JSONObject(json);
    } catch (Exception e) {
        Log.e("JSON Parser", "Error parsing data " + e.toString());
    }
    return jOb;
}

}
