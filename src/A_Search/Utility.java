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
    public Puzzle_Node createNode(int[][] board, int k) {
        return new Puzzle_Node(board, k, null, 0);
    }

    @Override
    public ArrayList<Puzzle_Node> createSuccessor(Puzzle_Node pn) {
        int [] curr_node_blank = pn.getBlankPos();
        int [] parent_node_blank;

        if (pn.getParentNode() != null) {
            parent_node_blank = pn.getParentNode().getBlankPos();
        }
        else {
            parent_node_blank = new int[2];
            parent_node_blank[0] = -1;
            parent_node_blank[1] = -1;
        }

        // 0 -> row
        // 1 -> col

        int successorCnt = 0;
        int k = pn.getSize();
        ArrayList<Puzzle_Node> success_list = new ArrayList<>();



        if ((curr_node_blank[0]==0 && (curr_node_blank[1] == 0 || curr_node_blank[1]==k-1)) ||
                (curr_node_blank[0] == k-1 && (curr_node_blank[1]==0 || curr_node_blank[1] == k-1))) {
            successorCnt=2;
        }
        else if (curr_node_blank[0]==0||curr_node_blank[0]==k-1||curr_node_blank[1]==0||curr_node_blank[1]==k-1) {
            successorCnt=3;
        }
        else {
            successorCnt=4;
        }

        int [][] temp = pn.getBoard_stateCopy();

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

        if (curr_node_blank[0]!=0) {
            scsr_1[curr_node_blank[0]][curr_node_blank[1]] = scsr_1[curr_node_blank[0]-1][curr_node_blank[1]];
            scsr_1[curr_node_blank[0]-1][curr_node_blank[1]] = 0;
            if (!((curr_node_blank[0]-1)==parent_node_blank[0] && curr_node_blank[1]==parent_node_blank[1])) {
                success_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance()+1));
            }

        }
        if (curr_node_blank[0]!=k-1) {
            scsr_2[curr_node_blank[0]][curr_node_blank[1]] = scsr_2[curr_node_blank[0]+1][curr_node_blank[1]];
            scsr_2[curr_node_blank[0]+1][curr_node_blank[1]] = 0;
            if (!((curr_node_blank[0]+1)==parent_node_blank[0] && curr_node_blank[1]==parent_node_blank[1])) {
                success_list.add(new Puzzle_Node(scsr_2, k, pn, pn.getDistance()+1));
            }
        }
        if (curr_node_blank[1]!=0) {
            scsr_3[curr_node_blank[0]][curr_node_blank[1]] = scsr_3[curr_node_blank[0]][curr_node_blank[1]-1];
            scsr_3[curr_node_blank[0]][curr_node_blank[1]-1] = 0;
            if (!(curr_node_blank[0]==parent_node_blank[0] && (curr_node_blank[1]-1)==parent_node_blank[1])) {
                success_list.add(new Puzzle_Node(scsr_3, k, pn, pn.getDistance()+1));
            }
        }
        if (curr_node_blank[1]!=k-1) {
            scsr_4[curr_node_blank[0]][curr_node_blank[1]] = scsr_4[curr_node_blank[0]][curr_node_blank[1]+1];
            scsr_4[curr_node_blank[0]][curr_node_blank[1]+1] = 0;
            if (!(curr_node_blank[0]==parent_node_blank[0] && (curr_node_blank[1]+1)==parent_node_blank[1])) {
                success_list.add(new Puzzle_Node(scsr_4, k, pn, pn.getDistance()+1));
            }
        }


        return success_list;
    }

//
//    @Override
//    public ArrayList<Puzzle_Node> createSuccessor(Puzzle_Node pn) {
//        ArrayList<Puzzle_Node> succ_list = new ArrayList<>();
//        int[][] temp = pn.getBoard_stateCopy();
//
//        int[] parent_blank_pos;
//        if (pn.getParentNode() != null) {
//            parent_blank_pos = pn.getParentNode().getBlankPos();
//        } else {
//            parent_blank_pos = new int[2];
//            parent_blank_pos[0] = -1;
//            parent_blank_pos[1] = -1;
//        }
//
//        int row = -1, col = -1, k = pn.getSize();
//        boolean isBlankFound = false;
//        for (row = 0; row < k; row++) {
//            for (col = 0; col < k; col++) {
//                if (temp[row][col] == 0) {
//                    isBlankFound = true;
//                    break;
//                }
//            }
//            if (isBlankFound) break;
//        }
//
//        if (row == 0 && col == 0) {
//            int[][] scsr_1 = new int[k][k];
//            for (int i = 0; i < k; i++) {
//                System.arraycopy(temp[i], 0, scsr_1[i], 0, k);
//            }
//            if (parent_blank_pos[0] == row) { // the parent node had move from right column of 0, 0
//                scsr_1[row][col] = temp[row + 1][col];
//                scsr_1[row + 1][col] = 0;
//            } else if (parent_blank_pos[1] == col) {
//                scsr_1[row][col] = temp[row][col + 1];
//                scsr_1[row][col + 1] = 0;
//            }
//
//            succ_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance() + 1));
//
//        } else if (row == 0 && col == k - 1) {
//            int[][] scsr_1 = new int[k][k];
//            for (int i = 0; i < k; i++) {
//                System.arraycopy(temp[i], 0, scsr_1[i], 0, k);
//            }
//            if (parent_blank_pos[0] == row) { // the parent node had move from right column of 0, 0
//                scsr_1[row][col] = temp[row + 1][col];
//                scsr_1[row + 1][col] = 0;
//            } else if (parent_blank_pos[1] == col) {
//                scsr_1[row][col] = temp[row][col - 1];
//                scsr_1[row][col - 1] = 0;
//            }
//
//            succ_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance() + 1));
//        } else if (row == k - 1 && col == 0) {
//            int[][] scsr_1 = new int[k][k];
//            for (int i = 0; i < k; i++) {
//                System.arraycopy(temp[i], 0, scsr_1[i], 0, k);
//            }
//            if (parent_blank_pos[0] == row) { // the parent node had move from right column of 0, 0
//                scsr_1[row][col] = temp[row - 1][col];
//                scsr_1[row - 1][col] = 0;
//            } else if (parent_blank_pos[1] == col) {
//                scsr_1[row][col] = temp[row][col + 1];
//                scsr_1[row][col + 1] = 0;
//            }
//
//            succ_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance() + 1));
//        } else if (row == k - 1 && col == k - 1) {
//            int[][] scsr_1 = new int[k][k];
//            for (int i = 0; i < k; i++) {
//                System.arraycopy(temp[i], 0, scsr_1[i], 0, k);
//            }
//            if (parent_blank_pos[0] == row) { // the parent node had move from right column of 0, 0
//                scsr_1[row][col] = temp[row - 1][col];
//                scsr_1[row - 1][col] = 0;
//            } else if (parent_blank_pos[1] == col) {
//                scsr_1[row][col] = temp[row][col - 1];
//                scsr_1[row][col - 1] = 0;
//            }
//
//            succ_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance() + 1));
//        } else if (row == 0 || row == k - 1) {
//            int[][] scsr_1 = new int[k][k];
//            int[][] scsr_2 = new int[k][k];
//
//            for (int i = 0; i < k; i++) {
//                System.arraycopy(temp[i], 0, scsr_1[i], 0, k);
//                System.arraycopy(temp[i], 0, scsr_2[i], 0, k);
//            }
//
//            if (parent_blank_pos[0] == row) {
//                if (parent_blank_pos[1] == col - 1) {
//                    scsr_1[row][col] = temp[row][col + 1];
//                    scsr_1[row][col + 1] = 0;
//                    if (row == 0) {
//                        scsr_2[row][col] = temp[row + 1][col];
//                        scsr_2[row + 1][col] = 0;
//                    } else {
//                        scsr_2[row][col] = temp[row - 1][col];
//                        scsr_2[row - 1][col] = 0;
//                    }
//                } else if (parent_blank_pos[1] == col + 1) {
//                    scsr_1[row][col] = temp[row][col - 1];
//                    scsr_1[row][col - 1] = 0;
//                    if (row == 0) {
//                        scsr_2[row][col] = temp[row + 1][col];
//                        scsr_2[row + 1][col] = 0;
//                    } else {
//                        scsr_2[row][col] = temp[row - 1][col];
//                        scsr_2[row - 1][col] = 0;
//                    }
//                }
//            } else if (parent_blank_pos[1] == col) {
//                scsr_1[row][col] = temp[row][col - 1];
//                scsr_1[row][col - 1] = 0;
//                scsr_2[row][col] = temp[row][col + 1];
//                scsr_2[row][col + 1] = 0;
//            }
//
//            succ_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance() + 1));
//            succ_list.add(new Puzzle_Node(scsr_2, k, pn, pn.getDistance() + 1));
//        } else if (col == 0 || col == k - 1) {
//            int[][] scsr_1 = new int[k][k];
//            int[][] scsr_2 = new int[k][k];
//
//            for (int i = 0; i < k; i++) {
//                System.arraycopy(temp[i], 0, scsr_1[i], 0, k);
//                System.arraycopy(temp[i], 0, scsr_2[i], 0, k);
//            }
//
//            if (parent_blank_pos[1] == col) {
//                if (parent_blank_pos[0] == row - 1) {
//                    scsr_1[row][col] = temp[row + 1][col];
//                    scsr_1[row + 1][col] = 0;
//                    if (col == 0) {
//                        scsr_2[row][col] = temp[row][col + 1];
//                        scsr_2[row][col + 1] = 0;
//                    } else {
//                        scsr_2[row][col] = temp[row][col - 1];
//                        scsr_2[row][col - 1] = 0;
//                    }
//                } else if (parent_blank_pos[0] == row + 1) {
//                    scsr_1[row][col] = temp[row - 1][col];
//                    scsr_1[row - 1][col] = 0;
//                    if (col == 0) {
//                        scsr_2[row][col] = temp[row][col + 1];
//                        scsr_2[row][col + 1] = 0;
//                    } else {
//                        scsr_2[row][col] = temp[row][col - 1];
//                        scsr_2[row][col - 1] = 0;
//                    }
//                }
//            } else if (parent_blank_pos[0] == row) {
//                scsr_1[row][col] = temp[row - 1][col];
//                scsr_1[row - 1][col] = 0;
//                scsr_2[row][col] = temp[row + 1][col];
//                scsr_2[row + 1][col] = 0;
//            }
//
//            succ_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance() + 1));
//            succ_list.add(new Puzzle_Node(scsr_2, k, pn, pn.getDistance() + 1));
//        } else {
////            System.out.println("haha");
//            int[][] scsr_1 = new int[k][k]; // at row-1, col
//            int[][] scsr_2 = new int[k][k]; // at row+1, col
//            int[][] scsr_3 = new int[k][k]; // at row, col-1
//            int[][] scsr_4 = new int[k][k]; // at row, col+1
//
//            for (int i = 0; i < k; i++) {
//                System.arraycopy(temp[i], 0, scsr_1[i], 0, k);
//                System.arraycopy(temp[i], 0, scsr_2[i], 0, k);
//                System.arraycopy(temp[i], 0, scsr_3[i], 0, k);
//                System.arraycopy(temp[i], 0, scsr_4[i], 0, k);
//            }
//
//            scsr_1[row][col] = temp[row-1][col];
//            scsr_1[row-1][col] = 0;
//            scsr_2[row][col] = temp[row+1][col];
//            scsr_2[row+1][col] = 0;
//            scsr_3[row][col] = temp[row][col-1];
//            scsr_3[row][col-1] = 0;
//            scsr_4[row][col] = temp[row][col+1];
//            scsr_4[row][col+1] = 0;
//
//            if (parent_blank_pos[0] == row) {
//                if (parent_blank_pos[1] == col-1) { // all except 3
//                    succ_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance()+1));
//                    succ_list.add(new Puzzle_Node(scsr_2, k, pn, pn.getDistance()+1));
//                    succ_list.add(new Puzzle_Node(scsr_4, k, pn, pn.getDistance()+1));
//                }
//                else { // all except 4
//                    succ_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance()+1));
//                    succ_list.add(new Puzzle_Node(scsr_2, k, pn, pn.getDistance()+1));
//                    succ_list.add(new Puzzle_Node(scsr_3, k, pn, pn.getDistance()+1));
//                }
//            }
//            else if (parent_blank_pos[1] == col) {
//                if (parent_blank_pos[0] == row-1) { // all except 1
//                    succ_list.add(new Puzzle_Node(scsr_2, k, pn, pn.getDistance()+1));
//                    succ_list.add(new Puzzle_Node(scsr_3, k, pn, pn.getDistance()+1));
//                    succ_list.add(new Puzzle_Node(scsr_4, k, pn, pn.getDistance()+1));
//                }
//                else { // all except 2
//                    succ_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance()+1));
//                    succ_list.add(new Puzzle_Node(scsr_3, k, pn, pn.getDistance()+1));
//                    succ_list.add(new Puzzle_Node(scsr_4, k, pn, pn.getDistance()+1));
//                }
//            }
//            else if (parent_blank_pos[0]==-1 && parent_blank_pos[1]==-1) {
//                succ_list.add(new Puzzle_Node(scsr_1, k, pn, pn.getDistance()+1));
//                succ_list.add(new Puzzle_Node(scsr_2, k, pn, pn.getDistance()+1));
//                succ_list.add(new Puzzle_Node(scsr_3, k, pn, pn.getDistance()+1));
//                succ_list.add(new Puzzle_Node(scsr_4, k, pn, pn.getDistance()+1));
//            }
//
//
//
//        }
//
//        return succ_list;
//    }

    @Override
    public boolean isGoalBoard(Puzzle_Node pn) {
        int [][] temp = pn.getBoard_stateCopy();
        int k = pn.getSize();
        for(int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                if (temp[i][j] != 0) {
                    if (temp[i][j] != i*k+j+1) return false;
                }
                else {
                    if (i*k+j!=8) return false;
                }
            }
        }
        return true;
    }
}
