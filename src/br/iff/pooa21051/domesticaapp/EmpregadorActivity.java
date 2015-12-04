package br.iff.pooa21051.domesticaapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmpregadorActivity extends Activity {

	private EditText etCodigo;
	private EditText etNome;
	private EditText etEndereco;
	private Button btConsultar;
	private Button btSalvar;
	private Button btLimpar;
	private Button btDeletar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empregador);

		etCodigo = (EditText) findViewById(R.id.etCodigo);
		etNome = (EditText) findViewById(R.id.etNome);
		etEndereco = (EditText) findViewById(R.id.etEndereco);

		btConsultar = (Button) findViewById(R.id.btConsutar);
		btConsultar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String filtro = etCodigo.getText().toString();
				if (!filtro.equalsIgnoreCase("")) {

					gettInformationtoAPI();

				}
			}

		});

		btSalvar = (Button) findViewById(R.id.btSalvar);

		btSalvar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(etCodigo.getText().toString()))

					postInformationtoAPI();

				else
					putInformationtoAPI();
			}

		});

		btLimpar = (Button) findViewById(R.id.btLimpar);
		btLimpar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				etCodigo.setText("");
				etNome.setText("");
				etEndereco.setText("");

			}
		});
		
		btDeletar = (Button) findViewById(R.id.btDeletar);
		btDeletar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				deletarInformationtoAPI();

			}
		});
	}

	private void deletarInformationtoAPI(){
		
		Log.i("Deletar====", "Deletar ORDER");

		JSONObject params = new JSONObject();

		EmpregadorTask bgtDel = new EmpregadorTask(
				"http://192.168.0.52:3000/empregadors/"
						+ etCodigo.getText().toString() + ".json", "DELETAR",
				params);
		bgtDel.execute();
		
	}
	private void gettInformationtoAPI() {

		Log.i("Get====", "GETTING ORDER");

		JSONObject params = new JSONObject();

		EmpregadorTask bgtGet = new EmpregadorTask(
				"http://192.168.0.52:3000/empregadors/"
						+ etCodigo.getText().toString() + ".json", "GET",
				params);
		bgtGet.execute();

	}

	private void postInformationtoAPI() {

		Log.i("Post====", "POSTING ORDER");

		JSONObject params = new JSONObject();

		try {
			params.put("nome", etNome.getText().toString());
			params.put("endereco", etEndereco.getText().toString());
			params.put("numero", "adadf");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EmpregadorTask bgtPost = new EmpregadorTask(
				"http://192.168.0.52:3000/empregadors", "POST", params);
		bgtPost.execute();

	}

	private void putInformationtoAPI() {

		Log.i("Put====", "PUT ORDER");

		JSONObject params = new JSONObject();

		try {
			params.put("nome", etNome.getText().toString());
			params.put("endereco", etEndereco.getText().toString());
			params.put("numero", "adadf");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EmpregadorTask bgtPut = new EmpregadorTask(
				"http://192.168.0.52:3000/empregadors/"
						+ etCodigo.getText().toString() + ".json", "PUT",
				params);
		bgtPut.execute();

	}

	public class EmpregadorTask extends AsyncTask<String, String, JSONObject> {

		String URL = null;
		String method = null;

		InputStream is = null;
		JSONObject jObj = null;
		JSONObject params1 = null;
		String json = "";
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(EmpregadorActivity.this);
			dialog.show();
		}

		@Override
		protected void onPostExecute(JSONObject empregador) {

			if (empregador != null) {

				try {
					etCodigo.setText(empregador.getString("id"));
					etNome.setText(empregador.getString("nome"));
					etEndereco.setText(empregador.getString("endereco"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			dialog.dismiss();
		}

		public EmpregadorTask(String url, String method, JSONObject params1) {
			this.URL = url;

			this.method = method;
			this.params1 = params1;

		}

		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			// Making HTTP request

			try {
				// Making HTTP request
				// check for request method

				if (method.equals("POST")) {
					// request method is POST
					// defaultHttpClient

					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(URL);
					httpPost.setEntity(new StringEntity("UTF-8"));
					StringEntity entity = new StringEntity(params1.toString());
					httpPost.setEntity(entity);

					httpPost.setHeader("Content-Type", "application/json");
					httpPost.setHeader("Accept", "application/json");

					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();

				} else if (method.equals("DELETAR")) {
					// request method is POST
					// defaultHttpClient

					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpDelete httpDel = new HttpDelete(URL);
										
					httpDel.setHeader("Content-Type", "application/json");
					httpDel.setHeader("Accept", "application/json");

					HttpResponse httpResponse = httpClient.execute(httpDel);
					HttpEntity httpEntity = httpResponse.getEntity();
					//is = httpEntity.getContent();

				} else if (method.equals("PUT")) {
					// request method is POST
					// defaultHttpClient

					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPut httpPut = new HttpPut(URL);
					httpPut.setEntity(new StringEntity("UTF-8"));
					StringEntity entity = new StringEntity(params1.toString());
					httpPut.setEntity(entity);

					httpPut.setHeader("Content-Type", "application/json");
					httpPut.setHeader("Accept", "application/json");

					HttpResponse httpResponse = httpClient.execute(httpPut);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();

				} else if (method == "GET") {
					// request method is GET
					DefaultHttpClient httpClient = new DefaultHttpClient();

					HttpGet httpGet = new HttpGet(URL);

					HttpResponse httpResponse = httpClient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				Log.i("Logging out *is* before beffered reader", is.toString());
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "utf-8"), 8);
				Log.i("Logging out *is* after beffered reader", is.toString());
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				json = sb.toString();
				// Log.i("json: ",json);
			} catch (Exception e) {
				Log.e("Buffer Error", "Error converting result " + e.toString());
			}
			// try parse the string to a JSON object
			try {
				jObj = new JSONObject(json);
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data TEST " + e.toString());
			}

			// return JSON String
			return jObj;

		}
	}

}
