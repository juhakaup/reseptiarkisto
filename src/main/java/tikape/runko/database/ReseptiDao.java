package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Resepti;

public class ReseptiDao implements Dao<Resepti, Integer> {
    
    private Database database;
    
    public ReseptiDao (Database database) {
        this.database = database;
    }

    @Override
    public Resepti findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Resepti WHERE id=?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next()) {
            rs.close();
            stmt.close();
            conn.close();
        
            return null;
        }
        
        Resepti r = new Resepti(rs.getInt("id"), rs.getString("nimi"), rs.getString("ohje")); 
        
        rs.close();
        stmt.close();
        conn.close();
        return r;
    }

    @Override
    public List<Resepti> findAll() throws SQLException {
        List<Resepti> reseptit = new ArrayList<>();
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Resepti");
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            Resepti r = new Resepti(rs.getInt("id"), rs.getString("nimi"), rs.getString("ohje"));
            reseptit.add(r);
        }
        
        rs.close();
        stmt.close();
        conn.close();
        
        return reseptit;
    }
    
    public void saveOrUpdate(Resepti resepti) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Resepti WHERE nimi=?");
        stmt.setString(1, resepti.getNimi());
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next()) {
           stmt = conn.prepareStatement("INSERT INTO Resepti (nimi, ohje) VALUES (?,?)");
           stmt.setString(1, resepti.getNimi());
           stmt.setString(2, resepti.getOhje());
           stmt.executeUpdate();
        } else {
            rs.close();
            stmt.close();
            conn.close();
            update(resepti);
        }
    }
    
    private void update(Resepti resepti) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Resepti SET nimi = ?, ohje = ? WHERE id = ?");
        stmt.setString(1, resepti.getNimi());
        stmt.setString(2, resepti.getOhje());
        stmt.setInt(3, resepti.getId());
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Resepti WHERE id = ?");
        stmt.setInt(1, key);
        stmt.execute();
        
        stmt.close();
        conn.close();
    }
    
}
