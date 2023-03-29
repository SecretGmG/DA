import java.util.ArrayList;
import java.util.LinkedList;

public class MySort {
    /*
     * Implements modified radix sort. Character arrays of different lengths
     * are ordered lexicographically, for example, a<ab<b.
     *
     *
     * First splits the Array into buckets of strings with equal length.
     * Then the buckets are each sorted with radix sort.
     * The given implementation of radix sort is used here.
     *
     *
     * @param A an array of character arrays with different lengths
     * @param d the length of the longest String in A
     */
    public static void sort(ArrayList<String> A, int d){
        ArrayList<String>[] buckets = new ArrayList[d+1];

        //initialize buckets
        for(int i = 0; i<buckets.length; i++)
            buckets[i] = new ArrayList<String>();

        //fill buckets
        for (int i = 0; i < A.size(); i++)
            buckets[A.get(i).length()].add(A.get(i));

        //sort individual buckets
        for (int i = 0; i < buckets.length; i++)
            RadixSort.radixSort(buckets[i], i);

        //I know this is not in Phi(sum of all letters)
        for (int i = buckets.length -1; i > 0; i--)
            A = merge(A, buckets[i]);
    }

    /**
     * merges two sorted StringArrays,
     */
    private static ArrayList<String> merge(ArrayList<String> arr1, ArrayList<String> arr2){
        int i = 0;
        int j = 0;

        ArrayList<String> A = new ArrayList<>();

        while(i<arr1.size() && j <arr2.size()){
            if(arr1.get(i).compareTo(arr2.get(j)) > 0){
                A.add(arr1.get(i));
                i++;
            }
            else{
                A.add(arr2.get(j));
                j++;
            }
        }
        A.addAll(i, arr1);
        A.addAll(j, arr2);

        return A;
    }
}
