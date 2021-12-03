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
import java.util.Stack;

public class IDSAlgorithm {
    private static final int dimension = 3;

    private Stack<String> solutionStack = new Stack<>();
    private LinkedList<Long> codeOfPuzzleList = new LinkedList<>();
    private int[][] firstPuzzle = new int[dimension][dimension];
    private boolean isSolved = false;
    private boolean isNotSolved = true;

    protected void iterativeDeepeningSearch(int[][] puzzle) {
        int[][] resultPuzzle = new int[dimension][dimension];
        resultPuzzle = initializeResultPuzzle(resultPuzzle);
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                firstPuzzle[i][j] = puzzle[i][j];
            }
        }
        for (int l = 1;; l++) {
            solutionStack = new Stack<>();
            solutionStack.add("");
            codeOfPuzzleList = new LinkedList<>();
            if (isSolved) {
                System.out.print("solved");
                return;
            }
            long codeOfPuzzle = encoding(puzzle);
            codeOfPuzzleList.add(codeOfPuzzle);
            depthLimitedSearch(resultPuzzle, puzzle, l, 0);
            if (isNotSolved) {
                System.out.println("not solved");
                return;
            }
        }
    }

    private void depthLimitedSearch(int[][] resultPuzzle, int[][] puzzle, int length, int depth) {

        int iIndexOfZero = 0;
        int jIndexOfZero = 0;

        String value = solutionStack.pop();
        if (solved(resultPuzzle, puzzle)) {
            printSolution(firstPuzzle, value, iIndexOfZero, jIndexOfZero, 1);
            isSolved = true;
            return;
        }
        if (depth < length) {
            for (int i = 0; i < puzzle.length; i++) {
                for (int j = 0; j < puzzle[0].length; j++) {
                    if (puzzle[i][j] == 0) {
                        iIndexOfZero = i;
                        jIndexOfZero = j;
                    }
                }
            }
            ArrayList<Character> availableMovesCharacters = checkAvailableMoves(iIndexOfZero, jIndexOfZero);
            for (int i = 0; i < availableMovesCharacters.size(); i++) {
                int[][] tempOfPuzzleValue = move(puzzle, availableMovesCharacters.get(i), iIndexOfZero, jIndexOfZero);
                String tempOfSolutionValue = value;
                long codeOfPuzzle = encoding(tempOfPuzzleValue);
                if (!codeOfPuzzleList.contains(codeOfPuzzle)) {
                    codeOfPuzzleList.add(codeOfPuzzle);
                    isNotSolved = false;
                    tempOfSolutionValue = tempOfSolutionValue + availableMovesCharacters.get(i);
                    solutionStack.add(tempOfSolutionValue);
                    depthLimitedSearch(resultPuzzle, tempOfPuzzleValue, length, depth + 1);
                }
            }
        }
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

    private ArrayList<Character> checkAvailableMoves(int i, int j) {
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

    protected void initializePuzzle(int[][] puzzle) {
        puzzle[0][0] = 8;
        puzzle[0][1] = 6;
        puzzle[0][2] = 7;
        puzzle[1][0] = 2;
        puzzle[1][1] = 5;
        puzzle[1][2] = 4;
        puzzle[2][0] = 3;
        puzzle[2][1] = 0;
        puzzle[2][2] = 1;
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
