public class Node {
    public int[][] a;
    public Node parent;

    public Node(int[][] a, Node parent) {
        this.a = a;
        this.parent = parent;
    }

    public int[][] getA() {
        return a;
    }

    public Node getParent() {
        return parent;
    }
}
