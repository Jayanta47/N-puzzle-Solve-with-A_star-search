import A_Search.IUtil;
import A_Search.Utility;
import Heuristic.IHeuristic;
import Heuristic.LinearConf;
import Node.Puzzle_Node;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;

public class test {
    public static void main(String[] args) {
        IUtil u = new Utility();
        int [][] board = new int[4][4];
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++) {
                board[i][j] = i*4+j+1;
            }
        }
        board[3][3] = 0;

        System.out.println(Arrays.deepToString(board));

        if (u.isGoalBoard(new Puzzle_Node(board, 4, null, 0))){
            System.out.println("all");
        }
    }
}
