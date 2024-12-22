package fr.upc.mi.paa.colonie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class RessourceTest {

		private Ressource ressource;

	    @BeforeEach
	    void setUp() {
	        
	        ressource = new Ressource("toto");
	    }

	    @Test
	    void testConstructor() {
	        assertNotNull(ressource);
	        assertEquals("toto", ressource.getName());
	        assertTrue(ressource.isAvailable());
	    }

	    @Test
	    void testSetAvailable() {
	        
	        ressource.setAvailable(false);
	        assertFalse(ressource.isAvailable());

	        ressource.setAvailable(true);
	        assertTrue(ressource.isAvailable());
	    }

	    

	    @Test
	    void testToString() {
	      
	        String expected = "Ressource{rscName=toto}";
	        assertEquals(expected, ressource.toString(), "La méthode toString() doit renvoyer la bonne chaîne de caractères");
	    }
	}

	


