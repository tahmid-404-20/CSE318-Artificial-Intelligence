public class Utility {
    public static long calculateHammingDistance(int[][] a) {
        int k = a.length;

        long hammingDistance = 0;
        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                if(a[i][j] != (i*k + j + 1) && a[i][j] != 0) {
                    hammingDistance++;
                }
            }
        }

        return hammingDistance;
    }

    public static long calculateManhattanDistance(int[][] a) {
        int k = a.length;

        long manhattanDistance = 0;
        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                int number = a[i][j];

                if(number != 0) {
                    int row = (number - 1) / k;
                    int col = (number - 1) % k;
                    manhattanDistance += Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }

        return manhattanDistance;
    }

    public static long calculateNumberOfInversions(int[][] a) {
        int k = a.length;

        long numberOfInversions = 0;
        // Now writing O(n^2), will change later
        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                int number = a[i][j];

                if(number != 0) {
                    for(int p = i; p<k;p++) {
                        for(int q=j; q<k;q++) {
                            if(number > a[p][q] && a[p][q] != 0) {
                                numberOfInversions++;
                            }
                        }
                    }
                }
            }
        }
        return numberOfInversions;
    }

    public static Index findZero(int[][] a) {
        int k = a.length;

        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                if(a[i][j] == 0) {
                    return new Index(i, j);
                }
            }
        }

        return null;
    }
}
