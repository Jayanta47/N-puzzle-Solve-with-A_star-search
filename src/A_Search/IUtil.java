package A_Search;

import Node.Puzzle_Node;

import java.util.ArrayList;

public interface IUtil {
    ArrayList<Puzzle_Node> createSuccessor(Puzzle_Node pn);
    boolean isGoalBoard(Puzzle_Node pn);
}
