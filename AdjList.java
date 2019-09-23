import java.io.*;
import java.util.*;
import java.util.HashMap;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 * 
 * Your task is to complete the implementation of this class. You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class AdjList extends AbstractAssocGraph {

    // Private Map to map labels to vertices.
    private HashMap<String, Integer> indexToLabel;
    // Private array of linked lists.
    LinkedListImpl[] vertices;

    /**
     * Contructs empty graph.
     */
    public AdjList() {
        indexToLabel = new HashMap<String, Integer>();
        vertices = new LinkedListImpl[100];
    } // end of AdjList()


    public void addVertex(String vertLabel) {
        // locate empty space for vertex
        for (int i = 0; i < this.vertices.length; i++)
        {
            if (vertices[i] == null)
            {
                vertices[i] = new LinkedListImpl();
                indexToLabel.put(vertLabel, i);
                return;
            }
        }

        // linkedlist array doubled
        LinkedListImpl[] tempVertices = this.vertices;
        this.vertices = new LinkedListImpl[this.vertices.length * 2];

        // get array and iterate
        for (int i = 0; i < tempVertices.length; i++)
            this.vertices[i] = tempVertices[i];

        // puts new vertex
        vertices[tempVertices.length] = new LinkedListImpl();
        indexToLabel.put(vertLabel, tempVertices.length);
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel, int weight) {
        // if both vertices and weight exist in the table
        if (indexToLabel.containsKey(srcLabel) && indexToLabel.containsKey(tarLabel) && weight != 0)
        {

            // checking for identical vertices
            Node node = vertices[indexToLabel.get(srcLabel)].getStartNode();
            if (node != null)
            {
                do
                {
                    if (node.getPair().getKey().equals(tarLabel))
                        return;
                    node = node.getNext();
                }
                while (node != null);
            }
            vertices[indexToLabel.get(srcLabel)].add(tarLabel, weight);
        }
    } // end of addEdge()


    public int getEdgeWeight(String srcLabel, String tarLabel) {
        if (indexToLabel.containsKey(srcLabel))
        {
            Node node = vertices[indexToLabel.get(srcLabel)].getNodeTLabel(tarLabel);

            if (node != null)
                return node.getPair().getValue();
        }
        // update return value
        return EDGE_NOT_EXIST;
    } // end of existEdge()


    public void updateWeightEdge(String srcLabel, String tarLabel, int weight) {
        if (weight == 0)
        {
            vertices[indexToLabel.get(srcLabel)].removeNodeTLabel(tarLabel);
        }
        else if (weight > 0 && indexToLabel.containsKey(srcLabel))
        {
            Node node = vertices[indexToLabel.get(srcLabel)].getNodeTLabel(tarLabel);

            if (node != null)
                node.getPair().setValue(weight);
        }
    } // end of updateWeightEdge()


    public void removeVertex(String vertLabel) {
        if (indexToLabel.containsKey(vertLabel))
        {
            // remove vertex
            vertices[indexToLabel.get(vertLabel)] = null;
            indexToLabel.remove(vertLabel);

            // remove edges related to the vertex
            for (int i = 0; i < this.vertices.length; i++)
                if (vertices[i] != null)
                    vertices[i].removeNodeTLabel(vertLabel);
        }
    } // end of removeVertex()


    public List<MyPair> inNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();

        // count inNeighbours
        int count = 0;
        for (int i : indexToLabel.values())
            if (vertices[i].getNodeTLabel(vertLabel) != null)
                count++;

        // add temporary array
        int j = 0;
        Boolean[] tempCheckIndex = new Boolean[count];
        MyPair[] tempArray = new MyPair[count];

        Node node;

        for (String key : indexToLabel.keySet())
        {
            node = vertices[indexToLabel.get(key)].getNodeTLabel(vertLabel);
            if (node != null)
            {
                MyPair tempPair = new MyPair(key, node.getPair().getValue());
                tempArray[j] = tempPair;
                tempCheckIndex[j++] = false;
            }
        }

        // link list and k switching
        if (k == -1)
            for (MyPair pair : tempArray)
                neighbours.add(pair);
        else if (k > 0) 
        {
            while (neighbours.size() < k)
            {
                // locate the next biggest weight
                int max_weight = 0;
                int pairIndex_Biggest = 0;

                for (int i = 0; i < count; i++)
                {
                    if (tempArray[i].getValue() >= max_weight && tempCheckIndex[i] == false)
                    {
                        max_weight = tempArray[i].getValue();
                        pairIndex_Biggest = i;
                    }
                }

                // when all edges are added then break here
                // add the biggest pair to the list
                if (max_weight == 0)
                    break;
                neighbours.add(tempArray[pairIndex_Biggest]);
                tempCheckIndex[pairIndex_Biggest] = true;
            }
        }

        return neighbours;
    } // end of inNearestNeighbours()


    public List<MyPair> outNearestNeighbours(int k, String vertLabel) {
        List<MyPair> neighbours = new ArrayList<MyPair>();

        // get the required vertices
        if (indexToLabel.containsKey(vertLabel))
        {
            //k switching
            if (k == -1)
            {
                Node node = vertices[indexToLabel.get(vertLabel)].getStartNode();

                if (node != null)
                {
                    do
                    {
                        neighbours.add(node.getPair());
                        node = node.getNext();
                    }
                    while (node != null);
                }

            }
            else if (k >= 0)
            {
                // set initial count of edges to 0
                int count = 0;

                Node startNode = vertices[indexToLabel.get(vertLabel)].getStartNode();
                Node node = startNode;

                if (node != null)
                {
                    // begin counting edges
                    do
                    {
                        count++;
                        node = node.getNext();
                    }
                    while (node != null);

                    // add temporary array
                    Boolean[] tempCheckIndex = new Boolean[count];
                    MyPair[] tempArray = new MyPair[count];          

                    node = startNode;
                    for (int i = 0; i < count; i++)
                    {
                        tempArray[i] = node.getPair();
                        tempCheckIndex[i] = false;
                        node = node.getNext();
                    }

                    // link list
                    // locate the next biggest weight
                    while (neighbours.size() < k)
                    {
                        int pairIndex_Biggest = 0;
                        int max_weight = 0;

                        for (int i = 0; i < count; i++)
                        {
                            if (tempArray[i].getValue() >= max_weight && tempCheckIndex[i] == false)
                            {
                                max_weight = tempArray[i].getValue();
                                pairIndex_Biggest = i;
                            }
                        }

                        // when all edges are added then break here
                        if (max_weight == 0)
                            break;

                        // add the biggest pair to the list
                        neighbours.add(tempArray[pairIndex_Biggest]);
                        tempCheckIndex[pairIndex_Biggest] = true;
                    }

                }
            }
        }
        return neighbours;
    } // end of outNearestNeighbours()


    public void printVertices(PrintWriter os) {
        for (String key : indexToLabel.keySet()) {
            os.println(key + " "); // testing python script
            System.out.print(key + " "); 
        }
        System.out.println();
        os.println();
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        Node node;

        for (String srcLabel : indexToLabel.keySet())
        {
            node = vertices[indexToLabel.get(srcLabel)].getStartNode();

            if (node != null) 
            {
                do 
                {   // testing python script
                    os.println(srcLabel + " " + node.getPair().getKey()
                                      + " " + node.getPair().getValue());
                    System.out.println(srcLabel + " " + node.getPair().getKey()
                                              + " " + node.getPair().getValue());
                    node = node.getNext();
                } 
                while (node != null);
            }
        }
    } // end of printEdges()

} // end of class AdjList

class LinkedListImpl {

    private int size;
    private Node start;
    private Node end;


    public LinkedListImpl() {
    }

    public Node getStartNode() {
        return start;
    }

    public int getSize() {
        return size;
    }

    public boolean removeNodeTLabel(String tarLabel) {
        Node node = this.start;

        if (node != null)
        {
            if (node.getPair().getKey().equals(tarLabel))
            {
                this.start = node.getNext();
                this.size--;
                return true;
            }
            else
            {
                do
                {
                    if (node.getNext() != null && node.getNext().getPair().getKey().equals(tarLabel))
                    {
                        node.setNext(node.getNext().getNext());
                        this.size--;
                        return true;
                    }

                    node = node.getNext();
                }
                while (node != null);
            }
        }
        return false;
    }

    public Node getNodeTLabel(String tarLabel) {
        Node node = this.start;

        if (node != null)
        {
            do
            {
                if (node.getPair().getKey().equals(tarLabel))
                    return node;
                node = node.getNext();
            }
            while (node != null);
        }
        return null;
    }

    public int add(String tarLabel, int weight) {
        if (size == 0)
        {
            this.start = new Node(tarLabel, weight);
            this.end = this.start;
        }
        else
        {
            end.setNext(new Node(tarLabel, weight));
            this.end = end.getNext();
        }
        this.size++;
        return this.size - 1;
    }

}
