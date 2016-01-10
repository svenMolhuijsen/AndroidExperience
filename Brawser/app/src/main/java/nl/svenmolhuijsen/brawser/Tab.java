package nl.svenmolhuijsen.brawser;

import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;

public class Tab {
    private URLUtil urlUtil;
    private Uri adress;
    private String searchTerm;

    public Tab(){//CONSTRUCTOR
        urlUtil= new URLUtil();
    }

    public String checkUrl(String input){
        if( Patterns.WEB_URL.matcher(input).matches()){
            //check if first url is okay
            return input;
        }else{
            //tries to build url
            String repairedUrl="http://"+input;
            if( Patterns.WEB_URL.matcher(repairedUrl).matches()){
                // if there is now still no valid url it will try to make a search
                Log.d("t", repairedUrl);
                return repairedUrl;

            }else{

                adress.encode(input);
                String searchurl = "https://www.google.com/#q="+input;
                Log.d("t", searchurl);
                return searchurl;
            }
        }

    }


}
