package it.alessandra.community;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by utente7.academy on 06/12/2017.
 */

public class Gruppo implements Serializable {

    private String nome;
    private List<Post> listaPost;
    private Date lastChange;

    public Gruppo(){
        this.nome = null;
        this.listaPost = new ArrayList<>();
        this.lastChange = new Date();
    }

    public Gruppo(String nome){
        this.nome = nome;
        this.listaPost = new ArrayList<>();
        this.lastChange = new Date();
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date lastChange) {
        this.lastChange = lastChange;
    }

    public Gruppo(String nome, List<Post> listaPost){
        this.nome = nome;
        this.listaPost = listaPost;
        this.lastChange = new Date();

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

    public Post getPostById(String idPost){
        Post post = new Post();
        for(Post postTemp : listaPost){
            if(postTemp.getId().equals(idPost)){
                post = postTemp;
            }
        }
        return post;
    }
}
