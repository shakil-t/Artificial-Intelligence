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

public class BackTracking {
    private static final int dimension = 8;

    private Queue<ArrayList<Integer>> q = new LinkedList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

    protected void bt() {

        int stateNumber = 1;
        for (int stateOfFirstQueen = 1; stateOfFirstQueen <= dimension; stateOfFirstQueen++) {
            ArrayList<Integer> queen = new ArrayList<>(dimension);
            queen.add(stateOfFirstQueen);
            q.add(queen);
            while (!q.isEmpty()) {
                ArrayList<Integer> positionOfQueens = q.poll();
                result.add(positionOfQueens);

                for (int newQueen = 1; newQueen <= dimension; newQueen++) {
                    if (!positionOfQueens.contains(newQueen)) {
                        ArrayList<Integer> temp = new ArrayList<>(dimension);
                        for (int k = 0; k < positionOfQueens.size(); k++) {
                            temp.add(positionOfQueens.get(k));
                        }
                        temp.add(newQueen);
                        if (solved(temp)) {
                            if (temp.size() == dimension) {
                                System.out.println("state " + stateNumber + " : " + temp);
                                stateNumber++;
                                break;
                            } else if (!q.contains(temp) && !result.contains(temp)) {
                                q.add(temp);
                            }
                        }
                    }
                }
            }

        }

    }

    private boolean solved(ArrayList<Integer> positionOfQueens) {
        for (int i = 0; i < positionOfQueens.size() - 1; i++) {
            for (int j = i + 1; j < positionOfQueens.size(); j++) {
                if ((positionOfQueens.get(i) == positionOfQueens.get(j)) || (positionOfQueens.get(i) - positionOfQueens.get(j)) == (i - j) || (positionOfQueens.get(j) - positionOfQueens.get(i)) == (i - j)) {
                    return false;
                }
            }
        }
        return true;
    }
}
