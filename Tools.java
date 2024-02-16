import java.util.Arrays;
import java.util.Vector;

/**
 * This class represent and contain simple tools for the project.
 * @author Tom Shabalin
 */
public class Tools {
    
    /**
     * Prints a vector
     * @param <T> the type of the vector
     * @param v the vector to print
     */
    public static <T> void printVec(Vector<T> v){
        System.out.println(Arrays.deepToString(v.toArray()));
    }
    
    /**
     * Parses a number from a string
     * @param s the string to parse
     * @return the parsed number
     */
    public static int parseNum(String s){
        return Integer.parseInt(s.substring(0,s.length()-1));
    }
    
    /**
     * Prints a matrix
     * @param mat the matrix to print
     * @return the matrix as a string
     */
    public static String matString(int[][] mat){
        String res = "";
        for (int i = 0; i < mat.length; i++) {
            res+= "[";
            for (int j = 0; j < mat[0].length; j++) {
                res+= " " + mat[i][j] + " ";
            }
            res+= "]\n";
        }
        return res;
    }
    
    /**
     * Clones a matrix
     * @param mat the matrix to clone
     * @return the cloned matrix
     */
    public static int[][] Clone(int[][] mat){
        int[][] res = new int[mat.length][mat[0].length];
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                res[i][j] = mat[i][j];
            }
        }
        return res;
    }
    
    /**
     * Swaps two elements in a matrix
     * @param i1 
     * @param j1
     * @param i2
     * @param j2
     * @param mat
     * @return the matrix after the swap
     */
    public static int[][] swap(int i1, int j1, int i2, int j2, int[][] mat){
        int temp = mat[i1][j1];
        mat[i1][j1] = mat[i2][j2];
        mat[i2][j2] = temp;
        return mat;
    }
}
