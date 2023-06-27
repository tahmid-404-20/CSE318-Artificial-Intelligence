import java.util.Scanner;

public class Mancala {
    public static final int MAX_DEPTH = 15;

    static void visit(Node node) {
        if (node.isLeaf()) {
            // evaluate alpha(maxNode) and beta(minNode) based on heuristic
            if (node.isMax) {
//                node.alpha = Heuristics.heuristic1(node);
//                node.alpha = Heuristics.heuristic2(node);
//                node.alpha = Heuristics.heuristic3(node);
                node.alpha = Heuristics.heuristic4(node);
            } else {
//                node.beta = Heuristics.heuristic1(node);
//                node.beta = Heuristics.heuristic2(node);
                node.beta = Heuristics.heuristic3(node);
//                node.beta = Heuristics.heuristic4(node);
            }
        } else {
            int i = node.isMax ? 0 : 7;
            for (int binCount = 6; binCount > 0; binCount--, i++) {
                if (node.board[i] != 0) {
                    Node child;
//                    if (node.parent == null) {  // for first node
//                        child = new Node(node.board, node.depth + 1, node.alpha, node.beta, !node.isMax, i,
//                                0, 0, node);
//                    } else {
                    child = new Node(node.board, node.depth + 1, node.alpha, node.beta, !node.isMax, i,
                            node.numberOfAdditionalMoves, node);
//                    }

                    if (child.numberOfAdditionalMoves - node.numberOfAdditionalMoves > 0) {
                        child.numberOfAdditionalMoves = node.numberOfAdditionalMoves + 1;
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
                    if (node.isMax) {
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

//                alpha-beta pruning
                if (node.alpha >= node.beta) {
                    break;
                }
            }
        }
    }


    static void autoPlay() {
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

            System.out.println("Player" + (parent.isMax ? "1" : "2") + " move: index " + (node.moveIndex+1));
            node.printBoard();

//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
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
        System.out.println("Player" + (node.board[6] > node.board[13] ? "1" : "2") + " wins!");
        System.out.println("Player1: " + node.board[6] + " Player2: " + node.board[13]);
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
            } else if(board[i] == 1 && i >= 7) { // check for capturing stones
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

    public static void main(String[] args) {
//        autoPlay();
        playAsPlayer2();
    }
}
