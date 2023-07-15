import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Mancala {
    public static final int MAX_DEPTH = 10;
    static int player1Heuristic = 2;
    static int player2Heuristic = 2;
    static long exploredNodes;
    static long prunedNodes;

    static void visit(Node node) {
        exploredNodes++;
        if (node.isLeaf()) {
            // evaluate alpha(maxNode) and beta(minNode) based on heuristic
            if (node.isMax) {
                switch (player1Heuristic) {
                    case 1 -> node.alpha = Heuristics.heuristic1(node);
                    case 2 -> node.alpha = Heuristics.heuristic2(node);
                    case 3 -> node.alpha = Heuristics.heuristic3(node);
                    case 4 -> node.alpha = Heuristics.heuristic4(node);
                }
            } else {
                switch (player2Heuristic) {
                    case 1 -> node.beta = Heuristics.heuristic1(node);
                    case 2 -> node.beta = Heuristics.heuristic2(node);
                    case 3 -> node.beta = Heuristics.heuristic3(node);
                    case 4 -> node.beta = Heuristics.heuristic4(node);
                }
            }
        } else {
            int i = node.isMax ? 0 : 7;
            for (int binCount = 6; binCount > 0; binCount--, i++) {
                if (node.board[i] != 0) {
                    Node child;
                    // here creating the child node and testing whether it gained any additional move or not
                    // if gains, it the tree structure, we get the same MAX/MIN node again
                    // example MAX - MIN - MAX - MAX - MAX - MAX - MIN
                    child = new Node(node.board, node.depth + 1, node.alpha, node.beta, !node.isMax, i,
                            node.numberOfAdditionalMoves, node);

                    if (child.numberOfAdditionalMoves - node.numberOfAdditionalMoves > 0) { //gained additional move
                        // child.additionalMoves - parent.additionalMoves = 1
                        child.numberOfCapturedStones = node.numberOfCapturedStones; // no change in captured stones if additional move
                        child.isMax = node.isMax; // no change in player if additional move
                    } else {
                        // find previous parent whose isMax is of same type as child node
                        Node temp = node;
                        while (temp != null && temp.isMax != child.isMax) {
                            temp = temp.parent;
                        }

                        if (temp == null) {
                            // this is the first node of this type
                            child.numberOfAdditionalMoves = 0;
                            child.numberOfCapturedStones = 0;
                        } else {
                            // no change in additional moves if no additional move since last time, this may happen that it captured some nodes
                            child.numberOfAdditionalMoves = temp.numberOfAdditionalMoves;
                            child.numberOfCapturedStones += temp.numberOfCapturedStones;
                        }
                    }

                    visit(child);
                    if (node.isMax) {  // i am max, so update alpha
                        if (!child.isMax && (child.beta > node.alpha)) {
                            node.alpha = child.beta;
                            node.next = child;
                        } else if (child.isMax && (child.alpha > node.alpha)) {
                            node.alpha = child.alpha;
                            node.next = child;
                        }
                    } else {
                        if (child.isMax && (child.alpha < node.beta)) {
                            node.beta = child.alpha;
                            node.next = child;
                        } else if (!child.isMax && (child.beta < node.beta)) {
                            node.beta = child.beta;
                            node.next = child;
                        }
                    }
                }

//                alpha-beta pruning, for parent child of different type
                if (node.alpha >= node.beta && node.parent.isMax == node.isMax) {
                    for(;binCount > 0; binCount--, i++) {
                        prunedNodes += (node.board[i] != 0) ? 1 : 0;
                    }
                    break;
                }
            }
        }
    }


    static Result autoPlay() {
        int[] board = new int[14];
        for (int i = 0; i < 14; i++) {
            board[i] = 4;
        }
        board[6] = 0;
        board[13] = 0;

        Node node = new Node(board, true);
        System.out.println("Game starts!");
        node.printBoard();

        while (true) {
            exploredNodes = 0;
            prunedNodes = 0;
            visit(node);
            Node parent = node;
            System.out.println("Explored nodes: " + exploredNodes + " pruned nodes: " + prunedNodes);
            node = node.next;  // next move

            System.out.println("Player" + (parent.isMax ? "1" : "2") + " move: index " + (node.moveIndex+1));
            node.printBoard();

            if (node.isComplete()) {
                break;
            } else {
                if(parent.isMax == node.isMax)
                {
                    System.out.println("Additional move!");
                }
                node = new Node(node.board, node.isMax);
            }
        }

        // complete the game
        supplyRemainingStones(node);
        String resultAnnounced = null;
        if(node.board[6] == node.board[13]) {
            resultAnnounced = "Draw";
        } else {
            resultAnnounced = "Player" + (node.board[6] > node.board[13] ? "1" : "2") + " wins!";
        }
        System.out.println(resultAnnounced);
        System.out.println("Player1: " + node.board[6] + " Player2: " + node.board[13]);

        return new Result(resultAnnounced, node.board[6], node.board[13]);
    }

    static void playAsPlayer2() {
        int[] board = new int[14];
        for (int i = 0; i < 14; i++) {
            board[i] = 4;
        }
        board[6] = 0;
        board[13] = 0;

        Node node = new Node(board, true);
        System.out.println("Game starts!");
        node.printBoard();

        while (true) {
            visit(node);
            Node parent = node;
            node = node.next;  // next move

            System.out.println("After Player" + (parent.isMax ? "1" : "2") + " move: index " + (node.moveIndex+1));
            node.printBoard();

            if (node.isComplete()) {
                break;
            } else {
                if(node.isMax)
                {
                    System.out.println("Additional move of player1!");
                    node = new Node(node.board,true);
                } else {
                    Scanner sc = new Scanner(System.in);

                    int[] board2 = new int[node.board.length];
                    // copy node.board to board2
                    System.arraycopy(node.board, 0, board2, 0, node.board.length);
                    player2Move(board2, sc);
                    node = new Node(board2, true);
                    node.printBoard();
                    if(node.isComplete()) {
                        break;
                    }
                }
            }
        }

        // complete the game
        supplyRemainingStones(node);
        System.out.println("Player" + (node.board[6] > node.board[13] ? "1" : "2") + " wins!");
        System.out.println("Player1: " + node.board[6] + " Player2: " + node.board[13]);
    }

    static void player2Move(int[] board, Scanner scr) {
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
                Node temp = new Node(board, false);
                temp.printBoard();
                if(temp.isComplete()) {
                    break;
                }
                System.out.println("Additional move of player2!");
            } else if(board[i] == 1 && i >= 7 && board[12 - i] != 0) { // check for capturing stones
                int numberOfCapturedStones = board[12-i];
                board[12-i] = 0;
                board[13] += (numberOfCapturedStones + board[i]);  // putting both in mancala
                board[i] = 0;
                break;
            } else {
                break;
            }
        }
    }

    static void supplyRemainingStones(Node node) {
        for(int i=0; i<6; i++) {
            node.board[6] += node.board[i];
            node.board[i] = 0;
        }

        for(int i=7; i<13; i++) {
            node.board[13] += node.board[i];
            node.board[i] = 0;
        }
    }

    static void generateStatistics() {
        // Open a .txt file
        try {
            FileOutputStream fos = new FileOutputStream("Statistics.csv");
            String header = "Player1_Heuristic,Player2_Heuristic,Result,Player1_Mancala,Player2_Mancala\n";
            fos.write(header.getBytes(), 0, header.length());
            for(int i=1;i<=4;i++) {
                for(int j=1;j<=4;j++) {
                    player1Heuristic = i;
                    player2Heuristic = j;
                    Result result = autoPlay();
                    String str = i + "," + j + "," + result.result + "," + result.player1Mancala + "," + result.player2Mancala + "\n";
                    fos.write(str.getBytes(), 0, str.length());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        generateStatistics();
        autoPlay();
//        playAsPlayer2();
    }
}
