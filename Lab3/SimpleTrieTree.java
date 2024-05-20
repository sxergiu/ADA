package ADA.Lab3;

import java.util.LinkedList;

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

    ///written by me

    public boolean contains(String key) {

        return contains(root,key,"");
    }

    private boolean contains(TrieNode x,String key, String prefix) {

        if( x==null ) return false;

        if( x.val != null ) {
            if( prefix.equals(key) )
                return true;
        }

        for( int i=0; i < R; i++ ) {

            if( x.next[i] != null ) {
                return contains(x.next[i],key, prefix + (char) i );
            }
        }

        return false;
    }

    public void printAllKeys() {

        StringBuilder sb = new StringBuilder();
        printAllKeys(root,"",sb);
        System.out.println(sb.toString());
    }

    private void printAllKeys(TrieNode x,String prefix,StringBuilder sb) {

        if( x==null ) return;

        if( x.val != null ) {
            sb.append(prefix).append("\n");
        }

        for(int i=0; i<R; i++) {

            if( x.next[i] != null )
                printAllKeys(x.next[i],prefix + (char) i,sb);
        }
    }

    public void printAllWithPrefix(String prefix) {

        StringBuilder sb = new StringBuilder();
        printAllWithPrefix(root,"",prefix,sb);

        System.out.println(sb.toString());
    }

    private void printAllWithPrefix(TrieNode x,String crtPrefix, String prefix, StringBuilder sb) {

        if( x==null ) return;

        if( x.val != null && crtPrefix.startsWith(prefix) ){  sb.append(crtPrefix).append("\n"); }

        for( int i=0; i<R; i++ ) {

            if( x.next[i] != null ) {

                printAllWithPrefix(x.next[i],crtPrefix + (char) i,prefix,sb);
            }
        }
    }

    public LinkedList<String> GetAllKeys() {

        LinkedList<String> keys = new LinkedList<String>();
        GetAllKeys(root,keys,"");

        System.out.println(keys.toString());
        return keys;
    }

    private void GetAllKeys(TrieNode x, LinkedList<String> keys, String prefix) {

        if( x==null ) return;

        if( x.val != null ) {
          keys.add(prefix);
        }

        for( int i=0; i<R; i++) {

            if( x.next[i] != null ) {
                GetAllKeys(x.next[i], keys, prefix + (char) i);
            }
        }
    }

    ///written by me

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

        System.out.println(st.contains("banana"));  //true
         System.out.println(st.contains("blabla"));  //false
         System.out.println(st.contains("ban"));     //true
         System.out.println(st.contains("ba"));      //false

        LinkedList<String> keys = st.GetAllKeys();
    }
}

