public class HashCalculator {
    public static void main(String[] args){
        int[] values = {2021,2022,2023,2024,2025};

        //for(int x : values){
        for(int x = 3000; x < 3200; x++){
            int hash = calculateHash(512, x);
            if(hash == 23)
                System.out.printf("key: %d, hash: %d\n", x,hash);
        }

    }

    public static int calculateHash(int m, int key){
        final double PHI = (Math.sqrt(5.0) - 1.0) / 2.0;
        double x = PHI * key;
        return (int) Math.floor(m * (x - (int) x));
    }
}
