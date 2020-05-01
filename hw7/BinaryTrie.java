import edu.princeton.cs.algs4.MinPQ;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


public class BinaryTrie implements Serializable {
    private Node trie;


    public BinaryTrie(Map<Character, Integer> frequencyTable) {

        MinPQ<Node> pq = new MinPQ<>();

        // insert every node into the PQ
        Set<Character> keys = frequencyTable.keySet();
        for (Character c: keys) {
            Node n = new Node(c, frequencyTable.get(c), null, null);
            pq.insert(n);
        }

        // merge two smallest trees
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pq.insert(parent);
        }

        trie = pq.delMin();
    }


    public Match longestPrefixMatch(BitSequence querySequence) {
        Node temp = trie;
        int i = 0;

        while (!temp.isLeaf()) {
            int curr = querySequence.bitAt(i);
            if (curr == 0) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
            i += 1;
        }

        Character c = temp.getChar();
        BitSequence b = querySequence.firstNBits(i);
        Match result = new Match(b, c);

        return result;
    }


    public Map<Character, BitSequence> buildLookupTable() {
        Node temp = trie;
        HashMap<Character, BitSequence> map = new HashMap<>();

        append(map, temp, new BitSequence());
        return map;
    }


    // helper method
    private static void append(Map<Character, BitSequence> map, Node n, BitSequence b) {
        if (!n.isLeaf()) {
            append(map, n.left, new BitSequence(b).appended(0));
            append(map, n.right, new BitSequence(b).appended(1));
        } else {
            Character c = n.getChar();
            map.put(c, b);
        }
    }

    // Huffman trie node
    private static class Node implements Comparable<Node>, Serializable {
        private final char c;
        private final int freq;
        private final Node left, right;

        Node(char c, int freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return left == null;
        }

        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }

        public char getChar() {
            return c;
        }

        public int getFreq() {
            return freq;
        }
    }

}
