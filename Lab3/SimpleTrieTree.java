package ADA.Lab3;

public class SimpleTrieTree {
    private final int R = 256; // R - size of alphabet.
    // in this example keys(words) are sequences of characters from extended ASCII

    private TrieNode root;      // root of trie tree

    // R-way trie node
    private class TrieNode {
        private Integer val;  // if current node contains end of a key(end of a word)
        // then it has a non-null val associated
        private final TrieNode[] next = new TrieNode[R]; // may have R children
    }

    /**
     * Initializes an empty trie tree
     */
    public SimpleTrieTree() {
        root = null;
    }


    /**
     * Inserts a word into the trie tree - the recursive implementation
     *
     * @param key the word to be inserted
     * @param val the value associated with the word
     */
    public void put(String key, Integer val) {
        if ((key == null) || (val == null)) throw new IllegalArgumentException("key or val argument is null");
        else root = put(root, key, val);
    }

    private TrieNode put(TrieNode x, String key, Integer val) {
        if (x == null) x = new TrieNode();
        if (key.equals("")) {
            x.val = val;
            return x;
        }
        char c = key.charAt(0);
        String restkey = "";
        if (key.length() > 1) restkey = key.substring(1);
        x.next[c] = put(x.next[c], restkey, val);
        return x;
    }

    /**
     * Inserts a word into the trie tree - the iterative version
     *
     * @param key the word to be inserted
     * @param val the value associated with the word
     */
    public void put_v2(String key, Integer val) {
        if (root == null) {
            root = new TrieNode();
        }
        TrieNode node = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (node.next[c] == null) {
                node.next[c] = new TrieNode();
            }
            node = node.next[c];
        }
        node.val = val;
    }


    public boolean contains(String key) {
        System.out.println("operation contains not yet implemented!");
        return false;
    }

    public void printAllKeys() {
        System.out.println("operation printAllKeys not yet implemented!");
    }

    public void printAllWithPrefix(String prefix) {
        System.out.println("operation printAllWithPrefix not yet implemented!");
    }

    public static void main(String[] args) {

        String[] inputs = {"bar", "sea", "sunday", "bark", "ban", "bandage", "sun", "banana", "band"};

        SimpleTrieTree st = new SimpleTrieTree();

        for (int i = 0; i < inputs.length; i++) {
            String key = inputs[i];
            st.put(key, i);
            System.out.println("Have inserted into trie tree key="+key+" with value="+i);
        }

        System.out.println("All  keys in ascending order:");
        st.printAllKeys();

        String testprefix = "ban";
        System.out.println("keysWithPrefix " + testprefix);
        st.printAllWithPrefix(testprefix);   //Keys with prefix ban: ban banana band bandage

        // System.out.println(st.contains("banana"));  //true
        // System.out.println(st.contains("blabla"));  //false
        // System.out.println(st.contains("ban"));     //true
        // System.out.println(st.contains("ba"));      //false
    }
}

