import java.util.*;

public class GaleShapley {

    public static void main(String[] args) {
        int size = 8;   //Taille de chaque echantillon
        int seed = 25;  //Pour l'aleatoire

        Instance i = new Instance(size, seed);

        Mariages couples = i.runGS();

        couples.affiche();

        couples.afficheParProposant();
}

/**
 public static void main(String[] args) {
 int size = 3;
 int[][] menPref = { {1, 2, 0}, {2, 1, 0}, {0, 2, 1} }; // préférences des hommes
 int[][] womenPref = {{0, 2, 1}, {2, 0, 1}, {1, 2, 0}}; // préférences des femmes
 int n = menPref.length; // nombre de personnes
 System.out.println("il y a " + n + " personnes");
 int[] womenPartner = new int[n]; // tableau pour enregistrer les partenaires des femmes
 Arrays.fill(womenPartner, -1); // initialisation avec -1 (personne)
 for (int i =0; i< n; i++) {
 System.out.println("i = " + i + " la valeur pour le partenaire de la femme est " + womenPartner[i]);
 }
 Queue<Integer> freeMen = new LinkedList<>(); // file d'attente pour les hommes libres
 for (int i = 0; i < n; i++) {
 freeMen.add(i); // tous les hommes sont initialement libres
 }

 while (!freeMen.isEmpty()) {
 int man = freeMen.remove(); // prendre un homme libre
 int[] preferences = menPref[man]; // préférences de l'homme
 for (int i = 0; i < n; i++) {
 int woman = preferences[i]; // choisir une femme
 if (womenPartner[woman] == -1) {
 // la femme est libre, ils se mettent ensemble
 womenPartner[woman] = man;
 break;
 } else {
 // la femme est déjà avec un autre homme
 int otherMan = womenPartner[woman];
 int[] otherPreferences = womenPref[woman];
 //PartialOrder otherPreferences = womenPref[woman];
 if (Arrays.asList(otherPreferences).indexOf(man) < Arrays.asList(otherPreferences).indexOf(otherMan)) {
 //if (otherPreferences.greater(man, otherMan) {
 // la femme préfère l'homme actuel à son partenaire actuel
 womenPartner[woman] = man; // ils se mettent ensemble
 freeMen.add(otherMan); // l'autre homme devient libre
 break;
 }
 }
 }
 }

 // afficher les résultats
 System.out.println("Partenariats : ");
 for (int i = 0; i < n; i++) {
 System.out.println("Femme " + i + " avec Homme " + womenPartner[i]);
 }
 }
 **/