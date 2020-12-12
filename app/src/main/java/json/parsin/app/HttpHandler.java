package json.parsin.app;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler(){

    }

    public String makeServiceCall(String reqUrl){
        String response = null;

        try {
            URL url = new URL(reqUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        }catch (MalformedURLException e){
            Log.e(TAG,"MalformedURLException: " + e.getMessage());
        }catch (ProtocolException e){
            Log.e(TAG,"ProtocolException: " + e.getMessage());
        }catch (IOException e){
            Log.e(TAG,"IOException: " + e.getMessage());
        }catch (Exception e){
            Log.e(TAG,"Exception: " + e.getMessage());
        }
        return response;
    }

}
