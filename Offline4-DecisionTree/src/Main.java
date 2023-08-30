import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static final double SPLIT_RATIO = 0.8;
    public static final int N_ITERATIONS = 20;

    public static void main(String[] args) {

        // setting up attributes
        HashMap<String, List<String>> attributes = new HashMap<>();
        attributes.put("buying", Arrays.asList("vhigh", "high", "med", "low"));
        attributes.put("maint", Arrays.asList("vhigh", "high", "med", "low"));
        attributes.put("doors", Arrays.asList("2", "3", "4", "5more"));
        attributes.put("persons", Arrays.asList("2", "4", "more"));
        attributes.put("lug_boot", Arrays.asList("small", "med", "big"));
        attributes.put("safety", Arrays.asList("low", "med", "high"));

        List<String> classes = Arrays.asList("unacc", "acc", "good", "vgood");

        File file = new File("car.data");
        // read data from file line by line
        // for each line, split by comma and put into a list
        List<List<String>> data = new ArrayList<>();
        int lineCount = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> row = Arrays.asList(line.split(","));
                data.add(row);
                lineCount++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        double accuracyArray[] = new double[N_ITERATIONS];
        for(int i=0; i<N_ITERATIONS; i++) {
            Collections.shuffle(data);
            int trainingDataIndex = (int) (lineCount * SPLIT_RATIO);
            List<List<String>> trainingData = data.subList(0, trainingDataIndex);
            List<List<String>> testData = data.subList(trainingDataIndex, lineCount);

            DecisionTree decisionTree = new DecisionTree(attributes, classes, trainingData);
            System.out.println("Accuracy: " + decisionTree.calculateTestAccuracy(testData));
            accuracyArray[i] = decisionTree.calculateTestAccuracy(testData);
        }

        // calculate mean and standard deviation
        double mean = 0;
        for(int i=0; i<N_ITERATIONS; i++) {
            mean += accuracyArray[i];
        }
        mean /= N_ITERATIONS;

        double stdDev = 0;
        for(int i=0; i<N_ITERATIONS; i++) {
            stdDev += Math.pow(accuracyArray[i] - mean, 2);
        }
        stdDev /= N_ITERATIONS;
        stdDev = Math.sqrt(stdDev);

        System.out.println("Mean: " + mean);
        System.out.println("Standard Deviation: " + stdDev);



    }
}