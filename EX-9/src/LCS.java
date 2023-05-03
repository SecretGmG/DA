import java.util.Arrays;

public class LCS {

    static void lcs(String X, String Y){
        String[][] b = new String[X.length() + 1][Y.length() + 1];
        int[][] c = new int[X.length() + 1][Y.length() + 1];

        for (int i = 1; i < X.length() + 1; i++) {
            for (int j = 1; j < Y.length() + 1; j++) {
                if(X.charAt(i-1) == Y.charAt(j-1)){
                    c[i][j] = c[i-1][j-1] + 1;
                    b[i][j] = "↖";
                }
                else if(c[i-1][j] >= c[i][j-1]){
                    c[i][j] = c[i-1][j];
                    b[i][j] = "↑";
                }
                else{
                    c[i][j] = c[i][j-1];
                    b[i][j] = "←";
                }
            }
        }
        for(var subarr : b){
            System.out.println(Arrays.toString(subarr));
        }
        for(var suabrr : c){
           System.out.println(Arrays.toString(suabrr));
        }
    }

    public static void main(String[] args) {
        lcs("10110100","01001101");
    }
}
