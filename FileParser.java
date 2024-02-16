import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.Vector;

/**
 * This class represent the File parsing.
 */
public class FileParser {
    BufferedReader br; // buffer

    /**
     * Constructor
     * @param path - the path to the file
     */
    public FileParser(String path) {
        File file = new File(path);
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            System.err.println("No input file");
        }

    }

    /**
     * Parse the file
     * @return the matrix
     * @throws IOException - if the file is not found
     */
    public int[][] parse() throws IOException {
        String line;
        Vector<Integer> black, red;
        int[][] mat;

        /* First Settings */
        Algorithms.alg = br.readLine();
        String wtime = br.readLine();
        if (wtime.equalsIgnoreCase("with time"))
            Algorithms.with_time = true;
        else
            Algorithms.with_time = false;
        String openList = br.readLine();
        if (openList.equalsIgnoreCase("no open"))
            Algorithms.with_open_list = false;
        else
            Algorithms.with_open_list = true;

        /*              */
        String[] nm = br.readLine().split("x"); // mat [N][M]
        int rows = Integer.parseInt(nm[0]), colm = Integer.parseInt(nm[1]);
        mat = new int[rows][colm];
        Node.setGOAL(rows, colm);

        line = br.readLine(); // WHITE
        Node.WHITE = parseColors(line.substring(6));

        /* Matrix */
        while (br.ready()) {
            for (int i = 0; i < mat.length; i++) {
                String[] lineNumbers = br.readLine().split(",");
                int j = 0;
                for (String s : lineNumbers) {
                    mat[i][j++] = s.equals("_") ? 0 : Integer.parseInt(s);
                }
            }
        }
        return mat;
    }

    /**
     * Parse the white cells given in the input file
     * @param str - the string to parse
     * @return the parsed colors as a vector
     */
    private Vector<Integer> parseColors(String str) {
        Vector<Integer> vector = new Vector<>();
        HashMap<Integer, Integer> moveCount = new HashMap<>(); // white blocks
        String[] tileMoveCouples;
        try {
            tileMoveCouples = str.substring(1).split(",");
        } catch (Exception e) {
            Node.moveCount = moveCount;
            return vector;
        }

        for (String string : tileMoveCouples) {
            if (string.startsWith("("))
                vector.add(Integer.parseInt(string.substring(1)));
            if (string.endsWith(")"))
                moveCount.put(vector.lastElement(), Integer.parseInt(string.substring(0, 1)));

        }

        Node.moveCount = moveCount;

        return vector;
    }

}
