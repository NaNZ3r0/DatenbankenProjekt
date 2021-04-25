import main.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonControllerTest {
    @Test
    void inputValidation() {
        PersonController pc = new PersonController(0);
        assertThrows(ValueException.class, () -> {
            pc.createPerson("", "das.haus@gmail.com");
        });
        assertThrows(ValueException.class, () -> {
            pc.createPerson("paul", "das.hausgmail.com");
        });
    }

    @Test
    void personErstellen() {
        PersonController pc = new PersonController(0);
        Person p = null;
        try {
            p = pc.createPerson("UnitTest", "felix.franke.nr4@gmail.com");
        } catch (ValueException e) {
            System.out.println(e.message);
        }
        assertTrue(-1 < p.id);
        pc.deletePerson(p);
    }

    @Test
    void kundeMachen() {
        PersonController pc = new PersonController(0);
        Person person = pc.user;
        Kunde kunde = null;
        Person doesNotExist = new Person(-1);
        assertThrows(ValueException.class, () -> {
            pc.makeKunde("", person);
        });
        assertThrows(ValueException.class, () -> {
            pc.makeKunde("paul", doesNotExist);
        });
        try {
            kunde = pc.makeKunde("MehrSchalterBrauchendeFirma", person);
        } catch (ValueException e) {
            e.printStackTrace();
        }
        pc.deleteKunde(kunde);
    }

    @Test
    void personenFinden() {
        PersonController pc = new PersonController(0);
        List<Integer> list = null;
        try {
            list = pc.findePersonen("%f%", "%nr4@%");
        } catch (ValueException e) {
            e.printStackTrace();
        }
        for (int i : list) {
            System.out.println(i);
        }
        assertTrue(list.size() == 1);
        assertTrue(list.contains(303));
    }

    @Test
    void personenListeFelix() {
        PersonController pc = new PersonController(0);
        List<Person> list = null;
        try {
            list = pc.personenListe(pc.findePersonen("%e%", "%nr4@%"));
        } catch (ValueException e) {
            e.printStackTrace();
        }
        for (Person i : list) {
            System.out.println(i.email);
        }
        assertTrue(list.size() == 1);
        assertTrue(list.get(0).name.equals("Felix"));
    }

    @Test
    void personenListeAlle() {
        PersonController pc = new PersonController(0);
        List<Person> list = null;
        try {
            list = pc.personenListe(pc.findePersonen("%", "%"));
        } catch (ValueException e) {
            e.printStackTrace();
        }
        for (Person i : list) {
            System.out.println(i.email);
        }
        assertTrue(list.size() > 1);
    }

    @Test
    void personenLöschen() {
        PersonController pc = new PersonController(0);
        Person person = new Person("löschen", "diesen.löschen@del.com");
        try {
            pc.makeKunde("gelöschteFirma", person);
        } catch (ValueException e) {
            e.printStackTrace();
        }
        try {
            pc.makeAngestellter(5000, 5, 6, person);
        } catch (ValueException e) {
            e.printStackTrace();
        }
        person.delete();
    }

    @Test
    void angestellterMachen() {
        PersonController pc = new PersonController(303);
        Person person = pc.user;
        Angestellter angestellter = null;
        Person doesNotExist = new Person(-1);
        try {
            angestellter = pc.makeAngestellter(5000, 5, 7, person);
        } catch (ValueException e) {
            e.printStackTrace();
        }
        pc.deleteAngestellter(angestellter);
    }

    @Test
    void einfachePersonenLöschen() {
        PersonController pc = new PersonController(0);
        Person person = new Person("löschen", "diesen.löschen@del.com");
        person.delete();
    }
}
