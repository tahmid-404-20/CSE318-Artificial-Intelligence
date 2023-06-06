public class NPuzzle {
    int[][] root;

    public NPuzzle(int[][] root) {
        this.root = root;
    }

    public boolean checkPossibility() {
        int k = root.length;

        long numberOfInversions = Utility.calculateNumberOfInversions(root);

        if(k%2 != 0) {  // k is odd
            System.out.println("Number of inversiions -->" + numberOfInversions);
            return (numberOfInversions % 2) == 0;
        } else {
            Index zeroIndex = Utility.findZero(root);
            return (((k-1) - zeroIndex.row) + numberOfInversions) % 2 == 0;
        }
    }
}
