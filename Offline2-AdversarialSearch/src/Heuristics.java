public class Heuristics {
// calculate boardDiff
    private static int boardDiff(Node node) {
        int boardDiff = 0;
        for(int i=0; i<6; i++) {
            boardDiff += node.board[i];
        }
        for(int i=7; i<13; i++) {
            boardDiff -= node.board[i];
        }
        return boardDiff;
    }
    //  mancalaDiff
    public static int heuristic1(Node node) {
        return node.board[6] - node.board[13];
    }

//  mancalaDiff + BoardDiff
    public static int heuristic2(Node node) {
        int mancalaDiff = node.board[6] - node.board[13];

        int boardDiff = boardDiff(node);
        return mancalaDiff + boardDiff;
    }

//  mancalaDiff + BoardDiff + AdditionalMoveDiff
    public static int heuristic3(Node node) {
        int mancalaDiff = node.board[6] - node.board[13];
        int boardDiff = boardDiff(node);

        int additionalMoves = node.isMax ? node.numberOfAdditionalMoves : -node.numberOfAdditionalMoves;
        return mancalaDiff + boardDiff + additionalMoves;
    }

//  mancalaDiff + BoardDiff + AdditionalMoveDiff + CapturedStonesDiff
    public static int heuristic4(Node node) {
        int mancalaDiff = node.board[6] - node.board[13];
        int boardDiff = boardDiff(node);

        int additionalMoves = node.isMax ? node.numberOfAdditionalMoves : -node.numberOfAdditionalMoves;
        int capturedStones = node.isMax ? node.numberOfCapturedStones : -node.numberOfCapturedStones;

        return mancalaDiff + boardDiff + additionalMoves + capturedStones;
    }

}
