package json.parsin.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
        protected Void doInBackground(Void... arg0) {

            //na desnoj strani smo napravili objekt u memoriji racunala tipa HttpHandler
            //a na lijevoj strani samo referencu preko koje cemo dokucivati taj objekt
            //preko ovog objekta mozemo pristupati svim metodama koje se nalaze unutar
            //HttpHandler klase
            //HttpHandler --> je klasa koju smo deklarirali u ovom projektu te smo sada
            //                napravili objekt te klase
            HttpHandler sh = new HttpHandler();

            //ovdje smo na lijevoj strani napravili varijablu url koja je tipa String,a
            //u nju smo pohranili ovo sto se nalazi na desnoj strani unutar navodnika
            String url = "http://api.androidhive.info/contacts/";

            //jsonStr je varijabla tipa string u koju se pohranjuje rezultat koji vraca
            //metoda makeServiceCall(url) koja je unutar HttpHandler klase koju smo pozvali
            //preko objekta od te klase
            String jsonStr = sh.makeServiceCall(url);

            //ovo je metoda koja sluzi za prikazivanje raznoraznih poruka unutar Logcat-a
            //TAG --> prvi parametar u ovoj metodi uvijek mora biti tipa string,ovaj prvi
            //        parametar najcesce oznacava u kojem activity-u se dogodio eror te sluzi
            //        za određivanja razloga zbog kojeg je doslo do te error poruke
            //        ovaj parametar moze biti null
            //Response from url: + jsonStr --> ovo je poruka koju nam log metoda ispisuje u
            //                                 Logcat-u,ovaj parametar ne smije biti null
            //e --> e nam predstavlja EROR message
            //jsonStr -->jsonStr je varijabla tipa string u koju se pohranjuje ono sto vraca
            //           metoda makeServiceCall()
            Log.e(TAG,"Response from url: " + jsonStr);

            //ovdje smo napravili if naredbu gdje u zagradi ispitujemo ako je jsonStr razlicit
            //od nule,odnosno ako je u jsonStr spremljena neka vrijednos, ako nije prazan, onda
            //se izvrsavaju sve ove narebe unutar viticastih zagrada, ako ovo u zagradi nije istina
            //onda se izvrsava dio koda koji se nalazi pod else viticastim zagradama
            if (jsonStr != null){

                //ovom naredbom nam zapocinje try/catch blok gdje stavljamo neki kod koji zelimo
                //izvrsiti unutar try bloka i ako taj kod nije dobar,odnosno baca neki exception
                //onda taj exception hvatamo pomocu catch bloka unutuar kojeg smo definirali tocno
                //sto ce se desiti ako dode do exception-a
                try {

                    //na desnoj strani smo napravili objekt u memoriji racunala tipa JSONObject, a
                    //na lijevoj strani smo samo deklarirali ime jsonObj preko kojeg cemo dohvacati
                    //taj objekt iz memorije racunala.
                    //Objekt iz ove klase predstavlja kolekcuje key/value parova koji su neporedani
                    //u ovom slucaju smo napravili objekt kojeg cemo referencirati preko jsonObj koji
                    //je konstruiran pomocu konstruktora koji kao argument ima varijablu jsonStr koja
                    //u sebi ima pohranjeno sve sto je vratila metoda makeServiceCall koja se nalazi
                    //unutar HttpHandler klase
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    //desna strana -->zamislimo jsonObj kao bazu podataka unutar koje su podaci spremljeni
                    //                kao key/value parovi te ih imamo vise komada.Ako npr zelimo dohvatiti
                    //                podatak koji se nalazi na 6 mjestu u "bazi podataka" mozemo mu pristupiti
                    //                ako navedemo njegov key.Npr ako spremamo imena kao key,a godine kao value
                    //                onda npr key je Dominik, a value je 21, a ova getJSONArray metoda radi na
                    //                taj nacin da kao argument joj navedemo key vrijednost, a ona nam kao rezultat
                    //                vraca value vrijednost od tog key/value para.Znaci ukratko,stvorili smo objekt
                    //                u memoriji tipa JSONObject imena jsonObj u kojem su podaci spremljeni kao
                    //                key/value parovi, ako zelimo pristupiti određenoj vrijednost unutar tog objekta
                    //                kao argument te metode koja je pozvana preko tog objekt navodimo key i on nam
                    //                vraca value za taj pripadajuci key
                    //lijeva strana -->na lijevoj strani smo samo napravili varijablu contacst tipa JSONArray u koju
                    //                 spremamo sve ovo sto se odvilo na desnoj strani
                    JSONArray contacts = jsonObj.getJSONArray("contacts");


                    //ovdje smo deklarirali for petlju koja se izvrsava sve dok je i manji od duzine elemenata u contacts
                    for (int i = 0; i<contacts.length(); i++){

                        //desna strana -->
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        HashMap<String,String> contact = new HashMap<>();

                        contact.put("id",id);
                        contact.put("name",name);
                        contact.put("email",email);
                        contact.put("mobile",mobile);

                        contactList.add(contact);
                    }
                }catch (final JSONException e){
                        Log.e(TAG,"JSON parsing eror: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "JSON parsing eror: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                }
            }else {
                Log.e(TAG,"Couldn't get JSON from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get JSON from server.Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

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