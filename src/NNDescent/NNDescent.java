package NNDescent;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class NNDescent {

    private List<Node> graph = new ArrayList<>();
    private int k;

    public NNDescent(List<Profile> profiles, int k){
        for(Profile profile : profiles)
        this.graph.add(new Node(profile,k));
        this.k=k;
    }

    public List<Node> getKNN() {
        double time = System.currentTimeMillis();
        // get a simple from :graph
        sample();
        //counter to check if there has been any new update
        int c;
        int i=0;
        do {
            double t = System.currentTimeMillis();
            //reverse then General Neighbours calculation for all the node
            this.graph.parallelStream().forEach(n->{
                n.setKnown(new HashSet<Node>());
                n.setReversForNeighbours();
            });
            System.out.println("Reverse;"+(System.currentTimeMillis()-t));
            t = System.currentTimeMillis();
            this.graph.parallelStream().forEach(n->n.setGeneralNeighbours());
            System.out.println("General;"+(System.currentTimeMillis()-t));

            // initiating the counter for the updated neighbours
            c=0;
            double Tloop = System.currentTimeMillis();
            for(Node v: this.graph){
                for (Node u1 : v.getGeneralNeighbours()) {
                    for (Node u2 : u1.getGeneralNeighbours()) {
                        // looking for neighbours in my neighbours neighbours
                            Double l = v.similarity(u2);
                            c+=v.updateNeighbours(u2, l);
                           // System.out.println("c = "+c+" node "+u2.getId());
                    }
                }
            }
            System.out.println("Tloop;"+i+";"+(System.currentTimeMillis()-Tloop));
            i++;
        } while (c!= 0);
        /*for( Node n : this.graph) {
                n.printNeighbours();
        }*/
        System.out.println("time;"+(System.currentTimeMillis()-time));
        return this.graph;
    }

    // taking K random neighbour for each node
    public void sample() {
        int size = this.graph.size();
        for(Node n : this.graph){
            int c = 0;
            while(c<this.k){
                Node u = this.graph.get((int) (Math.random() * size));
                if(n.addNeighbour(u,n.similarity(u))) c++;
            }
        }
    }

    public int UpdateNN(Node h, Node u, Double l) {
        return h.updateNeighbours(u,l);
        /*
        if (h.getNeighbours().containsKey(u)) {
            //u is already in the neighbours no change needed
            return 0;
        } else {
            //getting the farest neighbours to h
            Entry<Node, Double> max = h.getFarestNeighbour();
            /*checking if u is closer than the farest neighbour,
            if (l < max.getValue()) {
                /*if it's the case replace the farest by the new one

                h.getNeighbours().remove(max.getKey());
                h.getNeighbours().put(u, l);
                return 1;
            } else
                /*if it is not, no changes are done
                return 0;

        }*/
    }

    public  void printNodes(List<Node> nodes) {
        for (Node v : nodes) {
            System.out.printf("%s, Neighbours are %s\n", v, v.getNeighbours());
        }
    }
    
}
