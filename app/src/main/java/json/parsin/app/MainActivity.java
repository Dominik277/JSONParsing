package json.parsin.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    //ovdje smo samo referencirali varijablu lv tipa ListView koja u
    //sebi za sada nema jos nista spremljeno,prazna je
    private ListView lv;

    //ovdje smo isto također samo referencirali varijablu contactList
    //tipa ArrayList koja u sebi isto nema nista spremljeno,prazna je
    //ArrayList je slican kao i Array,jedina razlika je u tome sto je
    //ArrayList moguce mjenjati mu velicinu koliko elemenata moze pohraniti
    //u njegove parametre smo stavili HashMap, a u parametre od HashMap-a
    //dva string.HashMap mozemo zamisiti kao neku vrstu baze podatak koja
    //svoje podatke pohranjuje u key/value parovima, a s ovim parametrima
    //gdje su dva stringa tu smo rekli da ce i key i value biti tipa String
    //ovaj objekt contactList ce onda biti tipa ArrayList koji ce svoje
    //elemente pohranjivati  kao key/value parove
    ArrayList<HashMap<String,String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //s desne strane jednađbe smo stvorili objekt tipa ArrayList u
        //memoriji racunala pomocu kljucne rijeci new, contactList je
        //samo referenca preko koje cemo zvati taj objekt iz memorije
        //odnosno ime preko kojeg cemo pristupati tom objektu
        contactList = new ArrayList<>();

        //lv varijabla je do sada bila prazna, ali s ovom jednađbom
        //smo u nju stavili ListView koji ce se nalaziti u activity_main
        //findViewById() metoda odlazi u XML i trazi prema id-u ime koje
        //smo naveli u paramtru i taj atribut iz XML-a pridodaje lv varijabli
        //na ovaj nacin smo od atributa iz XML-a stvorili java objekt
        lv = findViewById(R.id.list);
    }

    //ovo je klasa koja nasljeđuje AsyncTask klasu koja je abstrakta te u sebi
    //sadrzi neke metode koje moramo implementirati
    private class GetContacts extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }




    }
}