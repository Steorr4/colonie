package fr.upc.mi.paa.colonie;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ColonieSetupTest {
	// a changer 
	private String path = "./tests/fichiersTests/";

	@Test
    public void testFileSetUp_ValidFile() {
        assertTrue(ColonieSetup.verifFichier(path+"validFile.txt")); 
        ColonieSetup cs=new ColonieSetup();
        try {
			Colonie colonie=cs.fileSetUp(path+"validFile.txt");
			assertEquals(colonie.getCrewmateList().size(),2);
			assertEquals(colonie.getRessourceList().size(),2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        
        
        
    } 
	 @Test
	 public void testFileSetUp_InexistantFile() {
	     assertFalse(ColonieSetup.verifFichier(path+"toto.txt"));
	     ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"toto.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	        
	 }
	 
    @Test
    public void testFileSetUp_InvalidColonLineFormat() {
        assertFalse(ColonieSetup.verifFichier(path+"testInvalidColonLineFormat.txt"));
        ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"testInvalidColonLineFormat.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testFileSetUp_InvalidRessourceLineFormat() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testInvalidRessourceLineFormat.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"testInvalidRessourceLineFormat.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    //@Test
    public void testFileSetUp_InvalidDetesteLineFormat() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testInvalidDetesteLineFormat.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"testInvalidDetesteLineFormat.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testFileSetUp_InvalidPreferencesLineFormat() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testInvalidPreferencesLineFormat.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"testInvalidPreferencesLineFormat.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testFileSetUp_FileOutOfOrder() {
    	System.out.println("hello");
    	 assertFalse(ColonieSetup.verifFichier(path+"testFileOutOfOrder.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"testFileOutOfOrder.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testFileSetUp_MissingPreferences() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testMissingPreferences.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"testMissingPreferences.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testFileSetUp_EmptyFile() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testEmptyFile.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"testEmptyFile.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

  

    @Test
    public void testFileSetUp_DuplicateRessource() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testDuplicateRessource.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"testDuplicateRessource.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

    @Test
    public void testFileSetUp_InvalidRessourceReference() {
    	 assertFalse(ColonieSetup.verifFichier(path+" testInvalidRessourceReference.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+" testInvalidRessourceReference.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }

   @Test
    public void testFileSetUp_InvalidColonReference() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testInvalidColonReference.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"testInvalidColonReference.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
       
    }

   @Test
    public void testFileSetUp_WrongNbArguments() {
    	 assertFalse(ColonieSetup.verifFichier(path+"testWrongNbArguments.txt"));
    	 ColonieSetup cs=new ColonieSetup();
	     try {
			assertEquals(cs.fileSetUp(path+"testWrongNbArguments.txt"),null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
}
