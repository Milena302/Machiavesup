import java.util.ArrayList;

import java.util.*;

public class Instance {
    // Un jeu de données sur lequel on va exécuter l'algorithme de Gale-Shapley

    ArrayList<Proposant> proposants;
    ArrayList<Disposant> disposants;


    Instance(int size, int seed) {
        disposants = new ArrayList<>();
        proposants = new ArrayList<>();

        //Remplissage des disposants
        for (int i = 0; i < size; i++) {
            Disposant newDisposant = new Disposant(i, size, seed);
            newDisposant.genererListeSouhait();
            seed++;
            disposants.add(newDisposant);
        }

        //Remplissage des proposants
        for (int i = 0; i < size; i++) {
            Proposant newProposants = new Proposant(i, size, seed);
            newProposants.getPref();
            seed++;
            proposants.add(newProposants);
        }

        //Tri par ordre croissant des pos dans chaque liste de préférences
        for (Proposant p : proposants) {
            p.trigetCouples();
        }
        for (Disposant d : disposants) {
            d.getCouples();
        }
    }

    Mariages runGS() {
        // TODO!!!
    }


    void affiche() {
        //Affichage des disposants
        for (Disposant d : disposants) {
            System.out.println("Disposant " + d.getId() + ": " + Arrays.toString(d.getCouples()));
        }

        //Affichage des proposants
        System.out.println("Contenu des proposants : ");
        for (Proposant p : proposants) {
            System.out.println("Proposant " + p.getId() + ": " + Arrays.toString(p.trigetCouples()));
        }        
    }


}
