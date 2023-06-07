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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;

        boolean equals = true;
        for(int i=0;i<node.getA().length;i++) {
            for(int j=0;j<node.getA().length;j++) {
                if(this.getA()[i][j] != node.getA()[i][j]) {
                    equals = false;
                    break;
                }
            }
            if(!equals) {
                break;
            }
        }
        return equals;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        int index = 0;
        for(int i=0;i<this.getA().length;i++) {
            for(int j=0;j<this.getA().length;j++) {
                hash += this.getA()[i][j] * index;
                index++;
            }
        }

        return hash;
    }
}
