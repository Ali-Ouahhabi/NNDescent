import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;


public class NNDescent<T> {

    private Similarity<T> similarity;
    private List<Node<T>> graph;
    private int k;

    NNDescent(List<Node<T>> graph, int k, Similarity<T> similarity){
        this.graph = graph;
        this.k=k;
        this.similarity=similarity;
    }
    public List<Node<T>> getKNN() {

        // get a simple from :graph
        sample();
        for( Node<T> n : this.graph) {
            if(n.getId() < 10)
                n.printNeighbours();
        }
        System.out.println("*******************************");
        //counter to check if there has been any new update
        int c;
        do {
            //reverse then General Neighbours calculation for all the node
            this.graph.stream().forEach(n->n.setReversForNeighbours());
            this.graph.parallelStream().forEach(n->n.setGeneralNeighbours());
            // initiating the counter for the updated neighbours
            c = 0;
            for (Node<T> v : this.graph) {
                for (Node<T> u1 : v.getGeneralNeighbours()) {
                    if (u1.getGeneralNeighbours().contains(v)) u1.getGeneralNeighbours().remove(v);
                    for (Node<T> u2 : u1.getGeneralNeighbours()) {
                        // looking for neighbours in my neighbours neighbours
                        Double l = this.similarity.similarityComputing(v.getProfile(), u2.getProfile());
                        c += UpdateNN(v, u2, l);
                    }
                }
            }
        } while (c != 0);
//        printNodes(nodes);
        return this.graph;
    }

    // taking K random neighbour for each node
    public void sample() {
        int size = this.graph.size();
        for(Node n : this.graph){
            int c = 0;
            while(c<this.k){
                int i = (int) (Math.random() * graph.size());
                Node u = this.graph.get(i);
                if(n.addNeighbours(u,this.similarity.similarityComputing(n.getProfile(),u.getProfile())))
                    c++;
            }
        }
    }

    public int UpdateNN(Node<T> h, Node u, Double l) {
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
            } else
                /*if it is not, no changes are done*/
                return 0;

        }
    }

    public <T> void printNodes(List<Node<T>> nodes) {
        for (Node v : nodes) {
            System.out.printf("%s, Neighbours are %s\n", v, v.getNeighbours());
        }
    }

    public <T> double similarity(int type, List<T> profile1, List<T> profile2){
        // calculate similarity using the value of type.
        return this.similarity.similarityComputing(profile1,profile2);
    }
}
