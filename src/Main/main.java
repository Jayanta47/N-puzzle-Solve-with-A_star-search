package Main;

import Heuristic.IHeuristic;
import Heuristic.ManhattanDist;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        File test = new File("test.txt");
        try {
            Scanner scn = new Scanner(test);
            int k = Integer.parseInt(scn.nextLine());
            System.out.println(Integer.toString(k));

            int [][] board= new int[k][k];



            for(int i=0;i<k;i++) {
                String line = scn.nextLine();
                String[] entries = line.split(" ");
                for (int j =0; j<k;j++)  {
                    if (entries[j].equals("*")) {
                        board[i][j] = 0;
                    }
                    else
                        board[i][j] = Integer.parseInt(entries[j]);
                    System.out.printf(entries[j]+" ");
                }
                System.out.println();
            }

            IHeuristic hr=  new ManhattanDist();
            int man_dist = hr.getHeuristicValue(board, k);
            System.out.println("Manhattan : " + Integer.toString(man_dist));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
