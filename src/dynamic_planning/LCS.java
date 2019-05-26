package dynamic_planning;

/**
 *最长公共子序列
 */
public class LCS {
   static void LCS_LENGTH(char[] m, char[] n){
       int mLength = m.length;
       int nLength = n.length;
       //存储记录长度
       int[][] c = new int[mLength][nLength];
       //c是由哪一个记录到达的
       int[][] b = new int[mLength][nLength];
       for (int i=1; i<mLength; i++) c[i][0] = 0;
       for (int j=0; j<nLength; j++) c[0][j] = 0;
       for (int i=1; i<mLength; i++){
           for (int j=1; j<nLength; j++){
               if (m[i] == n[j]){
                   c[i][j] = c[i-1][j-1] + 1;
                   b[i][j] = 1; //"先右上的箭头
               }
               else if (c[i-1][j] >= c[i][j-1]){
                   c[i][j] = c[i-1][j];
                   b[i][j] = 2;//向上的箭头
               }
               else{
                   c[i][j] = c[i][j-1];
                   b[i][j] = 3; //先左的箭头
               }
           }
       }
       LCS(b, m, mLength-1, nLength-1);
   }

   static void LCS(int[][] b, char[] m, int i, int j){
       if (i == 0 || j == 0)
           return;
       if (b[i][j] == 1){
           LCS(b, m, i-1, j-1);
           System.out.println(m[i]);
       }
       else if (b[i][j] == 2) LCS(b, m, i-1, j);
       else LCS(b, m, i, j-1);
   }

    public static void main(String[] args) {
        char[] m = new char[]{
                '1', 'A', 'B', 'C', 'B', 'D', 'A', 'B'
        };
        char[] n = new char[]{
                '2', 'B', 'D', 'C', 'A', 'B', 'A'
        };
        LCS_LENGTH(m, n);
    }
}

