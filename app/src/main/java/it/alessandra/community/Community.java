package it.alessandra.community;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 08/12/2017.
 */

public class Community implements Serializable {
    private List<Gruppo> gruppi;

    public Community(){
        gruppi = new ArrayList<>();
    }

    public Community(List<Gruppo> gruppi){
        this.gruppi = gruppi;
    }

    public List<Gruppo> getGruppi() {
        return gruppi;
    }

    public void setGruppi(List<Gruppo> gruppi) {
        this.gruppi = gruppi;
    }

    public Gruppo getGroupByName(String nomeGruppo){
        Gruppo gruppo = new Gruppo();
        for(Gruppo tmp : gruppi){
            if (tmp.getNome().equals(nomeGruppo)){
                gruppo = tmp;
            }
        }
        return gruppo;
    }

   /* public Post getPostById(String groupName,String idPost){
        Gruppo gruppo = new Gruppo();
        List<Post> listaPost = new ArrayList<>();
        Post post = new Post();
        gruppo = getGroupByName(groupName);
            listaPost = gruppo.getListaPost();
            for(Post postTemp : listaPost){
                if(postTemp.getId().equals(idPost)){
                    post = postTemp;
                }
            }
        return post;
    }*/

}
