import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Proposant {
    private int id;
    private int seed;
    private int size;
    private Couple[] souhaits;

    // TODO!
    Disposant appelleSuivant() {
        // renvoie le disposant suivant dans la liste
        // suppose d'avoir ajoute un champs rangAppel
    }

    void reinitialise() {
        // remet le rang d'appel à 0
        // TODO!
    }


    Proposant(int id, int size, int seed) {
        this.id = id;
        this.seed = seed;
        this.size = size;
        this.souhaits = new Couple[size];
    }

    public Couple[] getPref() {
        Random random = new Random(this.seed);

        for (int i = 0; i < this.size; i++) {
            int randomNum = random.nextInt(this.size);
            Couple couple = new Couple(i, randomNum);
            this.souhaits[i] = couple;
        }

        // Tri par ordre croissant des positions
        Arrays.sort(this.souhaits, Comparator.comparing(Couple::getPos));

        return this.souhaits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Couple[] trigetsouhaits() {
        return souhaits;
    }


    public void setCouple(int disposantId) {
        souhaits[getPosition(disposantId)].setCouple();
    }

    public int getCouple() {
        for (Couple couple : souhaits) {
            if (couple.isCouple()) {
                return couple.getId();
            }
        }
        return -1; //retourne -1 si le proposant n'est pas en couple
    }


    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPosition(int index) {
        return souhaits[index].getPos();
    }

    public void removeCouple() {
        souhaits[getPosition(this.id)].setCouple();
    }
}
