package ma.fstt.model;

public class Produits {
    private long ID_produit;
    private String nom;
    private String description;

    public Produits(long ID_produit, String nom, String description) {
        this.ID_produit = ID_produit;
        this.nom = nom;
        this.description = description;
    }

    // Getter and setter methods
    public long getID_produit() {
        return ID_produit;
    }

    public void setID_produit(long ID_produit) {
        this.ID_produit = ID_produit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Override toString() method
    @Override
    public String toString() {
        return "Produit [ID_produit=" + ID_produit + ", nom=" + nom + ", description=" + description + "]";
    }
}

