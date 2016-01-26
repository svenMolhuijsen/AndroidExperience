package nl.svenmolhuijsen.bruwser;

        import android.content.Context;
        import android.content.res.Resources;
        import android.graphics.Bitmap;
        import android.graphics.drawable.BitmapDrawable;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Parcelable;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.util.Patterns;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewDebug;
        import android.view.ViewGroup;
        import android.view.inputmethod.InputMethodManager;
        import android.webkit.URLUtil;
        import android.webkit.WebChromeClient;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;

        import java.util.Random;

        import nl.svenmolhuijsen.bruwser.R;


public class TabFragment extends Fragment{
    View view;
    Button goBttn;
    EditText adressInput;
    WebView webView;
    ProgressBar progressBar;
    Toolbar toolBar;
    private Bundle webViewBundle;
    private String mText;

    public TabFragment() {
        // Required empty public constructor
    }


    public static TabFragment newInstance(String text) {
        TabFragment f = new TabFragment(text);
        return f;
    }

    public TabFragment(String text) {
        this.mText = text;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_one, container, false);

        goBttn = (Button) view.findViewById(R.id.GoBttn);
        adressInput = (EditText) view.findViewById(R.id.AdressInput);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        webView = (WebView) view.findViewById(R.id.WebView);

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
            Log.d("savedInstance", "rotationrestored");
        }else if(webViewBundle != null){
            webView.restoreState(webViewBundle);
            Log.d("savedInstance", "tabrestored");}
        else{
            webView.loadUrl("https://google.com/");
            Log.d("savedInstance", "rerouted to homepage");
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        goBttn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //if there has been clicked on the go button it wil execute thi function
                pageChange();
            }
        });
    }

    public void resetFragment(){
        adressInput.setText("");
        webView.loadUrl("http://google.com");
    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        // Save the state of the WebView
        webView.saveState(outState);
    }
    public void onPause() {
        super.onPause();

        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }


    public void pageChange(){
        //checks if there was something typed
        if(adressInput.getText().length() != 0){
            Log.d("t", "it entered the function");
            //hide keyboard
            //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.hideSoftInputFromWindow(adressInput.getWindowToken(), 0);
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


    public class MyView extends View {
        public MyView(Context context) {
            super(context);
        }

        @Override
        public Parcelable onSaveInstanceState() {
            super.onSaveInstanceState();
            Bundle bundle = new Bundle();
            // Save current View's state here
            return bundle;
        }

        @Override
        public void onRestoreInstanceState(Parcelable state) {
            super.onRestoreInstanceState(state);
            // Restore View's state here
        }


    }
    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setScaleY(12f);
            progressBar.setProgress(progress);
            // The progress meter will automatically disappear when we reach 100%
            if (progress == 100){
//                Thread thread=  new Thread() {
//                    public void run() {
//                        try {
//                            synchronized (this) {
//                                wait(500);
//
//                            }
//                        } catch (InterruptedException ex) {
//                        }
//                        progressBar.setProgress(0);
//                    }
                };
//                thread.start();
            }
        }

        public void onReceivedTitle(WebView view, String title){
            toolBar.setTitle(title);
        }
        public void onReceivedIcon(WebView view, Bitmap icon){
//            Resources res = getResources();
//            Bitmap resizedIcon = Bitmap.createScaledBitmap(icon, 60, 60, true);
//            BitmapDrawable logoIcon = new BitmapDrawable(res, resizedIcon);
//            toolBar.setLogo(logoIcon);
        }
    }

