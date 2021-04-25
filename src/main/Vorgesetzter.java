package main;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Vorgesetzter extends Model{
    Person person;
    int vorgesetztenID;
    int gehalt;
    public Vorgesetzter (int id){
        super();
        person = new Person(id);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = prepareStatement("select Gehalt,VorgesetztenID,PersonenID from vorgesetzter where ID=?");
            stmt.setInt(1, person.id);
            rs = executeQuery(stmt);
            if (rs.next()) {
                gehalt = rs.getInt("Gehalt");
                vorgesetztenID = rs.getInt("VorgesetztenID");
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
    public Vorgesetzter (String firma, Person person){
        super();
        PreparedStatement stmt = null;
        try {
            stmt = prepareStatement("INSERT INTO vorgesetzter(Gehalt, VorgesetztenID, PersonenID) VALUES (?, ?)");
            stmt.setInt(1, gehalt);
            stmt.setInt(2, person.id);
            executeUpdate(stmt);
            this.person = person;
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
