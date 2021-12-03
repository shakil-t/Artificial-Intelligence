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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class AStarAlgorithm {
    //ابعاد پازل که میشه به عنوان ورودی هم در نظر گرفت
    private static final int dimension = 3;

    private LinkedList<Long> code = new LinkedList<>();
    private Map<int[][], Long> puzzleMap = new HashMap<>();
    private ArrayList<Integer> puzzleList = new ArrayList<>();
    private ArrayList<Long> H = new ArrayList<>();
    private int[][] resultPuzzle = new int[dimension][dimension];
    private Map<Long, Character> solutionMap = new HashMap<>();
    private Stack<int[][]> solutionStack = new Stack<>();

    //وضعیت آغازین که ابتدا چک میکنیم قابل حل هست یا نه و اگر قابل حل بود ادامه میدیم
    protected void startState(int[][] puzzle) {
        if (solvable(puzzle)) {
            System.out.print("Can not solve!");
            return;
        }
        resultPuzzle = initializeResultPuzzle(resultPuzzle);
        long codeOfPuzzle = encoding(puzzle);
        code.add(codeOfPuzzle);
        ArrayList<int[][]> temp = new ArrayList<>();
        temp.add(puzzle);
        solutionMap.put(codeOfPuzzle, '0');
        aStar(puzzle);
    }

    //چک میکنیم پازل قابل حل هست یا نه
    private boolean solvable(int[][] puzzle) {
        ArrayList<Integer> withoutZero = new ArrayList<>(8);
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                int value = puzzle[i][j];
                if (value != 33) {
                    withoutZero.add(puzzleList.indexOf(value));
                }
            }
        }
        int inversion = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (withoutZero.get(i) < withoutZero.get(j)) {
                    inversion++;
                }
            }
        }
        if (inversion % 2 == 1) {
            //قابل حل نیست
            return true;
        }
        //قابل حله
        return false;
    }

    private void aStar(int[][] puzzle) {

        int iIndexOfZero = 0;
        int jIndexOfZero = 0;

        if (solved(resultPuzzle, puzzle)) {
            searchSolution(puzzle);
            return;
        }
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (puzzle[i][j] == 33) {
                    iIndexOfZero = i;
                    jIndexOfZero = j;
                }
            }
        }
        ArrayList<Character> availableMovesCharacters = checkAvailableMoves(iIndexOfZero, jIndexOfZero);
        for (int i = 0; i < availableMovesCharacters.size(); i++) {
            int[][] childOfPuzzle = move(puzzle, availableMovesCharacters.get(i), iIndexOfZero, jIndexOfZero);
            long codeOfPuzzle = encoding(childOfPuzzle);
            if (!code.contains(codeOfPuzzle)) {
                Long hOfPuzzle = calculateH(childOfPuzzle);
                code.add(codeOfPuzzle);
                H.add(hOfPuzzle);
                puzzleMap.put(childOfPuzzle, hOfPuzzle);
                solutionMap.put(codeOfPuzzle, availableMovesCharacters.get(i));
            }
        }

        if (!H.isEmpty()) {
            Long minH = findMinH(H);
            int[][] minPuzzle = null;
            for (int[][] keyValue : puzzleMap.keySet()) {
                if (puzzleMap.get(keyValue) != null && puzzleMap.get(keyValue).equals(minH)) {
                    minPuzzle = keyValue;
                    break;
                }
            }
            H.remove(minH);
            puzzleMap.put(minPuzzle, null);
            aStar(minPuzzle);
        } else {
            System.out.print("not solved");
            return;
        }
    }

    private Long findMinH(ArrayList<Long> arrayList) {
        long min = 40;
        for (int i = 0; i < arrayList.size(); i++) {
            long temp = arrayList.get(i);
            if (min > temp) {
                min = temp;
            }
        }
        return min;
    }

    private void searchSolution(int[][] puzzle) {
        solutionStack.add(puzzle);
        findTheParent(puzzle);
    }

    private void findTheParent(int[][] puzzle) {
        long codeOfPuzzle = encoding(puzzle);
        if (solutionMap.get(codeOfPuzzle).equals('0')) {
            printSolution();
            return;
        }

        int iIndexOfZero = 0;
        int jIndexOfZero = 0;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (puzzle[i][j] == 33) {
                    iIndexOfZero = i;
                    jIndexOfZero = j;
                }
            }
        }
        int[][] parentOfPuzzle = reverse(puzzle, solutionMap.get(codeOfPuzzle), iIndexOfZero, jIndexOfZero);
        solutionStack.add(parentOfPuzzle);
        findTheParent(parentOfPuzzle);
    }

    private void printSolution() {
        int size = solutionStack.size();
        for (int stateNumber = 0; stateNumber < size; stateNumber++) {
            printPuzzle(solutionStack.pop());
        }
        System.out.println("States:"+size);
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

    private long encoding(int[][] puzzle) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                result.append(puzzleList.indexOf(puzzle[i][j]));
            }
        }
        String r = result + "";
        return Long.parseLong(r);
    }

    private int[][] move(int[][] head, Character ch, int iIndexOfZero, int jIndexOfZero) {
        int[][] result = new int[dimension][dimension];
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

    private long calculateH(int[][] puzzle) {
        long h = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int temp = puzzle[i][j] % 10;
                int a = temp - (j + 1);
                if (a < 0) {
                    a = (j + 1) - temp;
                }
                int b = (puzzle[i][j] - temp) / 10 - (i + 1);
                if (b < 0) {
                    b = (i + 1) - (puzzle[i][j] - temp) / 10;
                }
                h += a + b;
            }
        }
        return h;
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
        resultPuzzle[0][0] = 11;
        resultPuzzle[0][1] = 12;
        resultPuzzle[0][2] = 13;
        resultPuzzle[1][0] = 21;
        resultPuzzle[1][1] = 22;
        resultPuzzle[1][2] = 23;
        resultPuzzle[2][0] = 31;
        resultPuzzle[2][1] = 32;
        resultPuzzle[2][2] = 33;
        return resultPuzzle;
    }

    protected void initializePuzzle(int[][] puzzle) {
        initializePuzzleArrayList();
        puzzle[0][0] = puzzleList.get(8);
        puzzle[0][1] = puzzleList.get(0);
        puzzle[0][2] = puzzleList.get(6);
        puzzle[1][0] = puzzleList.get(5);
        puzzle[1][1] = puzzleList.get(4);
        puzzle[1][2] = puzzleList.get(7);
        puzzle[2][0] = puzzleList.get(2);
        puzzle[2][1] = puzzleList.get(3);
        puzzle[2][2] = puzzleList.get(1);
    }

    private void initializePuzzleArrayList() {
        puzzleList.add(33);
        puzzleList.add(11);
        puzzleList.add(12);
        puzzleList.add(13);
        puzzleList.add(21);
        puzzleList.add(22);
        puzzleList.add(23);
        puzzleList.add(31);
        puzzleList.add(32);
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
                output.append(puzzleList.indexOf(result[i][j]));
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
