package A_Search;

import Node.Puzzle_Node;

import java.util.ArrayList;

public interface IUtil {
    Puzzle_Node createNode(int [][] board, int k);
    ArrayList<Puzzle_Node> createSuccessor(Puzzle_Node pn);
    boolean isGoalBoard(Puzzle_Node pn);
}
