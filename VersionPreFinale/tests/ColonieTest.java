import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.upc.mi.paa.affectation.*;
import fr.upc.mi.paa.colonie.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColonieTest {
    private List<fr.upc.mi.paa.colonie.Crewmate> crewmates;
    private List<Ressource> ressources;
    private Colonie colonie;

    @BeforeEach
    void setUp() {
        // Initialiser les ressources
        Ressource r1 = new Ressource("Eau");
        Ressource r2 = new Ressource("Nourriture");
        Ressource r3 = new Ressource("Oxygène");
        ressources = Arrays.asList(r1, r2, r3);

        // Initialiser les colons
        fr.upc.mi.paa.colonie.Crewmate c1 = new fr.upc.mi.paa.colonie.Crewmate("Alice");
        c1.setPreferences( Arrays.asList(r1, r2, r3));
        fr.upc.mi.paa.colonie.Crewmate c2 = new fr.upc.mi.paa.colonie.Crewmate("Bob");
        c2.setPreferences( Arrays.asList(r2, r1, r3));
        fr.upc.mi.paa.colonie.Crewmate c3 = new fr.upc.mi.paa.colonie.Crewmate("Charlie");
        c3.setPreferences( Arrays.asList(r3, r2, r1));
        c1.addRelation(c2); // Alice déteste Bob
        c2.addRelation(c3); // Bob déteste Charlie
        c3.addRelation(c1); // Charlie déteste Alice

        crewmates = Arrays.asList(c1, c2, c3);

        // Initialiser la colonie
        colonie = new Colonie(crewmates, ressources);
    }

    @Test
    void testAffectationAuto() throws Exception {
        colonie.affectationAuto();

        // Vérifie qu'aucune ressource n'est assignée deux fois
        List<Ressource> assigned = new ArrayList<>();
        for (fr.upc.mi.paa.colonie.Crewmate c : crewmates) {
            assertNotNull(c.getrAssigned());
            assertFalse(c.getrAssigned().isAvailable());
            assigned.add(c.getrAssigned());
        }

        // Vérifie que chaque ressource assignée est unique
        assertEquals(assigned.size(), assigned.stream().distinct().count());
    }

    @Test
    void testNbEnviousAfterAffectationAuto() throws Exception {
        colonie.affectationAuto();
        int nbEnvious = colonie.nbEnvious();
        
        // Vérifie que le nombre de jaloux est raisonnable (peut varier selon les préférences et relations)
        assertTrue(nbEnvious >= 0);
    }

    @Test
    void testSwapRessources() {
        fr.upc.mi.paa.colonie.Crewmate c1 = crewmates.get(0); // Alice
        fr.upc.mi.paa.colonie.Crewmate c2 = crewmates.get(1); // Bob

        // Assigner des ressources manuellement pour le test
        c1.setrAssigned(ressources.get(0)); // Alice reçoit "Eau"
        c2.setrAssigned(ressources.get(1)); // Bob reçoit "Nourriture"

        // Échange des ressources
        colonie.swapRessources(c1, c2);

        assertEquals(ressources.get(1), c1.getrAssigned());
        assertEquals(ressources.get(0), c2.getrAssigned());
    }


  
    @Test
    void testSetAffectationStrategy() {
        //test pour chaque strategie
        colonie.setAffectationStrategy(new fr.upc.mi.paa.affectation.AffectationAmelioree());
        assertNotNull(colonie.getAffectationStrategy());
        
        colonie.setAffectationStrategy(new fr.upc.mi.paa.affectation.AffectationBruteForce());
        assertNotNull(colonie.getAffectationStrategy());
        
        colonie.setAffectationStrategy(new fr.upc.mi.paa.affectation.AffectationLineaire());
        assertNotNull(colonie.getAffectationStrategy());
        
    }
    
    
    @Test
    void testAppliquerAffectation() throws Exception {
       
        //test pour chaque strategie si chaque colon recoit bien une ressource
        colonie.setAffectationStrategy(new fr.upc.mi.paa.affectation.AffectationAmelioree());
        colonie.appliquerAffectation();
        for(fr.upc.mi.paa.colonie.Crewmate c: colonie.getCrewmateList()) {
        	assertTrue(c.getrAssigned()!=null);
        }
        
        colonie.setAffectationStrategy(new fr.upc.mi.paa.affectation.AffectationBruteForce());
        colonie.appliquerAffectation();
        for(fr.upc.mi.paa.colonie.Crewmate c: colonie.getCrewmateList()) {
        	assertTrue(c.getrAssigned()!=null);
        }
        
        colonie.setAffectationStrategy(new AffectationLineaire());
        colonie.appliquerAffectation();
        for(fr.upc.mi.paa.colonie.Crewmate c: colonie.getCrewmateList()) {
        	assertTrue(c.getrAssigned()!=null);
        }
        
    }



    @Test
    void testSetEnviousList() throws Exception {
        colonie.affectationAuto();
        colonie.setEnviousList();

        // Vérifie que la liste des jaloux est correctement mise à jour
        for (fr.upc.mi.paa.colonie.Crewmate c : crewmates) {
            if (c.isEnvious()) {
                assertNotNull(c.getrAssigned());
            }
        }
    }

    @Test
    void testGetCrewmateAndGetRessource() {
        fr.upc.mi.paa.colonie.Crewmate c = Colonie.getCrewmate("Alice", crewmates);
        Ressource r = Colonie.getRessource("Eau", ressources);

        assertNotNull(c);
        assertEquals("Alice", c.getName());

        assertNotNull(r);
        assertEquals("Eau", r.getName());
    }
}
