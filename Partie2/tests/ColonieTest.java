

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.upc.mi.Colonie;
import fr.upc.mi.Crewmate;
import fr.upc.mi.Ressource;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ColonieTest {
    private Colonie colonie;
    private List<Crewmate> crewmateList;
    private List<Ressource> ressourceList;

    @BeforeEach
    void setUp() {
        ressourceList = new ArrayList<>(List.of(
            new Ressource("R1"),
            new Ressource("R2"),
            new Ressource("R3")
        ));
        Crewmate c1 = new Crewmate("A");
        c1.setPreferences(List.of(ressourceList.get(0), ressourceList.get(1)));
        Crewmate c2 = new Crewmate("B");
        c2.setPreferences(List.of(ressourceList.get(1), ressourceList.get(2)));
        Crewmate c3 = new Crewmate("C");
        c3.setPreferences(List.of(ressourceList.get(2), ressourceList.get(0)));
        crewmateList = new ArrayList<>(List.of(c1, c2, c3));
        colonie = new Colonie(crewmateList, ressourceList);
    }

 

    @Test
    void testAlgoAffectation() {
        colonie.algoAffectation(crewmateList);
        assertNotNull(crewmateList.get(0).getrAssigned());
        assertNotNull(crewmateList.get(1).getrAssigned());
        assertNotNull(crewmateList.get(2).getrAssigned());
    }

    @Test
    void testAlgoAffectationOptimise() {
        colonie.algoAffectationOptimise(100);
        assertNotNull(crewmateList.get(0).getrAssigned());
        assertNotNull(crewmateList.get(1).getrAssigned());
        assertNotNull(crewmateList.get(2).getrAssigned());
    }

    @Test
    void testNbEnvious() throws Exception {
        colonie.affectationAuto();
        assertEquals(0, colonie.nbEnvious());
    }

    @Test
    void testSetEnviousList() throws Exception {
        colonie.affectationAuto();
        colonie.setEnviousList();
        assertEquals(0, colonie.nbEnvious());
    }

   

    

    @Test
    void testPrintEnviousCrewmates() throws Exception {
        colonie.affectationAuto();
        assertDoesNotThrow(() -> colonie.printEnviousCrewmates());
    }

  

    @Test
    void testGetCrewmateList() {
        List<Crewmate> result = colonie.getCrewmateList();
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("A", result.get(0).getName());
        assertEquals("B", result.get(1).getName());
        assertEquals("C", result.get(2).getName());
    }

    @Test
    void testGetRessourceList() {
        List<Ressource> result = colonie.getRessourceList();
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("R1", result.get(0).getRessource());
        assertEquals("R2", result.get(1).getRessource());
        assertEquals("R3", result.get(2).getRessource());
    }


    @Test
    void testSetRAsigned() throws Exception {
        String filePath = "test_assign.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("A:R1\n");
            writer.write("B:R2\n");
            writer.write("C:R3\n");
        }
        colonie.setRAsigned(filePath);
        assertEquals("R1", crewmateList.get(0).getrAssigned().getRessource());
        assertEquals("R2", crewmateList.get(1).getrAssigned().getRessource());
        assertEquals("R3", crewmateList.get(2).getrAssigned().getRessource());
        new File(filePath).delete();
    }
}
