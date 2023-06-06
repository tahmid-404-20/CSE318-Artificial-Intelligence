public class Utility {
    public static int calculateHammingDistance(int[][] a) {
        int k = a.length;

        int hammingDistance = 0;
        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                if(a[i][j] != (i*k + j + 1) && a[i][j] != 0) {
                    hammingDistance++;
                }
            }
        }

        return hammingDistance;
    }

    public static int calculateManhattanDistance(int[][] a) {
        int k = a.length;

        int manhattanDistance = 0;
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

        // Copying the array into a single dimension array, skip zero
        int n = k*k - 1;
        int arr[] = new int[n];
        int index = 0;
        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                if(a[i][j] != 0) {
                    arr[index++] = a[i][j];
                }
            }
        }

        for(int i=0;i<n;i++) {
            for(int j=i+1;j<n;j++) {
                if(arr[i] > arr[j]) {
                    numberOfInversions++;
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

    static public int[][] createCopyOfArray(int[][] a) {
        int k = a.length;

        int[][] b = new int[k][k];
        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                b[i][j] = a[i][j];
            }
        }

        return b;
    }

    static public void printBoard(int[][] a) {
        int k = a.length;

        for(int i=0;i<k;i++) {
            for(int j=0;j<k;j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
