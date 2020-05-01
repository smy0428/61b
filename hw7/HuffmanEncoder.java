import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (Character c : inputSymbols) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        return map;
    }


    public static void main(String[] args) {
        char[] read = FileUtils.readFile(args[0]);
        Map<Character, Integer> tableFrequency = buildFrequencyTable(read);
        BinaryTrie trie = new BinaryTrie(tableFrequency);

        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(trie);

        Map<Character, BitSequence> tableLookup = trie.buildLookupTable();
        ArrayList<BitSequence> list = new ArrayList<>();

        // add all the BitSequence in the list
        for (char c : read) {
            BitSequence b = tableLookup.get(c);
            list.add(b);
        }

        BitSequence assemble = BitSequence.assemble(list);
        ow.writeObject(assemble);
    }
}
