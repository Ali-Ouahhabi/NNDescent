import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Similarity type [1: Euclidean, 2: Cosine ]
        int similarity = 2;
        Similarity<Double> cosine = new Cosine<>();
        // number of neighbors;
        int k = 3;
        NNDescent<Double> knn = new NNDescent<>(Main.readCSV("src/datasets/data.csv"),k,cosine);

        // Generate BruteForce graph from dataset
        //List<Node> KNNBruteForceGraph = BruteForceKNN.getKNN(readCSV("datasets/data.csv"), k, similarity);
        // BruteForceKNN.printNodes(KNNBruteForceGraph);

        // Generate NNDescent graph from dataset
        List<Node<Double>> KNNDescentGraph = knn.getKNN();
        for( Node<Double> n : KNNDescentGraph) {
             if(n.getId()<10) n.printNeighbours();
        }

        //double precision = precision(KNNDescentGraph, KNNBruteForceGraph);

        //System.out.printf ("Precision = % .2f%%", precision * 100);

      //  System.out.println();

    }
/*
    private static double precision(List<Node> knnDescentGraph, List<Node> knnBruteForceGraph) {
        // relevant items / sample items
        int similar = 0; // similar neighbors for each node in both graphs.
        int size = 0; // size of neighbors of all nodes
        for (Node node : knnDescentGraph) {
            for (Node entryD : node.getNeighbours().keySet()) {
                size++;
                Node bruteForceNode = getNodeFromGraph(knnBruteForceGraph, node.getId());
                if (bruteForceNode == null) {
                    break;
                }
                for (Node entryB : bruteForceNode.getNeighbours().keySet()) {
                    // comparing neighbors ids in both graphs
                    if (entryD.getId() == entryB.getId()) {
                        similar++;
                    }
                }
            }
            System.out.println();
        }

        return (double) similar / size;
    }
*/
    public static Node getNodeFromGraph(List<Node> graph, int id) {
        for (Node node : graph) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    public static List<Node<Double>> readCSV(String file) {

        List<Node<Double>> nodes = new ArrayList<>();
        Node.resetCounter();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            Scanner scanner = null;
            int index = 0;
            while ((line = reader.readLine()) != null) {
                Node node = new Node();
                List<Double> profile = new ArrayList<>();
                List<Integer> indexes = Arrays.asList(2, 3, 4, 5, 6);
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                while (scanner.hasNext()) {
                    String data = scanner.next();
                    try {
                        if (indexes.contains(index)) {
                            profile.add(Double.parseDouble(data));
                        }
                    } catch (Exception e) {
                    }
                    index++;
                }
                index = 0;
                if (!profile.isEmpty()) {
                    node.setProfile(profile);
                    nodes.add(node);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Exception detected! " + e.toString());
        }

        return nodes;
    }


}
