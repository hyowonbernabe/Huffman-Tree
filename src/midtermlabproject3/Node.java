package midtermlabproject3;

/**
 * Huffman Node Class representing each node in the Huffman tree.
 * Each node contains a character, its frequency, and references to its left and right child nodes.
 *
 * @author Hyowon Arzil Bernabe
 */
public class Node implements Comparable<Node> {
    private char data;
    private int frequency;
    private Node leftNode, rightNode;

    /**
     * Constructor for leaf nodes (nodes with a character).
     *
     * @param data the character stored in this node
     * @param frequency the frequency of the character
     */
    public Node(char data, int frequency) {
        this.frequency = frequency;
        this.data = data;
        this.leftNode = null;
        this.rightNode = null;
    }

    /**
     * Constructor for internal nodes (nodes without a character, but with left and right children).
     *
     * @param frequency the frequency of this internal node
     * @param leftNode the left child node
     * @param rightNode the right child node
     */
    public Node(int frequency, Node leftNode, Node rightNode) {
        this.data = '\0'; // Null character for internal nodes
        this.frequency = frequency;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    /**
     * Gets the character stored in this node.
     *
     * @return the character stored in this node
     */
    public char getData() {
        return data;
    }

    /**
     * Gets the frequency of this node.
     *
     * @return the frequency of this node
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Gets the left child node.
     *
     * @return the left child node
     */
    public Node getLeftNode() {
        return leftNode;
    }

    /**
     * Gets the right child node.
     *
     * @return the right child node
     */
    public Node getRightNode() {
        return rightNode;
    }

    /**
     * Sets the character for this node.
     *
     * @param data the character to be set in this node
     */
    public void setData(char data) {
        this.data = data;
    }

    /**
     * Sets the frequency for this node.
     *
     * @param frequency the frequency to be set in this node
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * Sets the left child node.
     *
     * @param leftNode the left child node to be set
     */
    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    /**
     * Sets the right child node.
     *
     * @param rightNode the right child node to be set
     */
    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    /**
     * Compares this node's frequency with another node's frequency.
     *
     * @param node the node to compare with
     * @return a negative integer, zero, or a positive integer as this node's frequency is less than, equal to, or greater than the specified node's frequency
     */
    @Override
    public int compareTo(Node node) {
        return Integer.compare(frequency, node.getFrequency());
    }

    /**
     * Checks if this node is a leaf node (i.e., it has no children).
     *
     * @return true if this node is a leaf, false otherwise
     */
    public boolean isLeaf() {
        return this.leftNode == null && this.rightNode == null;
    }
}