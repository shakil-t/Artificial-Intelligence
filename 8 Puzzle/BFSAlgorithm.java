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

//چون تعداد متغیرها خیلی بالا بوده به همه نوعشون در انتهای اسمشون اضافه شده تا مشکل یا ابهامی پیش نیاد

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFSAlgorithm {
    private static final int dimension = 3;

    private Queue<int[][]> puzzleQueue = new LinkedList<int[][]>();
    private Queue<String> solutionQueue = new LinkedList<String>();
    private LinkedList<Long> codeOfPuzzleList = new LinkedList<>();

    protected void breadthFirstSearch(int[][] puzzle) {
        int iIndexOfZero = 0;
        int jIndexOfZero = 0;
        int[][] resultPuzzle = new int[dimension][dimension];
        resultPuzzle = initializeResultPuzzle(resultPuzzle);

        long codeOfPuzzle = encoding(puzzle);
        codeOfPuzzleList.add(codeOfPuzzle);
        puzzleQueue.add(puzzle);
        solutionQueue.add("");
        while (!puzzleQueue.isEmpty() && !solutionQueue.isEmpty()) {
            int[][] puzzleValue = puzzleQueue.poll();
            String solutionValue = solutionQueue.poll();
            if (solved(resultPuzzle, puzzleValue)) {
                printSolution(puzzle, solutionValue, iIndexOfZero, jIndexOfZero, 1);
                System.out.println("solved");
                return;
            }

            for (int i = 0; i < puzzleValue.length; i++) {
                for (int j = 0; j < puzzleValue[0].length; j++) {
                    if (puzzleValue[i][j] == 0) {
                        iIndexOfZero = i;
                        jIndexOfZero = j;
                    }
                }
            }
            ArrayList<Character> availableMovesCharacters = checkAvailableMoves(puzzleValue, iIndexOfZero, jIndexOfZero);

            for (int i = 0; i < availableMovesCharacters.size(); i++) {
                int[][] temp = move(puzzleValue, availableMovesCharacters.get(i), iIndexOfZero, jIndexOfZero);
                String tempOfSolutionValue = solutionValue;
                codeOfPuzzle = encoding(temp);
                if (!codeOfPuzzleList.contains(codeOfPuzzle)) {
                    tempOfSolutionValue = tempOfSolutionValue + availableMovesCharacters.get(i);
                    solutionQueue.add(tempOfSolutionValue);
                    puzzleQueue.add(temp);
                    codeOfPuzzleList.add(codeOfPuzzle);
                }
            }
        }
        System.out.println("not solved");
        return;
    }

    private void printSolution(int[][] puzzle, String value, int iIndexOfZero, int jIndexOfZero, int stateNumber) {
        System.out.print("state " + stateNumber + " : ");
        printPuzzle(puzzle);
        for (int l = 0; l < value.length(); l++) {
            for (int i = 0; i < puzzle.length; i++) {
                for (int j = 0; j < puzzle[0].length; j++) {
                    if (puzzle[i][j] == 0) {
                        iIndexOfZero = i;
                        jIndexOfZero = j;
                    }
                }
            }
            puzzle = move(puzzle, value.charAt(l), iIndexOfZero, jIndexOfZero);
            stateNumber++;
            System.out.print("state " + stateNumber + " : ");
            printPuzzle(puzzle);
        }

    }

    private long encoding(int[][] puzzle) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                result.append(puzzle[i][j]);
            }
        }
        String r = result + "";
        return Long.parseLong(r);
    }

    private int[][] move(int[][] head, Character ch, int iIndexOfZero, int jIndexOfZero) {
        int[][] result = new int[3][3];
        for (int i = 0; i < head.length; i++) {
            for (int j = 0; j < head[0].length; j++) {
                result[i][j] = head[i][j];
            }
        }

        if (ch.equals('U')) {
            result[iIndexOfZero - 1][jIndexOfZero] = head[iIndexOfZero][jIndexOfZero];
            result[iIndexOfZero][jIndexOfZero] = head[iIndexOfZero - 1][jIndexOfZero];
            return result;
        }
        if (ch.equals('D')) {
            result[iIndexOfZero + 1][jIndexOfZero] = head[iIndexOfZero][jIndexOfZero];
            result[iIndexOfZero][jIndexOfZero] = head[iIndexOfZero + 1][jIndexOfZero];
            return result;
        }
        if (ch.equals('L')) {
            result[iIndexOfZero][jIndexOfZero - 1] = head[iIndexOfZero][jIndexOfZero];
            result[iIndexOfZero][jIndexOfZero] = head[iIndexOfZero][jIndexOfZero - 1];
            return result;
        }
        if (ch.equals('R')) {
            result[iIndexOfZero][jIndexOfZero + 1] = head[iIndexOfZero][jIndexOfZero];
            result[iIndexOfZero][jIndexOfZero] = head[iIndexOfZero][jIndexOfZero + 1];
            return result;
        }
        return null;
    }

    private ArrayList<Character> checkAvailableMoves(int[][] head, int i, int j) {
        ArrayList<Character> availableMovesCharacters = new ArrayList<>(4);
        if (i > 0) {
            availableMovesCharacters.add('U');
        }
        if (i < 2) {
            availableMovesCharacters.add('D');
        }
        if (j > 0) {
            availableMovesCharacters.add('L');
        }
        if (j < 2) {
            availableMovesCharacters.add('R');
        }
        return availableMovesCharacters;
    }

    private int[][] initializeResultPuzzle(int[][] resultPuzzle) {
        int number = 1;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                resultPuzzle[i][j] = number;
                if (number < 8) {
                    number++;
                } else {
                    number = 0;
                }
            }
        }
        return resultPuzzle;
    }

    private boolean solved(int[][] result, int[][] puzzle) {

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                if (puzzle[i][j] != result[i][j]) {
                    return false;
                }
            }
        }
        return true;

    }

    protected void initializePuzzle(int[][] puzzle) {
        puzzle[0][0] = 1;
        puzzle[0][1] = 3;
        puzzle[0][2] = 5;
        puzzle[1][0] = 7;
        puzzle[1][1] = 0;
        puzzle[1][2] = 2;
        puzzle[2][0] = 4;
        puzzle[2][1] = 6;
        puzzle[2][2] = 8;
    }

    private void printPuzzle(int[][] result) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            output.append("[");
            for (int j = 0; j < result[0].length; j++) {
                output.append(result[i][j]);
                if (j < result[0].length - 1) {
                    output.append(",");
                }
            }
            output.append("]");
        }
        System.out.print(output);
        System.out.println();
    }
}
