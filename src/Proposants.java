import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Proposants {
    private int id;
    private int seed;
    private int size;
    private Couple[] couples;

    Proposants(int id, int size, int seed) {
        this.id = id;
        this.seed = seed;
        this.size = size;
        this.couples = new Couple[size];
    }

    public Couple[] getPref() {
        Random random = new Random(this.seed);

        for (int i = 0; i < this.size; i++) {
            int randomNum = random.nextInt(this.size);
            Couple couple = new Couple(i, randomNum);
            this.couples[i] = couple;
        }

        // Tri par ordre croissant des positions
        Arrays.sort(this.couples, Comparator.comparing(Couple::getPos));

        return this.couples;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Couple[] trigetCouples() {
        return couples;
    }


    public void setCouple(int disposantId) {
        couples[getPosition(disposantId)].setCouple();
    }

    public int getCouple() {
        for (Couple couple : couples) {
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
        return couples[index].getPos();
    }

    public void removeCouple() {
        couples[getPosition(this.id)].setCouple();
    }
}
