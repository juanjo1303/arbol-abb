package co.edu.uniquindio.arbol;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinarySearchTree<T extends Comparable<T>> {
    Node<T> root;
    
    public BinarySearchTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private Node<T> insertRec (Node<T> root, T data) {
        if(root == null) {
            return new Node<>(data);
        }
        if(data.compareTo(root.getData()) < 0) {
            root.setLeft(insertRec(root.getLeft(), data));
        } else if(data.compareTo(root.getData()) > 0) {
            root.setRight(insertRec(root.getRight(), data));
        }
        return root;
    }

    public List<T> getInOrder() {
        List<T> result = new ArrayList<>();
        getInOrderRec(root, result);
        return result;
    }

    private void getInOrderRec(Node<T> node, List<T> result){
        if(node != null){
            getInOrderRec(node.getLeft(), result);
            result.add(node.getData());
            getInOrderRec(node.getRight(), result);
        }
    }

    public List<T> getPreOrder() {
        List<T> result = new ArrayList<>();
        getPreOrderRec(root, result);
        return result;
    }

    private void getPreOrderRec(Node<T> node, List<T> result){
        if(node != null){
            result.add(node.getData());
            getPreOrderRec(node.getLeft(), result);
            getPreOrderRec(node.getRight(), result);
        }
    }

    public List<T> getPostOrder() {
        List<T> result = new ArrayList<>();
        getPostOrderRec(root, result);
        return result;
    }

    private void getPostOrderRec(Node<T> node, List<T> result){
        if(node != null){
            getPostOrderRec(node.getLeft(), result);
            getPostOrderRec(node.getRight(), result);
            result.add(node.getData());
        }
    }

    public boolean exists(T data) {
        return existsRec(root, data);
    }

    private boolean existsRec(Node<T> node, T data) {
        if (node == null) {
            return false;
        }
        int compareResult = data.compareTo(node.getData());
        if (compareResult == 0) {
            return true;
        }
        if (compareResult < 0) {
            return existsRec(node.getLeft(), data);
        }
        return existsRec(node.getRight(), data);    
    }

    public int getSize() {
        return getSizeRec(root);
    }

    private int getSizeRec(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + getSizeRec(node.getLeft()) + getSizeRec(node.getRight());
    }

    public int getHeight() {
        return getHeightRec(root);
    }

    private int getHeightRec(Node<T> node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = getHeightRec(node.getLeft());
        int rightHeight = getHeightRec(node.getRight());
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public int getLevel(T data) {
        return getLevelRec(root, data, 1);
    }

    private int getLevelRec(Node<T> node, T data, int level) {
        if (node == null) {
            return -1;
        }
        int compareResult = data.compareTo(node.getData());
        if (compareResult == 0) {
            return level;
        }
        if(compareResult < 0) {
            return getLevelRec(node.getLeft(), data, level + 1);
        }
        return getLevelRec(node.getRight(), data, level + 1);
    }

    public int countLeaves() {
        return countLeavesRec(root);
    }

    private int countLeavesRec(Node<T> node) {
        if (node == null) {
            return 0;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        }
        return countLeavesRec(node.getLeft()) + countLeavesRec(node.getRight());
    }

    public T findMin() {
        if (root == null) {
            return null;
        }
        return findMin(root).getData();
    }

    private Node<T> findMin(Node<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    public void printAmplitude() {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.add(root);
        System.out.println("Level: ");
        while (!queue.isEmpty()) {
            Node<T> currentNode = queue.poll();
            System.out.print(currentNode.getData() + " ");
            if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
            }
            if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
            }
        }
        System.out.println();
    }

    public void delete(T data) {
        root = deleteRec(root, data);
    }

    private Node<T> deleteRec(Node<T> root, T data){
        if(root == null){
            return null;
        }
        int compareResult = data.compareTo(root.getData());
        if(compareResult < 0){
            root.setLeft(deleteRec(root.getLeft(), data));
        } else if(compareResult > 0){
            root.setRight(deleteRec(root.getRight(), data));
        } else {
            if(root.getLeft() == null && root.getRight() == null){
                return null;
            }
            if(root.getLeft() == null){
                return root.getRight();
            } else if(root.getRight() == null){
                return root.getLeft();
            }
            Node<T> minNode = findMin(root.getRight());
            root.setData(minNode.getData());
            root.setRight(deleteRec(root.getRight(), minNode.getData()));
        }
        return root;
    }

    public Node<T> getMajorNode() {
        if (root == null) {
            return null;
        }
        Node<T> currentNode = root;
        while (currentNode.getRight() != null) {
            currentNode = currentNode.getRight();
        }
        return currentNode;
    }

    public Node<T> getMinorNode() {
        if (root == null) {
            System.out.println("The tree is empty.");
            return null;
        }
        Node<T> currentNode = root;
        while (currentNode.getLeft() != null) {
            currentNode = currentNode.getLeft();
        }
        return currentNode;
    }

    public void clearTree(){
        root = null;
        System.out.println("The tree has been cleared.");
    }
}

