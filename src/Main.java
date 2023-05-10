import java.util.*;


/**
 * Main pour une seule instance
 *
 */
public class Main {


    public static void main(String[] args) {
        int size = 8;   //Taille de chaque echantillon
        int seed = 12;  //Pour l'aleatoire

        Instance i = new Instance(size, seed);
        i.affiche();
        System.out.println("\n\nLe cas fidelite");
        Mariages couples = i.runGS();
        System.out.println("La stabilite d'un point de vue strict");
        i.estStable(couples);
        System.out.println("La stabilite en cas d'egalite entre 2 proposants pour un disposant");
        i.estStableEgalite(couples);
        //couples.affiche();
        couples.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score des disposants vaut : " + i.calculeScoreDisposants(couples));
        System.out.println("Le score des proposants vaut : " + i.calculeScoreProposants(couples));
        couples.afficheLesRangsDePreferenceObtenus();


        System.out.println("\n\n\nLe cas volage");
        Instance i2 = new Instance(size, seed);
        Mariages couplesvolage = i2.runGSvolage();
        System.out.println("La stabilite d'un point de vue strict");
        i2.estStable(couplesvolage);
        System.out.println("La stabilite en cas d'egalite entre 2 proposants pour un disposant");
        i2.estStableEgalite(couplesvolage);
        //couplesvolage.affiche();
        couplesvolage.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score des disposants vaut : " + i2.calculeScoreDisposants(couplesvolage));
        System.out.println("Le score des proposants vaut : " + i2.calculeScoreProposants(couplesvolage));
        couplesvolage.afficheLesRangsDePreferenceObtenus();
        System.out.println("\n\n\n");


        System.out.println("\n\nApres avoir considerer toutes les combinaisons possibles");
        System.out.println("On cherche a favoriser ici l'ensemble des disposants");
        Instance i3 = new Instance(size, seed);
        //i3.affiche();
        Mariages couples3 = i3.runGSMeilleur(size);
        System.out.println("La stabilite d'un point de vue strict");
        i3.estStable(couples3);
        System.out.println("La stabilite en cas d'egalite entre 2 proposants pour un disposant");
        i3.estStableEgalite(couples3);
        //couples3.affiche();
        couples3.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        float scoreDisposants = i3.calculeScoreDisposants(couples3);
        float scoreProposants = i3.calculeScoreProposants(couples3);
        System.out.println("Le score des disposants vaut : " + scoreDisposants);
        System.out.println("Le score des proposants vaut : " + scoreProposants);
        System.out.println("Les rangs de preferences obtenus pour chaque individu est le suivant");
        couples3.afficheLesRangsDePreferenceObtenus();
        System.out.println("Un disposant a en moyenne son choix au rang " + scoreDisposants / size * 2);
        System.out.println("Un proposant a en moyenne son choix au rang " + scoreProposants / size);


        //Instance test = new Instance(size, seed);
        //test.afficherResultatOpti();


        //Instance i4 = new Instance(size, seed);
        //i4.afficherMariageOptimalPourA();

    }
}



/**
 * Main pour plusieurs instances
 * Statistiques pour etudier la stabilite
 */

/*

public class Main {

    public static void main(String[] args) {
        int size = 8;   //Taille de chaque echantillon
        int seed = 12;  //Pour l'aleatoire

        int nbTrueFideliteEstStable = 0;
        int nbFalseFideliteEstStable = 0;
        int nbTrueFideliteEstStableEgalite = 0;
        int nbFalseFideliteEstStableEgalite = 0;

        int nbTrueVolageEstStable = 0;
        int nbFalseVolageEstStable = 0;
        int nbTrueVolageEstStableEgalite = 0;
        int nbFalseVolageEstStableEgalite = 0;

        int nbTrueToutesCombinaisonsEstStable = 0;
        int nbFalseToutesCombinaisonsEstStable = 0;
        int nbTrueToutesCombinaisonsEstStableEgalite = 0;
        int nbFalseToutesCombinaisonsEstStableEgalite = 0;

        boolean estStable = true;
        boolean estStableEgalite = true;

        for (int it = 0; it <=150; it +=3) {
            seed += it;

            Instance i = new Instance(size, seed);
            Mariages couples = i.runGS();
            estStable = i.estStable(couples);
            estStableEgalite = i.estStableEgalite(couples);
            if (estStable == true){
                nbTrueFideliteEstStable += 1;
            } else if (estStable == false) {
                nbFalseFideliteEstStable += 1;
            }

            if (estStableEgalite == true){
                nbTrueFideliteEstStableEgalite += 1;
            } else if (estStableEgalite == false) {
                nbFalseFideliteEstStableEgalite += 1;
            }



            Instance i2 = new Instance(size, seed);
            Mariages couplesvolage = i2.runGSvolage();
            estStable = i2.estStable(couplesvolage);
            estStableEgalite = i2.estStableEgalite(couplesvolage);

            if (estStable == true){
                nbTrueVolageEstStable += 1;
            } else if (estStable == false) {
                nbFalseVolageEstStable += 1;
            }

            if (estStableEgalite == true){
                nbTrueVolageEstStableEgalite += 1;
            } else if (estStableEgalite == false) {
                nbFalseVolageEstStableEgalite += 1;
            }



            Instance i3 = new Instance(size, seed);
            Mariages couples3 = i3.runGSMeilleur(size);
            estStable = i3.estStable(couples3);
            estStableEgalite = i3.estStableEgalite(couples3);

            if (estStable == true){
                nbTrueToutesCombinaisonsEstStable += 1;
            } else if (estStable == false) {
                nbFalseToutesCombinaisonsEstStable += 1;
            }

            if (estStableEgalite == true){
                nbTrueToutesCombinaisonsEstStableEgalite += 1;
            } else if (estStableEgalite == false) {
                nbFalseToutesCombinaisonsEstStableEgalite += 1;
            }
        it++;
        }

        System.out.println("On a obtenu pour 38 instances differentes : ");
        System.out.println("Pour la strategie fidelite et la stabilite stricte : ");
        System.out.println(nbTrueFideliteEstStable + " true et " + nbFalseFideliteEstStable + " false");
        System.out.println("Pour la strategie fidelite et la stabilite considerant la situation d egalite comme etant instable :");
        System.out.println(nbTrueFideliteEstStableEgalite + " true et " + nbFalseFideliteEstStableEgalite + " false");

        System.out.println("\nPour la strategie volage et la stabilite stricte : ");
        System.out.println(nbTrueVolageEstStable + " true et " + nbFalseVolageEstStable + " false");
        System.out.println("Pour la strategie volage et la stabilite considerant la situation d egalite comme etant instable :");
        System.out.println(nbTrueVolageEstStableEgalite + " true et " + nbFalseVolageEstStableEgalite + " false");

        System.out.println("\nApres avoir trouve un mariage optimal et la stabilite stricte : ");
        System.out.println(nbTrueToutesCombinaisonsEstStable + " true et " + nbFalseToutesCombinaisonsEstStable + " false");
        System.out.println("Pour la strategie fidelite et la stabilite considerant la situation d egalite comme etant instable :");
        System.out.println(nbTrueToutesCombinaisonsEstStableEgalite + " true et " + nbFalseToutesCombinaisonsEstStableEgalite + " false");
    }
}

 */