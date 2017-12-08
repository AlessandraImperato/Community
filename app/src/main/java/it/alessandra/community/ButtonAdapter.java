package it.alessandra.community;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by utente7.academy on 06/12/2017.
 */

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {


    private List<Gruppo> gruppi;
    public Context context;
    public static Gruppo gruppo;

    public ButtonAdapter(List<Gruppo> listagruppi){
        gruppi = listagruppi;
    }
    public ButtonAdapter(List<Gruppo> listagruppi, Context mcontext){
        gruppi = listagruppi;
        context = mcontext;
    }

    @Override
    public ButtonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder vh = new ViewHolder(v,parent.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(final ButtonAdapter.ViewHolder holder, int position) {
        Gruppo tmp = gruppi.get(position);
        holder.button.setText(tmp.getNome());
        final String nomeGruppo = tmp.getNome();
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,PostActivity.class);
                i.putExtra("NOMEGRUPPO",nomeGruppo); //da passare alla PostActivity
                (v.getContext()).startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gruppi.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public Button button;

        public ViewHolder(View v, final Context context){
            super(v);
            cardView = (CardView)itemView.findViewById(R.id.cardview);
            button = (Button) v.findViewById(R.id.bgroup);
        }
    }
    public Context getContext(){
        return context;
    }
}
