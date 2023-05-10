import java.util.*;

public class Mariages {

    HashMap<Proposant, Disposant> couples;

    Mariages() {
        this.couples = new HashMap<>();
    }

    int size(){ return this.couples.size();}

    void ajouteCouple(Proposant p, Disposant d){
        // pas de polygamie, ni polyandrie:
        assert(!couples.values().contains(d));
        assert(!couples.keySet().contains(p));

        this.couples.put(p, d);
    }

    void retireCouple(Proposant p) {
        this.couples.remove(p);
    }

    void retireCouple(Disposant d) {
        retireCouple(conjoint(d));
    }

    Proposant conjoint(Disposant d){
        for (Map.Entry<Proposant, Disposant> couple : couples.entrySet()) {
            if (couple.getValue()==d) return couple.getKey();
        }
        assert(false);
        return null;
    }

    void affiche() {
        System.out.println("couples formés : ");
        for (Map.Entry<Proposant, Disposant> couple : couples.entrySet()) {
            System.out.println("Disposant " + couple.getKey() + " \t-\tProposant " + couple.getValue());
        }
    }

    void afficheParProposant() {
        List<Map.Entry<Proposant, Disposant>> list = new ArrayList<>(couples.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Proposant, Disposant>>() {
            @Override
            public int compare(Map.Entry<Proposant, Disposant> o1, Map.Entry<Proposant, Disposant> o2) {
                return ((Integer)(o1.getValue().getId())).compareTo(o2.getValue().getId());
            }
        });
        System.out.println("Sorted map by value: " + list);
    }


    void afficheLesRangsDePreferenceObtenus(){
        System.out.println("Affichage des rangs de preference obtenus");
        System.out.println("Du point de vue des proposants :");
        for (Map.Entry<Proposant, Disposant> couple : couples.entrySet()) {
            System.out.print(couple.getKey() + "\t" + couple.getKey().getRang(couple.getValue()) + "\t\t");
        }

        System.out.println("\nDu point de vue des disposants");
        for (Map.Entry<Proposant, Disposant> couple : couples.entrySet()) {
            System.out.print(couple.getValue() + "\t" + couple.getValue().getRang(couple.getKey()) + "\t\t");
        }
        System.out.println();
    }

    //méthode pour récupérer le conjoint d'un proposant
    Disposant getConjoint(Proposant p) {
        return couples.get(p);
    }

    /**
     * Les deux methodes suivantes servent a verifier dans la methode ..... se trouvant dans la classe Mariages qu'une
     * solution identique n'a pas deja ete trouve afin d'eviter les doublons.
     * Cela a servi de determiner combien de solutions maximales il peut exister (donc qu'il peut exister plus d'une
     * solution optimale
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Mariages)) {
            return false;
        }
        Mariages other = (Mariages) o;
        return Objects.equals(couples, other.couples);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couples);
    }
}
