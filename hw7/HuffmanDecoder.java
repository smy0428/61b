import java.util.ArrayList;

public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader or = new ObjectReader(args[0]);

        // read the Huffman coding trie
        Object o1 = or.readObject();

        // read the massive bit sequence
        Object o2 = or.readObject();

        BinaryTrie trie = (BinaryTrie) o1;
        BitSequence bit = (BitSequence) o2;
        ArrayList<Character> list = new ArrayList();

        while (bit.length() > 0) {

            // Perform a longest prefix match on the massive sequence
            Match m = trie.longestPrefixMatch(bit);
            char c = m.getSymbol();
            BitSequence b = m.getSequence();

            // Record the symbol in some data structure.
            list.add(c);

            // Create a new bit sequence containing the remaining unmatched bits
            int i = b.length();
            bit = bit.allButFirstNBits(i);
        }

        char[] result = new char[list.size()];
        for (int index = 0; index < list.size(); index += 1) {
            result[index] = list.get(index);
        }

        // Write the symbols in some data structure to the specified file
        FileUtils.writeCharArray(args[1], result);
    }
}
