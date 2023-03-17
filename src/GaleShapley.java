import java.util.*;

public class GaleShapley {

    public static void main(String[] args) {
        int size = 8;   //Taille de chaque echantillon
        int seed = 25;  //Pour l'aleatoire

        ArrayList<Disposants> disposants = new ArrayList<>();
        ArrayList<Proposants> proposants = new ArrayList<>();

        //Remplissage des disposants
        for (int i = 0; i < size; i++) {
            Disposants newDisposant = new Disposants(i, size, seed);
            newDisposant.genererListeSouhait();
            seed++;
            disposants.add(newDisposant);
        }

        //Remplissage des proposants
        for (int i = 0; i < size; i++) {
            Proposants newProposants = new Proposants(i, size, seed);
            newProposants.getPref();
            seed++;
            proposants.add(newProposants);
        }

        //Tri par ordre croissant des pos dans chaque liste de préférences
        for (Proposants p : proposants) {
            p.trigetCouples();
        }
        for (Disposants d : disposants) {
            d.getCouples();
        }

        //Affichage des disposants
        for (Disposants d : disposants) {
            System.out.println("Disposant " + d.getId() + ": " + Arrays.toString(d.getCouples()));
        }

        //Affichage des proposants
        System.out.println("Contenu des proposants : ");
        for (Proposants p : proposants) {
            System.out.println("Proposant " + p.getId() + ": " + Arrays.toString(p.trigetCouples()));
        }

        Map<Integer, Integer> couples = new HashMap<>();
        //Boucle principale de l'algorithme
        while (couples.size() < size) {
            //Recherche d'un disposant non-apparié
            Disposants disposant = null;
            for (Disposants d : disposants) {
                if (!couples.containsKey(d.getId())) {
                    disposant = d;
                    break;
                }
            }
            if (disposant == null) {
                break; //Tous les disposants sont appariés
            }

            //Sélection du premier proposant dans la liste de souhaits du disposant
            Proposants proposant = proposants.get(disposant.getSouhait(0).getId());
            couples.put(disposant.getId(), proposant.getId());
            proposant.setCouple(disposant.getId());
            // Boucle de recherche de meilleur proposant
            for (int i = 1; i < size; i++) {
                int proposantId = disposant.getSouhait(i).getId();
                Proposants nouveauProposant = proposants.get(proposantId);

                if (!couples.containsValue(nouveauProposant.getId())) {
                    // Le proposant est libre, vérifier qu'il n'est pas marié avec un autre disposant
                    boolean alreadyMarried = false;
                    for (Map.Entry<Integer, Integer> couple : couples.entrySet()) {
                        if (couple.getValue() == nouveauProposant.getId()) {
                            alreadyMarried = true;
                            break;
                        }
                    }
                    if (!alreadyMarried) {
                        // Le proposant est apparié avec le disposant
                        couples.put(disposant.getId(), nouveauProposant.getId());
                        nouveauProposant.setCouple(disposant.getId());
                        proposant = nouveauProposant;
                    } else if (disposant.proposantPrefere(proposant.getPosition(proposantId), nouveauProposant.getPosition(nouveauProposant.getId()))) {
                        // Le disposant préfère le nouveau proposant
                        couples.remove(nouveauProposant.getCouple());
                        Proposants ancienProposant = proposants.get(nouveauProposant.getCouple());
                        ancienProposant.removeCouple(); // Supprime la référence à l'ancien couple
                        couples.put(disposant.getId(), nouveauProposant.getId());
                        nouveauProposant.setCouple(disposant.getId());
                        proposant = nouveauProposant;
                    }
                }
            }
        }

        //Affichage des couples formés
        System.out.println("Couples formés : ");
        for (Map.Entry<Integer, Integer> couple : couples.entrySet()) {
            System.out.println("Disposant " + couple.getKey() + " \t-\tProposant " + couple.getValue());
        }

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(couples.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        System.out.println("Sorted map by value: " + list);
    }
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