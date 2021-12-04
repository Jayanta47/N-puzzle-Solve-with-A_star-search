package Heuristic;

public class LinearConf implements IHeuristic {

    IHeuristic manhattanDist;

    public LinearConf() {
        manhattanDist = new ManhattanDist();
    }


    @Override
    public int getHeuristicValue(int[][] board, int k) {
        int man_dist = this.manhattanDist.getHeuristicValue(board, k);
        int n_conflict = 0;

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                if (board[i][j] != 0) {
                    for (int l = j + 1; l < k; l++) {

                        if (board[i][l] != 0 &&
                                (board[i][l] - 1) / k == i &&
                                (board[i][j] - 1) / k == i) {
                            int dest_col_1 = (board[i][j] - 1) - (i * k);
                            int dest_col_2 = (board[i][l] - 1) - (i * k);
                            if (dest_col_1 > dest_col_2)
                                n_conflict++;
                        }
                    }
                }
            }
        }

        return man_dist + 2 * n_conflict;
    }

    @Override
    public String getHeuristicName() {
        return "LINEAR_CONFLICT";
    }
}


