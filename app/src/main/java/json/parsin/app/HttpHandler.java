package json.parsin.app;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpHandler {

    //varijabla TAG tipa string koju cemo koristiti u Log() metodi kada joj zelimo predati
    //prvi parametar, a taj prvi parametar nam predstavlja trenutnu klasu unutar koje se nalazimo
    private static final String TAG = HttpHandler.class.getSimpleName();

    //default konstruktor ove klase koji nam govori da kada budemo konstruirali objekt
    //da ce on biti prazan jer unutar zadgrada konstruktora nismo naveli ni jedan parametar
    public HttpHandler(){

    }

    public String makeServiceCall(String reqUrl){

        //ovdje smo samo napravili varijablu response tipa String u koju smo pohranili null vrijednost
        String response = null;

        //nakon toga dolazimo do try/catch bloka, u try bloku navodimo sav onaj kod koji mislimo da
        //je dobar, odnosno da ne baca exception, a u slucaju da taj kod unutar try bloka nije dobar
        //onda se poziva i izvrsava onaj kod koji je unutar catch bloka
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

    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        //ovdje smo samo napravili lokalnu varijablu line tipa String koja je prazna
        //ova varijable je lokalna jer se nalazi unutar metode i vidljiva je samo unutar
        //metode te ce i koristiti samo unutar metode
        String line;

        //nakon toga dolazimo do try/catch bloka, u try bloku navodimo sav onaj kod koji mislimo da
        //je dobar, odnosno da ne baca exception, a u slucaju da taj kod unutar try bloka nije dobar
        //onda se poziva i izvrsava onaj kod koji je unutar catch bloka
        try {
            while ((line = reader.readLine())!= null){
                sb.append(line).append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
