package main;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Kunde extends Model{
    Person person;
    int rating;
    String firma;
    public Kunde (int id){
        super();
        person = new Person(id);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = prepareStatement("select firma,rating,id from kunde where ID=?");
            stmt.setInt(1, person.id);
            rs = executeQuery(stmt);
            if (rs.next()) {
                firma = rs.getString("firma");
                rating = rs.getInt("rating");
            }
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally{
            cleanup(stmt, rs);
        }
    }
    public Kunde (String firma, Person person){
        super();
        PreparedStatement stmt = null;
        try {
            stmt = prepareStatement("INSERT INTO kunde(firma, id) VALUES (?, ?)");
            stmt.setString(1, firma);
            stmt.setInt(2, person.id);
            executeUpdate(stmt);
            this.person = person;
            this.firma = firma;
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally{
            cleanup(stmt);
        }
    }
    public void delete(){
        PreparedStatement stmt = null;
        if (person == null){
            return;
        }
        try {
            stmt = prepareStatement("DELETE FROM kunde WHERE ID = ?");
            stmt.setInt(1, person.id);
            executeUpdate(stmt);
        }
        catch (SQLException ex){
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally{
            cleanup(stmt);
        }
    }
}
