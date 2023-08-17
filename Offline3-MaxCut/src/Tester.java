import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tester {
//    public static final int MAX_ITERATIONS = 100;
//
//    static void generateReport(MaxCut maxCut) throws IOException {
//        File file = new File("data2.csv");
//        FileWriter fileWriter = new FileWriter(file);
//        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//
//        // setting up the title
//        bufferedWriter.write("nIterations/alpha");
//        for (int i = 0; i <= 10; i++) {
//            double alpha = 0.1 * i;
//            bufferedWriter.write("," + alpha);
//        }
//        bufferedWriter.newLine();
//
//        // computation and data writing
//
//        for (int iterations = 10; iterations <= MAX_ITERATIONS; iterations += 10) {
//            bufferedWriter.write(iterations + ",");
//            System.out.println("nIterations = " + iterations);
//
//            for (int i = 0; i <= 10; i++) {
//                double alpha = 0.1 * i;
//                int maxCutCost = maxCut.generateMaxCut(alpha, iterations);
//                System.out.println("Alpha = " + alpha + " maxCut: " + maxCutCost);
//                bufferedWriter.write( maxCutCost + ",");
//            }
//            bufferedWriter.newLine();
//            System.out.println();
//        }
//
//        // Close the file
//        bufferedWriter.close();
//        fileWriter.close();
//
//    }
//
//    static void interactiveMode(MaxCut maxCut) {
//        // calculate max cut for different values of alpha and 10 iterations in a for loop
////        for (int i = 0; i <= 10; i++) {
////            double alpha = 0.1 * i;
////            System.out.println("For alpha = " + alpha + " the max cut is " + maxCut.generateMaxCut(alpha, 10));
////        }
//
//        double alpha = 0.7;
//        System.out.println("For alpha = " + alpha + " the max cut is " + maxCut.generateMaxCut(alpha, 100));
//    }
//
//    public static void main(String[] args) {
//
//        InputStream originalSystemIn = System.in;
//
//        try {
//            FileInputStream fileInputStream = new FileInputStream("input.txt");
//            System.setIn(fileInputStream);
//            Scanner scr = new Scanner(System.in);
//
//            int n = scr.nextInt();
//            int m = scr.nextInt();
//
//            List<Edge> edges = new ArrayList<>();
//            for (int i = 0; i < m; i++) {
//                int src = scr.nextInt();
//                int dest = scr.nextInt();
//                int weight = scr.nextInt();
//                edges.add(new Edge(src, dest, weight));
//            }
//
//            MaxCut maxCut = new MaxCut(n, edges);
//
//            interactiveMode(maxCut);
////            generateReport(maxCut);
//
//            // Close the scanner and input stream
//            scr.close();
//            fileInputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            // Restore the original System.in
//            System.setIn(originalSystemIn);
//        }
////        File file = new File("report.csv");
////        FileWriter fileWriter = null;
////        try {
////            fileWriter = new FileWriter(file);
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
////        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
////
////        bufferedWriter.write(",Problem,,,Constructive_algorithm,,,LocalSearch,GRASP,Known_best_solution_of_upper_bound");
////
////        for(int i=1;i<=54;i++) {
////            try {
////                String filePath = "inputs/g"+i+".rud";
////                FileInputStream fileInputStream = new FileInputStream(filePath);
////                System.setIn(fileInputStream);
////                Scanner scr = new Scanner(System.in);
////
////                int n = scr.nextInt();
////                int m = scr.nextInt();
////
////                List<Edge> edges = new ArrayList<>();
////                for (int i = 0; i < m; i++) {
////                    int src = scr.nextInt();
////                    int dest = scr.nextInt();
////                    int weight = scr.nextInt();
////                    edges.add(new Edge(src, dest, weight));
////                }
////
////                MaxCut maxCut = new MaxCut(n, edges);
////
////                interactiveMode(maxCut);
//////            generateReport(maxCut);
////
////                // Close the scanner and input stream
////                scr.close();
////                fileInputStream.close();
////            } catch (IOException e) {
////                e.printStackTrace();
////            } finally {
////                // Restore the original System.in
////                System.setIn(originalSystemIn);
////            }
////        }
//
//    }
}
