import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Disposant {
    private final int id;
    private HashMap<Proposant, Integer> souhaits;
    private final int seed;

    public Disposant(int id, int seed) {
        // taille: nombre de proposants (= de disposants)
        this.id = id;
        this.seed = seed;
        this.souhaits = new HashMap<>();
    }

    public int getId() {
        return id;
    }


    public void genereListeSouhaits(ArrayList<Proposant> proposants) {
        Random random = new Random(seed);
        int rangMax = proposants.size(); // choix arbitraire!
        for (Proposant p : proposants) {
            Integer rang = random.nextInt(rangMax);
            this.souhaits.put(p, rang);
        }
    }

    public boolean prefere(Proposant p1, Proposant p2) {
        return this.souhaits.get(p1) <= this.souhaits.get(p2);
    }

    @Override 
    public String toString() { return "F" + id; }

    public String getSouhaits() {
        return this.souhaits.toString();
    }
}
