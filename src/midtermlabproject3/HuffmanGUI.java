package midtermlabproject3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Map;

/**
 * GUI class to display the Huffman encoding tree and the frequency table with corresponding Huffman codes.
 *
 * This class creates a graphical user interface using `JTree` for displaying the Huffman Tree and `JTable`
 * for displaying the frequency of characters and their Huffman codes.
 * The GUI is split into two sections: one for the Huffman tree and the other for the frequency table.
 *
 * @author Hyowon Arzil Bernabe
 */
public class HuffmanGUI {
    private JFrame frame;  // Main window of the GUI
    private JTree tree;    // JTree for displaying the Huffman Tree
    private JTable table;  // JTable for displaying the frequency table and Huffman codes

    /**
     * Constructor for initializing the GUI with the Huffman tree root, frequency table, and Huffman codes.
     *
     * @param huffmanRoot the root node of the Huffman tree
     * @param frequencyTable a map containing the frequency of each character in the input text
     * @param huffmanCodes a map containing the Huffman codes for each character
     */
    public HuffmanGUI(Node huffmanRoot, Map<Character, Integer> frequencyTable, Map<Character, String> huffmanCodes) {
        frame = new JFrame("Huffman Encoding GUI");
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Initialize the JTree for the Huffman Tree
        DefaultMutableTreeNode rootNode = buildTree(huffmanRoot);
        tree = new JTree(rootNode);
        expandAllNodes(tree);  // Expand all nodes in the JTree for full visibility
        JScrollPane treeScrollPane = new JScrollPane(tree);

        // Initialize the JTable for the Frequency Table and Huffman Codes
        table = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Character", "Frequency", "Huffman Code"}, 0);
        table.setModel(tableModel);
        fillTableModel(tableModel, frequencyTable, huffmanCodes);  // Populate the table
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Add the tree and table to a split pane for a two-panel layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, treeScrollPane, tableScrollPane);
        splitPane.setDividerLocation(200);
        frame.add(splitPane, BorderLayout.CENTER);
    }

    /**
     * Displays the GUI frame on the screen.
     * The frame will be centered on the screen and always remain on top.
     */
    public void show() {
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);  // Center the window
        frame.setAlwaysOnTop(true);         // Keep the window always on top
    }

    /**
     * Recursively builds the Huffman tree for display in the `JTree`.
     * This method creates tree nodes for each internal node and leaf node in the Huffman tree.
     *
     * @param node the current node in the Huffman tree
     * @return a `DefaultMutableTreeNode` representing the current node
     */
    private DefaultMutableTreeNode buildTree(Node node) {
        if (node == null) {
            return null;
        }

        DefaultMutableTreeNode treeNode;
        if (node.isLeaf()) {
            // Leaf node displays both the frequency and the character
            treeNode = new DefaultMutableTreeNode(node.getFrequency() + " (" + node.getData() + ")");
        } else {
            // Internal node displays only the frequency
            treeNode = new DefaultMutableTreeNode(node.getFrequency());
        }

        // Recursively add left and right child nodes if they exist
        if (node.getLeftNode() != null) {
            treeNode.add(buildTree(node.getLeftNode()));
        }
        if (node.getRightNode() != null) {
            treeNode.add(buildTree(node.getRightNode()));
        }

        return treeNode;
    }

    /**
     * Fills the `JTable` model with the frequency of each character and its corresponding Huffman code.
     *
     * @param tableModel the table model for the `JTable` that holds the data
     * @param frequencyTable a map containing the frequency of each character in the input text
     * @param huffmanCodes a map containing the Huffman codes for each character
     */
    private void fillTableModel(DefaultTableModel tableModel, Map<Character, Integer> frequencyTable, Map<Character, String> huffmanCodes) {
        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            Character character = entry.getKey();
            Integer frequency = entry.getValue();
            String code = huffmanCodes.get(character);
            tableModel.addRow(new Object[]{character, frequency, code});
        }
    }

    /**
     * Expands all the nodes in the `JTree` to make the full tree structure visible.
     * This method ensures that no nodes are collapsed in the tree view.
     *
     * @param tree the `JTree` component to expand
     */
    private void expandAllNodes(JTree tree) {
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
    }
}