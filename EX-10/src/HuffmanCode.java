import java.util.HashMap;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class HuffmanCode {
    public static void main(String[] args) {
        //String str = "mississippi";
        String str = "0123456789ABCDEF";
        //String str = "Jobs launched into a sermon about how the Macintosh and its software would be so easy to use";
        //String str = "An academic career in which a person is forced to produce scientific writings in great amounts creates a danger of intellectual superficiality, Einstein said.";
        HashMap<Character, String> map = buildPrefixCodeMap(str);
        System.out.println(str);
        System.out.println(map.toString());
        String code = encode(str, map);
        System.out.println(code);
        System.out.printf("Bits per Symbol: %f%n", (double) code.length() / (double) str.length());
    }

    /**
     * Encodes a given string s with the given character-map map
     * @return
     */
    public static String encode(String s, HashMap<Character, String> map){
        StringBuilder code = new StringBuilder();
        for (char c : s.toCharArray()){
            code.append(map.get(c));
        }
        return code.toString();
    }


    /**
     * generates a huffmanTree for the encoding of symbols in the string s
     * @param s
     * @return
     */
    public static Node huffmanTree(String s) {
        HashMap<Character, Integer> occurrenceMap = new HashMap<Character, Integer>();

        for (char c : s.toCharArray()) {
            occurrenceMap.put(c, occurrenceMap.containsKey(c) ? occurrenceMap.get(c) + 1 : 1);
        }
        PriorityQueue<Node> nodes = new PriorityQueue<>();
        for (char c: occurrenceMap.keySet()){
            nodes.add(new Node(c, occurrenceMap.get(c)));
        }
        while(nodes.stream().count() > 1){
            nodes.add(new Node(nodes.poll(), nodes.poll()));
        }
        return nodes.poll();
    }
    public static HashMap<Character, String> buildPrefixCodeMap(String s){
        Node huffmanTree = huffmanTree(s);
        var prefixCodeMap = new HashMap<Character, String>();
        buildPrefixCodeMap(huffmanTree, prefixCodeMap, "");
        return prefixCodeMap;
    }

    /**
     * recursively builds a prefix code map of the huffmanTree
     * @param huffmanTree
     * @param prefixCodeMap
     * @param code
     */
    public static void buildPrefixCodeMap(Node huffmanTree, HashMap<Character, String> prefixCodeMap, String code){
        if(huffmanTree.left == null){
            prefixCodeMap.put(huffmanTree.symbol, code);
            return;
        }
        assert huffmanTree.right != null;
        buildPrefixCodeMap(huffmanTree.left, prefixCodeMap, code + "0");
        buildPrefixCodeMap(huffmanTree.right, prefixCodeMap, code + "1");
    }

    /**
     * A node in a tree encoding a huffman code
     */
    static class Node implements Comparable<Node>{
        Node left;
        Node right;
        int occurrence;
        Character symbol;

        /**
         * builds a leaf node from the given symbol and its occurrence
         * @param symbol
         * @param occurrence
         */
        public Node(Character symbol, int occurrence){
            this.symbol = symbol;
            this.occurrence = occurrence;
            left = null;
            right = null;
        }

        /**
         * builds a parent node from the given child nodes
         * @param left
         * @param right
         */
        public Node(Node left, Node right){
            this.left = left;
            this.right = right;
            this.occurrence = left.occurrence + right.occurrence;
            symbol = Character.MAX_VALUE;
        }
        @Override
        public int compareTo(Node o) {
            return Integer.compare(occurrence, o.occurrence);
        }
    }
}