package it.alessandra.community;

/**
 * Created by utente7.academy on 06/12/2017.
 */

public class Gruppo {

    private String nome;

    public Gruppo(){
        this.nome = null;
    }

    public Gruppo(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
