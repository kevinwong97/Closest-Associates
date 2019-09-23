public class Node {
        
        // store value of pair 
        private MyPair pair;
        // next node reference
        private Node next;

        public Node(String tarLabel, int weight) {
            pair = new MyPair(tarLabel, weight);
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return next;
        }

        public MyPair getPair() {
            return pair;
        }
}