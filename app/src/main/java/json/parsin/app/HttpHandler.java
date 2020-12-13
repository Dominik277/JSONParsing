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

    //ovo je samo nasa custom metoda koja kao parametar prima String varijablu reqUrl
    public String makeServiceCall(String reqUrl){

        //ovdje smo samo napravili varijablu response tipa String u koju smo pohranili null vrijednost
        String response = null;

        //nakon toga dolazimo do try/catch bloka, u try bloku navodimo sav onaj kod koji mislimo da
        //je dobar, odnosno da ne baca exception, a u slucaju da taj kod unutar try bloka nije dobar
        //onda se poziva i izvrsava onaj kod koji je unutar catch bloka
        try {

            //URL klasa nam koristi kako bi njenom objektu predali neki link za neku www lokaciju
            //metodi makeServiceCall se kao parametar predaje taj link i onda se taj link prenosi
            //u konstruktor za pravljenje URL objekta.Znaci taj objekt od klase URL sada u sebi
            //sadrzi link za neku Web stranicu
            URL url = new URL(reqUrl);

            //ovom linijom koda smo omogucili HTTP konekciju sa internetom s pomocu openConnection() metode
            //znaci u varijablu conn tipa HttpsUrlConnection smo spremili objekt url i openConnection()
            //metodu koji su nam omogucili konekciju s internetom
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            //ova metoda postavlja naredbu zahtjeva koja ce biti predana HTTP serveru
            //ova metoda se mora pozvati prije nego sto je konekcija ostvarena
            conn.setRequestMethod("GET");

            //Za pocetak da definiramo sta je Stream, Stream je slijed podataka koji se salju u neko bloku
            //aplikacija koristi inputStream kako bi iscitavala podatke s neke određene lokacije na internetu
            //jedan item po jedan, InputStream klase sluze samo za citanje podataka s neke lokacije, za nista drugo
            //ukratko, InputStram nam predstavlja tok podataka od nekog izvora podataka
            //BufferedinputStram -->klasa koja omogucava citanje podataka koji su u stream-u, koristi "buffer mechanism"
            //                      kako bi ubrzala performanse,napravili smo objekt u memoriji racunala koji omogucuje
            //                      citanje podataka koji se nalaze unutar Stream-a a referencirati cemo ga pomocu imena in
            //                      argument cuva za kasniju uporabu
            //getInputStream() --> ova metoda vraca Stream s podacima koji dolaze s neke lokacije prema aplikaciji
            InputStream in = new BufferedInputStream(conn.getInputStream());

            //convertStreamToString() --> ovo je custom metoda koju smo naveli ispod ove metode
            //in --> je parametar metode convertStreamToString i to je varijabla tipa InputStream
            response = convertStreamToString(in);

            //
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
