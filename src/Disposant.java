import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Disposant {
    private int id;
    private int taille;
    private ArrayList<Pair<Proposant, Integer>> souhaits;
    int seed;

    public Disposant(int id, int taille, int seed) {
        this.id = id;
        this.taille = taille;
        this.souhaits = new ArrayList<>();
        this.seed = seed;
    }

    public int getId() {
        return id;
    }

    public int getTaille() {
        return taille;
    }

    public void genererListeSouhait(ArrayList<Proposant> proposants) {
        Random random = new Random(seed);
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);
        for (int i = 0; i < taille; i++) {
            Pair<Proposant, Integer> pair = new Pair<>(proposants.get(indices.get(i)), i+1);
            souhaits.add(pair);
        }
        // A enlever?? utile pour l'affichage?
        Collections.sort(souhaits);
    }

    public Couple getSouhait(int index) {
        return souhaits.get(index);
    }

    public int getRang(Proposant p) {
        for (Couple couple : souhaits) {
            if (couple.getId() == p.getId()) {
                return couple.getPos();
            }
        }
        assert(false);
        return -1;
    }

    public boolean prefere(Proposant p1, Proposant p2) {
        return getRang(p1) <= getRang(p2);
    }

    public Couple[] getCouples() {
        return souhaits.toArray(new Couple[souhaits.size()]);
    }
}
