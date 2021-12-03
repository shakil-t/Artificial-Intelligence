/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg8.queen;

/**
 *
 * @author shakil
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFSAlgorithm {
    private Queue<ArrayList<Integer>> q = new LinkedList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> test = new ArrayList<ArrayList<Integer>>();
    private static final int chessBoard = 8;
    private static final int totalStates = 92;

    protected void breadthFirstSearch(ArrayList<Integer> chessboard) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>(totalStates);
        q.add(chessboard);
        int stateNumber = 1;
        while (!q.isEmpty()) {
            ArrayList<Integer> head = q.poll();
            test.add(head);
            if (solved(head)) {
                result.add(head);
                System.out.println("state " + stateNumber + " : " + head);
                stateNumber++;
                if (result.size() >= totalStates) {
                    return;
                }
            }
            ArrayList<Integer> childOfHead = new ArrayList<>();
            for (int i = 0; i < head.size(); i++) {
                childOfHead.add(head.get(i));
            }

            for (int i = 0; i < head.size() - 1; i++) {
                for (int j = i + 1; j < head.size(); j++) {
                    childOfHead.set(i, head.get(j));
                    childOfHead.set(j, head.get(i));
                    if (!test.contains(childOfHead) && !q.contains(childOfHead)) {
                        q.add(childOfHead);
                    }
                    childOfHead = new ArrayList<>();
                    for (int k = 0; k < head.size(); k++) {
                        childOfHead.add(head.get(k));
                    }
                }
            }
        }
    }

    private boolean solved(ArrayList<Integer> head) {
        for (int i = 0; i < head.size() - 1; i++) {
            for (int j = i + 1; j < head.size(); j++) {
                if ((head.get(i) - head.get(j)) == (i - j) || (head.get(j) - head.get(i)) == (i - j)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void initializeChessboard(ArrayList<Integer> chessboard) {
        for (int i = 0; i < chessBoard; i++) {
            chessboard.add(i + 1);
        }
    }
}
