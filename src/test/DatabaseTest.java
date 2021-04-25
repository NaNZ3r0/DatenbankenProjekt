
import main.Person;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static main.Model.createConnection;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    @Test
    void firstTest() {
        Person p = new Person(203);
        assertEquals( "Kiona", p.name);
    }
    @Test
    void connectionTest(){
        Connection c = createConnection();
        assertNotNull(c);
    }
    @Test
    void insertAndDeleteTest(){
        Person person = new Person("UnitTest", "felix.franke.nr4@gmail.com");
        assertTrue(-1<person.id);
        person.delete();
    }
    @Test
    void findAllPersons(){
        List<Integer> ids = Person.byName("%");
        assertTrue(ids.size() > 0);
    }

}
