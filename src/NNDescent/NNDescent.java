package NNDescent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;


public class NNDescent {

    private Similarity similarity;
    private List<Node> graph = new ArrayList<>();
    private int k;

    NNDescent(List<Profile> profiles, int k){
        for(Profile profile : profiles)
        this.graph.add(new Node(profile));
        this.k=k;
    }

    public List<Node> getKNN() {

        // get a simple from :graph
        sample();
        for( Node n : this.graph) {
            if(n.getId() < 20)
                System.out.println("0,"+n.State());;
        }
        //counter to check if there has been any new update
        int c;
        int i=0;
        do {
            //reverse then General Neighbours calculation for all the node
            this.graph.stream().forEach(n->n.setReversForNeighbours());
            this.graph.parallelStream().forEach(n->n.setGeneralNeighbours());
            // initiating the counter for the updated neighbours
            c = 0;
            for (Node v : this.graph) {
                for (Node u1 : v.getGeneralNeighbours()) {
                    if (u1.getGeneralNeighbours().contains(v)) u1.getGeneralNeighbours().remove(v);
                    for (Node u2 : u1.getGeneralNeighbours()) {
                        // looking for neighbours in my neighbours neighbours
                        Double l = v.similarity(u2);
                        c += UpdateNN(v, u2, l);
                    }
                }
            }
            i++;
            for( Node n : this.graph) {
                if(n.getId() < 20)
                    System.out.println(i+","+n.State());;
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
                Node u = this.graph.get((int) (Math.random() * graph.size()));
                if(n.addNeighbours(u,n.similarity(u))) c++;
            }
        }
    }

    public int UpdateNN(Node h, Node u, Double l) {
        if (h.getNeighbours().containsKey(u)) {
            //u is already in the neighbours no change needed
            return 0;
        } else {
            //getting the farest neighbours to H in it list of nodes
            Entry<Node, Double> max = h.getFarestNeighbour();
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

    public  void printNodes(List<Node> nodes) {
        for (Node v : nodes) {
            System.out.printf("%s, Neighbours are %s\n", v, v.getNeighbours());
        }
    }

    public  double similarity(int type, List profile1, List profile2){
        // calculate similarity using the value of type.
        return this.similarity.similarityComputing(profile1,profile2);
    }
}
