import java.util.*;

public class Main {

    public static void main(String[] args) {
        int size = 5000;   //Taille de chaque echantillon
        int seed = 25;  //Pour l'aleatoire


        System.out.println("Le cas fidelite");
        Instance i = new Instance(size, seed);
        i.affiche();
        System.out.println("\n");
        Mariages couples = i.runGS();
        i.estStable(couples);
        //couples.affiche();
        couples.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score des disposants vaut : " + i.calculeScoreDisposants(couples));
        System.out.println("Le score des proposants vaut : " + i.calculeScoreProposants(couples));


        System.out.println("\n\n\nLe cas volage");
        Instance i2 = new Instance(size, seed);
        Mariages couplesvolage = i2.runGSvolage();
        i2.estStable(couplesvolage);
        //couplesvolage.affiche();
        couplesvolage.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score des disposants vaut : " + i2.calculeScoreDisposants(couplesvolage));
        System.out.println("Le score des proposants vaut : " + i2.calculeScoreProposants(couplesvolage));
        System.out.println("\n\n\n");


        /*
        System.out.println("\n\nApres avoir considerer toutes les combinaisons possibles");
        Instance i3 = new Instance(size, seed);
        //ibis.affiche();
        Mariages couples3 = i3.runGSMeilleur(size);
        i3.estStable(couples3);
        //couples3.affiche();
        couples3.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        float scoreDisposants = i3.calculeScoreDisposants(couples3);
        float scoreProposants = i3.calculeScoreProposants(couples3);
        System.out.println("Le score des disposants vaut : " + scoreDisposants);
        System.out.println("Le score des proposants vaut : " + scoreProposants);
        //System.out.println("Un disposant a en moyenne son choix au rang " + scoreDisposants / size * 2);
        //System.out.println("Un proposant a en moyenne son choix au rang " + scoreProposants / size);
         */


        //Instance test = new Instance(size, seed);
        //test.afficherResultatOpti();


        Instance i4 = new Instance(size, seed);
        i4.afficherMariageOptimalPourA();

    }
}