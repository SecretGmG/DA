package HashTableWithFree;
public class Element {
    boolean isSome;
    int value_prev;
    int next;

    public Element(boolean isSome, int value_prev, int next) {
        this.isSome = isSome;
        this.value_prev = value_prev;
        this.next = next;
    }

    public static Element Some(int value) {
        return new Element(true, value, -1);
    }
    public boolean hasNext(){
        return next != -1;
    }

    public static Element None(int prev, int next) {
        return new Element(false, prev, next);
    }
    public String toString(){
        if (isSome){
            return String.format("S(%d,%d)", value_prev, next);
        }
        return String.format("N(%d,%d)", value_prev, next);
    }
}