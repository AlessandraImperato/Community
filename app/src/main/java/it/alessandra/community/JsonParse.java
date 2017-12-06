package it.alessandra.community;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by utente7.academy on 06/12/2017.
 */

public class JsonParse {

    public static List<Gruppo> getList(String json) throws JSONException {

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

}
