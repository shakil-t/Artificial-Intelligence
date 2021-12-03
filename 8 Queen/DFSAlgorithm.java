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
import java.util.Stack;

public class DFSAlgorithm {
    protected void dfs(ArrayList<Integer> queens) {
        for (int newQueen = 1; newQueen <= 8; newQueen++) {
            if (!queens.contains(newQueen)) {
                queens.add(newQueen);
                if (solved(queens)) {
                    if (queens.size() == 8) {
                        System.out.println(queens);
                        return;
                    } else {
                        dfs(queens);
                    }
                } else {
                    queens.remove(queens.indexOf(newQueen));
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
