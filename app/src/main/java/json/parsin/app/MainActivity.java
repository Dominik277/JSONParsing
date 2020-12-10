package json.parsin.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //ovdje smo prvo stvorili tri objekta objekta u memoriji tipa ArrayList te naveli
    //imena preko kojih cemo ih pozivati.Znaci dio na desnoj strani nam je zaduzen za
    //kreaciju objekta i taj objekt je u memoriji racunala na određenoj lokaciji,a da
    //bi mi pristupili toj lokaciji moramo navesti preko kojeg imena cemo pristupati, a
    //to je upravo ono sto su nam imena personNames,emailIds,mobileNumbers
    //objekt od ArrayList-a mozemo zamisliti kao i objekt obicnog Array-a samo sto ovaj
    //objekt ima mogucnost širenja,tj. mozemo dodavati elemenata koliko zelimo , dok kod
    //obicnog Array-a pri deklaraciji odma moramo navesti koliko elemenata mora biti
    ArrayList<String> personNames = new ArrayList<>();
    ArrayList<String> emailIds = new ArrayList<>();
    ArrayList<String> mobileNumbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ovdje smo samo referencirali recyclerView atribut iz XML-a.To mozemo zamisliti
        //na sljedeci nacin.Napravili smo u XML-u listu RecyclerView i nazvali ju recyclerView
        //i mi nakon toga zelimo nekako dovuci taj recyclerView iz XML-a u Java kod, a to radimo
        //s pomocu findViewById() metode, koja odlazi u XML i vraca taj atribut s tim imenom, i
        //rezultat koji vraca ta nasa metoda spremamo u istoimenu varijablu te sad s pomocu tog
        //imena mozemo referencirati recyclerView iz XML-a.
        RecyclerView recyclerView = findViewById(R.id.recyclerView);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray userArray = obj.getJSONArray("users");
            for (int i=0; i<userArray.length(); i++){
                JSONObject userDetail = userArray.getJSONObject(i);
                personNames.add(userDetail.getString("name"));
                emailIds.add(userDetail.getString("email"));
                JSONObject contact = userDetail.getJSONObject("contact");
                mobileNumbers.add(contact.getString("mobile"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this,personNames,emailIds,mobileNumbers);
        recyclerView.setAdapter(customAdapter);


    }

    public String loadJSONFromAsset(){
        String json = null;
        try {
            InputStream is = getAssets().open("users_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer,"UTF-8");
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return json;
    }

}