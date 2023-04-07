import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Proposant {
    private final int id;
    private final int seed;
    // ordre total sur les souhaits
    private ArrayList<Disposant> souhaits;
    private int rangAppel;

    Disposant appelleSuivant() {
        // renvoie le disposant suivant dans la liste
        // on a suppose qu'il y a autant de proposant que de disposant,
        // et donc on n'atteint jamais la fin de la liste de souhaits
        // TODO renvoyer une option sur un disposant pour gerer le cas où on arrive
        // en fin de liste
        // https://docs.oracle.com/javase/7/docs/api/javax/swing/text/html/Option.html
        return souhaits.get(rangAppel++);
    }

    void reinitialise() {
        // remet le rang d'appel à 0
        rangAppel = 0;
    }


    Proposant(int id, int seed) {
        // size : nombre de disposants (= nombre de proposants)
        this.id = id;
        this.seed = seed;
    }

    public void genereListeSouhaits(ArrayList<Disposant> disposants) {
        Random random = new Random(this.seed);
        this.souhaits = new ArrayList<>();
        this.souhaits.addAll(disposants);
        Collections.shuffle(souhaits, random);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "H" + id;
    }

    public String getSouhaits() {
        return this.souhaits.toString();
    }

}
