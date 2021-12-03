package Main;

import A_Search.A_star_Search;
import A_Search.IUtil;
import A_Search.Utility;
import Heuristic.IHeuristic;
import Heuristic.LinearConf;
import Heuristic.ManhattanDist;
import Node.Puzzle_Node;
import Solvable.Solvability_Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class main {
    static File test;
    static int k;

    public static int[][] readBoard(String fileName) {
        try {
            test = new File(fileName);
            Scanner scn = new Scanner(test);
            k = Integer.parseInt(scn.nextLine());
            System.out.println("Size: " + Integer.toString(k));

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
                    System.out.print(entries[j]+" ");
                }
                System.out.println();
            }

            return board;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {

        int [][] board = readBoard("test3.txt");
//        int [][] unsolvable_board = readBoard("test2.txt");


        IHeuristic hr=  new ManhattanDist();
        int man_dist = hr.getHeuristicValue(board, k);
        System.out.println("Manhattan : " + Integer.toString(man_dist));

        hr=  new LinearConf();
        int lc_dist = hr.getHeuristicValue(board, k);
        System.out.println("Linear Conflict : " + Integer.toString(lc_dist));

        System.out.println(Boolean.toString(Solvability_Test.isSolvable(board, k)));

//        File file = new File("./tests/");
//        File[] filesList = file.listFiles();
//
//        Puzzle_Node pn;
//        ArrayList<Long> hashvals = new ArrayList<>();
//        for (File file2:
//             filesList) {
//            String fileName = "./tests/"+file2.getName();
//            pn = new Puzzle_Node(readBoard(fileName), 3, null, 0);
//            hashvals.add(pn.rollingHash());
//            System.out.println(pn.rollingHash());
//        }
//
//        Set<Long> set = new HashSet<>(hashvals);
//        if (hashvals.size() == set.size()) {
//            System.out.println("All unique");
//        }
//        System.out.printf(set.toString());
        A_star_Search as = new A_star_Search("MANHATTAN");
//        IUtil iu = new Utility();
//        ArrayList<Puzzle_Node> succ = iu.createSuccessor(new Puzzle_Node(board, k, null, 0));
//        for (Puzzle_Node a:
//             succ) {
//            System.out.println(a);
//        }

        File file = new File("./tests/");
        File[] filesList = file.listFiles();

        Puzzle_Node pn;
        ArrayList<Long> hashvals = new ArrayList<>();
        int s = 0, us = 0;
        for (File file2:
             filesList) {
            String fileName = "./tests/"+file2.getName();
            pn = new Puzzle_Node(readBoard(fileName), 3, null, 0);
            if (Solvability_Test.isSolvable(pn.getBoard_stateCopy(), k)) {
                pn = as.solve(pn);
                if (pn!=null) {s++; as.printSolution(pn);}
                else System.out.println("not found");
            }
            else us++;
        }

        System.out.printf("Total Solved: %d\nTotal unsolved: %d", s, us);

//        if (Solvability_Test.isSolvable(board, k)) {
//            Puzzle_Node solveNode = as.solve(new Puzzle_Node(board, k, null, 0));
//            if (solveNode!=null) as.printSolution(solveNode);
//            else System.out.println("not found");
//        }

    }
}
