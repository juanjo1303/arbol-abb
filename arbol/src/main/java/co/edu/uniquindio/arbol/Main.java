package co.edu.uniquindio.arbol;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);
        tree.insert(7);
        tree.insert(12);
        tree.insert(18);

        System.out.println("===================================================================");
        System.out.println(tree.isEmpty() ? "The tree is empty." : "The tree is not empty.");
        System.out.println("===================================================================");
        System.out.println("In-order: " + tree.getInOrder());
        System.out.println("===================================================================");
        System.out.println("Pre-order: " + tree.getPreOrder());
        System.out.println("===================================================================");
        System.out.println("Post-order: " + tree.getPostOrder());
        System.out.println("===================================================================");
        System.out.println("Size: " + tree.getSize());
        System.out.println("===================================================================");
        System.out.println("Height: " + tree.getHeight());
        System.out.println("===================================================================");
        System.out.println("Level: " + tree.getLevel(7));
        System.out.println("===================================================================");
        System.out.println("Number of leaves: " + tree.countLeaves());
        System.out.println("===================================================================");
        tree.printAmplitude();
        System.out.println("===================================================================");
        tree.delete(15);
        System.out.println("In-order after deletion: " + tree.getInOrder());
        System.out.println("===================================================================");
        System.out.println("Post-order after deletion: " + tree.getPostOrder());
        System.out.println("===================================================================");
        System.out.println("Pre-order after deletion: " + tree.getPreOrder());
        System.out.println("===================================================================");
        System.out.println("Size after deletion: " + tree.getSize());
        System.out.println("===================================================================");
        System.out.println("Major node: " + tree.getMajorNode());
        System.out.println("===================================================================");
        System.out.println("Minor node: " + tree.getMinorNode());
        System.out.println("===================================================================");
        tree.clearTree();
        System.out.println("===================================================================");
    }


}
