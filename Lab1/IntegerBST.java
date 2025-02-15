package ADA.Lab1;
import org.bouncycastle.asn1.cmp.Challenge;

import java.util.NoSuchElementException;
import java.util.Random;

public class IntegerBST {

        private Node root;             // root of BST

        public IntegerBST() {
            root = null;        // initializes empty BST
        }


        /**
         * Print keys in ascending order by
         * doing an inorder tree walk
         */
        public void inorder() {
            inorder(root);
        }
        private void inorder(Node x) {
            if (x == null) return;
            inorder(x.left);
            System.out.print(" "+x.key);
            inorder(x.right);
        }

        ///written by me
        private void preorder(Node x) {
            if( x==null ) return;
            System.out.print(" "+ "key: " + x.key+" value: " +x.val);
            preorder(x.left);
            preorder(x.right);
        }

        public void preorder() {
            preorder(root);
        }

        public void postorder() { postorder(root); }

        private void postorder(Node x) {
            if( x==null ) return;
            postorder(x.left);
            postorder(x.right);
            System.out.print(" " + x.key);
        }

        public int height() {
            return height(root);
        }

        private int height(Node x) {
            if ( x==null ) return 0;
            else return 1+Math.max( height(x.left), height(x.right) );
        }

        public void SpoilValues(){
            SpoilValues(root);
        }

        private void SpoilValues(Node x) {

            if( x==null ) return;
            else {

                x.val = new Random().nextInt()%99;
                SpoilValues(x.left);
                SpoilValues(x.right);
            }
        }

        public boolean isBST() {
            return isBST(root);
        }

        private boolean isBST(Node x) {

            if( x==null ) return true;
            else {

                if( x.left != null  &&  x.key < x.left.key )
                    return false;

                if( x.right != null &&  x.key > x.right.key )
                    return false;

                return isBST(x.left) && isBST(x.right);
            }
        }

        public Node Succesor(int key) {

            Node succesor = new Node(0,0);
            Succesor(root, key,succesor);
            return succesor;
        }

        private void Succesor(Node x, int key,Node suc) {

            if( x == null ) return;

            if( x.key == key ) {

                suc = min(x.right);
                return;
            }

            if( x.key > key ) {
                suc = x;
                Succesor(x.left,key,suc);
            }
            else {
                Succesor(x.right,key,suc);
            }
        }

        public Node Predecessor(int key) {

            Node predecessor = new Node(0,0);
            Predecessor(root,key,predecessor);
            return predecessor;
        }

        private void Predecessor(Node x,int key, Node pre) {

            if( x== null) return;

            if( x.key == key) {
                pre = max(x.left);
                return;
            }

            if( x.key < key ) {
                pre = x;
                Predecessor(x.right,key,pre);
            }
            else {
                Predecessor(x.left,key,pre);
            }
        }

        public boolean isPerfectlyBalanced() {
            return isPerfectlyBalanced(root);
        }

        private boolean isPerfectlyBalanced(Node x) {
                if( x==null ) return true;
                else {
                    return Math.abs( height(x.left) - height(x.right) ) <= 1;
                }
        }

        public Node rotateLeft(int key) {

            Node rot =
             rotateLeft(root,key);

            return rot;
        }

        private Node rotateLeft(Node x,int key) {

            if( x==null) return null;

            if( x.key == key ) {

                System.out.println("rotating left at key= " + x.key );

                if( x.right == null )
                    return x;

                Node y = x.right;
                x.right = y.left;
                y.left = x;
                return y;
            }
            else if ( x.key > key ) x.left = rotateLeft(x.left,key);
            else x.right = rotateLeft(x.right,key);

            return x;
        }

        public Node rotateRight(int key) {
                return rotateRight(root,key);
        }

        private Node rotateRight(Node x,int key) {

            if( x==null ) return null;

            if(x.key > key)
                x.left = rotateRight(x.left,key);
            if(x.key > key)
                x.right = rotateRight(x.right,key);
            else {

                System.out.println("Rotating right at key= " + x.key);
                Node y = x.left;
                x.left = y.right;
                y.right = x;

                return y;
            }

            return x;
        }

        ///written by me
        /**
         * Searches for a given key in the BST
         * returns true if the key is contained in the BST and false otherwise
         */
        public boolean contains(Integer key) {
            return contains(root, key);
        }
        private boolean contains(Node x, Integer key) {
            if (x == null) return false;
            if (key < x.key) return contains(x.left, key);
            else if (key > x.key) return contains(x.right, key);
            else return true;
        }



        /**
         * Inserts the specified key-value pair, overwriting the old
         * value in the node with the new value if the BST already contains the key.
         */
        public void put(Integer key, Integer val) {
            if (key == null) throw new IllegalArgumentException("calls put() with a null key");
            root=put(root, key, val);
        }

        private Node put(Node x, Integer key, Integer val) {
            if (x == null) return new Node(key, val);
            if (key < x.key) x.left = put(x.left, key, val);
            else if (key > x.key) x.right = put(x.right, key, val);
            else x.val = val;
            return x;
        }

        /**
         * Returns the smallest key in the BST
         */
        public Integer min() {
            if (root == null) throw new NoSuchElementException("calls min() with empty BST");
            return min(root).key;
        }

        private Node min(Node x) {
            if (x.left == null) return x;
            else return min(x.left);
        }

        /**
         * Returns the largest key in the BST
         */
        public Integer max() {
            if (root == null) throw new NoSuchElementException("calls max() with empty BST");
            return max(root).key;
        }

        private Node max(Node x) {
            if (x.right == null) return x;
            else return max(x.right);
        }

        /**
         * Deletes the node containing the specified key
         */
        public void delete(Integer key) {
            if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
            root = deleteRecursive(root, key);
        }

        private Node deleteRecursive(Node z, Integer key) {
            if (z == null) return null;
            int cmp = key.compareTo(z.key);
            if (cmp < 0) z.left = deleteRecursive(z.left, key);
            else if (cmp > 0) z.right = deleteRecursive(z.right, key);
            else {
                // node z contains the key to be deleted
                if (z.right == null) return z.left;  // case 1: only 1 child left
                if (z.left == null) return z.right;  // case 1: only 1 child right
                //case 2: node z to be deleted has 2 children
                Node y = min(z.right); // find minimum in its right subtree (successor of z)
                z.right = deleteRecursive(z.right, y.key); //delete minimum node - we KNOW it has max 1 child
                z.key = y.key; //replace current key with minimum  key
            }
            return z;
        }

        /**
         * A short program to test IntegerBST
         */
        public static void main(String[] args) {

            int[] a = {20, 30, 15, 1, 7, 9, 29, 11, 12, 4};

            IntegerBST bst = new IntegerBST();

            for (int i = 0; i < a.length; i++) {
                bst.put(a[i], a[i]);
            }
            bst.delete(a[2]);


            System.out.println("inorder: ");
            bst.inorder();

            System.out.println();
            bst.rotateLeft(30);

            System.out.println("inorder: ");
            bst.inorder();


        }
    }

