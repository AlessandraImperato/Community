package it.alessandra.community;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utente7.academy on 06/12/2017.
 */

public class Gruppo implements Serializable {

    private String nome;
    private List<Post> listaPost;

    public Gruppo(){
        this.nome = null;
        this.listaPost = new ArrayList<>();
    }

    public Gruppo(String nome){
        this.nome = nome;
        this.listaPost = new ArrayList<>();
    }

    public Gruppo(String nome, List<Post> listaPost){
        this.nome = nome;
        this.listaPost = listaPost;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Post> getListaPost() {
        return listaPost;
    }

    public void setListaPost(List<Post> listaPost) {
        this.listaPost = listaPost;
    }
}
