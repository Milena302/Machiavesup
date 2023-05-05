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
        if (rangAppel < souhaits.size()) {
            return souhaits.get(rangAppel++);
        } else {
            return null;
        }
        //return souhaits.get(rangAppel++);
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


    public ArrayList<Disposant> getPreferences(){
        return this.souhaits;
    }

    public boolean prefere(Disposant d1, Disposant d2) {
        Integer rangD1 = this.souhaits.indexOf(d1);
        Integer rangD2 = this.souhaits.indexOf(d2);
        if (rangD1 == -1) {
            System.out.println("rangP1 vaut null");
            return false;
        }
        if (rangD2 == -1){
            System.out.println("rangP2 vaut null");
            return false;
        }
        return rangD1 < rangD2;
    }

    public int getRang(Disposant disposant) {
        return this.souhaits.indexOf(disposant);
    }

    public ArrayList<Disposant> disposantsRangMin() {
        // Trouver le rang de préférence minimum parmi tous les disposants de la liste de souhaits
        int rangMin = Integer.MAX_VALUE;
        for (Disposant d : souhaits) {
            int rang = getRang(d);
            if (rang < rangMin) {
                rangMin = rang;
            }
        }

        // Trouver tous les disposants qui ont ce rang de préférence minimum
        ArrayList<Disposant> disposantsRangMin = new ArrayList<>();
        for (Disposant d : souhaits) {
            if (getRang(d) == rangMin) {
                disposantsRangMin.add(d);
            }
        }

        return disposantsRangMin;
    }

    public boolean estDansListe(Disposant disposant, ArrayList<Disposant> listeDisposants) {
        return listeDisposants.contains(disposant);
    }

}
