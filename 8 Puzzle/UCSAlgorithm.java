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

public class UCSAlgorithm {
    private static final int dimension = 3;

    private Queue<int[][]> puzzleQueueFromTheBeginning = new LinkedList<int[][]>();
    private Queue<String> solutionQueueFromTheBeginning = new LinkedList<String>();
    private LinkedList<Long> codeOfPuzzleListFromTheBeginning = new LinkedList<>();
    private LinkedList<String> solutionOfPuzzleListFromTheBeginning = new LinkedList<>();

    private Queue<int[][]> puzzleQueueFromTheEnd = new LinkedList<int[][]>();
    private Queue<String> solutionQueueFromTheEnd = new LinkedList<String>();
    private LinkedList<Long> codeOfPuzzleListFromTheEnd = new LinkedList<>();
    private LinkedList<String> solutionOfPuzzleListFromTheEnd = new LinkedList<>();

    protected void breadthFirstSearchFromTheBeginning(int[][] puzzleFromTheBeginning, int[][] puzzleFromTheEnd) {
        int iIndexOfZeroFromTheBeginning = 0;
        int jIndexOfZeroFromTheBeginning = 0;
        int iIndexOfZeroFromTheEnd = 0;
        int jIndexOfZeroFromTheEnd = 0;

        long codeOfPuzzleFromTheBeginning = encoding(puzzleFromTheBeginning);
        codeOfPuzzleListFromTheBeginning.add(codeOfPuzzleFromTheBeginning);
        solutionOfPuzzleListFromTheBeginning.add("");
        puzzleQueueFromTheBeginning.add(puzzleFromTheBeginning);
        solutionQueueFromTheBeginning.add("");

        long codeOfPuzzleFromTheEnd = encoding(puzzleFromTheEnd);
        codeOfPuzzleListFromTheEnd.add(codeOfPuzzleFromTheEnd);
        solutionOfPuzzleListFromTheEnd.add("");
        puzzleQueueFromTheEnd.add(puzzleFromTheEnd);
        solutionQueueFromTheEnd.add("");

        while (!puzzleQueueFromTheBeginning.isEmpty() && !solutionQueueFromTheBeginning.isEmpty()
                && !puzzleQueueFromTheEnd.isEmpty() && !solutionQueueFromTheEnd.isEmpty()) {

            int[][] puzzleValueFromTheBeginning = puzzleQueueFromTheBeginning.poll();
            String solutionValueFromTheBeginning = solutionQueueFromTheBeginning.poll();

            int[][] puzzleValueFromTheEnd = puzzleQueueFromTheEnd.poll();
            String solutionValueFromTheEnd = solutionQueueFromTheEnd.poll();

            long codeOfPuzzleValueFromTheBeginning = encoding(puzzleValueFromTheBeginning);
            long codeOfPuzzleValueFromTheEnd = encoding(puzzleValueFromTheEnd);

            if (codeOfPuzzleListFromTheBeginning.contains(codeOfPuzzleValueFromTheEnd)
                    || codeOfPuzzleListFromTheEnd.contains(codeOfPuzzleValueFromTheBeginning)) {
                if (codeOfPuzzleListFromTheBeginning.contains(codeOfPuzzleValueFromTheEnd)) {
                    System.out.println("first_BFS");
                    int solutionIndex = codeOfPuzzleListFromTheBeginning.indexOf(codeOfPuzzleValueFromTheEnd);
                    printSolution(puzzleFromTheBeginning, solutionOfPuzzleListFromTheBeginning.get(solutionIndex), iIndexOfZeroFromTheBeginning, jIndexOfZeroFromTheBeginning, 1);
                    System.out.println("second_BFS");
                    solutionIndex = codeOfPuzzleListFromTheEnd.indexOf(codeOfPuzzleValueFromTheEnd);
                    printReverseSolution(puzzleValueFromTheEnd, solutionOfPuzzleListFromTheEnd.get(solutionIndex), iIndexOfZeroFromTheEnd, jIndexOfZeroFromTheEnd, 1);
                    //printReverseSolution(puzzleValueFromTheEnd, solutionValueFromTheEnd, iIndexOfZeroFromTheEnd, jIndexOfZeroFromTheEnd, 1);
                } else {
                    System.out.println("first_BFS");
                    int solutionIndex = codeOfPuzzleListFromTheBeginning.indexOf(codeOfPuzzleValueFromTheBeginning);
                    printSolution(puzzleFromTheBeginning, solutionOfPuzzleListFromTheBeginning.get(solutionIndex), iIndexOfZeroFromTheBeginning, jIndexOfZeroFromTheBeginning, 1);
                    System.out.println("second_BFS");
                    solutionIndex = codeOfPuzzleListFromTheEnd.indexOf(codeOfPuzzleValueFromTheBeginning);
                    printReverseSolution(puzzleValueFromTheBeginning, solutionOfPuzzleListFromTheEnd.get(solutionIndex), iIndexOfZeroFromTheEnd, jIndexOfZeroFromTheEnd, 1);
                }
                System.out.println("solved");
                return;
            }

            for (int i = 0; i < puzzleValueFromTheBeginning.length; i++) {
                for (int j = 0; j < puzzleValueFromTheBeginning[0].length; j++) {
                    if (puzzleValueFromTheBeginning[i][j] == 0) {
                        iIndexOfZeroFromTheBeginning = i;
                        jIndexOfZeroFromTheBeginning = j;
                    }
                }
            }
            ArrayList<Character> availableMovesCharactersFromTheBeginning = checkAvailableMoves(puzzleValueFromTheBeginning, iIndexOfZeroFromTheBeginning, jIndexOfZeroFromTheBeginning);

            for (int i = 0; i < availableMovesCharactersFromTheBeginning.size(); i++) {
                int[][] tempOfPuzzleValueFromTheBeginning = move(puzzleValueFromTheBeginning, availableMovesCharactersFromTheBeginning.get(i), iIndexOfZeroFromTheBeginning, jIndexOfZeroFromTheBeginning);
                String tempOfSolutionValueFromTheBeginning = solutionValueFromTheBeginning;
                codeOfPuzzleFromTheBeginning = encoding(tempOfPuzzleValueFromTheBeginning);
                if (!codeOfPuzzleListFromTheBeginning.contains(codeOfPuzzleFromTheBeginning)) {
                    tempOfSolutionValueFromTheBeginning = tempOfSolutionValueFromTheBeginning + availableMovesCharactersFromTheBeginning.get(i);
                    solutionQueueFromTheBeginning.add(tempOfSolutionValueFromTheBeginning);
                    puzzleQueueFromTheBeginning.add(tempOfPuzzleValueFromTheBeginning);
                    codeOfPuzzleListFromTheBeginning.add(codeOfPuzzleFromTheBeginning);
                    solutionOfPuzzleListFromTheBeginning.add(tempOfSolutionValueFromTheBeginning);
                }
            }

            for (int i = 0; i < puzzleValueFromTheEnd.length; i++) {
                for (int j = 0; j < puzzleValueFromTheEnd[0].length; j++) {
                    if (puzzleValueFromTheEnd[i][j] == 0) {
                        iIndexOfZeroFromTheEnd = i;
                        jIndexOfZeroFromTheEnd = j;
                    }
                }
            }
            ArrayList<Character> availableMovesCharactersFromTheEnd = checkAvailableMoves(puzzleValueFromTheEnd, iIndexOfZeroFromTheEnd, jIndexOfZeroFromTheEnd);

            for (int i = 0; i < availableMovesCharactersFromTheEnd.size(); i++) {
                int[][] tempOfPuzzleValueFromTheEnd = move(puzzleValueFromTheEnd, availableMovesCharactersFromTheEnd.get(i), iIndexOfZeroFromTheEnd, jIndexOfZeroFromTheEnd);
                String tempOfSolutionValueFromTheEnd = solutionValueFromTheEnd;
                codeOfPuzzleFromTheEnd = encoding(tempOfPuzzleValueFromTheEnd);
                if (!codeOfPuzzleListFromTheEnd.contains(codeOfPuzzleFromTheEnd)) {
                    tempOfSolutionValueFromTheEnd = tempOfSolutionValueFromTheEnd + availableMovesCharactersFromTheEnd.get(i);
                    solutionQueueFromTheEnd.add(tempOfSolutionValueFromTheEnd);
                    puzzleQueueFromTheEnd.add(tempOfPuzzleValueFromTheEnd);
                    codeOfPuzzleListFromTheEnd.add(codeOfPuzzleFromTheEnd);
                    solutionOfPuzzleListFromTheEnd.add(tempOfSolutionValueFromTheEnd);
                }
            }
        }
        System.out.println("not solved");
        return;
    }

    private void printSolution(int[][] puzzle, String solutionValue, int iOfZero, int jOfZero, int stateNumber) {
        System.out.print("state " + stateNumber + " : ");
        printPuzzle(puzzle);
        for (int l = 0; l < solutionValue.length(); l++) {
            for (int i = 0; i < puzzle.length; i++) {
                for (int j = 0; j < puzzle[0].length; j++) {
                    if (puzzle[i][j] == 0) {
                        iOfZero = i;
                        jOfZero = j;
                    }
                }
            }
            puzzle = move(puzzle, solutionValue.charAt(l), iOfZero, jOfZero);
            stateNumber++;
            System.out.print("state " + stateNumber + " : ");
            printPuzzle(puzzle);
        }

    }

    private void printReverseSolution(int[][] puzzle, String solutionValue, int iOfZero, int jOfZero, int stateNumber) {
        System.out.print("state " + stateNumber + " : ");
        printPuzzle(puzzle);
        for (int l = solutionValue.length() - 1; l >= 0; l--) {
            for (int i = 0; i < puzzle.length; i++) {
                for (int j = 0; j < puzzle[0].length; j++) {
                    if (puzzle[i][j] == 0) {
                        iOfZero = i;
                        jOfZero = j;
                    }
                }
            }
            puzzle = reverse(puzzle, solutionValue.charAt(l), iOfZero, jOfZero);
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

    private int[][] reverse(int[][] head, Character ch, int iIndexOfZero, int jIndexOfZero) {
        int[][] result = new int[3][3];
        for (int i = 0; i < head.length; i++) {
            for (int j = 0; j < head[0].length; j++) {
                result[i][j] = head[i][j];
            }
        }

        if (ch.equals('D')) {
            result[iIndexOfZero - 1][jIndexOfZero] = head[iIndexOfZero][jIndexOfZero];
            result[iIndexOfZero][jIndexOfZero] = head[iIndexOfZero - 1][jIndexOfZero];
            return result;
        }
        if (ch.equals('U')) {
            result[iIndexOfZero + 1][jIndexOfZero] = head[iIndexOfZero][jIndexOfZero];
            result[iIndexOfZero][jIndexOfZero] = head[iIndexOfZero + 1][jIndexOfZero];
            return result;
        }
        if (ch.equals('R')) {
            result[iIndexOfZero][jIndexOfZero - 1] = head[iIndexOfZero][jIndexOfZero];
            result[iIndexOfZero][jIndexOfZero] = head[iIndexOfZero][jIndexOfZero - 1];
            return result;
        }
        if (ch.equals('L')) {
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

    protected int[][] initializeResultPuzzle(int[][] resultPuzzle) {
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
