public class Heuristics {
//    calculate board diff

    //  mancalaDiff
    public static int heuristic1(Node node) {
        return node.board[6] - node.board[13];
    }

//  mancalaDiff + BoardDiff
    public static int heuristic2(Node node) {
        int mancalaDiff = node.board[6] - node.board[13];

        int boardDiff = 0;
        for(int i=0; i<6; i++) {
            boardDiff += node.board[i];
        }
        for(int i=7; i<13; i++) {
            boardDiff -= node.board[i];
        }
        return mancalaDiff + boardDiff;
    }

//  mancalaDiff + BoardDiff + AdditionalMoveDiff
    public static int heuristic3(Node node) {
        int mancalaDiff = node.board[6] - node.board[13];
        int boardDiff = 0;
        for(int i=0; i<6; i++) {
            boardDiff += node.board[i];
        }
        for(int i=7; i<13; i++) {
            boardDiff -= node.board[i];
        }

        int additionalMoveDiff;
        Node parent = node.parent;
        if(node.isMax != parent.isMax) {
            additionalMoveDiff = node.numberOfAdditionalMoves - parent.numberOfAdditionalMoves;
        } else {
            // find previous parent whose isMax is different from node isMax
            Node temp = parent;
            while (temp != null && temp.isMax == node.isMax) {
                temp = temp.parent;
            }

            if(temp == null) {
                additionalMoveDiff = node.numberOfAdditionalMoves;
            } else {
                additionalMoveDiff = node.numberOfAdditionalMoves - temp.numberOfAdditionalMoves;
            }
        }

        // difference always from maxNode's perspective
        // think leaf as min, then diff is basically maxmove(which is in leaf) - minmove(which is in parent)
        // so, if leaf is max, we negate the diff
        if(node.isMax) {
            additionalMoveDiff *= -1;
        }

        return mancalaDiff + boardDiff + additionalMoveDiff;
    }

//  mancalaDiff + BoardDiff + AdditionalMoveDiff + CapturedStonesDiff
    public static int heuristic4(Node node) {
        int mancalaDiff = node.board[6] - node.board[13];
        int boardDiff = 0;
        for(int i=0; i<6; i++) {
            boardDiff += node.board[i];
        }
        for(int i=7; i<13; i++) {
            boardDiff -= node.board[i];
        }

        int additionalMoveDiff;
        int capturedStonesDiff;
        Node parent = node.parent;
        if(node.isMax != parent.isMax) {
            additionalMoveDiff = node.numberOfAdditionalMoves - parent.numberOfAdditionalMoves;
            capturedStonesDiff = node.numberOfCapturedStones - parent.numberOfCapturedStones;
        } else {
            // find previous parent whose isMax is different from node isMax
            Node temp = parent;
            while (temp != null && temp.isMax == node.isMax) {
                temp = temp.parent;
            }

            if(temp == null) {
                additionalMoveDiff = node.numberOfAdditionalMoves;
                capturedStonesDiff = node.numberOfCapturedStones;
            } else {
                additionalMoveDiff = node.numberOfAdditionalMoves - temp.numberOfAdditionalMoves;
                capturedStonesDiff = node.numberOfCapturedStones - temp.numberOfCapturedStones;
            }
        }

        // difference always from maxNode's perspective
        if(node.isMax) {
            additionalMoveDiff *= -1;
            capturedStonesDiff *= -1;
        }

        return mancalaDiff + boardDiff + additionalMoveDiff + capturedStonesDiff;
    }

}
