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

    public static List<Utente> getList(String json) throws JSONException {
        List<Utente> utenti = new ArrayList<>();
        //List<Gruppo> gruppi = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> user = jsonObject.keys(); // user1/user2/...
            while (user.hasNext()){
                Utente utente = new Utente();
                /**/List<Gruppo> gruppi = new ArrayList<>();
                Gruppo gruppo = new Gruppo();
                String username = user.next();
               // String nome = jsonObject.getString(oneKey);
                utente.setNome(username);

                JSONObject oneUser = jsonObject.getJSONObject(username);
                Iterator<String> dettagliUtente = oneUser.keys(); //gruppi/password
                while (dettagliUtente.hasNext()){
                    String onekey = dettagliUtente.next();
                    if(onekey.equals("password")){
                        String password = oneUser.getString(onekey);
                        utente.setPassword(password);
                    }
                    else if(onekey.equals("Gruppi")){
                        JSONObject oneGroup = oneUser.getJSONObject(onekey);
                        Iterator<String> dettagliGruppo = oneGroup.keys();
                        while (dettagliGruppo.hasNext()){
                            //Gruppo gruppo = new Gruppo();
                            String nomegruppo = dettagliGruppo.next();
                            //String nomegruppo = oneGroup.getString(onekey3);
                            gruppo.setNome(nomegruppo);
                            gruppi.add(gruppo);
                            //utente.setGruppi(gruppi);
                        }
                    }
                }utente.setGruppi(gruppi);
                utenti.add(utente);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return utenti;
    }

}
