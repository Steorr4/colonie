

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import fr.upc.mi.Colonie;
import fr.upc.mi.ColonieSetup;

public class ColonieSetupTest {
	// a changer 
	private String path = "C:\\Users\\sioua\\OneDrive\\Bureau\\LICENCE\\JAVA\\ProjetAmongUs\\ProjetPAA\\tests\\fichiersTests\\";

	@Test
    public void testSetUp_ValidFile() {
        assertTrue(ColonieSetup.verifFichier(path+"validFile.txt")); 
        ColonieSetup cs=new ColonieSetup();
        try {
			Colonie colonie=cs.setUp(path+"validFile.txt");
			assertEquals(colonie.getCrewmateList().size(),2);
			assertEquals(colonie.getRessourceList().size(),2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        
        
        
    } 
	 @Test
	 public void testSetUp_InexistantFile() {
	     assertFalse(ColonieSetup.verifFichier(path+"toto.txt"));
	     ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"toto.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	        
	 }
	 
    @Test
    public void testSetUp_InvalidColonLineFormat() {
        assertFalse(ColonieSetup.verifFichier(path+"testInvalidColonLineFormat.txt"));
        ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"testInvalidColonLineFormat.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testSetUp_InvalidRessourceLineFormat() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testInvalidRessourceLineFormat.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"testInvalidRessourceLineFormat.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    //@Test
    public void testSetUp_InvalidDetesteLineFormat() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testInvalidDetesteLineFormat.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"testInvalidDetesteLineFormat.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testSetUp_InvalidPreferencesLineFormat() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testInvalidPreferencesLineFormat.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"testInvalidPreferencesLineFormat.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testSetUp_FileOutOfOrder() {
    	System.out.println("hello");
    	 assertFalse(ColonieSetup.verifFichier(path+"testFileOutOfOrder.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"testFileOutOfOrder.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testSetUp_MissingPreferences() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testMissingPreferences.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"testMissingPreferences.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testSetUp_EmptyFile() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testEmptyFile.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"testEmptyFile.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

  

    @Test
    public void testSetUp_DuplicateRessource() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testDuplicateRessource.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"testDuplicateRessource.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testSetUp_InvalidRessourceReference() {
    	 assertFalse(ColonieSetup.verifFichier(path+" testInvalidRessourceReference.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+" testInvalidRessourceReference.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

   @Test
    public void testSetUp_InvalidColonReference() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testInvalidColonReference.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"testInvalidColonReference.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
       
    }

   @Test
    public void testSetUp_WrongNbArguments() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testWrongNbArguments.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.setUp(path+"testWrongNbArguments.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
}
