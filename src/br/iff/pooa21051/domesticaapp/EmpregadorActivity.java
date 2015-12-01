package br.iff.pooa21051.domesticaapp;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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

					new EmpregadorTask().execute("empregadors/" + filtro
							+ ".json");

				}
			}

		});

		btSalvar = (Button) findViewById(R.id.btSalvar);

		btSalvar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

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
	}

	private class EmpregadorTask extends AsyncTask<String, Void, String[]> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(EmpregadorActivity.this);
			dialog.show();
		}

		@Override
		protected void onPostExecute(String[] result) {
			if (result != null) {
				etCodigo.setText(result[0]);
				etNome.setText(result[1]);
				etEndereco.setText(result[2]);

			}

			dialog.dismiss();
		}

		@Override
		protected String[] doInBackground(String... params) {

			try {
				String filtro = params[0];
				if (TextUtils.isEmpty(filtro)) {
					return null;
				}

				String conteudo = HTTPUtils.acessar(filtro);

				String[] dadosEmpregador = new String[4];
				if (!conteudo.equalsIgnoreCase("")) {

					JSONObject professor = new JSONObject(conteudo);

					dadosEmpregador[0] = professor.getString("id");
					dadosEmpregador[1] = professor.getString("nome");
					dadosEmpregador[2] = professor.getString("endereco");

				}
				return dadosEmpregador;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

	}
}
