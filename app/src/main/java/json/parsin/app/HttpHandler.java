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

            //ovdje definiramo sto ce se desili ako nam ne prođe kod unutar try bloka, postoji vise mogucih pogresaka
            //koje se mogu desiti pa smo za svaku od njih napravili catch blok te ce svaki ispisivati zasebnu poruku
            //unutar Logcat-a
            //TAG --> TAG nam je konstanta u koju smo spremili trenutnu klasu u kojoj se nalazimo,TAG predajemo kao
            //        prvi parametar unutar Log() metode
            //MalformedURLException --> ovaj exception se događa ako link nije pravilno oblikovan, odnosno ako se ne
            //                          moze prepoznati ili ako se varijabla tipa string u koji je link spremljen ne
            //                          moze rasclaniti
            //ProtocolException --> jedan tip ovakvog exceptiona moze biti TCP Error koji sluzi za slaganje pristiglih
            //                      podataka.Kada primamo podatke sa neke lokacije s interneta oni stizu u mnogo blokova
            //                      a TCP protokol sluzi za slaganje tih blokova u jednu jedinstvenu cjelinu, znaci ako
            //                      dode do TCP error-a onda aplikacija baca ProtocolException
            //IOException --> ovaj exception nam signalizira da je doslo do neke pogreske u primanju ili slanju podataka
            //Exception --> ovo je opcenita klasa koja hvata sve vrste exception-a
            //getMessage() --> ova metoda vraca string sa detaljnom informacijom u vezi exception-a koji se desio
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


    //ovo je custom metoda koja kao parametar prima varijablu is tipa InputStream
    private String convertStreamToString(InputStream is){

        //BufferedReader --> objekt ove klase sluzi kako bi citao podatke koji su u Stream-u,
        //                   ali koji su u Stremu i tipa su character, on ih rasclanjuje i cita
        //                   ova metoda ubrzava performase citanja podataka
        //InputStramReader --> objekt ove klase je most između Stream-a koji u sebi imaju byte
        //                     tip podataka i Stream-a koji u sebi ima character podatke
        //                     cita byte-ove i pretvara ih u characters kako bi svi podaci bili
        //                     istog tipa
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        //ovdje smo na desnoj strani stvorili objekt tipa StringBuilder u memoriji racunala
        //koji je prazan jer konstruktor nema nikakvih parametara, a s lijeve strane smo samo
        //naveli ime preko kojeg cemo ga referencirati odnosno pozivati ga
        //StringBuilder --> objekt ove klase se koristi za stvaranje promjenjive(modifiable)
        //                  stringove
        StringBuilder sb = new StringBuilder();

        //ovdje smo samo napravili lokalnu varijablu line tipa String koja je prazna
        //ova varijable je lokalna jer se nalazi unutar metode i vidljiva je samo unutar
        //metode te ce i koristiti samo unutar metode
        String line;

        //nakon toga dolazimo do try/catch bloka, u try bloku navodimo sav onaj kod koji mislimo da
        //je dobar, odnosno da ne baca exception, a u slucaju da taj kod unutar try bloka nije dobar
        //onda se poziva i izvrsava onaj kod koji je unutar catch bloka
        try {

            //line --> line nam je samo ime varijable koje je tipa String
            //reader --> reader je referenca na objekt u memoriji tipa BufferedReader
            //           reader sluzi kako bi citao podatke koji dolaze unutar streama
            //readLine() --> ova metoda sluzi za citanje linije teksta
            //               ova metoda vraca String koji sadrzi teks jedne cijele linije
            //unutar zagrada se događa sljedece --> u string varijablu se sprema string vrijednost
            //                                      koja je procitana u Stream-u, znaci tekst koji je
            //                                      procitan unutar Stream-a ako je razlicit on null,
            //                                      odnosno ako Stream nije prazan onda se odvija sljedeci
            //                                      dio koda unutar viticastih zagrada
            while ((line = reader.readLine())!= null){

                //sb --> referenca na objekt u memoriji tipa StringBuilder
                //       sb je objekt koji ima mogucnost stvaranja promenjivih stringova
                //append na engleskom znaci dodati sto onda znaci da sve ono prethodno sto smo spremili unutar
                //string varijable line, a to jest string linije koje su se nalazite u Stram-u, na kraju
                //toga stringa dodajemo znak za novi red
                sb.append(line).append("\n");
            }
            //ovaj dio koda u catch bloku se odvija ako dio koda unutar try bloka nije dobar, odnosno
            //ako baca nekakav exception
            //IOException --> ovaj exception nam signalizira da je doslo do neke pogreske u primanju ili slanju podataka
            //printStackTrace() --> ova metoda nam pomaze kako bi lakse nasli uzrok problema i na kojoj liniji je doslo do
            //                      tog problema, također nam prikazuje i koja metoda nam stvara problem
            //                      radi na nacin da se kako se izvodi program metoda po metoda ubacuje u stack i prva metoda
            //                      koja nije valjana nam se ispisuje pomocu printStackTrace() metode
        }catch (IOException e){
            e.printStackTrace();
        }finally {

            //finally --> dio koda unutar finally kljucne rijeci se izvodi bez obzira dali je doslo
            //            do exceptiona ili nije
            //isprobavamo dio koda unutar try bloka
            //close() --> ova metoda zatvara "dotok" inputStream-a i pusta sve podatke koji su vezani
            //            uz taj Stream
            try {
                is.close();

                //dio koda unutar catch bloka se odvija samo u slucaju ako nesto nije dobro unutar
                //try bloka, ondnoso ako try blok baca nekakav exception onda catch blok ga hvata
                //IOExcetpion --> ovaj exception nam signalizira da je doslo do neke pogreske u primanju ili slanju podataka
                //printStackTrace() --> ova metoda nam pomaze kako bi lakse nasli uzrok problema i na kojoj liniji je doslo do
                //                      tog problema, također nam prikazuje i koja metoda nam stvara problem
                //                      radi na nacin da se kako se izvodi program metoda po metoda ubacuje u stack i prva metoda
                //                      koja nije valjana nam se ispisuje pomocu printStackTrace() metode
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
