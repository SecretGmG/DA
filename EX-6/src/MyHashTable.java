import java.util.Arrays;

public class MyHashTable {
    int length;
    int[] keys;

    public MyHashTable(int length){
        keys = new int[length];
        this.length = length;
    }
    public int helperHash(int key){
        return key % length;
    }
    public int hash(int key, int i){
        return (helperHash(key) + secondHashProbing(key, i)) % length;
    }
    public int linearProbing(int key, int i){
        return i;
    }
    public int quadProbing(int key, int i){
        final int c1 = 1;
        final int c2 = 3;
        return c1*i + c2*i^2;
    }
    public int secondHashProbing(int key, int i){
        return i * (1 + (key % (length - 1)));
    }

    public void insert(int  key){
        for(int i = 0; i<length; i++){
            int index = hash(key, i);
            System.out.print(index + ",");
            if(keys[index] == 0){
                keys[index] = key;
                return;
            }
        }
        System.out.println("The hashtable is full");
    }
    public String toString(){
        return Arrays.toString(keys);
    }

    public static void main(String[] args){
        int[] values = new int[]{24, 18, 13, 56, 44, 7, 19, 23, 33 };

        MyHashTable ht = new MyHashTable(11);

        for(int x: values){
            ht.insert(x);
            System.out.println("\n" + ht);
        }
    }
}
