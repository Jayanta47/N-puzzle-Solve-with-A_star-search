package A_Search;

import Heuristic.HammingDist;
import Heuristic.IHeuristic;
import Heuristic.LinearConf;
import Heuristic.ManhattanDist;
import Node.Puzzle_Node;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class A_star_Search {
    IUtil utilityFunc;
    IHeuristic h_n;
    HammingDist hm;
    ManhattanDist md;
    LinearConf lc;
    PriorityQueue<Puzzle_Node> pQ;
    ArrayList<Puzzle_Node> solvePath;
    int exploredNode;
    int expandedNode;

    public A_star_Search(String heuristic) {
        hm = new HammingDist();
        md = new ManhattanDist();
        lc = new LinearConf();
        this.setHeuristic(heuristic);
        this.utilityFunc = new Utility();
        this.pQ = new PriorityQueue<>(new QComp());
        this.exploredNode = 0;
        this.expandedNode = 0;
        this.solvePath = new ArrayList<>();
    }

    public String getCurrentHeuristic() {
        return this.h_n.getHeuristicName();
    }

    /**
     * A function to set the heuristic of the class
     *
     * @param heuristic -> the desired heuristic that is intended to run
     */
    private void setHeuristic(String heuristic) {
        if (heuristic.equals("HAMMING")) {
            this.h_n = hm;
        } else if (heuristic.equals("MANHATTAN")) {
            this.h_n = md;
        } else if (heuristic.equals("LINEAR_CONFLICT")) {
            this.h_n = lc;
        }
    }

    /**
     * Method to reset the whole setup and make it new for use
     *
     * @param heuristic -> the desired heuristic that should be used after reset
     */
    public void reset(String heuristic) {
        this.pQ.clear();
        if (!this.h_n.getHeuristicName().equals(heuristic)) {
            this.setHeuristic(heuristic);
        }
        this.exploredNode = 0;
        this.expandedNode = 0;
    }


    /**
     * Method to run A* search on the puzzle boards
     *
     * @param rootNode -> the starting state
     * @return the last node i.e goal state
     */
    public void solve(Puzzle_Node rootNode) {

//        initializing the close list as a hashset
//        the hashset would contain the rolling hash numbers of the puzzle board
//        HashMap<Long, Puzzle_Node> closeList = new HashMap<>();

        HashSet<Long> closeList = new HashSet<>();

//        the open list is created to keep track of the unique nodes that can be explored further
        HashSet<Long> exploredNodes = new HashSet<>();

        Puzzle_Node lastNode = null;
        pQ.add(rootNode);
        exploredNodes.add(rootNode.rollingHash());
//        int i=0;
        while (!pQ.isEmpty()) {
            Puzzle_Node pn = pQ.poll();
//            System.out.println("picked");
//            System.out.println(pn);
//            System.out.println("-->fn: " + pn.get_F_n());
            this.expandedNode++;
//            System.out.println(pn);
            if (this.utilityFunc.isGoalBoard(pn)) {
                lastNode = pn;
                break;
            }
            closeList.add(pn.rollingHash());
            ArrayList<Puzzle_Node> successors = this.utilityFunc.createSuccessor(pn);
            for (Puzzle_Node spn :
                    successors) {
                spn.setHeuristicValue(this.h_n.getHeuristicValue(spn.getBoard_stateCopy(), spn.getSize()));
//                System.out.println("Child");
//                System.out.println(spn);
//                System.out.println("-->"+spn.get_F_n());
//                break;

                if (!closeList.contains(spn.rollingHash())) {
//                    System.out.println("pushed->");
//                    System.out.println(spn);
                    pQ.add(spn);
                    exploredNodes.add(spn.rollingHash());
                }

            }
        }

        this.exploredNode = exploredNodes.size();
        assert lastNode != null;

        Puzzle_Node solveNode = lastNode;
        solvePath.clear();
        while (solveNode.getParentNode() != null) {
            solvePath.add(solveNode);
            solveNode = solveNode.getParentNode();
        }
        solvePath.add(solveNode);

    }

    public HashMap<String, Integer> getResult() {
        HashMap<String, Integer> result = new HashMap<>();
        result.put("cost", solvePath.size()-1);
        result.put("exploredNodes", this.exploredNode);
        result.put("expandedNodes", this.expandedNode-1);
        return result;
    }

    /**
     * Print the solution to the puzzle problem
     * @param pw -> if the output is ti be written in a file, then the file object is given as argument
     */
    public void printSolution(PrintWriter pw) {
        assert solvePath.size()!=0;
        if (pw == null) {
            System.out.println("==Solution Path==");
            System.out.println("-----------------------------------------");
            for (int i = solvePath.size() - 1; i >= 0; i--) {
                System.out.println(solvePath.get(i));
            }
            System.out.println("-----------------------------------------");

        }
        else {
            pw.write("==Solution Path==\n");
            pw.write("-----------------------------------------\n");
            for (int i = solvePath.size() - 1; i >= 0; i--) {
                pw.write(solvePath.get(i).toString());
            }
            pw.write("-----------------------------------------\n");

        }
    }

}

class QComp implements Comparator<Puzzle_Node> {

    @Override
    public int compare(Puzzle_Node pn1, Puzzle_Node pn2) {
        if (pn1.get_F_n() > pn2.get_F_n()) {
            return 1;
        }
        else if (pn1.get_F_n()<pn2.get_F_n()) {
            return -1;
        }
        return 0;
    }
}