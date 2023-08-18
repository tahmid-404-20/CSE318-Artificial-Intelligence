import java.io.*;
import java.util.*;

public class ReportGenerator {
    public static final int MAX_ITERATIONS = 50;
    public static final double ALPHA = 0.6;

    public static void main(String[] args) throws IOException {
        InputStream originalSystemIn = System.in;

        File file = new File("report.csv");
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fileWriter);

        bw.write(",Problem,,,Constructive_Algorithm,,,,LocalSearch,,,,GRASP,,Known_Best\n");
        bw.write(",,,,,,Randomized-1,,SimpleGreedy,,SemiGreedy-1,,nIterations,BestVal,,\n");
        bw.write("Name,|V|,|E|,Randomized-1,SimpleGreedy,SemiGreedy-1,nIterations,BestVal,nIterations,BestVal,nIterations,BestVal,,,,\n");

        HashMap<Integer, Integer> knownBest = (HashMap<Integer, Integer>) readKnownBest();

        for(int key=1; key <= 54; key++) {
            String fileName = "g" + key;
            String filePath = "inputs/" + fileName + ".rud";
            FileInputStream fileInputStream = new FileInputStream(filePath);
            System.setIn(fileInputStream);
            Scanner scr = new Scanner(System.in);

            int n = scr.nextInt();
            int m = scr.nextInt();

            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int src = scr.nextInt();
                int dest = scr.nextInt();
                int weight = scr.nextInt();
                edges.add(new Edge(src, dest, weight));
            }

            MaxCut maxCut = new MaxCut(n, edges);

            System.out.println("Running for file g" + key);

           String knownBestVal = (knownBest.containsKey(key)) ? Integer.toString(knownBest.get(key)) : ",";

            System.out.println("Running randomized-1");
            Result randomResult = maxCut.maxCutRandom(MAX_ITERATIONS);

            System.out.println("Running greedy-1");
            Result greedyResult = maxCut.maxCutGreedy();

            System.out.println("Running semi-greedy-1");
            Result semiGreedyResult = maxCut.maxCutSemiGreedy(ALPHA,MAX_ITERATIONS);

            System.out.println();

            String writeString = fileName + "," + n + "," + m + ","
                    + randomResult.constructPhaseCost + "," + greedyResult.constructPhaseCost + "," + semiGreedyResult.constructPhaseCost + ","
                    + randomResult.localSearchIterations + "," + randomResult.localSearchAvgCost + ","
                    + greedyResult.localSearchIterations + "," + greedyResult.localSearchAvgCost + ","
                    + semiGreedyResult.localSearchIterations + "," + semiGreedyResult.localSearchAvgCost + ","
                    + semiGreedyResult.graspIterations + "," + semiGreedyResult.graspCost + "," + knownBestVal + "\n";

            bw.write(writeString);
        }

        bw.close();
        fileWriter.close();

        System.setIn(originalSystemIn);
    }

    static Map<Integer,Integer> readKnownBest() throws IOException {
        Map<Integer, Integer> knownBest = new HashMap<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("knownBest.txt"));
        String line = bufferedReader.readLine();
        while (line != null) {
//            System.out.println(line);
            String[] split = line.split(",");
            knownBest.put(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            line = bufferedReader.readLine();
        }

        return knownBest;
    }
}
