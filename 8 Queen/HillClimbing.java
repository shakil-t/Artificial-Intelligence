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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class HillClimbing {
    private static final int chessBoard = 100;

    LinkedList<Integer> random = new LinkedList<>();
    private HashMap<Integer, Integer> calculateFitness = new HashMap<>(10000, 0.6f);

    protected void HC() {
        for (int i = 1; i < chessBoard + 1; i++) {
            random.add(i);
        }
        ArrayList<ArrayList<Integer>> checkedChessboards = new ArrayList<>(10000);
        ArrayList<Integer> currentState = new ArrayList<>(chessBoard);
        ArrayList<Integer> neighborState = new ArrayList<>(chessBoard);

        currentState = initialize(random);
        if (solved(currentState)) {
            System.out.println(currentState);
            return;
        }
        if (checkedChessboards.contains(currentState)) {
            HC();
            return;
        }
        checkedChessboards.add(currentState);
        for (;;) {
            neighborState = childrenFitness(getChildren(currentState), currentState);
            if (neighborState == null) {
                HC();
                return;
            }
            if (checkedChessboards.contains(neighborState)) {
                HC();
                return;
            }
            if (solved(neighborState)) {
                System.out.println(neighborState);
                return;
            }
            checkedChessboards.add(neighborState);
            currentState = new ArrayList<>();
            for (int j = 0; j < neighborState.size(); j++) {
                currentState.add(neighborState.get(j));
            }
        }

    }

    private ArrayList<Integer> childrenFitness(ArrayList<ArrayList<Integer>> children, ArrayList<Integer> currentState) {
        int hParent = fitness(currentState);
        int minH = fitness(children.get(0));
        ArrayList<Integer> minChild = children.get(0);
        for (int i = 1; i < children.size(); i++) {
            int hTemp = fitness(children.get(i));
            if (minH > hTemp) {
                minH = hTemp;
                minChild = children.get(i);
            }
        }
        if (minH > hParent) {
            return null;
        }
        return minChild;
    }

    private int fitness(ArrayList<Integer> chessboard) {
        int hashCode = chessboard.hashCode();
        Integer result = calculateFitness.get(hashCode);
        if (result != null) {
            return result.intValue();
        }

        int h = 0;
        for (int i = 0; i < chessboard.size() - 1; i++) {
            for (int j = i + 1; j < chessboard.size(); j++) {
                if ((Math.abs(chessboard.get(i) - chessboard.get(j))) == (j - i)) {
                    h++;
                }
            }
        }
        calculateFitness.put(hashCode, h);
        return h;
    }

    private ArrayList<ArrayList<Integer>> getChildren(ArrayList<Integer> currentState) {
        ArrayList<ArrayList<Integer>> children = new ArrayList<>();
        for (int i = 0; i < chessBoard - 1; i++) {
            for (int j = i + 1; j < chessBoard; j++) {
                ArrayList<Integer> child = new ArrayList<>(currentState);
                child.set(i, currentState.get(j));
                child.set(j, currentState.get(i));
                children.add(child);
            }
        }
        return children;
    }

    private ArrayList<Integer> initialize(LinkedList<Integer> randomList) {
        Random rand = new Random(System.currentTimeMillis());
        ArrayList<Integer> chessboard = new ArrayList<>();
        while (!randomList.isEmpty()) {
            int index = rand.nextInt(randomList.size());
            Integer remove = randomList.remove(index);
            chessboard.add(remove);
        }
        return chessboard;
    }

    private boolean solved(ArrayList<Integer> chessboard) {
        for (int i = 0; i < chessboard.size() - 1; i++) {
            for (int j = i + 1; j < chessboard.size(); j++) {
                if ((Math.abs(chessboard.get(i) - chessboard.get(j)) == (j - i))) {
                    return false;
                }
            }
        }
        return true;
    }
}
