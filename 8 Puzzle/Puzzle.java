/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8.puzzle;

/**
 *
 * @author shakil
 */

public class Puzzle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        final int len=3;
        int[][] puzzle = new int[len][len];
        
        System.out.println("A*");
        AStarAlgorithm p1=new AStarAlgorithm();
        p1.initializePuzzle(puzzle);
        p1.startState(puzzle);
        System.out.println();
        
        System.out.println("BFS");
        BFSAlgorithm p2=new BFSAlgorithm();
        p2.initializePuzzle(puzzle);
        p2.breadthFirstSearch(puzzle);
        System.out.println();
        
        System.out.println("Uniformed CostSearch");
        UCSAlgorithm p3=new UCSAlgorithm();
        p3.initializePuzzle(puzzle);
        int[][] resultPuzzle = new int[len][len];
        resultPuzzle = p3.initializeResultPuzzle(resultPuzzle);
        p3.breadthFirstSearchFromTheBeginning(puzzle, resultPuzzle);
        System.out.println();
        
        System.out.println("IDS");
        IDSAlgorithm p4 = new IDSAlgorithm();
        p4.initializePuzzle(puzzle);
        p4.iterativeDeepeningSearch(puzzle);
        System.out.println();
        
    }
    
}
