package nl.svenmolhuijsen.brawser;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.Console;


public class MainActivity extends AppCompatActivity {

    Button goBttn;
    EditText adressInput;
    Tab tab;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        goBttn = (Button)findViewById(R.id.GoBttn);
        adressInput = (EditText)findViewById(R.id.AdressInput);
        tab = new Tab();
        webView = (WebView)findViewById(R.id.WebView);
        webView.getSettings().setJavaScriptEnabled(true);


        goBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("t", "er is geklikt");
                pageChange();
            }
        });
    }

    public void pageChange(){
        if(adressInput.getText().toString() != ""){
            Log.d("t", "hij is de functie ingegaan");
            String newUrl = tab.checkUrl(adressInput.getText().toString());
            webView.loadUrl(newUrl);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
