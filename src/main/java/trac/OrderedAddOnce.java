////package trac;
//
//import java.util.Iterator;
//import java.util.Stack;
//
//
//public class OrderedAddOnce<T extends Comparable<? super T>> implements Iterable<T>, AddOnce<T> {
//    private Node root; // Root of the BST
//    private int size;  // Track the number of nodes in the BST
//
//    // Node class for the binary search tree
//    private class Node {
//        T value;
//        Node left, right;
//
//        Node(T value) {
//            this.value = value;
//            this.left = this.right = null;
//        }
//    }
//
//    // Default constructor
//    public OrderedAddOnce() {
//        root = null;
//        size = 0; // Initialize size to 0
//    }
//
//    // AddOnce method that inserts item in sorted order and avoids duplicates
//    
//    @Override
//    public T addOnce(T newItem) {
//    if (root == null) {
//        root = new Node(newItem);
//        size++;
//        return newItem;  // Return the item added
//    } else {
//        return addOnceRecursive(root, newItem);
//    }
//}
//
//// Update the return value in addOnceRecursive:
//private T addOnceRecursive(Node node, T newItem) {
//    int comparison = newItem.compareTo(node.value);
//    if (comparison < 0) {
//        if (node.left == null) {
//            node.left = new Node(newItem);
//            size++;
//            return newItem;  // Return the item added
//        } else {
//            return addOnceRecursive(node.left, newItem);
//        }
//    } else if (comparison > 0) {
//        if (node.right == null) {
//            node.right = new Node(newItem);
//            size++;
//            return newItem;  // Return the item added
//        } else {
//            return addOnceRecursive(node.right, newItem);
//        }
//    }
//    return node.value;  // Return the existing item
//}
//
//    public int size() {
//    return size;
//}
//    
//    @Override
//    public Iterator<T> iterator() {
//        return new InOrderIterator();
//    }
//
//    // In-order iterator for the BST
//    private class InOrderIterator implements Iterator<T> {
//        private Stack<Node> stack = new Stack<>();
//
//        public InOrderIterator() {
//            pushLeft(root);
//        }
//
//        private void pushLeft(Node node) {
//            while (node != null) {
//                stack.push(node);
//                node = node.left;
//            }
//        }
//
//        @Override
//        public boolean hasNext() {
//            return !stack.isEmpty();
//        }
//
//        @Override
//        public T next() {
//            Node node = stack.pop();
//            pushLeft(node.right);
//            return node.value;
//        }
//    }
//}
