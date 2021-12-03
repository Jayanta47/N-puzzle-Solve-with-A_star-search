package Heuristic;

public interface IHeuristic {
    int getHeuristicValue(int[][] board, int k);
    String getHeuristicName();
}
