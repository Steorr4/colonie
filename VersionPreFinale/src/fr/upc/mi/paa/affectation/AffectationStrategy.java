package fr.upc.mi.paa.affectation;

import fr.upc.mi.paa.colonie.Colonie;

public interface AffectationStrategy {
	void affecterRessources(Colonie colonie) throws Exception;
}
