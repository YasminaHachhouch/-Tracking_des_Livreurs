package ma.fstt.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitsDAO extends BaseDAO<Produits> {

    public ProduitsDAO() throws SQLException {
        super();
    }

    @Override
    public void save(Produits object) throws SQLException {
        String sql = "INSERT INTO produit(nom, description) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, object.getNom());
            stmt.setString(2, object.getDescription());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void update(Produits object) throws SQLException {
        String sql = "UPDATE produit SET nom=?, description=? WHERE ID_produit=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, object.getNom());
            stmt.setString(2, object.getDescription());
            stmt.setLong(3, object.getID_produit());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void delete(Produits object) throws SQLException {
        String sql = "DELETE FROM produit WHERE ID_produit = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, object.getID_produit());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public List<Produits> getAll() throws SQLException {
        List<Produits> mylist = new ArrayList<>();

        String sql = "SELECT * FROM produit";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                mylist.add(new Produits(rs.getLong("ID_produit"), rs.getString("nom"), rs.getString("description")));
            }
        } catch (SQLException ex) {
            throw ex;
        }

        return mylist;
    }

    @Override
    public Produits getOne(Long id) throws SQLException {
        Produits produit = null;

        String sql = "SELECT * FROM produit WHERE ID_produit = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produit = new Produits(rs.getLong("ID_produit"), rs.getString("nom"), rs.getString("description"));
            }
        } catch (SQLException ex) {
            throw ex;
        }

        return produit;
    }
}
