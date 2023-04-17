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
        System.out.println("Le score vaut : " + i.calculeScore(couples));


        System.out.println("\nDans le sens inverse afin de prouver que les mariages ne sont pas stables");
        Instance ibis = new Instance(size, seed);
        //ibis.affiche();
        Mariages couplesbis = ibis.runGSRev(size);
        ibis.estStable(couplesbis);
        //couplesbis.affiche();
        couplesbis.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score vaut : " + ibis.calculeScore(couplesbis));


        System.out.println("\n\n\nLe cas volage");
        Instance i2 = new Instance(size, seed);
        Mariages couplesvolage = i2.runGSvolage(size);
        i2.estStable(couplesvolage);
        //couplesvolage.affiche();
        couplesvolage.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score vaut : " + i2.calculeScore(couplesvolage));


        System.out.println("\nDans le sens inverse afin de prouver que les mariages ne sont pas stables");
        Instance i2bis = new Instance(size, seed);
        //ibis.affiche();
        Mariages couplesvolage2 = i2bis.runGSvolageRev(size);
        i2bis.estStable(couplesvolage2);
        //couplesvolage2.affiche();
        couplesvolage2.afficheParProposant();
        System.out.println("Plus le score est bas mieux c'est");
        System.out.println("Le score vaut : " + i2bis.calculeScore(couplesvolage2));

    }
}