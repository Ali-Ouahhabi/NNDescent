
import java.util.*;
import java.util.Map.Entry;

public class Node {

    private static int counter = 0; // id generator
    private Map<Node, Double> neighbours = new HashMap<Node, Double>();
    private List<Node> reverse = new ArrayList<Node>();
    private Profile profile;
    private Set<Node> generalNeighbours = new HashSet<Node>();
    private int id = 0;

    public Node(Profile profile) {
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

    boolean addNeighbours(Node neighbour, Double d) {
        if (this.neighbours.containsKey(neighbour))
            return false;
        else
            this.neighbours.put(neighbour, d);
        return true;
    }

    void setReversForNeighbours() {
        for (Node neighbour : neighbours.keySet()) {
            neighbour.addRevers(this);
        }
    }

    void setGeneralNeighbours() {
        Set<Node> tmpNeighbours = new HashSet(neighbours.keySet());
        Set<Node> tmpReverse = new HashSet(reverse);
        generalNeighbours.addAll(tmpNeighbours);
        generalNeighbours.addAll(tmpReverse);
        if (generalNeighbours.contains(this))
            generalNeighbours.remove(this);
    }

    public void addRevers(Node aReverse) {
        this.reverse.add(aReverse);
    }


    // farest Neighbour: the neighbour with the biggest similarity
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
    }


    public static void resetCounter(){
        Node.counter = 0;
    }

    public Map<Node, Double> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Map<Node, Double> neighbours) {
        this.neighbours = neighbours;
    }

    public List<Node> getReverse() {
        return reverse;
    }

    public void setReverse(List<Node> reverse) {
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
        return profile.toString();
    }

    public void printRevers() {
        System.out.println("REVERS");
        System.out.println("node :" + this.printProfile());
        for (Node n : reverse) {
            System.out.println("\t " + n.printProfile());
        }

    }

    public void printGeneralNeighbours() {
        System.out.println("nndescent.Node.printGeneralNeighbours()");
        System.out.println("this " + printProfile());

        for (Node n : generalNeighbours) {
            System.out.println("\t" + n.printProfile());
        }
    }

    public void printNeighbours() {

        System.out.println("this " + this.id);
        for (Map.Entry<Node, Double> a : neighbours.entrySet()) {
            System.out.println("\t node " + a.getKey().getId() + " similarity " + a.getValue());
        }
    }

    public String State(){
        String tmp = ""+this.id+",";
        Double tmp2 = 0.0;
        for (Map.Entry<Node, Double> a : neighbours.entrySet()){
            tmp2 += a.getValue();
        }
        tmp+=tmp2;
        return tmp;
    }

    @Override
    public String toString() {
        return "Node{" + this.id + "}";
    }
}