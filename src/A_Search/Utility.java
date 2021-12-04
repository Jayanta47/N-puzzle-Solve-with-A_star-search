package A_Search;

import Node.Puzzle_Node;

import java.util.ArrayList;

public class Utility implements IUtil {

    private boolean areNodesEqual(Puzzle_Node p1, Puzzle_Node p2) {
        if (p1 == p2) return true;
        else if (p1.getSize() != p2.getSize()) return false;
        int k = p1.getSize();
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                if (p1.getValueAt(i, j) != p2.getValueAt(i, j)) return false;
            }
        }
        return true;
    }

    private boolean areNodesEqual(int[][] board1, int[][] board2, int k) {
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                if (board1[i][j] != board2[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<Puzzle_Node> createSuccessor(Puzzle_Node pn) {
        int[] curr_node_blank = pn.getBlankPos();
        int[] parent_node_blank;

        if (pn.getParentNode() != null) {
            parent_node_blank = pn.getParentNode().getBlankPos();
        } else {
            parent_node_blank = new int[2];
            parent_node_blank[0] = -1;
            parent_node_blank[1] = -1;
        }

        // 0 -> row
        // 1 -> col

        int successorCnt = 0;
        int k = pn.getSize();
        ArrayList<Puzzle_Node> success_list = new ArrayList<>();


        if ((curr_node_blank[0] == 0 && (curr_node_blank[1] == 0 || curr_node_blank[1] == k - 1)) ||
                (curr_node_blank[0] == k - 1 && (curr_node_blank[1] == 0 || curr_node_blank[1] == k - 1))) {
            successorCnt = 2;
        } else if (curr_node_blank[0] == 0 || curr_node_blank[0] == k - 1 || curr_node_blank[1] == 0 || curr_node_blank[1] == k - 1) {
            successorCnt = 3;
        } else {
            successorCnt = 4;
        }

        int[][] temp = pn.getBoard_stateCopy();

        int[][] scsr_1 = new int[k][k]; // at row-1, col
        int[][] scsr_2 = new int[k][k]; // at row+1, col
        int[][] scsr_3 = new int[k][k]; // at row, col-1
        int[][] scsr_4 = new int[k][k]; // at row, col+1

        for (int i = 0; i < k; i++) {
            System.arraycopy(temp[i], 0, scsr_1[i], 0, k);
            System.arraycopy(temp[i], 0, scsr_2[i], 0, k);
            System.arraycopy(temp[i], 0, scsr_3[i], 0, k);
            System.arraycopy(temp[i], 0, scsr_4[i], 0, k);
        }

        if (curr_node_blank[0] != 0) {
            scsr_1[curr_node_blank[0]][curr_node_blank[1]] = scsr_1[curr_node_blank[0] - 1][curr_node_blank[1]];
            scsr_1[curr_node_blank[0] - 1][curr_node_blank[1]] = 0;
            if (!((curr_node_blank[0] - 1) == parent_node_blank[0] && curr_node_blank[1] == parent_node_blank[1])) {
                success_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance() + 1));
            }

        }
        if (curr_node_blank[0] != k - 1) {
            scsr_2[curr_node_blank[0]][curr_node_blank[1]] = scsr_2[curr_node_blank[0] + 1][curr_node_blank[1]];
            scsr_2[curr_node_blank[0] + 1][curr_node_blank[1]] = 0;
            if (!((curr_node_blank[0] + 1) == parent_node_blank[0] && curr_node_blank[1] == parent_node_blank[1])) {
                success_list.add(new Puzzle_Node(scsr_2, k, pn, pn.getDistance() + 1));
            }
        }
        if (curr_node_blank[1] != 0) {
            scsr_3[curr_node_blank[0]][curr_node_blank[1]] = scsr_3[curr_node_blank[0]][curr_node_blank[1] - 1];
            scsr_3[curr_node_blank[0]][curr_node_blank[1] - 1] = 0;
            if (!(curr_node_blank[0] == parent_node_blank[0] && (curr_node_blank[1] - 1) == parent_node_blank[1])) {
                success_list.add(new Puzzle_Node(scsr_3, k, pn, pn.getDistance() + 1));
            }
        }
        if (curr_node_blank[1] != k - 1) {
            scsr_4[curr_node_blank[0]][curr_node_blank[1]] = scsr_4[curr_node_blank[0]][curr_node_blank[1] + 1];
            scsr_4[curr_node_blank[0]][curr_node_blank[1] + 1] = 0;
            if (!(curr_node_blank[0] == parent_node_blank[0] && (curr_node_blank[1] + 1) == parent_node_blank[1])) {
                success_list.add(new Puzzle_Node(scsr_4, k, pn, pn.getDistance() + 1));
            }
        }


        return success_list;
    }

    @Override
    public boolean isGoalBoard(Puzzle_Node pn) {
        int[][] temp = pn.getBoard_stateCopy();
        int k = pn.getSize();
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                if (temp[i][j] != 0) {
                    if (temp[i][j] != (i * k + j + 1)) return false;
                } else {
                    if ((i * k + j) != (k*k-1)) return false;
                }
            }
        }
        return true;
    }
}
