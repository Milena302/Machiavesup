import java.util.*;

public class Main {

    public static void main(String[] args) {
        int size = 8;   //Taille de chaque echantillon
        int seed = 25;  //Pour l'aleatoire


        System.out.println("Le cas fidelite");
        Instance i = new Instance(size, seed);
        i.affiche();
        System.out.println("\n");
        Mariages couples = i.runGS(size);
        i.estStable(couples);
        //couples.affiche();
        couples.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score des disposants vaut : " + i.calculeScoreDisposants(couples));
        System.out.println("Le score des proposants vaut : " + i.calculeScoreProposants(couples));


        System.out.println("\nDans le sens inverse afin de prouver que les mariages ne sont pas stables");
        Instance ibis = new Instance(size, seed);
        //ibis.affiche();
        Mariages couplesbis = ibis.runGSRev(size);
        ibis.estStable(couplesbis);
        //couplesbis.affiche();
        couplesbis.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score des disposants vaut : " + ibis.calculeScoreDisposants(couplesbis));
        System.out.println("Le score des proposants vaut : " + ibis.calculeScoreProposants(couplesbis));


        System.out.println("\n\n\nLe cas volage");
        Instance i2 = new Instance(size, seed);
        Mariages couplesvolage = i2.runGSvolage(size);
        i2.estStable(couplesvolage);
        //couplesvolage.affiche();
        couplesvolage.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score des disposants vaut : " + i2.calculeScoreDisposants(couplesvolage));
        System.out.println("Le score des proposants vaut : " + i2.calculeScoreProposants(couplesvolage));


        System.out.println("\nDans le sens inverse afin de prouver que les mariages ne sont pas stables");
        Instance i2bis = new Instance(size, seed);
        //ibis.affiche();
        Mariages couplesvolage2 = i2bis.runGSvolageRev(size);
        i2bis.estStable(couplesvolage2);
        //couplesvolage2.affiche();
        couplesvolage2.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score des disposants vaut : " + i2bis.calculeScoreDisposants(couplesvolage2));
        System.out.println("Le score des proposants vaut : " + i2bis.calculeScoreProposants(couplesvolage2));



        System.out.println("\n\nApres avoir considerer toutes les combinaisons possibles");
        Instance i3 = new Instance(size, seed);
        //ibis.affiche();
        Mariages couples3 = i3.runGSMeilleur(size);
        i3.estStable(couples3);
        //couples3.affiche();
        couples3.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score des disposants vaut : " + i3.calculeScoreDisposants(couples3));
        System.out.println("Le score des proposants vaut : " + i3.calculeScoreProposants(couples3));
    }

}