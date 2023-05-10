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



        System.out.println("\n\n\nOn va a present regarder combien de mariages optimaux existent");
        Instance i3bis = new Instance(size, seed);
        ArrayList<Mariages> Allcouples3 = i3bis.runGSTousLesMeilleurs(size);

        System.out.println("On a obtenu " + Allcouples3.size() + " mariages differents");
        for (Mariages mariage : Allcouples3) {
            // appel à votre fonction qui affiche les résultats pour chaque mariage
            i3bis.estStable(mariage);
            i3bis.estStableEgalite(mariage);
            mariage.afficheLesRangsDePreferenceObtenus();
            System.out.println("--------------------");
        }


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


/**
 * Main pour trouver le nombre maximale de solutions optimales apres avoir parcouru un certain nombre d'instances
 */

/*
public class Main{

    public static void main(String[] args){
        int size = 8;   //Taille de chaque echantillon
        int seed = 12;  //Pour l'aleatoire
        int maximum = 0;
        int tour=0;
        int seedMax = 12;

        //On a choisi it <=15000 afin de parcourir 5001 instances. Comme cela prend du temps a s'executer j'ai prefere laisser it <=150 a la place
        //J'ai d'ailleurs commence par it <=150
        for (int it =0; it <=150; it+=3) {
            seed+=it;
            //System.out.println("\n\n\nOn va a present regarder combien de mariages optimaux existent");
            Instance i3bis = new Instance(size, seed);
            ArrayList<Mariages> Allcouples3 = i3bis.runGSTousLesMeilleurs(size);

            //System.out.println("On a obtenu " + Allcouples3.size() + " mariages differents");
            //for (Mariages mariage : Allcouples3) {
                // appel à votre fonction qui affiche les résultats pour chaque mariage
                //i3bis.estStable(mariage);
                //i3bis.estStableEgalite(mariage);
                //mariage.afficheLesRangsDePreferenceObtenus();
                //System.out.println("--------------------");
            //}
            //System.out.println("---------------------------");
            //System.out.println("***");

            if (maximum < Allcouples3.size()){
                maximum = Allcouples3.size();
                seedMax = seed;
            }
            tour++;
        }
        System.out.println("\n\nLe nombre maximal de mariages possibles trouves sur " + tour + " instances est " + maximum);
        System.out.println("Il s'agit de l'instance obtenue avec la seed " + seedMax);
        System.out.println("Les disposants sont favorise\n\n\n");       //Modifier la phrase selon le groupe que l'on veut favoriser dans son ensemble

        Instance seedMaxEtude = new Instance(size, seedMax);
        seedMaxEtude.affiche();
        System.out.println("\n\n\n");
        ArrayList<Mariages> AllcouplesSeed = seedMaxEtude.runGSTousLesMeilleurs(size);
        for (Mariages mariage : AllcouplesSeed) {
            // appel à votre fonction qui affiche les résultats pour chaque mariage
            System.out.println("Stabilite comme pour Gale-Shapley");
            seedMaxEtude.estStable(mariage);
            System.out.println("La situation d'egalite peut engendrer de la jalousie");
            seedMaxEtude.estStableEgalite(mariage);
            mariage.afficheLesRangsDePreferenceObtenus();
            System.out.println("Calcul des scores pour les proposants");
            System.out.println(seedMaxEtude.calculeScoreProposants(mariage));
            System.out.println("Calcul des scores pour les disposants");
            System.out.println(seedMaxEtude.calculeScoreDisposants(mariage));
            System.out.println("--------------------");
        }
    }
}

 */