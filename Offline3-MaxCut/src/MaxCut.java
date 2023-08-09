import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class Edge implements Comparable<Edge> {
    int src;
    int dest;
    int weight;

    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {
        return this.weight - o.weight;
    }
}


enum Assignment {
    ASSIGNED_TO_X, ASSIGNED_TO_Y
}

// this for keeping max(sigmaX, sigmaY) for each v in V
class Enrollment {
    int index;
    int cutCost;
    Assignment assignment;

    public Enrollment(int index, int cutCost, Assignment assignment) {
        this.index = index;
        this.cutCost = cutCost;
        this.assignment = assignment;
    }
}

public class MaxCut {
    int[][] graph;
    int n;

    List<Edge> edges;

    MaxCut(int nNodes, List<Edge> edges) {
        this.n = nNodes;
        this.graph = new int[nNodes][nNodes];

        // initialize graph with zero weight
        for (int i = 0; i < nNodes; i++) {
            for (int j = 0; j < nNodes; j++) {
                graph[i][j] = 0;
            }
        }

        // add edges to graph ---> undirected graph
        for (Edge e : edges) {
            graph[e.src][e.dest] += e.weight;
            graph[e.dest][e.src] += e.weight;
        }

        // this is for handling multiple edges between two nodes
        this.edges = new ArrayList<>();
        for (int i = 0; i < nNodes; i++) {
            for (int j = i + 1; j < nNodes; j++) {
                if (graph[i][j] != 0) {
                    this.edges.add(new Edge(i, j, graph[i][j]));
                }
            }
        }
    }

    void addEdge(int src, int dest, int weight) {
        graph[src][dest] += weight;
        graph[dest][src] += weight;

        edges.add(new Edge(src, dest, weight));
    }



    void generateMaxCut(double alpha, int maxIterations) {
        // greedy part
        Edge randomEdge = chooseFirstEdge(alpha);

        // now defining two partitions X and Y
        List<Integer> X = new ArrayList<>();
        List<Integer> Y = new ArrayList<>();
        // V = X U Y
        List<Integer> V = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            V.add(i);
        }

        X.add(randomEdge.src);
        Y.add(randomEdge.dest);
        // remove these two nodes from V
        V.remove((Integer) randomEdge.src);
        V.remove((Integer) randomEdge.dest);

        // now we have to assign each node in V to X or Y
        while(V.size() > 0) {
            List<Enrollment> RCL = computeRCL(X, Y, V, alpha);

            Random random = new Random();
            int randomIndex = random.nextInt(RCL.size());
            Enrollment enrollment = RCL.get(randomIndex);

            if(enrollment.assignment == Assignment.ASSIGNED_TO_X) {
                X.add(enrollment.index);
            } else {
                Y.add(enrollment.index);
            }
            V.remove((Integer) enrollment.index);
        }

        System.out.println("Cut cost: " + computeCutCost(X, Y));
    }

    private int computeCutCost(List<Integer> X, List<Integer> Y) {
        int cutCost = 0;
        for(Integer x:X) {
            for(Integer y:Y) {
                cutCost += graph[x][y];
            }
        }
        return cutCost;
    }

    private List<Enrollment> computeRCL(List<Integer> X, List<Integer> Y, List<Integer> V, double alpha) {
        int maxX, maxY, minX, minY;
        maxX = maxY = Integer.MIN_VALUE;
        minX = minY = Integer.MAX_VALUE;

        // list keeping max(sigmaX, sigmaY) for each v in V
        List<Enrollment> enrollments = new ArrayList<>();

        for(Integer v:V) {
            // v is assigned to X
            int sum = 0;
            for(Integer y:Y) {  // w(v,y) for all y in Y
                sum += graph[v][y];
            }
            maxX = Math.max(maxX, sum);
            minX = Math.min(minX, sum);

            Enrollment enrollment = new Enrollment(v, sum, Assignment.ASSIGNED_TO_X);
            // v is assigned to Y
            sum = 0;
            for(Integer x:X) { // w(v,x) for all x in X
                sum += graph[v][x];
            }
            maxY = Math.max(maxY, sum);
            minY = Math.min(minY, sum);

            if(enrollment.cutCost < sum) {
                enrollment.cutCost = sum;
                enrollment.assignment = Assignment.ASSIGNED_TO_Y;
            }

            enrollments.add(enrollment);
        }

        int wMin = Math.min(minX, minY);
        int wMax = Math.max(maxX, maxY);

        int threshold = (int) (alpha * (wMax - wMin)) + wMin;

        List<Enrollment> candidateVertices = new ArrayList<>();

        for(Enrollment enrollment: enrollments) {
            if(enrollment.cutCost >= threshold) {
                candidateVertices.add(enrollment);
            }
        }

        return candidateVertices;
    }

    private Edge chooseFirstEdge(double alpha) {
        int maxWeight = Integer.MIN_VALUE;
        int minWeight = Integer.MAX_VALUE;

        // initialization, choosing the first edge with randomness
        for (Edge e : edges) {
            maxWeight = Math.max(maxWeight, e.weight);
            minWeight = Math.min(minWeight, e.weight);
        }

        int threshold = (int) (alpha * (maxWeight - minWeight)) + minWeight;
        List<Edge> candidateEdges = new ArrayList<>();
        for (Edge e : edges) {
            if (e.weight >= threshold) {
                candidateEdges.add(e);
            }
        }

        // selecting an edge randomly from candidate edges
        Random random = new Random();
        int randomIndex = random.nextInt(candidateEdges.size());
        return candidateEdges.get(randomIndex);
    }


}
