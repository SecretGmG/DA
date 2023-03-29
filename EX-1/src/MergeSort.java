public class MergeSort {
    /**
     * Fügt zwei sortierte Teil-Sequenzen zusammen.
     *
     * Basiert auf Pseudocode MERGE in CLRS:
     *  Kap. 2, Seite 32 (deutsche Ausgabe, 4. Auflage)
     *          Seite 31 (englische Ausgabe, 3. Auflage)
     * Achtung: Der Pseudocode im Buch verwendet Felder, wo das erste 
     * Element den Index 1 hat. In Java Arrays hat das erste Element
     * aber Index 0.
     * Weiter modifizert, da ∞ als Sentinel fuer ints ungeeignet ist.
     */
    private static void merge(int[] array, int p, int q, int r)
    {
        int i, j, k = p;
        int n1 = q-p+1;
        int n2 = r-q;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for(i = 0; i < n1; i++) { L[i] = array[p+i]; }
        for(j = 0; j < n2; j++) { R[j] = array[q+j+1]; }
        for (i = j = 0; i < n1 && j < n2; k++) {
            if(L[i] <= R[j]) { // '<=' bevorzugt das linke Array bei Gleichheit
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
        }
        // Verbleibende Elemente kopieren:
        for (; i < n1; i++, k++) { array[k] = L[i]; }
        for (; j < n2; j++, k++) { array[k] = R[j]; }
        //System.out.println("p = " + p + ", q = " + q + ", r = " + r + ", k= " + k);
        assert(k == r + 1);
    }

    /**
     * Helper-Funktion: Sortiert den Array `array` im Bereich [p...r] (Inklusive Grenzen)
     */
    private static void mergeSortInternal(int[] array, int p, int r) {
        if(p<r) {
            int q=(p+r)/2;
            //System.out.println("p = " + p + ", q = " + q + ", r = " + r + ", arr= " + java.util.Arrays.toString(array));
            mergeSortInternal(array, p, q);
            mergeSortInternal(array, q+1, r);
            merge(array, p, q, r);
        }
    }

    /**
     * Sortiert einen Array mit Merge Sort.
     */
    public static void mergeSort(int[] array) {
        mergeSortInternal(array, 0, array.length-1);
    }

}






