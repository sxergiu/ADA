package ADA.Lab2;

class AVLNode {
    Integer key;      // sorted by key
    Integer val;      // associated data
    AVLNode left;
    AVLNode right;    // left and right subtrees
    int height;       // height of subtree rooted in this node

    public AVLNode(Integer key, Integer val, int height) {
        this.key = key;
        this.val = val;
        this.height = height;
        this.left = null;
        this.right = null;
    }
}
