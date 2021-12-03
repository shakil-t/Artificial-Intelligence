/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n.queen;


/**
 *
 * @author shakil
 */

import java.util.ArrayList;
import java.util.Scanner;

class BackTracking{
    private static final int chessBoard = 8;

    public ArrayList<Integer> column = new ArrayList<>(chessBoard);
    private int stateNumber = 1;

    protected void queens(int i) {
        if (promising(i)) {
            if (i == chessBoard) {
                System.out.print("state " + stateNumber + " : ");
                System.out.println(column);
                stateNumber++;
                return;
            } else {
                for (int j = 1; j <= chessBoard; j++) {
                    if (column.size() > (i + 1)) {
                        column.set(i + 1, j);
                    } else {
                        column.add(j);
                    }
                    queens(i + 1);
                }
            }
        }
    }

    private boolean promising(int i) {
        int k = 1;
        while (k < i) {
            if (column.get(i).equals(column.get(k)) || column.get(i) - column.get(k) == i - k || column.get(i) - column.get(k) == k - i) {
                return false;
            }
            k++;
        }
        return true;
    }
}

public class NQueen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scan=new Scanner(System.in);
        BackTracking p=new BackTracking();
        System.out.println("Number of Queens:");
        int q=scan.nextInt();
        p.queens(q);
    }
    
}
