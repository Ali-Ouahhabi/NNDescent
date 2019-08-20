
import java.util.*;
import java.util.Map.Entry;

public class Node<T> {
    private static int counter = 0; // id generator
    private Map<Node<T>, Double> neighbours = new HashMap<Node<T>, Double>();
    private List<Node<T>> reverse = new ArrayList<Node<T>>();
    private List<T> profile;
    private Set<Node<T>> generalNeighbours = new HashSet<Node<T>>();
    private int id = 0;

    public Node() {
        this.id = Node.counter; // generate a node id incrementally (0 to n)
        Node.counter++;
    }

    public int getId() {
        return id;
    }

    void addNeighbours(Node neighbour, Double d) {
        this.neighbours.put(neighbour, d);
    }

    void setReversForNeighbours() {
        for (Node neighbour : neighbours.keySet()) {
            neighbour.addRevers(this);
        }
    }

    void setGeneralNeighbours() {
        Set<Node<T>> tmpNeighbours = new HashSet(neighbours.keySet());
        Set<Node<T>> tmpReverse = new HashSet(reverse);
        generalNeighbours.addAll(tmpNeighbours);
        generalNeighbours.addAll(tmpReverse);
        if (generalNeighbours.contains(this))
            generalNeighbours.remove(this);
    }

    public void addRevers(Node aReverse) {
        this.reverse.add(aReverse);
    }

    @Override
    public String toString() {
        return "Node{" + this.id + "}";
    }

    // farest Neighbour: the neighbour with the biggest similarity
    public Entry<Node<T>, Double> getFarestNeighbour() {
        Iterator tmp = neighbours.entrySet().iterator();
        Entry<Node<T>, Double> max = (Entry<Node<T>, Double>) tmp.next();
        while (tmp.hasNext()) {
            Entry<Node<T>, Double> nxt = (Entry<Node<T>, Double>) tmp.next();
            if (nxt.getValue() > max.getValue()) max = nxt;
        }
        return max;
    }

    /// print function for the states of the node -------------------------------------------------
    public void printGeneralNeighbours() {
        System.out.println("nndescent.Node.printGeneralNeighbours()");
        System.out.println("this " + printProfile());

        for (Node<T> n : generalNeighbours) {
            System.out.println("\t" + n.printProfile());
        }
    }

    public void printNeighbours() {

        System.out.println("this " + printProfile());
        for (Map.Entry<Node<T>, Double> a : neighbours.entrySet()) {
            System.out.println("\t node " + a.getKey().printProfile() + " similarity " + a.getValue());
        }
    }

    public static void resetCounter(){
        Node.counter = 0;
    }

    public String printProfile() {
        String tmp = "";
        tmp += "" + this.profile.get(0);
        for (int i = 1; i < this.profile.size(); i++)
            tmp += "," + this.profile.get(i);

        return tmp;
    }

    public void printRevers() {
        System.out.println("REVERS");
        System.out.println("node :" + this.printProfile());
        for (Node<T> n : reverse) {
            System.out.println("\t " + n.printProfile());
        }

    }

    public Map<Node<T>, Double> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Map<Node<T>, Double> neighbours) {
        this.neighbours = neighbours;
    }

    public List<Node<T>> getReverse() {
        return reverse;
    }

    public void setReverse(List<Node<T>> reverse) {
        this.reverse = reverse;
    }

    public Set<Node<T>> getGeneralNeighbours() {
        return generalNeighbours;
    }

    public void setGeneralNeighbours(Set<Node<T>> generalNeighbours) {
        this.generalNeighbours = generalNeighbours;
    }

    public List<T> getProfile() {
        return profile;
    }

    public void setProfile(List<T> profile) {
        this.profile = profile;
    }
}