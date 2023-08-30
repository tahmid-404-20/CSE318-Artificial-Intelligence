import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.*;

class Node{
    boolean isLeaf;
    String attribute;
    HashMap<String, Node> children;
    // if leaf node
    String className;


    public Node(String name, boolean isLeaf) {
        if(!isLeaf) {
            attribute = name;
            children = new HashMap<>();
        } else {
            className = name;
        }

        this.isLeaf = isLeaf;
    }

    public void addChild(String attributeValue, Node child) {
        children.put(attributeValue, child);
    }
}

public class DecisionTree {

    HashMap<String, List<String>> attributesMap;
    List<String> attributes;
    List<String> classes;
    List<List<String>> data;

    Node root;

    public DecisionTree(HashMap<String, List<String>> attributesMap, List<String> classes, List<List<String>> data) {
        this.attributesMap = attributesMap;
        this.attributes = new ArrayList<>(attributesMap.keySet());
        this.classes = classes;
        this.data = data;
        this.root = constructDecisionTree(data, attributes, null);
//        printTree();
    }

    Node constructDecisionTree(List<List<String>> examples, List<String> attributes, List<List<String>> parentExamples) {
        if(examples.isEmpty())
            return pluralityValue(parentExamples);
        else if(getClassCount(examples).size() == 1)  // if all examples belong to same class
            return new Node(examples.get(0).get(examples.get(0).size() - 1), true);
        else if(attributes.isEmpty())
            return pluralityValue(examples);

        else {
            double initialEntropy = getEntropy(examples);
            double maxGain = -1.0;
            String maxGainAttribute = "";
            for(String attribute: attributes) {
                double gain = calculateGainForAttribute(examples, initialEntropy, attribute);
                if(gain > maxGain) {
                    maxGain = gain;
                    maxGainAttribute = attribute;
                }
            }

            Node node = new Node(maxGainAttribute, false);
            for(String attributeValue: attributesMap.get(maxGainAttribute)) {
                List<List<String>> subset = getSubsetForAttributeValue(examples, getAttributeIndex(maxGainAttribute), attributeValue);
                List<String> newAttributes = new ArrayList<>(attributes);
                newAttributes.remove(maxGainAttribute);
                node.addChild(attributeValue, constructDecisionTree(subset, newAttributes, examples));
            }

            return node;
        }
    }

    double calculateTestAccuracy(List<List<String>> testData) {
        int correct = 0;
        for(List<String> row: testData) {
            String predictedClass = predictClass(row);
            if(predictedClass.equalsIgnoreCase(row.get(row.size() - 1))) {
                correct++;
            }
        }
        return (double) correct / testData.size();
    }

    String predictClass(List<String> row) {
        Node node = root;
        while(!node.isLeaf) {
            String attributeValue = row.get(getAttributeIndex(node.attribute));
            node = node.children.get(attributeValue);
        }
        return node.className;
    }

    void printTree() {
        printTreeRecursive(root, 0);
    }

    private void printTreeRecursive(Node root, int depth) {
        for(int i=0; i<depth; i++) {
            System.out.print("-------");
        }

        if(root.isLeaf) {
            System.out.println("**" + root.className + "**");
        } else {
            System.out.println(root.attribute);
            for(Map.Entry<String, Node> entry: root.children.entrySet()) {
                for(int i=0; i<depth; i++) {
                    System.out.print("-------");
                }
                System.out.println(":" + entry.getKey());
                printTreeRecursive(entry.getValue(), depth + 1);
            }
        }
    }
    Node pluralityValue(List<List<String>> examples) {
        HashMap<String, Integer> classCount = getClassCount(examples);
        int maxCount = 0;
        String maxClass = "";
        for(Map.Entry<String, Integer> entry: classCount.entrySet()) {
            if(entry.getValue() >= maxCount) {
                maxCount = entry.getValue();
                maxClass = entry.getKey();
            }
        }
        return new Node(maxClass, true);
    }

    private HashMap<String, Integer> getClassCount(List<List<String>> data) {
        HashMap<String, Integer> classCount = new HashMap<>();
        for(List<String> classData: data) {
            String className = classData.get(classData.size() - 1); // last element is class
            if(classCount.containsKey(className)) {
                classCount.put(className, classCount.get(className) + 1);
            } else {
                classCount.put(className, 1);
            }
        }
        return classCount;
    }

    double getEntropy(List<List<String>> data) {
        HashMap<String, Integer> classCount = getClassCount(data);
        double entropy = 0;
        for(Map.Entry<String, Integer> entry: classCount.entrySet()) {
            double p = (double) entry.getValue() / data.size();
            entropy += -p * Math.log(p);
        }
        return entropy;
    }

    double calculateGainForAttribute(List<List<String>> data, double initialEntropy, String attribute) {
        double gain = initialEntropy;
        for(String attbtVal: attributesMap.get(attribute)) {
            List<List<String>> subset = getSubsetForAttributeValue(data, getAttributeIndex(attribute), attbtVal);
            double entropy = getEntropy(subset);
            gain -= entropy * subset.size() / data.size();  // calculate weighted entropy
        }

        return gain;
    }


    // attributeIndex is required for accessing the attribute value in the row
    List<List<String>> getSubsetForAttributeValue(List<List<String>> data, int attributeIndex, String attributeValue) {
        List<List<String>> subset = new ArrayList<>();
        for(List<String> row: data) {
            if(row.get(attributeIndex).equalsIgnoreCase(attributeValue)) {
                subset.add(row);
            }
        }
        return subset;
    }

    int getAttributeIndex(String attribute) {
        return switch (attribute) {
            case "buying" -> 0;
            case "maint" -> 1;
            case "doors" -> 2;
            case "persons" -> 3;
            case "lug_boot" -> 4;
            case "safety" -> 5;
            default -> -1;
        };
    }

}


