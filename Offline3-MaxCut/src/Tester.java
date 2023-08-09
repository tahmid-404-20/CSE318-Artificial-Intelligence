import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) {

        InputStream originalSystemIn = System.in;

        try {
            FileInputStream fileInputStream = new FileInputStream("input.txt");
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
            maxCut.generateMaxCut(1.0, 0);

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
