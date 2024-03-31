package ADA.Lab1;

class Node {
    Integer key;           // sorted by Key
    Integer val;         // associated data
    Node left, right;  // left and right subtrees

    Node(Integer key, Integer val) {
        this.key = key;
        this.val = val;
    }
}