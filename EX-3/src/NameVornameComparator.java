public class NameVornameComparator implements java.util.Comparator<StudentIn> {

    /** Vergleicht Objekt a mit Objekt b und
     *  liefert -1 (wenn a<b), 0 (wenn a=b) oder +1 (wenn a>b)
     */
    public int compare(StudentIn a, StudentIn b) {
        return chainComparison(
                a.getName().compareTo(b.getName()),
                a.getVorname().compareTo(b.getVorname())
        );

    }

    /** Berechnet die Aneinanderreihung von Vergleichen */
    private static int chainComparison (int comp1, int comp2) {
        if(comp1 != 0){return comp1;}
        return comp2;
    }

}