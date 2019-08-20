import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;


public class NNDescent<T> {

    public static <T> List<Node<T>> getKNN(List<Node<T>> graph, int k, int type) {
        // get a simple from :graph
        List<Node<T>> nodes = sample(graph, k, type);
        //counter to check if there has been any new update
        int c;
        do {
            //reverse calcutation for all the node
            revers(nodes);
            for (int i = 0; i < nodes.size(); i++) {
                nodes.get(i).setGeneralNeighbours();
            }
            // initiating the counter for the updated neighbours

            c = 0;
            for (Node<T> v : nodes) {
                for (Node<T> u1 : v.getGeneralNeighbours()) {
                    if (u1.getGeneralNeighbours().contains(v)) u1.getGeneralNeighbours().remove(v);
                    for (Node<T> u2 : u1.getGeneralNeighbours()) {
                        // looking for neighbours in my neighbours neighbours
                        Double l = similarity(type, v.getProfile(), u2.getProfile());
                        c += UpdateNN(v, u2, l);
                    }
                }
            }
        } while (c != 0);

//        printNodes(nodes);

        return nodes;

    }

    // taking K random neighbour for each node
    public static <T> List<Node<T>> sample(List<Node<T>> graph, int k, int type) {
        List<Node<T>> simple = new ArrayList<>();
        Set<Integer> tmp;
        for (int e = 0; e < graph.size(); e++) {
            // tmp temporary set filled with the indexes of the nodes in the  array nodes[], to check for duplications
            tmp = new HashSet<Integer>();
            for (int i = 0; i < graph.size(); tmp.add(i++)) ;
            // removing the index e of the concerned nodes in this iteration
            tmp.remove(e);
            int i = 0;
            Node<T> node = graph.get(e);
            while (i < k) {
                int j = (int) (Math.random() * graph.size());
                /*checking if it hasn't alerady been taken as a neighbour*/
                if (tmp.contains(j)) {
                    /*if it's not add it as a neighbour*/
                    node.addNeighbours(graph.get(j), similarity(type, node.getProfile(), graph.get(j).getProfile()));
                    /*then remove it from the temporary set*/
                    tmp.remove(j);
                    i++;
                }
            }
            simple.add(node);
        }
        return simple;
    }

    public static <T> void revers(List<Node<T>> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            // declare my selfe(the node) as a revers for my neighbours
            nodes.get(i).setReversForNeighbours();
        }
    }

    public static <T> int UpdateNN(Node<T> h, Node u, Double l) {

        if (h.getNeighbours().containsKey(u)) {
            //u is already in the neighbours no change needed
            return 0;
        } else {
            //getting the farest neighbours to H in it list of nodes
            Entry<Node<T>, Double> max = h.getFarestNeighbour();
            /*checking if u is closer than the farest neighbour, */
            if (l < max.getValue()) {
                /*if it's the case replace the farest by the new one*/
                h.getNeighbours().remove(max.getKey());
                h.getNeighbours().put(u, l);
                return 1;
            } else {
                /*if it is not, no changes are done*/
                return 0;
            }
        }
    }

    public static <T> void printNodes(List<Node<T>> nodes) {
        for (Node v : nodes) {
            System.out.printf("%s, Neighbours are %s\n", v, v.getNeighbours());
        }
    }

    public static <T> double similarity(int type, List<T> profile1, List<T> profile2){
        // calculate similarity using the value of type.
        switch (type) {
            case 1:
                return new Euclidean().similarityComputing(profile1, profile2);
            case 2:
                return new Cosine().similarityComputing(profile1, profile2);
            default:
                throw new IllegalArgumentException ("This similarity is not defined!");
        }
    }
}
