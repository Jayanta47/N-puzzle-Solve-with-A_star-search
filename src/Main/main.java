package Main;

import A_Search.A_star_Search;
import Node.Puzzle_Node;
import Solvable.Solvability_Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class main {
    static File test;
    static int k;

    public static int[][] readBoard(String fileName) {
        try {
            test = new File(fileName);
            Scanner scn = new Scanner(test);
            k = Integer.parseInt(scn.nextLine());
//            System.out.println("Size: " + Integer.toString(k));

            int[][] board = new int[k][k];


            for (int i = 0; i < k; i++) {
                String line = scn.nextLine();
                String[] entries = line.split(" ");
                for (int j = 0; j < k; j++) {
                    if (entries[j].equals("*")) {
                        board[i][j] = 0;
                    } else
                        board[i][j] = Integer.parseInt(entries[j]);
                    System.out.printf("%d ", board[i][j]);
                }
                System.out.println();
            }
            scn.close();
//            System.out.println(Arrays.deepToString(board));
            return board;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeToFile(String Heuristic, HashMap<String, Integer> result, PrintWriter pw) {

        pw.write("Heuristic Used: " + Heuristic + "\n");
        pw.write("Optimal Cost: " + result.get("cost") + "\n");
        pw.write("Expanded Nodes: " + result.get("expandedNodes") + "\n");
        pw.write("Explored Nodes: " + result.get("exploredNodes") + "\n\n\n");
    }

    public static void main(String[] args) throws IOException {

        A_star_Search as = new A_star_Search("HAMMING");


        File file = new File("./tests3/");
        File[] filesList = file.listFiles();

        Puzzle_Node pn;
        Puzzle_Node rn;
        int s = 0, us = 0;

        String fileName;
        for (File testFile :
                filesList) {
            fileName = "./tests3/" + testFile.getName();
            System.out.println("Current File: " + fileName);
            pn = new Puzzle_Node(readBoard(fileName), k, null, 0);
            if (Solvability_Test.isSolvable(pn.getBoard_stateCopy(), k)) {
                System.out.println("Status: Solvable");
                s++;
                String outputFileName = "./output3/out_" + testFile.getName();
                System.out.println("--> Redirecting output to file: " + outputFileName);
                System.out.println();

                File writeFile = new File(outputFileName);
                FileOutputStream fos = new FileOutputStream(writeFile, false);
                OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
                BufferedWriter bw = new BufferedWriter(osw);
                PrintWriter pw = new PrintWriter(bw, true);

                pw.write("===SOLUTION=== \n\n");

                // solve for hamming distance

                as.reset("HAMMING");
                as.solve(pn);

                writeToFile(as.getCurrentHeuristic(), as.getResult(), pw);


                as.reset("MANHATTAN");
                as.solve(pn);

                writeToFile(as.getCurrentHeuristic(), as.getResult(), pw);


                as.reset("LINEAR_CONFLICT");
                as.solve(pn);

                writeToFile(as.getCurrentHeuristic(), as.getResult(), pw);

                as.printSolution(pw);

                pw.close();

            } else {
                us++;
                System.out.println("Status: Unsolvable");
                System.out.println();
            }
        }
        System.out.println("-------------------------------------------");
        System.out.printf("Total Solved: %d\nTotal unsolved: %d", s, us);


    }
}
