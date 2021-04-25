package main;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Angestellter extends Model{
    Person person;
    int gehalt;
    int rating;
    int tid;

    public Angestellter(int id){
        super();
        person = new Person(id);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = prepareStatement("select Gehalt,Rating,TeamID from angestellter where PersonenID=?");
            stmt.setInt(1, person.id);
            rs = executeQuery(stmt);
            if (rs.next()) {
                gehalt = rs.getInt("Gehalt");
                rating = rs.getInt("Rating");
                tid = rs.getInt("TeamID");
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
    public Angestellter (int gehalt, int rating, int tid, Person person){
        super();
        PreparedStatement stmt = null;
        try {
            stmt = prepareStatement("INSERT INTO angestellter(Gehalt, Rating, TeamID, PersonenID) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, gehalt);
            stmt.setInt(2, rating);
            stmt.setInt(3, tid);
            stmt.setInt(4, person.id);
            executeUpdate(stmt);
            this.person = person;
            this.gehalt = gehalt;
            this.rating = rating;
            this.tid = tid;
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
            stmt = prepareStatement("DELETE FROM angestellter WHERE PersonenID = ?");
            stmt = prepareStatement("DELETE FROM angestellter WHERE PersonenID = ?");
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
