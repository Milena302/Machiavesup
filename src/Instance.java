import java.util.ArrayList;

import java.util.*;

public class Instance {
    // Un jeu de données sur lequel on va exécuter l'algorithme de Gale-Shapley

    ArrayList<Proposant> proposants;
    ArrayList<Disposant> disposants;


    Instance(int size, int seed) {
        disposants = new ArrayList<>();
        proposants = new ArrayList<>();

        //Creation des disposants et des proposants
        for (int i = 0; i < size; i++) {
            Disposant newDisposant = new Disposant(i, seed);
            seed++;
            Proposant newProposant = new Proposant(i, seed);
            seed++;
            disposants.add(newDisposant);
            proposants.add(newProposant);
        }

        //Remplissage des listes de souhaits
        for (int i = 0; i < size; i++) {
            disposants.get(i).genereListeSouhaits(proposants);
            proposants.get(i).genereListeSouhaits(disposants);
        }

    }

    Mariages runGS() {
        // TODO!!!
        return null;
        // méthodes à utiliser
        // proposant.appelleSuivant
        // disposant.prefere
        // mariage.ajouteCouple et mariage.retireCouple et mariage.conjoint et le constructeur
    }


    void affiche() {
        //Affichage des disposants
        for (Disposant d : disposants) {
            System.out.printf("%s : %s\n", d, d.getSouhaits());
        }

        //Affichage des proposants
        for (Proposant p : proposants) {
            System.out.printf("%s : %s\n", p, p.getSouhaits());
        }
    }


}
