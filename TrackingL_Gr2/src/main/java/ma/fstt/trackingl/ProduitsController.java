package ma.fstt.trackingl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.fstt.model.Commande;
import ma.fstt.model.CommandeDAO;
import ma.fstt.model.Produits;
import ma.fstt.model.ProduitsDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import static java.sql.Date.valueOf;

public class ProduitsController {
    @FXML
    private TextField nom;
    @FXML
    private TextField description;

    @FXML
    public void onSaveButtonClick(ActionEvent event) {
        try {
            Produits produit = new Produits(0L, nom.getText(), description.getText());
            ProduitsDAO doa = new ProduitsDAO();
            doa.save(produit);
            updateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateTable() {
    }

    @FXML
    private void goToCommandeView(ActionEvent event) throws IOException {
        Parent commandeParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("commande.fxml")));
        Scene commandeScene = new Scene(commandeParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(commandeScene);
        window.show();
    }

    @FXML
    public void gotoLivreurView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HelloView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
