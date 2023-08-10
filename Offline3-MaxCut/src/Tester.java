import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tester {
    public static final int MAX_ITERATIONS = 100;

    public static void main(String[] args) {

        InputStream originalSystemIn = System.in;

        try {
            FileInputStream fileInputStream = new FileInputStream("input.txt");
            System.setIn(fileInputStream);
            Scanner scr = new Scanner(System.in);

            File file = new File("data.csv");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            int n = scr.nextInt();
            int m = scr.nextInt();

            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int src = scr.nextInt();
                int dest = scr.nextInt();
                int weight = scr.nextInt();
                edges.add(new Edge(src, dest, weight));
            }

            // setting up the title
            bufferedWriter.write("nIterations/alpha");
            for (int i = 0; i <= 10; i++) {
                double alpha = 0.1 * i;
                bufferedWriter.write("," + alpha);
            }
            bufferedWriter.newLine();

            // computation and data writing
            MaxCut maxCut = new MaxCut(n, edges);
            for (int iterations = 10; iterations <= MAX_ITERATIONS; iterations += 10) {
                bufferedWriter.write(iterations + ",");
                System.out.println("nIterations = " + iterations);

                for (int i = 0; i <= 10; i++) {
                    double alpha = 0.1 * i;
                    bufferedWriter.write( maxCut.generateMaxCut(alpha, iterations) + ",");
                    System.out.println("alpha = " + alpha);
                    maxCut.generateMaxCut(alpha, iterations);
                }
                bufferedWriter.newLine();
                System.out.println();
            }

            // Close the file
            bufferedWriter.close();
            fileWriter.close();

            // Close the scanner and input stream
            scr.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Restore the original System.in
            System.setIn(originalSystemIn);
        }
    }
}
