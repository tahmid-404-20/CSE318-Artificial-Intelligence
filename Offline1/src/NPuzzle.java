import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class NPuzzle {
    Node root;
    long exploredNodeCount;
    long expandedNodeCount;

    public NPuzzle(int[][] a) {
        this.root = new Node(a,null, 0);
    }

    public boolean checkPossibility() {
        int k = root.a.length;

        long numberOfInversions = Utility.calculateNumberOfInversions(root.a);

        if(k%2 != 0) {  // k is odd
//            System.out.println("Number of inversiions -->" + numberOfInversions);
            return (numberOfInversions % 2) == 0;
        } else {
            Index zeroIndex = Utility.findZero(root.a);
            return (((k-1) - zeroIndex.row) + numberOfInversions) % 2 == 0;
        }
    }

    public void printSolutionDetails(Node node) {
        System.out.println("Number of nodes explored: " + exploredNodeCount);
        System.out.println("Number of nodes expanded: " + expandedNodeCount);
        System.out.println("Minimum Number of Moves: " + node.g);
        System.out.println("Solution path:");
        printSolutionPath(node);
    }

    public void printSolutionPath(Node node) {
        if(node != null) {
            printSolutionPath(node.parent);
            Utility.printBoard(node.a);
        }
    }

    public void findPathHamming() {
        System.out.println("Solution Using Hamming distance heuristic");

        PriorityQueue<Node> pq = new PriorityQueue<>();
        Map<Node,Boolean> closedList = new HashMap<>();

        int hammingDistance = Utility.calculateHammingDistance(root.a);
        root.setH(hammingDistance);

        closedList.put(root, true);
        pq.add(root);
        exploredNodeCount = 1;
        expandedNodeCount = 0;
        if(root.getH() == 0) {
            // we have reached solution
            printSolutionDetails(root);
            return;
        }

        expandedNodeCount = 0;
        while(!pq.isEmpty()) {
            Node node = pq.poll();
            expandedNodeCount++;

            Index zeroIndex = Utility.findZero(node.a);
            int row = zeroIndex.row;
            int col = zeroIndex.col;

            if(row > 0) {  // Neighbor1, move blank up
                int[][] a = Utility.createCopyOfArray(node.a);
                a[row][col] = a[row-1][col];
                a[row-1][col] = 0;
                Node newNode = new Node(a, node, node.g+1);
                newNode.setH(Utility.calculateHammingDistance(a));
                if(newNode.getH() ==0) {
                    // reached solution
                    printSolutionDetails(newNode);
                    break;
                }

                if(!closedList.containsKey(newNode)) {
                    closedList.put(newNode, true);
                    pq.add(newNode);
                    exploredNodeCount++;
                }

            }

            if(row < node.a.length-1) {  // Neighbor2, move blank down
                int[][] a = Utility.createCopyOfArray(node.a);
                a[row][col] = a[row+1][col];
                a[row+1][col] = 0;
                Node newNode = new Node(a, node, node.g+1);
                newNode.setH(Utility.calculateHammingDistance(a));
                if(newNode.getH() ==0) {
                    // reached solution
                    printSolutionDetails(newNode);
                    break;
                }

                if(!closedList.containsKey(newNode)) {
                    closedList.put(newNode, true);
                    pq.add(newNode);
                    exploredNodeCount++;
                }
            }

            if(col > 0) { // Neighbor3, move blank left
                int[][] a = Utility.createCopyOfArray(node.a);
                a[row][col] = a[row][col-1];
                a[row][col-1] = 0;
                Node newNode = new Node(a, node, node.g+1);
                newNode.setH(Utility.calculateHammingDistance(a));
                if(newNode.getH() ==0) {
                    // reached solution
                    printSolutionDetails(newNode);
                    break;
                }

                if(!closedList.containsKey(newNode)) {
                    closedList.put(newNode, true);
                    pq.add(newNode);
                    exploredNodeCount++;
                }
            }

            if(col < node.a.length-1) { // Neighbor3, move blank right
                int[][] a = Utility.createCopyOfArray(node.a);
                a[row][col] = a[row][col+1];
                a[row][col+1] = 0;
                Node newNode = new Node(a, node, node.g+1);
                newNode.setH(Utility.calculateHammingDistance(a));
                if(newNode.getH() ==0) {
                    // reached solution
                    printSolutionDetails(newNode);
                    break;
                }

                if(!closedList.containsKey(newNode)) {
                    closedList.put(newNode, true);
                    pq.add(newNode);
                    exploredNodeCount++;
                }
            }
        }
    }

    public void findPathManhattan() {
        System.out.println("Solution Using Manhattan distance heuristic");

        PriorityQueue<Node> pq = new PriorityQueue<>();
        Map<Node,Boolean> closedList = new HashMap<>();

        int ManhattanDistance = Utility.calculateManhattanDistance(root.a);
        root.setH(ManhattanDistance);

        closedList.put(root, true);
        pq.add(root);
        exploredNodeCount = 1;
        expandedNodeCount = 0;
        if(root.getH() == 0) {
            // we have reached solution
            printSolutionDetails(root);
            return;
        }

        while(!pq.isEmpty()) {
            Node node = pq.poll();
            expandedNodeCount++;

            Index zeroIndex = Utility.findZero(node.a);
            int row = zeroIndex.row;
            int col = zeroIndex.col;

            if(row > 0) {  // Neighbor1, move blank up
                int[][] a = Utility.createCopyOfArray(node.a);
                a[row][col] = a[row-1][col];
                a[row-1][col] = 0;
                Node newNode = new Node(a, node, node.g+1);
                newNode.setH(Utility.calculateManhattanDistance(a));
                if(newNode.getH() ==0) {
                    // reached solution
                    printSolutionDetails(newNode);
                    break;
                }

                if(!closedList.containsKey(newNode)) {
                    closedList.put(newNode, true);
                    pq.add(newNode);
                    exploredNodeCount++;
                }

            }

            if(row < node.a.length-1) {  // Neighbor2, move blank down
                int[][] a = Utility.createCopyOfArray(node.a);
                a[row][col] = a[row+1][col];
                a[row+1][col] = 0;
                Node newNode = new Node(a, node, node.g+1);
                newNode.setH(Utility.calculateManhattanDistance(a));
                if(newNode.getH() ==0) {
                    // reached solution
                    printSolutionDetails(newNode);
                    break;
                }

                if(!closedList.containsKey(newNode)) {
                    closedList.put(newNode, true);
                    pq.add(newNode);
                    exploredNodeCount++;
                }
            }

            if(col > 0) { // Neighbor3, move blank left
                int[][] a = Utility.createCopyOfArray(node.a);
                a[row][col] = a[row][col-1];
                a[row][col-1] = 0;
                Node newNode = new Node(a, node, node.g+1);
                newNode.setH(Utility.calculateManhattanDistance(a));
                if(newNode.getH() ==0) {
                    // reached solution
                    printSolutionDetails(newNode);
                    break;
                }

                if(!closedList.containsKey(newNode)) {
                    closedList.put(newNode, true);
                    pq.add(newNode);
                    exploredNodeCount++;
                }
            }

            if(col < node.a.length-1) { // Neighbor3, move blank right
                int[][] a = Utility.createCopyOfArray(node.a);
                a[row][col] = a[row][col+1];
                a[row][col+1] = 0;
                Node newNode = new Node(a, node, node.g+1);
                newNode.setH(Utility.calculateManhattanDistance(a));
                if(newNode.getH() ==0) {
                    // reached solution
                    printSolutionDetails(newNode);
                    break;
                }

                if(!closedList.containsKey(newNode)) {
                    closedList.put(newNode, true);
                    pq.add(newNode);
                    exploredNodeCount++;
                }
            }
        }
    }
}
