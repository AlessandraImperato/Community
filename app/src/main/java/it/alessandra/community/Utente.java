package it.alessandra.community;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by utente7.academy on 06/12/2017.
 */

public class Utente {

    private String nome;
    private String password;
    private List<Gruppo> gruppi;

    public Utente(){
        this.nome = null;
        this.password = null;
        this.gruppi = new ArrayList<>();
    }

    public Utente(String nome, String password, List<Gruppo> gruppi){
        this.nome = nome;
        this.password = password;
        this.gruppi = gruppi;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Gruppo> getGruppi() {
        return gruppi;
    }

    public void setGruppi(List<Gruppo> gruppi) {
        this.gruppi = gruppi;
    }
}