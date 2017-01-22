import java.util.Stack;
import java.util.Arrays;

/**
 * @author Dima Pasieka
 */
public class Board
{
    // Board size
    private int dimension;

    // Current board with blocks
    private int[][] blocksBoard;

    // Construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)
    {
        dimension = blocks.length;
        blocksBoard = copyBlocks(blocks, dimension);
    }

    // Board dimension n
    public int dimension()
    {
        return dimension;
    }

    // Number of blocks out of place
    public int hamming()
    {
        int count = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                if ((blocksBoard[i][j] != 0) && (blocksBoard[i][j] != expectedValueAtPosition(i, j))) {
                    count++;
                }
            }
        }

        return count;
    }

    // Sum of Manhattan distances between blocks and goal
    public int manhattan()
    {
        int sum = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {

                if ((blocksBoard[i][j] != 0) && (blocksBoard[i][j] != expectedValueAtPosition(i, j))) {
                    int expectedI = (blocksBoard[i][j] - 1) / dimension;
                    int expectedJ = (blocksBoard[i][j] - 1) % dimension;

                    // sum vertical and horizontal difference in distance
                    sum += Math.abs(i - expectedI) + Math.abs(j - expectedJ);
                }
            }
        }

        return sum;
    }

    // Is this board the goal board?
    public boolean isGoal()
    {
        // either hamming() or manhattan() can be used
        return (hamming() == 0);
    }

    // A board that is obtained by exchanging any pair of blocks
    public Board twin()
    {
        int[][] blocksCopy = copyBlocks(blocksBoard, dimension);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension - 1; j++) {

                if ((blocksCopy[i][j] != 0) && (blocksCopy[i][j + 1] != 0)) {
                    int toSwap = blocksCopy[i][j];
                    blocksCopy[i][j] =  blocksCopy[i][j + 1];
                    blocksCopy[i][j + 1] = toSwap;

                    return new Board(blocksCopy);
                }
            }
        }

        return null;
    }

    // Does this board equal y?
    public boolean equals(Object y)
    {
        if (y == null) {
            return false;
        }

        if (y == this) {
            return true;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;

        return Arrays.deepEquals(this.blocksBoard, that.blocksBoard);
    }

    // All neighboring boards
    public Iterable<Board> neighbors()
    {
        Stack<Board> boardStack = new Stack<>();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                // we have found 0 block
                if (blocksBoard[i][j] == 0) {

                    // if 0 block is not at top position
                    if (i > 0) {
                        int[][] blocksCopy = copyBlocks(blocksBoard, dimension);
                        blocksCopy[i][j] = blocksBoard[i - 1][j];
                        blocksCopy[i - 1][j] = blocksBoard[i][j];
                        boardStack.push(new Board(blocksCopy));
                    }

                    // if 0 block is not at left position
                    if (j > 0) {
                        int[][] blocksCopy = copyBlocks(blocksBoard, dimension);
                        blocksCopy[i][j] = blocksBoard[i][j - 1];
                        blocksCopy[i][j - 1] = blocksBoard[i][j];
                        boardStack.push(new Board(blocksCopy));
                    }

                    // if 0 block is not at bottom position
                    if (i < dimension - 1) {
                        int[][] blocksCopy = copyBlocks(blocksBoard, dimension);
                        blocksCopy[i][j] = blocksBoard[i + 1][j];
                        blocksCopy[i + 1][j] = blocksBoard[i][j];
                        boardStack.push(new Board(blocksCopy));
                    }

                    // if 0 block is not at right position
                    if (j < dimension - 1) {
                        int[][] blocksCopy = copyBlocks(blocksBoard, dimension);
                        blocksCopy[i][j] = blocksBoard[i][j + 1];
                        blocksCopy[i][j + 1] = blocksBoard[i][j];
                        boardStack.push(new Board(blocksCopy));
                    }

                    break;
                }
            }
        }

        return boardStack;
    }

    // String representation of this board (in the output format specified below)
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", blocksBoard[i][j]));
            }
            s.append("\n");
        }

        return s.toString();
    }

    // Copy blocks (2-dimension array)
    private int[][] copyBlocks(int[][] blocks, int dimensionSize) {
        int[][] result = new int[dimensionSize][dimensionSize];

        for (int i = 0; i < dimensionSize; i++) {
            for (int j = 0; j < dimensionSize; j++) {
                result[i][j] = blocks[i][j];
            }
        }

        return result;
    }

    // Get expected value that should be in provided position
    private int expectedValueAtPosition(int i, int j)
    {
        return ((i * dimension) + j + 1);
    }

        // Unit tests (not graded)
    public static void main(String[] args)
    {
        // To implement
    }
}
