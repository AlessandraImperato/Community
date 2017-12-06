package it.alessandra.community;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by utente7.academy on 06/12/2017.
 */

public class Community {

    public List<Utente> utenti;

    public Community(){
        utenti = new ArrayList<>();
    }

    public Community(List<Utente>utenti){
        this.utenti = utenti;
    }

    public List<Utente> getUtenti() {
        return utenti;
    }

    public void setUtenti(List<Utente> utenti) {
        this.utenti = utenti;
    }

    public boolean controlPassword(String nome, String password){
        for(Utente tmp : utenti){
            if(tmp.getNome().equals(nome)){
                if(tmp.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }
}
