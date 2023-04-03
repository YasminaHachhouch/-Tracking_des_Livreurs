package ma.fstt.model;

import java.util.Date;

public class Commande {
    private long id_command;
    private Date date_debut;
    private Date date_fin;
    private String client;
    private String etat;

    public Commande(long id_command, Date date_debut, Date date_fin, String client, String etat) {
        this.id_command = id_command;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.client = client;
        this.etat = etat;
    }

    public long getId_command() {
        return id_command;
    }

    public void setId_command(long id_command) {
        this.id_command = id_command;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}

