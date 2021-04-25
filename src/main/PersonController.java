package main;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonController {
    public Person user;

    public PersonController(int userid) {
        user = new Person(userid);
    }

    public Person createPerson(String name, String email) throws ValueException {
        Person p = null;
        if (name.length() < 1) {
            throw new ValueException("name darf nicht leer sein");
        }
        if (name.contains(" ")) {
            throw new ValueException("name kann keine Leerzeichen enthalten");
        }
        if (!email.contains("@")) {
            throw new ValueException("Email muss gültig sein");
        }
        if (email.length() < 3) {
            throw new ValueException("Email muss länger als drei zeichen sein");
        }
        p = new Person(name, email);
        return p;
    }

    public void deletePerson(Person person) {
        Kunde kunde = new Kunde(person.id);
        kunde.delete();
        Angestellter angestellter = new Angestellter(person.id);
        angestellter.delete();
        person.delete();
    }

    public Kunde makeKunde(String firma, Person person) throws ValueException {
        if (firma.length() < 1) {
            throw new ValueException("Firmenname darf nicht leer sein");
        }
        if (firma.contains(" ")) {
            throw new ValueException("name kann keine Leerzeichen enthalten");
        }
        if (person.id < -1) {
            throw new ValueException("darf nicht negativ sein");
        }
        Kunde kunde = new Kunde(firma, person);
        return kunde;
    }
    public Angestellter makeAngestellter(int gehalt, int rating, int tid, Person person) throws ValueException {
        if (gehalt != rating * 1000){
            throw new ValueException("Gehalt muss dem Rating * 1000 entsprechen");
        }

        if (rating < 1 || rating > 10){
            throw new ValueException("rating muss gültig sein");
        }
        Angestellter angestellter = new Angestellter(gehalt, rating, tid, person);
        return angestellter;
    }

    public void deleteKunde(Kunde kunde) {
        kunde.delete();
    }
    public void deleteAngestellter(Angestellter angestellter) {
        angestellter.delete();
    }
    public List<Integer> findePersonen(String namePattern, String emailPattern) throws ValueException {
        List<Integer> byName = new ArrayList<Integer>();
        List<Integer> byEmail = new ArrayList<Integer>();
        List<Integer> result = null;
        if (namePattern == null && emailPattern == null){
            throw new ValueException("Darf nicht beides leer sein");
        }
        if (namePattern != null){
            if (namePattern.length() < 1) {
                throw new ValueException("name darf nicht leer sein");
            }
            byName = Person.byName(namePattern);
            result = byName;
        }
        if (emailPattern != null){
            if (emailPattern.length() < 1) {
                throw new ValueException("email darf nicht leer sein");
            }
            byEmail = Person.byEmail(emailPattern);
            result = byEmail;
        }
        if (namePattern != null && emailPattern != null) {
            result = byName.stream()
                    .filter(byEmail::contains)
                    .collect(Collectors.toList());
        }
        return result;
    }
    public List<Person> personenListe(List<Integer> ids){
        List<Person> personen= new ArrayList<Person>();
        Connection connection = Person.createConnection();
        for (int i : ids) {
            personen.add(new Person (i, connection));
        }
        return personen;
    }
    public void delete(Person person){
        int id = person.id;

    }
}
