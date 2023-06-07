import java.util.Scanner;

public class Tester {
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);

        int[][] a;
        int k = scr.nextInt();

        a = new int[k][k];

        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                a[i][j] = scr.nextInt();
            }
        }

//        System.out.println("Hamming distance: " + Utility.calculateHammingDistance(a));
//        System.out.println("Manhattan distance: " + Utility.calculateManhattanDistance(a));

        NPuzzle nPuzzle = new NPuzzle(a);
        if(nPuzzle.checkPossibility()) {
            System.out.println("Solvable Puzzle");
            nPuzzle.findPathManhattan();
            System.out.println("***********************************");
            nPuzzle.findPathHamming();
        } else {
            System.out.println("Unsolvable Puzzle");
        }

    }
}
