package ma.fstt.trackingl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;

public class HelloController implements Initializable {

    @FXML
    private TextField nom;

    @FXML
    private TextField tele;

    @FXML
    private TableView<Livreur> mytable;

    @FXML
    private TableColumn<Livreur, Long> col_id;

    @FXML
    private TableColumn<Livreur, String> col_nom;

    @FXML
    private TableColumn<Livreur, String> col_tele;

    @FXML
    private TableColumn<Livreur, Void> col_edit;

    @FXML
    private TableColumn<Livreur, Void> col_delete;

    private final LivreurDAO livreurDAO = new LivreurDAO();

    public HelloController() throws SQLException {
    }

    @FXML
    protected void onSaveButtonClick() {
        try {
            Livreur livreur = new Livreur(0L, nom.getText(), tele.getText());
            livreurDAO.save(livreur);
            updateTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTable() {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id_livreur"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_tele.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        ObservableList<Livreur> livreurList = getLivreurList();

        col_delete.setCellFactory(column -> new TableCell<Livreur, Void>() {
            final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    try {
                        Livreur livreur = getTableView().getItems().get(getIndex());
                        livreurDAO.delete(livreur);
                        livreurList.remove(livreur);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        col_edit.setCellFactory(column -> new TableCell<Livreur, Void>() {
            final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    Livreur livreur = getTableView().getItems().get(getIndex());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
                    try {
                        Parent root = loader.load();
                        EditController controller = loader.getController();
                        controller.setLivreur(livreur);
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                        if (controller.isUpdate()) {
                            livreurDAO.update(livreur);
                            updateTable();
                        }
                    } catch (IOException | SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {

                    setGraphic(editButton);
                }
            }
        });
        // add edit button event handler
        col_edit.setCellFactory(column -> {
            return new TableCell<Livreur, Void>() {
                final Button editButton = new Button("Edit");

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        editButton.setOnAction(event -> {
                            Livreur livreur = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit.fxml"));
                            try {
                                Parent root = loader.load();
                                EditController controller = loader.getController();
                                controller.setLivreur(livreur);
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.showAndWait();
                                if (controller.isUpdate()) {
                                    livreurDAO.update(livreur);
                                    updateTable();
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        setGraphic(editButton);
                        setText(null);
                    }
                }
            };
        });


        mytable.setItems(livreurList);
    }

    private ObservableList<Livreur> getLivreurList() {
        try {
            return FXCollections.observableArrayList(livreurDAO.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTable();
    }
 @FXML
    private void goToProduitsView(ActionEvent event) throws IOException {
        Parent produitsParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("produits.fxml")));
        Scene produitsScene = new Scene(produitsParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(produitsScene);
        window.show();
    }

    @FXML
    private void goToCommandeView(ActionEvent event) throws IOException {
        Parent commandeParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("commande.fxml")));
        Scene commandeScene = new Scene(commandeParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(commandeScene);
        window.show();
    }
}
