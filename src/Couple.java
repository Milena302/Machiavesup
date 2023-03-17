public class Couple implements Comparable<Couple>{
    private int id;
    private int pos;
    private boolean couple;

    Couple(int id, int pos){
        this.id = id;
        this.pos = pos;
        this.couple = false; // initialize couple to false
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isCouple() {
        return couple;
    }

    //public void setCouple(boolean couple) {
    //    this.couple = couple;
    //}

    public void setCouple() {
        this.couple = true;
    }



    private int getPosition(int disposantId) {
        return disposantId;
    }


    @Override
    public String toString() {
        return "(" + id + ", " + pos + ")";
    }

    @Override
    public int compareTo(Couple other) {
        return Integer.compare(this.pos, other.pos);
    }
}
