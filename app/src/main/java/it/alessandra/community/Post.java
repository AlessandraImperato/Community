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
    private String body;
    private String id;

    public Post() {
        this.autore = null;
        this.titolo = null;
        this.dataCreazione = new Date();
        this.id = null;
        this.body = null;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Post(String autore, String titolo, Date dataCreazione) {
        this.autore = autore;
        this.titolo = titolo;
        this.dataCreazione = dataCreazione;
        this.id = null;
        this.body = null;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
