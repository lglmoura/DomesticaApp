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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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

	private void deletarInformationtoAPI() {

		Log.i("Deletar====", "Deletar ORDER");

		JSONObject params = new JSONObject();

		EmpregadorTask bgtDel = new EmpregadorTask(
				"http://192.168.0.52:3000/empregadors/"
						+ etCodigo.getText().toString() + ".json",
				RestFull.DELETAR, params);
		bgtDel.execute();

	}

	private void gettInformationtoAPI() {

		Log.i("Get====", "GETTING ORDER");

		JSONObject params = new JSONObject();

		EmpregadorTask bgtGet = new EmpregadorTask(
				"http://192.168.0.52:3000/empregadors/"
						+ etCodigo.getText().toString() + ".json",
				RestFull.GET, params);
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
				"http://192.168.0.52:3000/empregadors", RestFull.POST, params);
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
						+ etCodigo.getText().toString() + ".json",
				RestFull.PUT, params);
		bgtPut.execute();

	}

	public class EmpregadorTask extends AsyncTask<String, String, JSONObject> {

		String url = null;
		String method = null;
		JSONObject params1 = null;

		ProgressDialog dialog;

		public EmpregadorTask(String url, String method, JSONObject params1) {
			this.url = url;
			this.method = method;
			this.params1 = params1;

		}

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

		@Override
		protected JSONObject doInBackground(String... params) {

			return RestFull.access(url, method, params1);

		}
	}

}
