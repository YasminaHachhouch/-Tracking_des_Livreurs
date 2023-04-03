package ma.fstt.trackingl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import ma.fstt.model.Commande;
import ma.fstt.model.CommandeDAO;
import ma.fstt.model.Livreur;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static java.sql.Date.valueOf;


public class CommandeController {
    @FXML
    private TableView<Commande> mytable;

    @FXML
    private TableColumn<Livreur, Long> col_id;

    @FXML
    private TableColumn<Livreur, Date> col_date_debut;
    @FXML
    private TableColumn<Livreur, Date> col_date_fin;

    @FXML
    private TableColumn<Livreur, String> col_client;
    @FXML
    private TableColumn<Livreur, String> col_etat;

    @FXML
    private TableColumn<Commande, String> col_edit;

    @FXML
    private TableColumn<Commande, String> col_delete;
    @FXML 
    private DatePicker dateDebut;
    @FXML
    private DatePicker dateFin;
    @FXML
private TextField client;

    @FXML
    private TextField etat;
    public void onSaveButtonClick(ActionEvent actionEvent) {
        try {
           Commande commande = new Commande(0L, valueOf(dateDebut.getValue()), valueOf(dateFin.getValue()),client.getText(),etat.getText());
            CommandeDAO doa =new CommandeDAO();
                    doa.save(commande);
            updateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTable() {
        try {
            CommandeDAO dao = new CommandeDAO();
            List<Commande> list = dao.getAllCommande();
            ObservableList<Commande> data = FXCollections.observableArrayList(list);
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_client.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
            col_date_debut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
            col_date_fin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
            col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            col_delete.setCellValueFactory(new PropertyValueFactory<Commande, String>("id"));
            col_edit.setCellValueFactory(new PropertyValueFactory<Commande, String>("id"));
            Callback<TableColumn<Commande, String>, TableCell<Commande, String>> deleteButtonCellFactory = new Callback<TableColumn<Commande, String>, TableCell<Commande, String>>() {
                @Override
                public TableCell<Commande, String> call(final TableColumn<Commande, String> param) {
                    final TableCell<Commande, String> cell = new TableCell<Commande, String>() {
                        final Button deleteButton = new Button("Delete");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                deleteButton.setOnAction(event -> {
                                    Commande commande = getTableView().getItems().get(getIndex());
                                    CommandeDAO dao = null; try {
                                        dao = new CommandeDAO();
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                    try {
                                        dao.delete(commande);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                    updateTable();
                                });
                                setGraphic(deleteButton);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };

            Callback<TableColumn<Commande, String>, TableCell<Commande, String>> editButtonCellFactory = new Callback<TableColumn<Commande, String>, TableCell<Commande, String>>() {
                @Override
                public TableCell<Commande, String> call(final TableColumn<Commande, String> param) {
                    final TableCell<Commande, String> cell = new TableCell<Commande, String>() {
                        final Button editButton = new Button("Edit");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                editButton.setOnAction(event -> {
                                    Commande commande = getTableView().getItems().get(getIndex());
                                    CommandeDAO dao = null;
                                    try {
                                        dao = new CommandeDAO();
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                    try {
                                        dao.update(commande);
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                setGraphic(editButton);
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }
            };

            col_delete.setCellFactory(deleteButtonCellFactory);
            col_edit.setCellFactory(editButtonCellFactory);

            mytable.setItems(data);

        } catch (Exception e) {
            System.out.println("Error while getting Commandes " + e);
        }
    }




    private void loadTableData() {
    }

    @FXML
    private void goToProduitsView(ActionEvent event) throws IOException {
        Parent produitsParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("produits.fxml")));
        Scene produitsScene = new Scene(produitsParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(produitsScene);
        window.show();
    }

    public void goToLivreurView(ActionEvent actionEvent) {
    }
}
