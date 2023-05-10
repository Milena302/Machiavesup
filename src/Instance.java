import java.util.ArrayList;

import java.util.*;

public class Instance {
    // Un jeu de données sur lequel on va exécuter l'algorithme de Gale-Shapley
    ArrayList<Proposant> proposants;
    ArrayList<Disposant> disposants;

    /**
     * Constructeur
     * @param size
     * @param seed
     */
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

    /**
     * Gale Shapley avec la strategie fidelite s
     * si meme rang de preference entre 2 hommes, la femme garde son partenaire actuel
     * @return
     */
    Mariages runGS() {
        Mariages couples = new Mariages();
        ArrayList<Proposant> celibataires = new ArrayList<>(proposants);
        while (celibataires.size() > 0) {
            Proposant p = celibataires.get(0);
            Disposant d = p.appelleSuivant();
            if (d == null) {
                celibataires.remove(p);
            } else if (!couples.couples.containsKey(p) && !couples.couples.containsValue(d)) {
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
            } else if (d.prefere(p, couples.conjoint(d))) { // Si d préfère p à son conjoint actuel
                Proposant ancienConjoint = couples.conjoint(d);
                //couples.retireCouple(ancienConjoint);
                couples.retireCouple(d);
                celibataires.add(ancienConjoint);
                couples.ajouteCouple(p, d);
                celibataires.remove(p);
            }
        }
        return couples;
    }

    /**
     * Gale Shapley avec la strategie volage
     * si meme rang de preference entre 2 hommes la femme change de partenaire
     * @return
     */
    Mariages runGSvolage() {
        Mariages couples = new Mariages();
        ArrayList<Proposant> celibataires = new ArrayList<>(proposants);
        while (celibataires.size() > 0) {
            Proposant p = celibataires.get(0);
            Disposant d = p.appelleSuivant();
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


    /**
     * On construit toutes les permutations possibles pour l'ordre de passage des proposants
     * Dans le but de considerer toutes les possibilites
     * Inconvenient : la complexite
     * @param taille
     * @return
     */
    public Mariages runGSMeilleur(int taille) {
        ArrayList<Proposant> permut = new ArrayList<>(proposants);
        ArrayList<ArrayList<Proposant>> toutesPermutations = permute(permut);
        Mariages meilleurMariage = null;
        int scoreMax = Integer.MAX_VALUE;
        for (ArrayList<Proposant> permutCourant : toutesPermutations) {
            Mariages mariageCourant = calculeMariage(permutCourant, taille);
            int scoreCourant = calculeScoreDisposants(mariageCourant);  //Ici on choisit quel groupe favoriser dans son ensemble
            //Soit les proposants soit les disposants
            if (scoreCourant < scoreMax) {
                scoreMax = scoreCourant;
                meilleurMariage = mariageCourant;
            }
        }
        return meilleurMariage;
    }

    public ArrayList<Mariages> runGSTousLesMeilleurs(int taille) {
        ArrayList<Proposant> permut = new ArrayList<>(proposants);
        ArrayList<ArrayList<Proposant>> toutesPermutations = permute(permut);
        ArrayList<Mariages> meilleursMariages = new ArrayList<>();
        int scoreMax = Integer.MAX_VALUE;
        for (ArrayList<Proposant> permutCourant : toutesPermutations) {
            Mariages mariageCourant = calculeMariage(permutCourant, taille);
            int scoreCourant = calculeScoreDisposants(mariageCourant);  //Ici on choisit quel groupe favoriser dans son ensemble
            //Soit les proposants soit les disposants
            if (scoreCourant < scoreMax) {
                scoreMax = scoreCourant;
                meilleursMariages.clear();
                meilleursMariages.add(mariageCourant);
            } else if (scoreCourant == scoreMax) {
                if (!meilleursMariages.contains(mariageCourant)) {  //pour eviter les doublons
                    meilleursMariages.add(mariageCourant);
                }
            }
        }
        return meilleursMariages;
    }


    private Mariages calculeMariage(ArrayList<Proposant> permut, int taille) {
        Mariages couples = new Mariages();
        ArrayList<Proposant> celibataires = new ArrayList<>(permut);
        while (couples.size() < taille) {
            Proposant p = celibataires.get(0);
            Disposant d = p.appelleSuivant();
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
            if (celibataires.size() == 0){
                break;
            }
        }
        return couples;
    }

    /**
     * Methode generant toutes les permutations possibles pour les 2 precedentes methodes
     * @param input
     * @return
     * @param <T>
     */
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

    /**
     * Deux methodes qui vont permettre d'evaluer quel mariage est plus favorable pour l'ensemble des hommes
     * ou l'ensemble des femmes
     * On remarque que le resultat change selon le groupe qui est privilegie
     * @param couples
     * @return
     */
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
            int rang = p.getRang(conjoint);
            score += rang;
        }
        return score;
    }


    /**
     * Methodes pour trouver tous les mariages dont le score est le meilleur possible pour un disposant en particulier
     *
     */


    public void AfficherunGSMeilleursDisposants(int taille) {
        System.out.println("\n\n\nOn cherche a present a ameliorer le score pour un disposant en particulier");
        ArrayList<Mariages> ToutesLesMeilleursMariagesPourA = new ArrayList<Mariages>();
        Disposant A = disposants.get(0);
        ToutesLesMeilleursMariagesPourA = runGSMeilleursDisposants(taille, A);
        System.out.println("On a obtenu " + ToutesLesMeilleursMariagesPourA.size() + " differents mariages optimise pour le disposant " + A.toString());

        int i=1;
        while (ToutesLesMeilleursMariagesPourA.size() > 1 && i < disposants.size()) {
            Disposant enCours = disposants.get(i);
            int score = Integer.MAX_VALUE;
            ArrayList<Mariages> ToutesLesMeilleursMariagesPourEnCours = new ArrayList<Mariages>();
            for (Mariages mariage : ToutesLesMeilleursMariagesPourA){
                int scoreCourant = ScoreDisposant(mariage, enCours);
                if (scoreCourant < score){
                    score = scoreCourant;
                    ToutesLesMeilleursMariagesPourEnCours.clear();
                    ToutesLesMeilleursMariagesPourEnCours.add(mariage);
                } else if (scoreCourant == score) {
                    ToutesLesMeilleursMariagesPourEnCours.add(mariage);
                }
            }
            ToutesLesMeilleursMariagesPourA = ToutesLesMeilleursMariagesPourEnCours;
            i++;
        }

        for (Mariages mariage : ToutesLesMeilleursMariagesPourA) {
            System.out.println("La stabilite ne tenant pas compte des egalites possibles");
            estStable(mariage);
            System.out.println("L'egalite peut conduire a de l'infidelite");
            estStableEgalite(mariage);
            mariage.afficheLesRangsDePreferenceObtenus();
            System.out.println("--------------------");
        }
    }

    public ArrayList<Mariages> runGSMeilleursDisposants(int taille, Disposant disposant) {
        ArrayList<Proposant> permut = new ArrayList<>(proposants);
        ArrayList<ArrayList<Proposant>> toutesPermutations = permute(permut);
        ArrayList<Mariages> meilleursMariages = new ArrayList<>();
        int scoreMax = Integer.MAX_VALUE;
        int scoreDisposant = Integer.MAX_VALUE;
        //int scoreDisposant = disposant.getRang(couples.conjoint(disposant));
        for (ArrayList<Proposant> permutCourant : toutesPermutations) {
            Mariages mariageCourant = calculeMariage(permutCourant, taille);
            int scoreCourant = ScoreDisposant(mariageCourant, disposant); // Ici on choisit quel groupe favoriser dans son ensemble
            // Soit les proposants soit les disposants
            if (scoreCourant < scoreMax) {
                scoreMax = scoreCourant;
                meilleursMariages.clear();
                meilleursMariages.add(mariageCourant);
            } else if (scoreCourant == scoreMax) {
                if (!meilleursMariages.contains(mariageCourant)) { // pour éviter les doublons
                    meilleursMariages.add(mariageCourant);
                }
            }
        }
        // On filtre les mariages qui ont un score supérieur à celui du disposant donné en paramètre
        meilleursMariages.removeIf(mariage -> disposant.getRang(mariage.conjoint(disposant)) > scoreDisposant);
        return meilleursMariages;
    }

    private int ScoreDisposant(Mariages couples, Disposant disposant) {
        int score = 0;
        for (Disposant d : disposants) {
            if (d == disposant) {
                Proposant conjoint = couples.conjoint(d);
                int rang = d.getRang(conjoint);
                score += rang;
            }
        }
        return score;
    }


    /**
     * Methode afin de determiner si les mariages obtenus sont stables ou pas
     * mariage instable si il existe deux couples (p1,d1), (p2,d2) tels que p1 préfère d2 à d1 *ET* d2 préfère p1 à p2
     * @param couples
     * @return
     */
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

    public boolean estStableEgalite(Mariages couples) {
        if (couples == null) { return false; }
        for (Map.Entry<Proposant, Disposant> couple1 : couples.couples.entrySet()) {
            Proposant p1 = couple1.getKey();
            Disposant d1 = couple1.getValue();
            for (Map.Entry<Proposant, Disposant> couple2 : couples.couples.entrySet()) {
                if (couple1.equals(couple2)) { continue; }
                Proposant p2 = couple2.getKey();
                Disposant d2 = couple2.getValue();
                if (d2.identique(p1, p2) && p1.prefere(d2, d1)) {
                    System.out.println("Il y a au moins un cas d'infidelite possible entre " + p1.toString() + " et " + d1.toString() + " et " + p2.toString() + " et " + d2.toString());
                    return false;
                }
            }
        }
        System.out.println("Le mariage est stable");
        return true;
    }

    /**
     * Methode pour afficher les rangs de preferences pour chaque proposant et chaque disposant
     */
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

    /**
     * 3 methodes dont le but est de trouver le meilleur mariage possible
     */

    public void afficherResultatOpti(){
        Mariages couples = new Mariages();
        System.out.println("\n\nOn essaye d'optimiser les mariages");
        couples = runGSOptimize(couples, proposants);
        System.out.println("fin des calculs");
        couples.afficheParProposant();
        System.out.println("Resultat des scores des disposants : " + calculeScoreDisposants(couples));
        System.out.println("Resultat des scores des proposants : " + calculeScoreProposants(couples));
    }

    public int calculeScoreProposants(Mariages couples, ArrayList<Proposant> celibataires) {
        int score = 0;
        for (Proposant p : celibataires) {
            Disposant conjoint = couples.getConjoint(p);
            if (conjoint != null) { // on ne considère pas les proposants mariés
                int rang = p.getRang(conjoint);
                score += rang;
            }
        }
        return score;
    }

    Mariages runGSOptimize(Mariages couples, ArrayList<Proposant> celibataires) {
        while (celibataires.size() > 0) {
            Proposant p = celibataires.get(0);
            Disposant d = p.appelleSuivant();
            if (d == null) {
                celibataires.remove(p);
                return couples;
            } else {
                if (!couples.couples.containsKey(p) && !couples.couples.containsValue(d)){
                    couples.ajouteCouple(p, d);
                    celibataires.remove(p);
                }
                else if(d.prefere(p, couples.conjoint(d))){
                    Proposant ancienConjoint = couples.conjoint(d);
                    couples.retireCouple(ancienConjoint);
                    couples.retireCouple(d);
                    celibataires.add(ancienConjoint);
                    couples.ajouteCouple(p, d);
                    celibataires.remove(p);
                } else if (d.identique(p, couples.conjoint(d))) {
                    Mariages couples1 = new Mariages();
                    couples1 = couples;
                    couples1 = runGSOptimize(couples1, celibataires);
                    int count1 = calculeScoreProposants(couples1, celibataires);

                    Mariages couples2 = new Mariages();
                    couples2 = couples;
                    Proposant ancienConjoint = couples2.conjoint(d);
                    couples2.retireCouple(ancienConjoint);
                    couples2.retireCouple(d);
                    celibataires.add(ancienConjoint);
                    couples2.ajouteCouple(p, d);
                    celibataires.remove(p);
                    couples2 = runGSOptimize(couples2, celibataires);

                    int count2 = calculeScoreProposants(couples2, celibataires);
                    if(count1 < count2) {
                        couples = couples1;
                    } else if (count2 <= count1){
                        couples = couples2;
                    }
                }
            }
        }
        if (estComplet(couples)) {
            return couples;
        }
        return null;
    }


    /**
     * Methodes servant a optimiser les resultats pour un disposant afin d'observer si il est possible
     * d'ameliorer les resultats pour un disposant
     */
    public Mariages chercherAOptimiserUnDisposant(Disposant d0, ArrayList<Proposant> celibataires, ArrayList<Disposant> femmes, Mariages couples) {
        //Mariages couples = new Mariages();
        if (celibataires.size() == 0){
            return couples;
        } else {
            ArrayList<Disposant> meilleursChoix = new ArrayList<Disposant>();
            meilleursChoix = celibataires.get(0).disposantsRangMin();
            if (celibataires.get(0).estDansListe(d0, meilleursChoix)){
                //La recursive
                //parcours 1
                Mariages couples1 = new Mariages();
                couples1 = couples;
                couples1.ajouteCouple(celibataires.get(0), d0);
                ArrayList<Proposant> celibataires1 = new ArrayList<Proposant>();
                celibataires1.remove(0);
                chercherAOptimiserUnDisposant(d0, celibataires1, femmes, couples1);
                //parcours 2
                Mariages couples2 = new Mariages();
                couples2 = couples;
                ArrayList<Disposant> femmes2 = new ArrayList<Disposant>();

            } else {
                Proposant p = celibataires.get(0);
                Disposant d = p.appelleSuivant();
                if (d == null) {
                    celibataires.remove(p);
                } else if (!couples.couples.containsKey(p) && !couples.couples.containsValue(d)) {
                    couples.ajouteCouple(p, d);
                    celibataires.remove(p);
                } else if (d.prefere(p, couples.conjoint(d))) { // Si d préfère p à son conjoint actuel
                    Proposant ancienConjoint = couples.conjoint(d);
                    //couples.retireCouple(ancienConjoint);
                    couples.retireCouple(d);
                    celibataires.add(ancienConjoint);
                    couples.ajouteCouple(p, d);
                    celibataires.remove(p);
                }
            }
        }

        return couples;
    }

    public void afficherMariageOptimalPourA() {
        Mariages couples = new Mariages();
        ArrayList<Proposant> celibataires = new ArrayList<Proposant>();
        celibataires = this.proposants;
        ArrayList<Disposant> femmes = new ArrayList<Disposant>();
        couples = chercherAOptimiserUnDisposant(disposants.get(0), celibataires, femmes, couples);

        System.out.println("Le disposant dont on cherche a ameliorer le score est le suivant : " + disposants.get(0).toString());
        couples.afficheParProposant();
        System.out.println("Le score des disposants vaut : " + calculeScoreDisposants(couples));
        System.out.println("Le score des proposants vaut : " + calculeScoreProposants(couples));
        couples.afficheLesRangsDePreferenceObtenus();
    }

    /**
     * Une methode de controle afin de s'assurer que le nombre de mariages obtenus est le bon
     */
    private boolean estComplet(Mariages couples) {
        if (couples.size() == disposants.size()){
            return true;
        } else{
            return false;
        }
    }

}
