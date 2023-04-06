package HashTableWithFree;

import java.util.Arrays;
import java.util.Random;

public class HashTableWithFree {

    Element[] h;
    int free;

    public HashTableWithFree(int size){
        h = new Element[size];
        for (int i = 0; i < h.length; i++) {
            h[i] = Element.None(i-1, i+1);
        }
        h[0].value_prev = h.length -1;
        h[h.length - 1].next = 0;

        free = 0;
    }

    private int hash(int value){
        return value % h.length;
    }
    //removes the given element from the hashtable
    public void remove(int value){
        int i = hash(value);

        while (isOfHash(i, hash(value))){
            //if the value of h[i] matches the given value
            if(h[i].value_prev == value){
                //if h[i] is the last element in the list, it can simply be removed
                if(!h[i].hasNext()){
                    free(i);
                }
                //otherwise swap it with the next element free the space where the next element was
                else{
                    int nextIndex = h[i].next;
                    h[i] = h[nextIndex];
                    free(nextIndex);
                }

            }
            i = h[i].next;
        }
    }

    //inserts the value in O(1+alpha)
    public void insert(int value) throws Exception {
        int i = hash(value);
        //if h[i] is empty simply insert the value
        if (!isOfHash(i, hash(value))) {
            makeSpace(i);
            alloc(i);
            h[i] = Element.Some(value);
        }
        //otherwise h[i] is the head of the list with all elements that have the same hash as value
        else {
            while (h[i].hasNext()){
                i = h[i].next;
            }
            h[i].next = add(value);
        }
    }
    //gets the value returns -1 if not found int O(1 + alpha)
    public int get(int key){
        int i = hash(key);
        while (isOfHash(i, hash(key))){
            if (h[i].value_prev == key)
                return h[i].value_prev;
            i = h[i].next;
        }
        return -1;
    }

    //moves the element at the index i to somewhere else in O(1+alpha)
    //if the element at the index i nothing is changed
    private void makeSpace(int i) throws Exception {
        if(!h[i].isSome){
            return;
        }

        Element prevElement = h[hash(h[i].value_prev)]; //get the head of the list with the hash of h[i].value
        //find the element that points to i
        while (prevElement.next != i){
            prevElement = h[prevElement.next];
        }

        int newIndex = free;
        alloc(newIndex);
        h[newIndex] = h[i];
        prevElement.next = newIndex;

        free(i);
    }
    //returns true if the index is a valid value with the given hash
    private boolean isOfHash(int index, int hash){
        if( index < 0){
            return false;
        }
        if (!h[index].isSome) {
            return false;
        }
        return hash(h[index].value_prev) == hash;

    }
    //adds the value at any free space and returns the index where it was added in O(1)
    private int add(int value) throws Exception {
        int i = free;
        alloc(free);
        h[i] = Element.Some(value);
        return i;
    }
    //allocates memory at i
    private void alloc(int i) throws Exception {
        if (h[i].isSome){
            throw new Exception("tried to allocate used memory");
        }

        int n = h[i].next;
        int p = h[i].value_prev;

        h[n].value_prev = p;
        h[p].next = n;
        free = n;
        //to handle i == free
        //the memory is used similarly to linear probing except that removed values do not take up any space in this implementation
    }
    //frees the memory at i
    private void free(int i){
        int n = h[free].next;
        h[i] = Element.None(free, n);
        h[free].next = i;
        h[n].value_prev = i;
    }


    public String toString(){
        return Arrays.toString(this.h) + "   free: " + free;
    }

    public static void main(String[] args) throws Exception {
        test(10000);
    }
    static void test(int size){
        int[] testValues = new int[size - 1];
        Random r = new Random();
        //get size -1 unique testValues with random hashValues each
        for (int i = 0; i < size - 1; i++) {
            testValues[i] = r.nextInt(i*size, i*size + size);
        }

        HashTableWithFree ht = new HashTableWithFree(size);
        for (int i = 0; i < testValues.length; i++) {
            try {
                ht.insert(testValues[i]);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Something went wrong");
            }
        }

        //System.out.println(ht);

        //shuffle the testValues
        for (int i = 0; i < size - 1; i++) {
            int j = r.nextInt(size - 1);
            int temp = testValues[i];
            testValues[i] = testValues[j];
            testValues[j] = temp;
        }

        for (int i = 0; i < size - 1; i++) {
            //check that the value is actually there
            if(ht.get(testValues[i]) != testValues[i]){
                System.out.println("Something went wrong");
            }
            //remove the value
            ht.remove(testValues[i]);
            //check that the value actually was removed
            if(ht.get(testValues[i]) != -1){
                System.out.println("Something went wrong");
            }
        }

        //System.out.println(ht);
    }
}
