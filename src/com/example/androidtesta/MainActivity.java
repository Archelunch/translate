package com.example.androidtesta;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MyTask().execute("house of the rising sun");
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    class MyTask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
		String res = "Error";
		HttpURLConnection connection;	
		try {
			 connection = (HttpURLConnection) new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20160413T144240Z.5abb49ecf249570d.150c5c84d647042c96740644e82f341104bc500a&lang=ru&text="+params[0]).openConnection();
				
				int result = connection.getResponseCode();
			InputStream in =	connection.getInputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int r = 1;
			while(r != -1){
				r = in.read();
				 if(r!=-1) bos.write(r);
			}
				res = new String(bos.toByteArray());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
		}
    	@Override
    	protected void onPostExecute(String result){
    		super.onPostExecute(result);
    		TextView tv = (TextView)findViewById(R.id.text);
    		tv.setText(result);
    		try {
				JSONObject obj = new JSONObject(result);
				String as = obj.getString("text");
				
				tv.setText(as.substring(2, as.length() - 2));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	@Override
    	protected void onPreExecute(){
    		super.onPreExecute();
    		
    	}
    	
    }
}
