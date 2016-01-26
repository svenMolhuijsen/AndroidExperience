package nl.svenmolhuijsen.brawser;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabHost;


public class MainActivity extends AppCompatActivity {


    Button goBttn;
    EditText adressInput;
    WebView webView;
    ProgressBar progressBar;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        goBttn = (Button)findViewById(R.id.GoBttn);
        adressInput = (EditText)findViewById(R.id.AdressInput);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        //defines the webview and let it surf to its standard url
        webView = (WebView)findViewById(R.id.WebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://google.com/");

        goBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if there has been clicked on the go button it wil execute thi function
                pageChange();
            }
        });

    }


    public void pageChange(){
        //checks if there was something typed
        if(adressInput.getText().length() != 0){
            Log.d("t", "it entered the function");
            //hide keyboard
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(adressInput.getWindowToken(), 0);
            //checks url
            String newUrl = checkUrl(adressInput.getText().toString());
            webView.loadUrl(newUrl);
        }else{
            Snackbar snackbar = Snackbar
                    .make(webView, "Please enter a adress or search term", Snackbar.LENGTH_LONG);

            snackbar.show();
        }
    }

    public String checkUrl(String input) {

        if (URLUtil.isValidUrl(input)) {
            Log.d("option 1", input);
            //check if first url is okay
            return input;
        } else if (Patterns.WEB_URL.matcher(input).matches()) {
            //tries to build url
            Log.d("option 2", input);
            String repairedUrl = "http://" + input;
            Log.d("option 2", repairedUrl);

            // if there is now still no valid url it will try to make a search
            Log.d("option 2", repairedUrl);
            return repairedUrl;
        } else {
            Uri.encode(input);
            String searchurl = "https://www.google.com/#q=" + input;
            Log.d("option 3", searchurl);
            return searchurl;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add("About");
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

        Intent myIntent = new Intent(MainActivity.this, About.class);
        MainActivity.this.startActivity(myIntent);
        return super.onOptionsItemSelected(item);
    }

    public class MyWebChromeClient extends WebChromeClient{
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setScaleY(12f);
            progressBar.setProgress(progress);
            // The progress meter will automatically disappear when we reach 100%
            if (progress == 100){
                Thread thread=  new Thread() {
                    public void run() {
                        try {
                            synchronized (this) {
                                wait(500);

                            }
                        } catch (InterruptedException ex) {
                        }
                        progressBar.setProgress(0);
                    }
                };
                thread.start();
            }
        }

        public void onReceivedTitle(WebView view, String title){
            toolBar.setTitle(title);
        }
        public void onReceivedIcon(WebView view, Bitmap icon){
            Resources res = getResources();
            Bitmap resizedIcon = Bitmap.createScaledBitmap(icon, 60, 60, true);
            BitmapDrawable logoIcon = new BitmapDrawable(res, resizedIcon);
            toolBar.setLogo(logoIcon);
        }
    }
}
