/**
 * 
 */
package br.iff.pooa21051.domesticaapp;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * @author lglmoura
 * 
 */
public class HTTPUtils {

	public static String acessar(String modelo) {
		// try {
		String conteudo = null;
		String urlIFF = "http://192.168.0.52:3000/";
		//String urlIFF = "http://10.12.255.18:3000/";
		String url1 = urlIFF + modelo;

		HttpURLConnection urlConnection = null;
		;

		try {

			URL url = new URL(url1);
			urlConnection = (HttpURLConnection) url.openConnection();
			InputStream is = new BufferedInputStream(
					urlConnection.getInputStream());
			Scanner scanner = new Scanner(is);
			conteudo = scanner.useDelimiter("\\A").next();
			scanner.close();

		} catch (Exception e) {
			e.printStackTrace();
			return conteudo;
		} finally {
			urlConnection.disconnect();
		}

		return conteudo;

	}
}
