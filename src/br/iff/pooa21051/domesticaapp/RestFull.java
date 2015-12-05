package br.iff.pooa21051.domesticaapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * @author lglmoura
 *
 */
public class RestFull {

	public static String POST = "Post";
	public static String PUT = "Put";
	public static String DELETAR = "Delete";
	public static String GET = "Get";

	public static JSONObject access(String url, String method, JSONObject params) {

		JSONObject jObj = null;
		InputStream is = null;
		DefaultHttpClient httpClient = new DefaultHttpClient();

		HttpRequestBase http = null;

		try {

			if (method.equalsIgnoreCase(POST)) {
				http = (HttpPost) new HttpPost(url);
				((HttpPost) http).setEntity(new StringEntity("UTF-8"));
				((HttpPost) http)
						.setEntity(new StringEntity(params.toString()));
			}

			if (method.equalsIgnoreCase(PUT)) {
				http = (HttpPut) new HttpPut(url);
				((HttpPut) http).setEntity(new StringEntity("UTF-8"));
				((HttpPut) http).setEntity(new StringEntity(params.toString()));
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (method.equalsIgnoreCase(GET)) {

			http = (HttpGet) new HttpGet(url);

		}
		if (method.equalsIgnoreCase(DELETAR)) {

			http = (HttpDelete) new HttpDelete(url);

		}

		http.setHeader("Content-Type", "application/json");
		http.setHeader("Accept", "application/json");

		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(http);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (!method.equalsIgnoreCase(DELETAR)) {
			HttpEntity httpEntity = httpResponse.getEntity();
			try {
				is = httpEntity.getContent();
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		StringBuilder sb = new StringBuilder();
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(sb.toString());
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data TEST " + e.toString());
		}

		// return JSON String
		return jObj;

	}
}
