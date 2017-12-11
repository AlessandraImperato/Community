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
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 08/12/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> listaPost;
    public Context context;
    public static Post post;

    public PostAdapter(List<Post> listapost){

        listaPost = listapost;
    }
    public PostAdapter(List<Post> listapost, Context mcontext){
        listaPost = listapost;
        context = mcontext;
    }

        @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempost,parent,false);
        ViewHolder vh = new ViewHolder(v,parent.getContext());
        return vh;
    }

    @Override
    public void onBindViewHolder(final PostAdapter.ViewHolder holder, int position) {
        final Post tmp = listaPost.get(position);
        holder.autorePost.setText(tmp.getAutore());
        holder.titoloPost.setText(tmp.getTitolo());
        holder.dataCreazione.setText(formatDate(tmp.getDataCreazione()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SinglePostActivity.class);
                String id = tmp.getId();
                intent.putExtra("IdPost", id);
                (v.getContext()).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPost.size();
    }

    public String formatDate(Date date){
        Format format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        return format.format(date);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView autorePost;
        public TextView titoloPost;
        public TextView dataCreazione;

        public ViewHolder(View v, final Context context){
            super(v);
            cardView = (CardView)itemView.findViewById(R.id.cardpost);
            autorePost = (TextView) v.findViewById(R.id.autore);
            titoloPost = (TextView) v.findViewById(R.id.titolo);
            dataCreazione = (TextView) v.findViewById(R.id.data);
        }
    }

    public Context getContext(){
        return context;
    }
}
