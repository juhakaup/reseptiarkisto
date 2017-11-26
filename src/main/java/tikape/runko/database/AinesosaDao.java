package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Ainesosa;

public class AinesosaDao implements Dao<Ainesosa, Integer>{
    
    private Database database;
    
    public AinesosaDao(Database database) {
        this.database = database;
    }

    @Override
    public Ainesosa findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Ainesosa WHERE id=?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next()) {
            return null;
        }
        
        Ainesosa ainesosa = new Ainesosa(rs.getInt("id"), rs.getString("nimi"));
        
        return ainesosa;
    }

    @Override
    public List<Ainesosa> findAll() throws SQLException {
        List<Ainesosa> ainesosat = new ArrayList<>();
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Ainesosa");
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            Ainesosa a = new Ainesosa(rs.getInt("id"), rs.getString("nimi"));
            ainesosat.add(a);
        }
        
        return ainesosat;
    }
    
    public void SaveOrUpdate (Ainesosa ainesosa) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Ainesosa WHERE nimi=?");
        stmt.setString(1, ainesosa.getNimi());
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            stmt = conn.prepareStatement("");
        } else {
            stmt = conn.prepareStatement("INSERT INTO Ainesosa (nimi) VALUES (?)");
            stmt.setString(1, ainesosa.getNimi());
            stmt.executeUpdate();
        }
        
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Ainesosa WHERE id = ?");
        stmt.setInt(1, key);
        stmt.execute();
        
        stmt.close();
        conn.close();
    }
 
}
