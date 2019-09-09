package NNDescent;

import java.util.*;
import java.util.Map.Entry;

public class Node {

    private static int counter = 0; // id generator
    //private Map<Node, Double> neighbours = new HashMap<Node, Double>();
    private TreeMap<Double,Set<Node>> neighbours=new TreeMap<Double,Set<Node>>();
    private Set<Node> reverse = new HashSet<Node>();
    private Profile profile;
    private Set<Node> generalNeighbours = new HashSet<Node>();
    private int id = 0;

    public Node(Profile profile,int k) {
        this.profile = profile;
        this.id = Node.counter; // generate a node id incrementally (0 to n)
        Node.counter++;
    }

    public Double similarity(Node node){
        return this.profile.similarity(node.getProfile());
    }

    public int getId() {
        return id;
    }

    int updateNeighbours(Node neighbour, Double d){
        // assume that N is sorted
        if(neighbour == this) return 0;
        if(d<this.neighbours.lastKey()){
            if(this.neighbours.containsKey(d))
                if(this.neighbours.get(d).add(neighbour)) {
                    Map.Entry<Double,Set<Node>> max = this.neighbours.lastEntry();
                    if(max.getValue().size()==1) this.neighbours.pollLastEntry();
                    else max.getValue().iterator().remove();
                    return 1;
                }
                else return 0;
            else {
                Set<Node> tmp = new HashSet<Node>();
                tmp.add(neighbour);
                this.neighbours.put(d,tmp);
                Map.Entry<Double,Set<Node>> max = this.neighbours.lastEntry();
                if(max.getValue().size()==1) this.neighbours.pollLastEntry();
                else {
                    max.getValue().iterator().next();
                    max.getValue().iterator().remove();
                }
                return 1;
            }
        }
        return 0;
    }

    boolean addNeighbour(Node neighbour, Double d){
        // assume that N is sorted
            if(this.neighbours.containsKey(d))
                if(this.neighbours.get(d).add(neighbour)) return true;
                else return false;
            else {
                Set<Node> tmp = new HashSet<Node>();
                tmp.add(neighbour);
                this.neighbours.put(d,tmp);
                return true;
            }
    }

    void setReversForNeighbours() {
        for (Map.Entry<Double,Set<Node>> neighbour : neighbours.entrySet()) {
            for(Node e : neighbour.getValue()) e.addRevers(this);
        }
    }

    void setGeneralNeighbours() {
        for (Map.Entry<Double,Set<Node>> neighbour : neighbours.entrySet())
            generalNeighbours.addAll(neighbour.getValue());
        generalNeighbours.addAll(reverse);
        generalNeighbours.remove(this);
    }

    public void addRevers(Node aReverse) {
        this.reverse.add(aReverse);
    }


    /* farest Neighbour: the neighbour with the biggest similarity
    public Entry<Node, Double> getFarestNeighbour() {
        Iterator tmp = neighbours.entrySet().iterator();
        Entry<Node, Double> max = (Entry<Node, Double>) tmp.next();
        while (tmp.hasNext()) {
            Entry<Node, Double> nxt = (Entry<Node, Double>) tmp.next();
            if (nxt.getValue() > max.getValue()) max = nxt;
        }
        //this.printNeighbours();
       // System.out.println("it farest neighbor "+max.getKey().getId());
        return max;
    }*/


    public static void resetCounter(){
        Node.counter = 0;
    }

    public TreeMap<Double, Set<Node>> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(TreeMap<Double, Set<Node>> neighbours) {
        this.neighbours = neighbours;
    }

    public Set<Node> getReverse() {
        return reverse;
    }

    public void setReverse(Set<Node> reverse) {
        this.reverse = reverse;
    }

    public Set<Node> getGeneralNeighbours() {
        return generalNeighbours;
    }

    public void setGeneralNeighbours(Set<Node> generalNeighbours) {
        this.generalNeighbours = generalNeighbours;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

//----------------------------------------------------------------------------------------------

    public String printProfile() {
        return this.getId()+" "+this.profile.toString();
    }

    public void printRevers() {
        System.out.println("REVERS");
        System.out.println("node :" + this.printProfile());
        for (Node n : reverse) {
            System.out.println("\t " + n.printProfile());
        }

    }

    public void printGeneralNeighbours() {
        System.out.println("this " + printProfile());

        for (Node n : generalNeighbours) {
            System.out.println("\t" + n.printProfile());
        }
    }

    public void printNeighbours() {

        System.out.println("this " + this.id +" info "+this.getProfile().toString());
        for (Map.Entry<Double,Set<Node>> as : neighbours.entrySet()) {
            for(Node a: as.getValue())
            System.out.println("\t "+a.getId()+" similarity " + as.getKey() + " n "+a.getProfile().toString());
        }
    }


    @Override
    public String toString() {
        return "NNDescent.Node{" + this.id + "}";
    }
}