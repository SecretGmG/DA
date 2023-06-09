import java.util.ArrayList;

public class RadixSortTester {

    /**
     * Generate a random character array of length d
     * @param len length
     * @return character array
     */
    public static String randomString(int len)
    {
        char[] s = new char[len];

        for(int i=0; i<len; i++)
        {
            s[i] = (char)('a'+Math.random()*26);
        }

        return new String(s);
    }

    /**
     * Generate an array of random character arrays of
     * different lengths.
     * @param n number of arrays
     * @param d maximum length of arrays
     * @return array of character arrays
     */
    public static ArrayList<String> generateTestData(int n, int d)
    {
        ArrayList<String> testData = new ArrayList<>(n);

        for(int i=0; i<n; i++)
        {
            int len = 1+(int)Math.floor(Math.random()*d);
            testData.add(randomString(len));
        }

        return testData;
    }
    public static void checkSorted(ArrayList<String> testData) {
        for(int i=0; i<testData.size() - 1; i++) {
            String first = testData.get(i);
            String second = testData.get(i+1);
            if (first.compareTo(second) > 0) {
                System.out.println("Error: " + first + " >= " + second);
            }
        }
    }

    public static void main(String[] args) {

        int n[] = {40000, 80000, 120000, 160000, 200000, 240000};
        int d = 50;

        System.out.printf("d = %d\n", d);


        for(int i=0; i<n.length; i++)
        {
            n[i] = n[i]*2;
            ArrayList<String> testData = generateTestData(n[i], d);
            int sumOfAllLetters = sumOfAllLetters(testData);

            Timer t = new Timer();
            t.reset();
            MySort.sort(testData, d);
            //RadixSort.radixSort(testData, d);
            //checkSorted() messes up benchmark, only for testing correctness:
            checkSorted(testData);
            //System.out.printf("N*D = %d E6   ", n[i] * d);
            System.out.printf("letters = %d E6 ,", sumOfAllLetters / 1000000);
            System.out.printf("Time = %dms\n", t.timeElapsed());
        }
    }

    private static int sumOfAllLetters(ArrayList<String> arrayList){
        int sum = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            sum += arrayList.get(i).length();
        }
        return sum;
    }
}
