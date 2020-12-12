package json.parsin.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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
    //AsyncTask je wrapper class koja omogucuje obavljanje teskog posla u pozadini
    //kako bi olaksali rad UI threada tako sto automatski micemo taj teski posao
    //iz UI threada te sluzi za updatanje UI threada.AsyncTask zapocinje na UI
    // threadu i gura sve teske zadatke
    //na asyncTask klasu koja zapocinje backgroundThread.Kada background thread
    //obavi svoj zadatak on rjesenje salje u UI thread
    private class GetContacts extends AsyncTask<Void,Void,Void>{

        //ovo je zapravo jedina metoda koju je obavezno implementirati
        //sve ostale su bonus.U ovoj metodi navodimo kod u kojem su sadrzani
        //svi oni "teski" zadaci koji trebaju biti obavljeni u backgroundu.
        //Zadatak kojem bi trebalo dosta vremena da se izvrsi se stavlja u ovu
        //metodu.Ova metoda radi u background Thred-u.Ova metoda se poziva odmah
        //nakon sto metoda onPreExecute() obavi svoj posao.Operacije u ovoj metodi
        //ne bi smjele dodirivati nikakve activitie i fragmente od main thread-a.
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        //ovo je prva metoda koja se poziva kada je AsyncTask pozvan.Nakon ove
        //metode se izvodi doInBackground.Ova metoda se izvodi na UI thread-u i
        //najcesce se koristi za instanciranje UI elemenata,kao npr pokazivanje
        //spinnera dok se obavlja background task
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"JSON data is downloading"
                    ,Toast.LENGTH_LONG).show();
        }


        //kada je background task gotov, onda trebamo rezultate te operacije
        //poslati nazad u UI thread.Ova metoda radi u UI thredu i metoda
        //doInBackground() svoje podatke salje ovoj metodi kao parametar i
        // onda ova metoda te podatke prosljeđuje UI thread-u
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this,contactList,
                    R.layout.list_item, new String[]{"email","mobile"},
                    new int[]{R.id.email,R.id.mobile});
            lv.setAdapter(adapter);
        }




    }
}