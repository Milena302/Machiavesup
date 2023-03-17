import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Disposants {
    private int id;
    private int taille;
    private Random random;
    private ArrayList<Couple> souhaits;

    public Disposants(int id, int taille, int seed) {
        this.id = id;
        this.taille = taille;
        this.random = new Random(seed);
        this.souhaits = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getTaille() {
        return taille;
    }

    public ArrayList<Couple> getSouhaits() {
        return souhaits;
    }

    public void genererListeSouhait() {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < taille; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);
        for (int i = 0; i < taille; i++) {
            Couple couple = new Couple(indices.get(i), i+1);
            souhaits.add(couple);
        }
        Collections.sort(souhaits);
    }

    public Couple getSouhait(int index) {
        return souhaits.get(index);
    }

    public int getRang(int proposantId) {
        for (Couple couple : souhaits) {
            if (couple.getId() == proposantId) {
                return couple.getPos();
            }
        }
        return -1;
    }

    public boolean proposantPrefere(int proposant1Id, int proposant2Id) {
        return getRang(proposant1Id) < getRang(proposant2Id);
    }

    public Couple[] getCouples() {
        return souhaits.toArray(new Couple[souhaits.size()]);
    }


}
