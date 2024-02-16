import java.util.ArrayList;
import java.util.Arrays;

/**
 * Custom Priority Queue for Nodes
 */
public class PriorityQueueNode {

    ArrayList<Node> pl;

    public PriorityQueueNode(){
        pl = new ArrayList<Node>();
    }

    /**
     * Add a node to the priority queue
     * @param n - node
     */
    public void add(Node n) {
        pl.add(n);
        pl.sort((a,b)->(a.f() - b.f()));
    }

    /**
     * Check if the priority queue is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return pl.isEmpty();
    }

    /**
     * Poll the first node in the priority queue
     * @return the first node
     */
    public Node poll() {
        Node t = pl.get(0);
        pl.remove(0);
        return t;
    }

    /**
     * Check if the priority queue contains a node
     * @param g - node
     * @return true if contains
     */
    public boolean contains(Node g) {
        for(Node n : pl){
            if(n.equals(g)) return true;
        }
        return false;
    }

    /**
     * Swap a node with a lower value
     * @param n - node
     */
    public void swapForLowerValue(Node n){
        for (int i = 0; i < pl.size(); i++) {
            Node t = pl.get(i);
            if(t.equals(n)){
                    pl.remove(i);
                    this.add(n);
                    break;
            }
        }
    }

    public String toString(){
        return Arrays.deepToString(pl.toArray());
    }

    public int size() {
        return pl.size();
    }

    public Object[] toArray() {
        return pl.toArray();
    }
}
