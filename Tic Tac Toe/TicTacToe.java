/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tic.tac.toe;

/**
 *
 * @author shakil
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Algorithm {
    String name;
    public static final int empty = 0;
    public static final int x = 1;
    public static final int o = 2;
    private static final int xWining = 1;
    private static final int xLosing = -1;
    private static final int draw = 0;

    Algorithm(String name){
        this.name=name;
    }
    
    protected ArrayList<Integer> initialize(ArrayList<Integer> game) {
        for (int i = 1; i < 9; i++) {
            game.add(empty);
        }
        return game;
    }

    protected ArrayList<Integer> answer(ArrayList<Integer> game) {
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<ArrayList<Integer>> children = getChildrenOfX(game);
        HashMap<ArrayList<Integer>, Integer> resultMap = new HashMap<>();
        for (ArrayList<Integer> child : children) {
            ArrayList<Integer> perResult = miniMax(child);
            if (perResult.get(0).equals(xWining)) {
                int perResultPath = perResult.get(1);
                resultMap.put(child, perResultPath);
                path.add(perResultPath);
            }
        }
        if (resultMap.size() == 0) {
            for (ArrayList<Integer> child : children) {
                ArrayList<Integer> perResult = miniMax(child);
                if (perResult.get(0).equals(draw)) {
                    return child;
                }
            }
            return children.get(0);
        } else {
            int minPath = findPath(path);
            for (ArrayList<Integer> key : resultMap.keySet()) {
                if (resultMap.get(key).equals(minPath)) {
                    return key;
                }
            }
            return children.get(0);
        }
    }

    private int findPath(ArrayList<Integer> path) {
        int minPath = 1000000;
        for (int i = 0; i < path.size(); i++) {
            int p = path.get(i);
            if (p < minPath) {
                minPath = p;
            }
        }
        return minPath;
    }

    private ArrayList<Integer> miniMax(ArrayList<Integer> game) {
        int path = 1;
        ArrayList<Integer> v = minValue(game, path);
        return v;
    }

    private ArrayList<Integer> maxValue(ArrayList<Integer> game, int path) {
        ArrayList<Integer> v = new ArrayList<>(2);
        if (check(game)) {
            v.add(utility(game));
            v.add(path);
            return v;
        }
        int max = -100000000;
        int childPath = 1;
        ArrayList<ArrayList<Integer>> children = getChildrenOfX(game);
        for (ArrayList<Integer> child : children) {
            ArrayList<Integer> minValue = minValue(child, path);
            max = getMax(max, minValue.get(0));
            childPath = minValue.get(1);
        }
        v.add(max);
        childPath++;
        v.add(childPath);
        return v;
    }

    private ArrayList<Integer> minValue(ArrayList<Integer> game, int path) {
        ArrayList<Integer> v = new ArrayList<>(2);
        if (check(game)) {
            v.add(utility(game));
            v.add(path);
            return v;
        }
        int min = 100000000;
        int childPath = 1;
        ArrayList<ArrayList<Integer>> children = getChildrenOfO(game);
        for (ArrayList<Integer> child : children) {
            ArrayList<Integer> maxValue = maxValue(child, path);
            min = getMin(min, maxValue.get(0));
            childPath = maxValue.get(1);
        }
        v.add(min);
        childPath++;
        v.add(childPath);
        return v;
    }

    private ArrayList<ArrayList<Integer>> getChildrenOfX(ArrayList<Integer> game) {
        ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 9; i++) {
            if (game.get(i).equals(empty)) {
                ArrayList<Integer> child = new ArrayList<>(9);
                for (int k = 0; k < 9; k++) {
                    child.add(game.get(k));
                }
                child.set(i, x);
                children.add(child);
            }
        }
        return children;
    }

    private ArrayList<ArrayList<Integer>> getChildrenOfO(ArrayList<Integer> game) {
        ArrayList<ArrayList<Integer>> children = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 9; i++) {
            if (game.get(i).equals(empty)) {
                ArrayList<Integer> child = new ArrayList<>(9);
                for (int k = 0; k < 9; k++) {
                    child.add(game.get(k));
                }
                child.set(i, o);
                children.add(child);
            }
        }
        return children;
    }

    private int utility(ArrayList<Integer> game) {
        if (winX(game)) {
            return xWining;
        }
        if (loseX(game)) {
            return xLosing;
        }
        return draw;
    }

    private boolean loseX(ArrayList<Integer> game) {
        if ((game.get(0).equals(o) && game.get(1).equals(o) && game.get(2).equals(o))
                || (game.get(3).equals(o) && game.get(4).equals(o) && game.get(5).equals(o))
                || (game.get(6).equals(o) && game.get(7).equals(o) && game.get(8).equals(o))
                || (game.get(0).equals(o) && game.get(3).equals(o) && game.get(6).equals(o))
                || (game.get(1).equals(o) && game.get(4).equals(o) && game.get(7).equals(o))
                || (game.get(2).equals(o) && game.get(5).equals(o) && game.get(8).equals(o))
                || (game.get(0).equals(o) && game.get(4).equals(o) && game.get(8).equals(o))
                || (game.get(2).equals(o) && game.get(4).equals(o) && game.get(6).equals(o))) {
            return true;
        }
        return false;
    }

    private boolean winX(ArrayList<Integer> game) {
        if ((game.get(0).equals(x) && game.get(1).equals(x) && game.get(2).equals(x))
                || (game.get(3).equals(x) && game.get(4).equals(x) && game.get(5).equals(x))
                || (game.get(6).equals(x) && game.get(7).equals(x) && game.get(8).equals(x))
                || (game.get(0).equals(x) && game.get(3).equals(x) && game.get(6).equals(x))
                || (game.get(1).equals(x) && game.get(4).equals(x) && game.get(7).equals(x))
                || (game.get(2).equals(x) && game.get(5).equals(x) && game.get(8).equals(x))
                || (game.get(0).equals(x) && game.get(4).equals(x) && game.get(8).equals(x))
                || (game.get(2).equals(x) && game.get(4).equals(x) && game.get(6).equals(x))) {
            return true;
        }
        return false;
    }

    private boolean check(ArrayList<Integer> game) {
        if (winX(game) || loseX(game)) {
            return true;
        }
        for (int i = 0; i < 9; i++) {
            if (game.get(i).equals(empty)) {
                return false;
            }
        }
        return true;
    }

    private int getMax(int v, int minValue) {
        if (v < minValue) {
            return minValue;
        } else {
            return v;
        }
    }

    private int getMin(int v, int maxValue) {
        if (v > maxValue) {
            return maxValue;
        } else {
            return v;
        }
    }

    protected ArrayList<Integer> setGame(ArrayList<Integer> game, int newO) {
        if (game.get(newO).equals(empty)) {
            game.set(newO, o);
            return game;
        }
        return null;
    }

    public void show(ArrayList<Integer> game) {
        System.out.print(input(game.get(0)));
        System.out.print("  |  ");
        System.out.print(input(game.get(1)));
        System.out.print("  |  ");
        System.out.println(input(game.get(2)));
        System.out.println("---------------");
        System.out.print(input(game.get(3)));
        System.out.print("  |  ");
        System.out.print(input(game.get(4)));
        System.out.print("  |  ");
        System.out.println(input(game.get(5)));
        System.out.println("---------------");
        System.out.print(input(game.get(6)));
        System.out.print("  |  ");
        System.out.print(input(game.get(7)));
        System.out.print("  |  ");
        System.out.println(input(game.get(8)));
        System.out.println("");
    }

    private String input(int key) {
        if (key == x) {
            return "X";
        }
        if (key == o) {
            return "O";
        }
        return " ";
    }

    public boolean test(ArrayList<Integer> game) {
        if (winX(game)) {
            System.out.print("Winner:"+name);
            return false;
        }
        if (loseX(game)) {
            System.out.print("Winner: Computer");
            return false;
        }
        for (int i = 0; i < 9; i++) {
            if (game.get(i).equals(empty)) {
                return true;
            }
        }
        System.out.print("It's a draw");
        return false;
    }
}

public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scan = new Scanner(System.in);
        int len = 9;
        System.out.println("What's your name?");
        String player=scan.next();
        Algorithm ticTacToe = new Algorithm(player);
        ArrayList<Integer> game = new ArrayList<>(len);
        for (;;) {
            System.out.print("Are you first?[n/y]");
            String answer = scan.next();
            if (answer.equals("n")) {
                game.add(ticTacToe.x);
                break;
            } else if (answer.equals("y")) {
                game.add(ticTacToe.empty);
                break;
            } else {
                System.out.println("");
            }
        }
        System.out.println("");
        game = ticTacToe.initialize(game);
        ticTacToe.show(game);
        System.out.println("Pick a number from 1-9");
        while (ticTacToe.test(game)) {
            int temp;
            for (;;) {
                System.out.print("Your turn: ");
                temp = scan.nextInt() - 1;
                if (temp >= 0 && temp <= 8) {
                    break;
                }
                System.out.println("Invalid");
            }
            ArrayList<Integer> newGame = ticTacToe.setGame(game, temp);
            if (newGame == null) {
                System.out.println("Invalid");
            } else {
                game = newGame;
                ticTacToe.show(game);
                if (ticTacToe.test(game)) {
                    game = ticTacToe.answer(game);
                    ticTacToe.show(game);
                }
            }
        }

    }

}
