package br.iff.pooa21051.domesticaapp;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class EmpregadorActivity extends Activity {
	
	private EditText etCodigo;
	private EditText etNome;
	private EditText etEndereco;
	private Button btConsultar;
	private Button btSalvar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empregador);
		
		etCodigo    = (EditText) findViewById(R.id.etCodigo);
		etNome      = (EditText) findViewById(R.id.etNome);
		etEndereco  = (EditText) findViewById(R.id.etEndereco);
		
		btConsultar = (Button) findViewById(R.id.btConsutar);
		
		btSalvar    = (Button) findViewById(R.id.btSalvar);
	}
}
