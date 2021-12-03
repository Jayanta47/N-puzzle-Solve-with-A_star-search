package Heuristic;

public class ManhattanDist implements IHeuristic{

    @Override
    public int getHeuristicValue(int[][] board, int k) {
        int dist = 0;
        int row_dest;
        int col_dest;
        for (int i =0; i<k; i++) {
            for (int j = 0; j < k; j++) {
                if (board[i][j] != 0) {
                    row_dest = board[i][j]/k;
                    col_dest = board[i][j]-(row_dest*k)-1;

                    dist+=Math.abs(col_dest-j)+Math.abs(row_dest-i);
//                    System.out.println(Integer.toString(dist));
                }
            }
        }
        return dist;
    }

    @Override
    public String getHeuristicName() {
        return "MANHATTAN";
    }
}
