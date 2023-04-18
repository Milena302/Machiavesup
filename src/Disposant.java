import java.util.*;

public class Disposant {
    private final int id;   // variable entière qui représente l'identifiant unique du disposant.
    private HashMap<Proposant, Integer> souhaits;   //stocke les préférences du disposant pour chaque proposant
    private final int seed; // pour initialiser l'objet "Random"

    public Disposant(int id, int seed) {
        // taille: nombre de proposants (= de disposants)
        this.id = id;
        this.seed = seed;
        this.souhaits = new HashMap<>();
    }

    public int getId() {
        return id;
    }


    // génère une liste de souhaits aléatoire pour le disposant, ordre partiel
    public void genereListeSouhaits(ArrayList<Proposant> proposants) {
        Random random = new Random(seed);
        int rangMax = proposants.size(); // choix arbitraire!
        for (Proposant p : proposants) {
            Integer rang = random.nextInt(rangMax);
            this.souhaits.put(p, rang);
        }

    }

    //comparaison des rangs des deux proposants dans la liste de préférences du disposant
    public boolean prefere(Proposant p1, Proposant p2) {
        Integer rangP1 = this.souhaits.get(p1);
        Integer rangP2 = this.souhaits.get(p2);
        if (rangP1 == null) {
            System.out.println("rangP1 vaut null");
            return false;
        }
        if (rangP2 == null){
            System.out.println("rangP2 vaut null");
            return false;
        }
        return this.souhaits.get(p1) < this.souhaits.get(p2);
    }

    @Override 
    public String toString() { return "F" + id; }

    //renvoie une représentation sous forme de chaîne de caractères des souhaits du disposant pour chaque proposant.
    public String getSouhaits() {
        return this.souhaits.toString();
    }



    public HashMap<Proposant, Integer> getSouhaits(int i){
        return souhaits;
    }

    public Integer getRang(Proposant p) {
        return this.souhaits.get(p);
    }

    public boolean identique(Proposant p1, Proposant p2) {
        Integer rangP1 = this.souhaits.get(p1);
        Integer rangP2 = this.souhaits.get(p2);
        if (rangP1 == null) {
            System.out.println("rangP1 vaut null");
            return false;
        }
        if (rangP2 == null){
            System.out.println("rangP2 vaut null");
            return false;
        }
        return this.souhaits.get(p1) == this.souhaits.get(p2);
    }
}
