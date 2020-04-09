import java.io.PrintWriter;
import java.util.Scanner;

public class Lesson02 {
    private static final int ELEMENTS_PER_LINE = 4;
    private static final int LINES_COUNT = 4;

    private static String[][] getMatrix(Scanner in) throws MatrixFormatException {
        String[][] result = new String[LINES_COUNT][ELEMENTS_PER_LINE];
        String[] line = in.nextLine().split("\\\\n");
        if (line.length > LINES_COUNT || line.length == 0) {
            throw new MatrixFormatException("Incorrect number of lines in matrix");
        }
        for (int i = 0; i < line.length; i++) {
            result[i] = line[i].split(" ");
            if (result[i].length > ELEMENTS_PER_LINE)
                throw new MatrixFormatException("Too many elements in line");
        }
        return result;
    }

    private static int[][] convertMatrix(String[][] strMatrix) throws MyNumberFormatException {
        int[][] matrix = new int[LINES_COUNT][ELEMENTS_PER_LINE];
        for (int i = 0; i < LINES_COUNT; i++) {
            for (int j = 0; i < ELEMENTS_PER_LINE; j++) {
                try {
                    matrix[i][j] = Integer.parseInt(strMatrix[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyNumberFormatException(e.getMessage());
                }
            }
        }
        return matrix;
    }

    private static int calculate(int m[][]) {
        int result = 0;
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                result += m[i][j];
            }
        }
        return result / 2;
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in);
             PrintWriter out = new PrintWriter(System.out);
        ) {
            String[][] s = getMatrix(in);
            int[][] m = convertMatrix(s);
            int result = calculate(m);
            out.println("Result is " + result);
        } catch (MatrixFormatException | MyNumberFormatException e) {
            System.err.println(e.getMessage());
        }
    }
}

class MatrixFormatException extends RuntimeException {
    public MatrixFormatException() {
    }

    public MatrixFormatException(String msg) {
        super(msg);
    }
}

class MyNumberFormatException extends RuntimeException {
    public MyNumberFormatException() {
    }

    public MyNumberFormatException(String msg) {
        super(msg);
    }
}
