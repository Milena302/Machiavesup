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

    //Cas fidelite
    Mariages runGS(int taille) {
        Mariages couples = new Mariages();
        ArrayList<Proposant> celibataires = new ArrayList<>(proposants);
        while (celibataires.size() > 0) {
            Proposant p = celibataires.get(0);
            Disposant d = p.appelleSuivant(taille);
            if (d == null) {
                celibataires.remove(p);
            } else if (!couples.couples.containsKey(p) && !couples.couples.containsValue(d)) {
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
            } else if (d.prefere(p, couples.conjoint(d))) { // Si d préfère p à son conjoint actuel
                Proposant ancienConjoint = couples.conjoint(d);
                couples.retireCouple(ancienConjoint);
                couples.retireCouple(d);
                celibataires.add(ancienConjoint);
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
            }
        }
        return couples;
    }

    //cas volage
    Mariages runGSvolage(int taille) {
        Mariages couples = new Mariages();
        ArrayList<Proposant> celibataires = new ArrayList<>(proposants);
        while (celibataires.size() > 0) {
            Proposant p = celibataires.get(0);
            Disposant d = p.appelleSuivant(taille);
            if (d == null) {
                celibataires.remove(p);
            } else if (!couples.couples.containsKey(p) && !couples.couples.containsValue(d)) {
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
            } else if (d.prefere(p, couples.conjoint(d)) || d.identique(p, couples.conjoint(d))) { // Si d préfère p à son conjoint actuel
                // ou si d n'a pas de preference entre p et son conjoint actuel
                Proposant ancienConjoint = couples.conjoint(d);
                couples.retireCouple(ancienConjoint);
                couples.retireCouple(d);
                celibataires.add(ancienConjoint);
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
            }
        }
        return couples;
    }

    /*
    Mariages runGStoutesLesPossibilites(int taille) {

    }


     */
/*
    int calculeScoreDisposant(Mariages mariages){
        //On va additionner le rang de preference obtenu pour l'ensemble des disposants
        int score = 0;
        for (Disposant d : disposants){
            HashMap<Proposant, Integer> temp =  d.getSouhaits(1);

            }
        return score;
    }



 */

    public int calculeScore(Mariages couples) {
        int score = 0;
        for (Disposant d : disposants) {
            Proposant conjoint = couples.conjoint(d);
            int rang = d.getRang(conjoint);
            score += rang;
        }
        return score;
    }

    public boolean estStable(Mariages couples) {
        for (Map.Entry<Proposant, Disposant> couple : couples.couples.entrySet()) {
            Proposant p = couple.getKey();
            Disposant d = couple.getValue();
            //verifier la fidelite pour d
            for(Proposant autreP : proposants){
                if (autreP == p){
                    continue;
                }
                if (d.prefere(autreP, p)) {
                    System.out.println("Il y a au moins un cas d'infidelite possible pour les disposants");
                    return false;
                }
            }
            //verifier la fidelite pour les proposants
            for (Disposant autreD : disposants){
                if (autreD == d){
                    continue;
                }
                if (p.prefere(autreD, d)){
                    System.out.println("Il y a au moins un cas d'infidelite possible pour les proposants");
                    return false;
                }
            }
        }
        System.out.println("Le mariage est stable");
        return true;
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

    public Mariages runGSRev(int taille) {
        Mariages couples = new Mariages();
        ArrayList<Proposant> celibataires = new ArrayList<>(proposants);
        while (celibataires.size() > 0) {
            Proposant p = celibataires.get(celibataires.size() - 1);
            Disposant d = p.appelleSuivant(taille);
            if (d == null) {
                celibataires.remove(p);
            } else if (!couples.couples.containsKey(p) && !couples.couples.containsValue(d)) {
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
            } else if (d.prefere(p, couples.conjoint(d))) { // Si d préfère p à son conjoint actuel
                Proposant ancienConjoint = couples.conjoint(d);
                couples.retireCouple(ancienConjoint);
                couples.retireCouple(d);
                celibataires.add(ancienConjoint);
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
            }
        }
        return couples;
    }

    public Mariages runGSvolageRev(int taille) {
        Mariages couples = new Mariages();
        ArrayList<Proposant> celibataires = new ArrayList<>(proposants);
        while (celibataires.size() > 0) {
            Proposant p = celibataires.get(celibataires.size() - 1);
            Disposant d = p.appelleSuivant(taille);
            if (d == null) {
                celibataires.remove(p);
            } else if (!couples.couples.containsKey(p) && !couples.couples.containsValue(d)) {
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
            } else if (d.prefere(p, couples.conjoint(d)) || d.identique(p, couples.conjoint(d))) { // Si d préfère p à son conjoint actuel
                // ou si d n'a pas de preference entre p et son conjoint actuel
                Proposant ancienConjoint = couples.conjoint(d);
                couples.retireCouple(ancienConjoint);
                couples.retireCouple(d);
                celibataires.add(ancienConjoint);
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
            }
        }
        return couples;
    }
}
