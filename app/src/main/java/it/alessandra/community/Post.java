package it.alessandra.community;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by User on 08/12/2017.
 */

public class Post implements Serializable {

    private String autore;
    private String titolo;
    private Date dataCreazione;

    public Post(){
        this.autore = null;
        this.titolo = null;
        this.dataCreazione = new Date();
    }

    public Post(String autore, String titolo, Date dataCreazione){
        this.autore = autore;
        this.titolo = titolo;
        this.dataCreazione = dataCreazione;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Date getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {
        this.dataCreazione = dataCreazione;
    }
}
