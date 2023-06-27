public class Node {
    int[] board;
    int depth;
    int alpha;
    int beta;
    boolean isMax; // player1 is Max, player2 is Min
    int numberOfAdditionalMoves;
    int numberOfCapturedStones;

    Node parent;
    Node next;
    int moveIndex;

    // for initial node
    public Node(int[] board, boolean isMax) {
        this.board = new int[board.length];
        System.arraycopy(board, 0, this.board, 0, board.length);

        this.depth = 0;
        this.alpha = Integer.MIN_VALUE;
        this.beta = Integer.MAX_VALUE;
        this.numberOfAdditionalMoves = 0;
        this.numberOfCapturedStones = 0;
        this.isMax = isMax;

        this.parent = null;
        this.next = null;
    }

    // for other nodes
    public Node(int[] board, int depth, int alpha, int beta, boolean isMax, int moveIndex, int numberOfAdditionalMoves, Node parent) {
        this.board = new int[board.length];
        System.arraycopy(board, 0, this.board, 0, board.length);

        this.depth = depth;
        this.alpha = alpha;
        this.beta = beta;
        this.isMax = isMax;
        this.numberOfAdditionalMoves = numberOfAdditionalMoves;
        this.numberOfCapturedStones = 0;
        this.parent = parent;
        this.next = null;
        this.moveIndex = moveIndex;

        move(moveIndex);
    }

    // this function updates the board according to proper move (+multiple turns) and updates the additional moves and captured stones
    void move(int index) {
//        printBoard();
        if (!isMax) {  // additional move calculation corresponds to parent's move
//                System.out.println("Player 1's turn");

            // if want to take input from user, uncomment this
//                Scanner scr = new Scanner(System.in);
                /*
                while (true) {
                    int bin = scr.nextInt();
                    index = bin + 6;
                    if (index > 12 || index < 7) {
                        System.out.print("Invalid bin no. Enter bin no again: ");
                    } else {
                        int count = board[index];
                        if (count == 0) {
                            System.out.print("Invalid bin, no Stone. Enter bin no again: ");
                        } else {
                            break;
                        }
                    }
                }
                */

            int nStones = board[index];
            board[index] = 0;
            int i;
//            if(parent.parent == null) {
//                System.out.println("Parent is null " + index + "---" + nStones);
//            }
            for (i = index + 1; nStones > 0; i = (i + 1) % 13) {
                board[i]++;
                nStones--;
            }

            // where last stone was placed?
            i--;
            if (i < 0) {
                i = 12;  // opponents last bin
            }
            // check for additional moves
            if (i == 6) {
                numberOfAdditionalMoves++;
            } else if (board[i] == 1 && i <= 5) { // check for capturing stones, i>=0 is true
                numberOfCapturedStones = board[12 - i];
                board[12 - i] = 0;
                board[6] += (numberOfCapturedStones + board[i]);  // putting both in mancala
                board[i] = 0;
            }

        } else { // player2
//                System.out.println("Player 2's turn(Choose a bin from 1-6): ");

            int nStones = board[index];
            board[index] = 0;
            int i;
            for (i = index + 1; nStones > 0; i = (i + 1) % 14) {
                if (i == 6) {
                    i = 7;
                }
                board[i]++;
                nStones--;
            }

            // where last stone was placed?
            i--;
            if (i < 0) {
                i = 13;  // player2 mancala
            }
            // check for additional moves
            if (i == 13) {
                numberOfAdditionalMoves++;
            } else if (board[i] == 1 && i >= 7) { // check for capturing stones
                numberOfCapturedStones = board[12 - i];
                board[12 - i] = 0;
                board[13] += (numberOfCapturedStones + board[i]);  // putting both in mancala
                board[i] = 0;
            }
        }

//        System.out.println("Additional moves: " + numberOfAdditionalMoves + " Captured stones: " + numberOfCapturedStones);
    }

    void printBoard() {
        System.out.println("***Player1 bins indexed from left, Player2 bins indexed from right***");
        System.out.print("Player2: [" + board[13] + "] -> ");
        for (int i = 12; i >= 7; i--) {
            System.out.print(board[i] + " ");
        }
        System.out.println();

        System.out.print("Player1: -> ");
        for (int i = 0; i < 6; i++) {
            System.out.print(board[i] + " ");
        }
        System.out.println(" [" + board[6] + "]\n");
    }

    boolean isComplete() {
        boolean isPlayer1Empty = true;
        boolean isPlayer2Empty = true;
        for (int i = 0; i < 6; i++) {
            if (board[i] != 0) {
                isPlayer1Empty = false;
                break;
            }
        }
        for (int i = 7; i < 13; i++) {
            if (board[i] != 0) {
                isPlayer2Empty = false;
                break;
            }
        }
        return isPlayer1Empty || isPlayer2Empty;
    }

    boolean isLeaf() {
        return (depth > Mancala.MAX_DEPTH || isComplete());
    }
}
