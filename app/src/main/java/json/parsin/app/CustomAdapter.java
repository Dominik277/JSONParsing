package json.parsin.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    //ovdje smo samo stvorili imena varijabli koji ce biti tipa ArrayList u koji ce se
    //spremati stringovi.Ove varijable su za sada samo inicijalizirane u njih jos nista
    //nismo pohranili
    ArrayList<String> personNames;
    ArrayList<String> emailIds;
    ArrayList<String> mobileNumbers;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> personNames, ArrayList<String> emailIds, ArrayList<String> mobileNumbers) {
        this.context = context;
        this.personNames = personNames;
        this.emailIds = emailIds;
        this.mobileNumbers = mobileNumbers;
    }


    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
       MyViewHolder vh = new MyViewHolder(v);
       return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.name.setText(personNames.get(position));
        holder.email.setText(emailIds.get(position));
        holder.mobileNo.setText(mobileNumbers.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,personNames.get(position),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return personNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //ovdje smo isto samo inicijalizirali varijable koje ce biti tipa TextView,
        //nismo u njih jos nista pohranili.Posto su tipa TextView odma vidimo da cemo
        //s pomocu ovih varijabli se odnositi prema atributima u XML-u koji se tako zovu
        TextView name,email,mobileNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            //ovdje isto kao i MainActivity.Pomocu metode findViewById() nađemo atribut iz
            //XML-a koji smo naveli kao argument metode, metoda ode u XML, kao rezultat svoje
            //operacije vrati taj atribut iz XML-a i pohrani ga u varijablu i svaki sljedeci
            //put kad se zelimo odnositi prema tom atributu iz XML-a, napisemo ime varijable
            //u kojem je on pohranjen
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            mobileNo = itemView.findViewById(R.id.mobileNo);
        }
    }

}
