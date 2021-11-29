package Heuristic;

public class HammingDist implements IHeuristic{
    @Override
    public int getHeuristicValue(int[][] board, int k) {
        int dist =0;
        for (int i =0 ; i<k; i++) {
            for(int j=0;j<k;j++) {
                if (board[i][j] != 0 && board[i][j]!=(i*k+j+1)) {
                    dist++;
                }
            }
        }
        return dist;
    }
}
