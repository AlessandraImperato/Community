package it.alessandra.community;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by utente7.academy on 06/12/2017.
 */

public class JsonParse {

    public static List<Gruppo> getListGroup(String json) throws JSONException {

        List<Gruppo> gruppi = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> groups = jsonObject.keys();
            while (groups.hasNext()){
             Gruppo gruppo = new Gruppo();
             String nomeGruppo = groups.next();
             gruppo.setNome(nomeGruppo);
             gruppi.add(gruppo);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return gruppi;
    }

    public static List<Post> getListPost(String json) throws JSONException{

        List<Post> listaPost = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(json); // json con tutti i post
            Iterator<String> posts = jsonObject.keys(); // itera i post
            while(posts.hasNext()){
                Post post = new Post();
                String oneKey = posts.next(); // post 1 / post 2 ?

                JSONObject onePost = jsonObject.getJSONObject(oneKey); //json con tutti i campi
                Iterator<String> field = onePost.keys(); //itera i campi del singolo post
                while (field.hasNext()){
                    String oneKey2 = field.next();
                    if(oneKey2.equals("Autore")){
                        String autore = onePost.getString(oneKey2);
                        post.setAutore(autore);
                    }
                    else if(oneKey2.equals("Titolo")){
                        String titolo = onePost.getString(oneKey2);
                        post.setTitolo(titolo);
                    }
                    else if(oneKey2.equals("Data")){
                        String data = onePost.getString(oneKey2);
                        Date dataCreazione = formatDate(data);
                        post.setDataCreazione(dataCreazione);
                    }
                }
                listaPost.add(post);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return listaPost;
    }

    public static Date formatDate(String dateString){ // trasformo la data da stringa a Date
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        return format.parse(dateString,new ParsePosition(0));
    }

}

