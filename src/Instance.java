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


    //Cas ou on considere toutes les possibilites
    public Mariages runGSMeilleur(int taille) {
        ArrayList<Proposant> permut = new ArrayList<>(proposants);
        //System.out.println("Il y a " + permut.size() + "proposants");
        ArrayList<ArrayList<Proposant>> toutesPermutations = permute(permut);
        //System.out.println("Il y a " + toutesPermutations.size() + "permutations");
        Mariages meilleurMariage = null;
        int scoreMax = Integer.MAX_VALUE;
        int i = 0;
        for (ArrayList<Proposant> permutCourant : toutesPermutations) {
            Mariages mariageCourant = calculeMariage(permutCourant, taille);
            int scoreCourant = calculeScoreProposants(mariageCourant);
            if (scoreCourant < scoreMax) {
                scoreMax = scoreCourant;
                meilleurMariage = mariageCourant;
            }
            //System.out.println("i vaut : " + i);
            i++;
        }
        //System.out.println("meilleur mariage");
        return meilleurMariage;
    }

    private Mariages calculeMariage(ArrayList<Proposant> permut, int taille) {
        //System.out.println("permut vaut " + permut.size());
        Mariages couples = new Mariages();
        ArrayList<Proposant> celibataires = new ArrayList<>(permut);
        //System.out.println("Il y a " + celibataires.size() + " celibataires");
        while (couples.size() < taille) {
            //System.out.println("Il y a " + couples.size() + " mariages");
            //System.out.println("Il y a " + celibataires.size() + " celibataires");
            //System.out.println("permut size vaut" + permut.size());
            Proposant p = celibataires.get(0);
            Disposant d = p.appelleSuivant(taille);
            if (d == null) {
                celibataires.remove(p);
            } else if (!couples.couples.containsKey(p) && !couples.couples.containsValue(d)) {
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
                p.reinitialise();
            } else if (d.prefere(p, couples.conjoint(d))) { // Si d préfère p à son conjoint actuel
                Proposant ancienConjoint = couples.conjoint(d);
                couples.retireCouple(ancienConjoint);
                couples.retireCouple(d);
                celibataires.add(ancienConjoint);
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
                p.reinitialise();
            }
            //System.out.println("Apres les ifs");
            //System.out.println("Il y a " + celibataires.size() + " celibataires");
            //System.out.println("Il y a " + couples.size() + " mariages\n");
            if (celibataires.size() == 0){
                break;
            }
        }
        //System.out.println("Avant le return : " + couples.size());
        return couples;
    }


    public static <T> ArrayList<ArrayList<T>> permute(ArrayList<T> input) {
        ArrayList<ArrayList<T>> output = new ArrayList<ArrayList<T>>();

        if (input.size() == 0) {
            output.add(new ArrayList<T>());
            return output;
        }

        T head = input.get(0);
        ArrayList<T> rest = new ArrayList<T>(input.subList(1, input.size()));
        ArrayList<ArrayList<T>> permutations = permute(rest);
        for (ArrayList<T> permutation : permutations) {
            for (int i = 0; i <= permutation.size(); i++) {
                ArrayList<T> newPermutation = new ArrayList<T>();
                newPermutation.addAll(permutation);
                newPermutation.add(i, head);
                output.add(newPermutation);
            }
        }
        return output;
    }


    //2 methodes afin de pouvoir evaluer si les mariages sont favorables ou pas
    public int calculeScoreDisposants(Mariages couples) {
        int score = 0;
        for (Disposant d : disposants) {
            Proposant conjoint = couples.conjoint(d);
            int rang = d.getRang(conjoint);
            score += rang;
        }
        return score;
    }

    public int calculeScoreProposants(Mariages couples) {
        int score = 0;
        for (Proposant p : proposants) {
            Disposant conjoint = couples.getConjoint(p);
            if (conjoint != null) {
                int rang = conjoint.getRang(p);
                score += rang;
            }
        }
        return score;
    }


    //Methode afin de determiner si les mariages obtenus sont stables ou pas
    //mariage instable si il existe deux couples (p1,d1), (p2,d2) tels que p1 préfère d2 à d1 *ET* d2 préfère p1 à p2
    public boolean estStable(Mariages couples) {
        if (couples == null) { return false; }
        for (Map.Entry<Proposant, Disposant> couple1 : couples.couples.entrySet()) {
            Proposant p1 = couple1.getKey();
            Disposant d1 = couple1.getValue();
            for (Map.Entry<Proposant, Disposant> couple2 : couples.couples.entrySet()) {
                if (couple1.equals(couple2)) { continue; }
                Proposant p2 = couple2.getKey();
                Disposant d2 = couple2.getValue();
                if (d2.prefere(p1, p2) && p1.prefere(d2, d1)) {
                    System.out.println("Il y a au moins un cas d'infidelite possible entre " + p1.toString() + " et " + d1.toString() + " et " + p2.toString() + " et " + d2.toString());
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

    //2 methodes pour verifier la stabilite des mariages
    //On parcourt les proposants dans le sens inverse
    //Si les resultats sont les memes pour dans l'ordre de passage et son inverse alors cela veut dire que les mariages sont stables
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
