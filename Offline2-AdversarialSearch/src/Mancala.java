public class Mancala {
    public static final int MAX_DEPTH = 10;

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

            System.out.println("Player" + (parent.isMax ? "1" : "2") + " move: ");
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

        System.out.println("Player" + (node.board[6] > node.board[13] ? "1" : "2") + " wins!");
    }

    public static void main(String[] args) {
        autoPlay();
    }
}
