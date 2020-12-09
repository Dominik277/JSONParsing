package json.parsin.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,email,mobileNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            mobileNo = itemView.findViewById(R.id.mobileNo);
        }
    }

}
