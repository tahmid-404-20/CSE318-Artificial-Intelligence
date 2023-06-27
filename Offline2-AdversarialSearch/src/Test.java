import java.util.Scanner;

public class Test {
    static void printBoard(int[] a) {
        System.out.println("***Player1 bins indexed from left, Player2 bins indexed from right***");
        System.out.print("Player2: [" + a[13] + "] -> ");
        for(int i=12;i>=7;i--) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        System.out.print("Player1: -> ");
        for(int i=0;i<6;i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println(" [" + a[6] + "]\n");
    }

    static boolean isComplete(int[] board) {
        boolean isPlayer1Empty = true;
        boolean isPlayer2Empty = true;
        for(int i=0;i<6;i++) {
            if(board[i] != 0) {
                isPlayer1Empty = false;
                break;
            }
        }
        for(int i=7;i<13;i++) {
            if(board[i] != 0) {
                isPlayer2Empty = false;
                break;
            }
        }
        return isPlayer1Empty || isPlayer2Empty;
    }

    static void move(int[] board, boolean isPlayer1, Scanner scr) {
//        int[] board = new int[14];
//        System.arraycopy(a, 0, board, 0, 14);

        printBoard(board);

        int numberOfAdditionalMoves = 0;
        int numberOfCapturedStones = 0;

        if(isPlayer1) {
            while(true) {
                System.out.print("Player 1's turn(Choose a bin from 1-6): ");

                int index;
                while(true) {
                    int bin = scr.nextInt();
                    index = bin - 1;
                    if(index > 5 || index < 0) {
                        System.out.print("Invalid move. Enter bin no again: ");
                    } else {
                        int count = board[index];
                        if(count == 0) {
                            System.out.print("Invalid bin, no Stone. Enter bin no again: ");
                        } else {
                            break;
                        }
                    }
                }

                int nStones = board[index];
                board[index] = 0;
                int i;
                for(i = index+1; nStones > 0; i = (i+1)%13) {
                    board[i]++;
                    nStones--;
                }

                // where last stone was placed?
                i--;
                if(i<0) {
                    i = 12;  // opponents last bin
                }
                // check for additional moves
                if(i == 6) {
                    numberOfAdditionalMoves++;
                    printBoard(board);
                } else if(board[i] == 1 && i <= 5) { // check for capturing stones
                    numberOfCapturedStones += board[12-i];
                    board[12-i] = 0;
                    board[6] += (numberOfCapturedStones + board[i]);  // putting both in mancala
                    board[i] = 0;
                    break;
                } else {
                    break;
                }
            }
        } else { // player2
            while(true) {
                System.out.print("Player 2's turn(Choose a bin from 1-6): ");

                int index;
                while(true) {
                    int bin = scr.nextInt();
                    index = bin + 6;
                    if(index > 12 || index < 7) {
                        System.out.print("Invalid bin no. Enter bin no again: ");
                    } else {
                        int count = board[index];
                        if(count == 0) {
                            System.out.print("Invalid bin, no Stone. Enter bin no again: ");
                        } else {
                            break;
                        }
                    }
                }

                int nStones = board[index];
                board[index] = 0;
                int i;
                for(i = index+1; nStones > 0; i = (i+1)%14) {
                    if(i == 6) {
                        i = 7;
                    }
                    board[i]++;
                    nStones--;
                }

                // where last stone was placed?
                i--;
                if(i<0) {
                    i = 13;  // player2 mancala
                }
                // check for additional moves
                if(i == 13) {
                    numberOfAdditionalMoves++;
                    printBoard(board);
                } else if(board[i] == 1 && i >= 7) { // check for capturing stones
                    numberOfCapturedStones += board[12-i];
                    board[12-i] = 0;
                    board[6] += (numberOfCapturedStones + board[i]);  // putting both in mancala
                    board[i] = 0;
                    break;
                } else {
                    break;
                }
            }
        }

        System.out.println("Additional moves: " + numberOfAdditionalMoves + " Captured stones: " + numberOfCapturedStones);
    }

    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);

        int[] board = new int[14];
        for(int i=0; i<14; i++) {
            board[i] = 4;
        }
        board[6] = 0;
        board[13] = 0;

        printBoard(board);

        boolean isPlayer1 = true;
        while(!isComplete(board)) {
            move(board, isPlayer1, scr);
            isPlayer1 = !isPlayer1;
        }

        System.out.println("Game Over");
        System.out.println("Player1: " + board[6] + " Player2: " + board[13]);

    }
}
