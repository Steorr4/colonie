package fr.upc.mi.paa.colonie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class CrewmateTest {

    private Crewmate crewmate;
    private Ressource ukulele;
    private Ressource food;

    @BeforeEach
    void setUp() {
        crewmate = new Crewmate("nina");
        ukulele = new Ressource("ukulele");
        food = new Ressource("food");
    }

    @Test
    void testConstructor() {
        assertNotNull(crewmate);
        assertEquals("nina", crewmate.getName());
        assertFalse(crewmate.isEnvious());
        assertNull(crewmate.getrAssigned());
        assertTrue(crewmate.getPreferences().isEmpty());
        assertTrue(crewmate.getRelations().isEmpty());
    }

    @Test
    void testAddRelation() {
        Crewmate friend = new Crewmate("Alice");
        crewmate.addRelation(friend);

        assertEquals(1, crewmate.getRelations().size());
        assertTrue(crewmate.getRelations().contains(friend));
    }

    @Test
    void testSetAndGetEnvious() {
        crewmate.setEnvious(true);
        assertTrue(crewmate.isEnvious());

        crewmate.setEnvious(false);
        assertFalse(crewmate.isEnvious());
    }

    @Test
    void testSetAndGetRessourceAssigned() {
        crewmate.setrAssigned(ukulele);
        assertEquals(ukulele, crewmate.getrAssigned());
    }

    @Test
    void testSetAndGetPreferences() {
        crewmate.setPreferences(Arrays.asList(ukulele, food));
        assertEquals(2, crewmate.getPreferences().size());
        assertTrue(crewmate.getPreferences().contains(ukulele));
        assertTrue(crewmate.getPreferences().contains(food));
    }

    @Test
    void testCompareTo() {
        Crewmate crewmate2 = new Crewmate("nicholas");
        crewmate.addRelation(new Crewmate("toto"));
        crewmate.addRelation(new Crewmate("bobo"));

        assertTrue(crewmate.compareTo(crewmate2) <0);
    }

    @Test
    void testToString() {
        assertEquals("Crewmate{name='nina'}", crewmate.toString());
    }
}
