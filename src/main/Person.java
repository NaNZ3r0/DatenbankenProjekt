package main;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Person extends Model{
    public String name;
    public int id = -1;
    public String email;
    public Person (int ID){
        super();
        loadData(ID);
    }
    public Person (int ID, Connection connection){
        super(connection);
        loadData(ID);
    }

    private void loadData(int ID) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = prepareStatement("select Name,Email,ID from Person where ID=?");
            stmt.setInt(1, ID);
            rs = executeQuery(stmt);
            if (rs.next()) {
                name = rs.getString("Name");
                email = rs.getString("Email");
                id = rs.getInt("ID");
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

    public Person (String Name, String Email){
        super();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = prepareStatement("INSERT INTO Person(Name, Email) VALUES (?, ?)");
            stmt.setString(1, Name);
            stmt.setString(2, Email);
            executeUpdate(stmt);
            stmt = prepareStatement("SELECT LAST_INSERT_ID()");
            rs = executeQuery(stmt);
            if (rs.next()){
                id = rs.getInt(1);
                name = Name;
                email = Email;
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
    public void delete(){
        PreparedStatement stmt = null;
        Angestellter angestellter = new Angestellter(this.id);
        angestellter.delete();
        Kunde kunde = new Kunde(this.id);
        kunde.delete();
        try {
            stmt = prepareStatement("DELETE FROM person WHERE ID = ?");
            stmt.setInt(1, id);
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
    public static List <Integer> byName(String namePattern){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            stmt = Person.prepareStatement("Select ID FROM person WHERE name LIKE ?", Person.createConnection());
            stmt.setString(1, namePattern);
            rs = executeQuery(stmt);
            while (rs.next()){
                int id = rs.getInt(1);
                result.add(id);
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
        return result;
    }
    public static List <Integer> byEmail(String emailPattern){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            stmt = Person.prepareStatement("Select ID FROM person WHERE email LIKE ?", Person.createConnection());
            stmt.setString(1, emailPattern);
            rs = executeQuery(stmt);
            while (rs.next()){
                int id = rs.getInt(1);
                result.add(id);
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
        return result;
    }
}
