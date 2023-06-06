public class Node implements Comparable{
    public int[][] a;
    public Node parent;
    public int g;
    public int h;

    public Node(int[][] a, Node parent, int g) {
        this.a = a;
        this.parent = parent;
        this.g = g;
    }

    public int[][] getA() {
        return a;
    }

    public Node getParent() {
        return parent;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    // setters
    public void setG(int g) {
        this.g = g;
    }
    public void setH(int h) {
        this.h = h;
    }

    @Override
    public int compareTo(Object o) {
        Node node = (Node)o;
        return ((this.g + this.h) - (node.g+node.h));
    }
}
