package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import tikape.runko.domain.ReseptiAinesosa;

public class ReseptiAinesosaDao implements Dao<ReseptiAinesosa, Integer> {

    private Database database;

    public ReseptiAinesosaDao(Database database) {
        this.database = database;
    }

    @Override
    public ReseptiAinesosa findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<ReseptiAinesosa> findByResepti(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ReseptiAinesosa WHERE resepti_id=?");
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();

        List<ReseptiAinesosa> ainesosat = new ArrayList<>();

        while (rs.next()) {
            ReseptiAinesosa ra = new ReseptiAinesosa(
                    rs.getInt("ainesosa_id"),
                    rs.getInt("resepti_id"),
                    rs.getInt("jarjestys"),
                    rs.getString("maara"),
                    rs.getString("ohje"));
            ainesosat.add(ra);
        }
        
        ainesosat.sort((o1, o2) -> o1.getJarjestys().compareTo(o2.getJarjestys()));

        if(ainesosat.isEmpty()) {
            return null;
        } else {
            return ainesosat;
        }
       
    }
    
    public void saveOrUpdate(ReseptiAinesosa reseptiAinesosa) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ReseptiAinesosa WHERE resepti_id = ? and ainesosa_id = ?");
        stmt.setInt(1, reseptiAinesosa.getReseptiId());
        stmt.setInt(2, reseptiAinesosa.getAinesosaId());
        ResultSet rs = stmt.executeQuery();
        
        if(!rs.next()) {
           stmt = conn.prepareStatement("INSERT INTO ReseptiAinesosa (ainesosa_id, resepti_id, jarjestys, maara, ohje) VALUES (?,?,?,?,?)");
           stmt.setInt(1, reseptiAinesosa.getAinesosaId());
           stmt.setInt(2, reseptiAinesosa.getReseptiId());
           stmt.setInt(3, reseptiAinesosa.getJarjestys());
           stmt.setString(4, reseptiAinesosa.getMaara());
           stmt.setString(5, reseptiAinesosa.getOhje());
           stmt.executeUpdate();
        } else {
            update(reseptiAinesosa);
        }
    }
    
    private void update(ReseptiAinesosa reseptiAinesosa) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE ReseptiAinesosa SET jarjestys = ?, maara = ?, ohje = ? WHERE resepti_id = ?");
        stmt.setInt(1, reseptiAinesosa.getJarjestys());
        stmt.setString(2, reseptiAinesosa.getMaara());
        stmt.setString(3, reseptiAinesosa.getOhje());
        stmt.setInt(4, reseptiAinesosa.getReseptiId());
    }

    @Override
    public List<ReseptiAinesosa> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
