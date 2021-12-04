package Node;

import java.util.Arrays;
import java.util.Objects;

public class Puzzle_Node {
    private int[][] board_state;
    private int k;
    private Puzzle_Node parent;
    private int g_n; // cost from initial state to present state
    private int h_n; // cost from heuristic;
    private boolean isGoalState;

    public Puzzle_Node(int[][] board_state, int k, Puzzle_Node parent, int g_n) {
        this.board_state = board_state;
        this.k = k;
        this.parent = parent;
        this.g_n = g_n;
        this.h_n = 0;
    }


    public int [][] getBoard_stateCopy() {
        int[][] temp = new int[k][k];

        for(int i=0;i<k;i++) {
            System.arraycopy(this.board_state[i], 0, temp[i], 0, k);
        }

        return temp;
    }

    public Puzzle_Node getParentNode() {
        return this.parent;
    }



    @Override
    public String toString() {
        StringBuilder node_view = new StringBuilder();

        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                node_view.append("| "+ (board_state[i][j]==0?"*":board_state[i][j]) +" ");
            }
            node_view.append("|\n");
        }
        node_view.append("\n");
        return node_view.toString();
    }

    public int getSize() {
        return this.k;
    }

    public int getValueAt(int row, int col) {
        return this.board_state[row][col];
    }

    public int [] getBlankPos() {
        int[] pos = new int[2];
        pos[0] = -1;
        for(int i=0;i<this.k;i++) {
            for (int j=0; j<this.k; j++) {
                if (this.board_state[i][j] == 0) {
                    pos[0] = i;
                    pos[1] = j;
                    break;
                }
            }
            if (pos[0] != -1) break;
        }

        return pos;
    }

    public int getDistance() {
        return this.g_n;
    }

    public void setHeuristicValue(int h_n) {
        this.h_n = h_n;
    }

    public int get_F_n() {
        return g_n+h_n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Puzzle_Node that = (Puzzle_Node) o;
        return k == that.k && Arrays.equals(board_state, that.board_state);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(k);
        result = 31 * result + Arrays.hashCode(board_state);
        return result;
    }

    public long rollingHash() {
        long hashValue = 0;
        int m = (int) 1e9+9;
        int p = 31;
        long p_pow = 1;
        for (int i=0;i<this.k;i++) {
            for(int j=0;j<this.k;j++) {
                hashValue = (hashValue+ ((this.board_state[i][j]==0)?10:this.board_state[i][j])*p_pow)%m;
                p_pow = (p_pow*p)%m;
            }
        }

        return hashValue;

    }
}
