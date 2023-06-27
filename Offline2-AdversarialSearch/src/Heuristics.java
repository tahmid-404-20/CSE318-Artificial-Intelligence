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

        int additionalMoveDiff = node.numberOfAdditionalMoves - node.parent.numberOfAdditionalMoves;

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

        int additionalMoveDiff = node.numberOfAdditionalMoves - node.parent.numberOfAdditionalMoves;
        int capturedStonesDiff = node.numberOfCapturedStones - node.parent.numberOfCapturedStones;

        // difference always from maxNode's perspective
        if(node.isMax) {
            additionalMoveDiff *= -1;
            capturedStonesDiff *= -1;
        }

        return mancalaDiff + boardDiff + additionalMoveDiff + capturedStonesDiff;
    }

}
