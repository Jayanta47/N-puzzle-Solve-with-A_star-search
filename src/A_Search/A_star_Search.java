package A_Search;

import Heuristic.HammingDist;
import Heuristic.IHeuristic;
import Heuristic.LinearConf;
import Heuristic.ManhattanDist;
import Node.Puzzle_Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class A_star_Search {
    IUtil utilityFunc;
    IHeuristic h_n;
    PriorityQueue<Puzzle_Node> pQ;
    int exploredNode;

    public A_star_Search(String heuristic) {
        this.setHeuristic(heuristic);
        this.utilityFunc = new Utility();
        this.pQ = new PriorityQueue<>(new QComp());
        this.exploredNode = 0;
    }

    private void setHeuristic(String heuristic) {
        if (heuristic.equals("HAMMING")) {
            this.h_n = new HammingDist();
        }
        else if (heuristic.equals("MANHATTAN")) {
            this.h_n = new ManhattanDist();
        }
        else if (heuristic.equals("LINEAR_CONFLICT")) {
            this.h_n = new LinearConf();
        }
    }

    public void reset(String heuristic) {
        this.pQ.clear();
        if(!this.h_n.getHeuristicName().equals(heuristic)) {
            this.setHeuristic(heuristic);
        }
        this.exploredNode = 0;

    }

    public Puzzle_Node solve(Puzzle_Node rootNode) {
        HashMap<Long, Puzzle_Node> closeList = new HashMap<>();

        Puzzle_Node lastNode = null;
        pQ.add(rootNode);

        while(!pQ.isEmpty()) {
            Puzzle_Node pn = pQ.poll();
            this.exploredNode++;

            if (this.utilityFunc.isGoalBoard(pn)) {
                lastNode = pn;
                System.out.println("haha");
                break;
            }
            closeList.put(pn.rollingHash(), pn);
            ArrayList<Puzzle_Node> successors = this.utilityFunc.createSuccessor(pn);
//            System.out.println(successors.size());
            for (Puzzle_Node spn:
                 successors) {
//                System.out.println("Haha");
                spn.setHeuristicValue(this.h_n.getHeuristicValue(spn.getBoard_stateCopy(), spn.getSize()));
                if (!closeList.containsKey(spn.rollingHash())) {
//                    System.out.println(spn);
//
//                    System.out.println("yes");
                    pQ.add(spn);
                }
            }
        }
        return lastNode;

    }

    public void printSolution(Puzzle_Node solveNode) {
        System.out.println("===SOLUTION=== \n");
        ArrayList<Puzzle_Node> solvePath = new ArrayList<>();
        while (solveNode.getParentNode()!=null) {
            solvePath.add(solveNode);
            solveNode = solveNode.getParentNode();
        }
        solvePath.add(solveNode);

        for(int i = solvePath.size()-1; i>=0; i--) {
            System.out.println(solvePath.get(i));
        }

    }

}

class QComp implements Comparator<Puzzle_Node> {

    @Override
    public int compare(Puzzle_Node pn1, Puzzle_Node pn2) {
        return pn1.get_F_n()-pn2.get_F_n();
    }
}