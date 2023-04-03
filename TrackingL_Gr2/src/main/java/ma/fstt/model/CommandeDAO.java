package ma.fstt.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CommandeDAO extends BaseDAO<Commande> {

    public CommandeDAO() throws SQLException {
        super();
    }

    public List<Commande> getAllCommande() throws SQLException {
        List<Commande> commandes = new ArrayList<>();

        String query = "SELECT * FROM commande";

        this.statement = this.connection.createStatement();
        this.resultSet = this.statement.executeQuery(query);

        while (this.resultSet.next()) {
            long id = this.resultSet.getLong("id");
            String client = this.resultSet.getString("client");
            Date dateDebut = new Date(this.resultSet.getTimestamp("date_debut").getTime());
            Date dateFin = new Date(this.resultSet.getTimestamp("date_fin").getTime());
            String etat = this.resultSet.getString("etat");
            Commande commande = new Commande(id, dateDebut, dateFin, client,etat);
            commandes.add(commande);
        }

        return commandes;
    }



    @Override
    public void save(Commande object) throws SQLException {
        String request = "INSERT INTO commande (date_debut, date_fin, client, etat) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(request)) {
            stmt.setDate(1, new java.sql.Date(object.getDate_debut().getTime()));
            stmt.setDate(2, new java.sql.Date(object.getDate_fin().getTime()));
            stmt.setString(3, object.getClient());
            stmt.setString(4, object.getEtat());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void update(Commande object) throws SQLException {
        String request = "UPDATE commande SET date_debut=?, date_fin=?, client=?, etat=? WHERE id_commande=?";

        try (PreparedStatement stmt = connection.prepareStatement(request)) {
            stmt.setDate(1, new java.sql.Date(object.getDate_debut().getTime()));
            stmt.setDate(2, new java.sql.Date(object.getDate_fin().getTime()));
            stmt.setString(3, object.getClient());
            stmt.setString(4, object.getEtat());
            stmt.setLong(5, object.getId_command());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void delete(Commande object) throws SQLException {
        String request = "DELETE FROM commande WHERE id_commande=?";

        try (PreparedStatement stmt = connection.prepareStatement(request)) {
            stmt.setLong(1, object.getId_command());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public List<Commande> getAll() throws SQLException {
        List<Commande> listCommandes = new ArrayList<>();

        String request = "SELECT * FROM commande";

        try (PreparedStatement stmt = connection.prepareStatement(request)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listCommandes.add(new Commande(rs.getLong("id_commande"),rs.getDate("date_debut"),rs.getDate("date_fin"), rs.getString("client"), rs.getString("etat")));

                }
            }
        } catch (SQLException ex) {
            throw ex;
        }

        return listCommandes;
    }

    @Override
    public Commande getOne(Long id) throws SQLException {
        Commande commande = null;

        String request = "SELECT * FROM commande WHERE id_commande=?";

        try (PreparedStatement stmt = connection.prepareStatement(request)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    commande = new Commande(rs.getLong("id_commande"),rs.getDate("date_debut"),rs.getDate("date_fin"), rs.getString("client"), rs.getString("etat"));
                }
            }
        } catch (SQLException ex) {
            throw ex;
        }

        return commande;
    }
}
