package midtermlabproject3;

import java.util.*;

/**
 * This class provides methods for encoding and decoding text using the Huffman encoding algorithm.
 * It includes functionality for building frequency tables, generating Huffman codes, and calculating encoding efficiency.
 * */
public class HuffmanEncoder {

    /**
     * Method to build the table with frequencies from the given text.
     *
     * @param text the input string to analyze
     * @return a map containing characters as keys and their frequencies as values
     * @author Audrey Matulay
     * */
    public Map<Character, Integer> buildFrequencyTable(String text) {
        Map<Character, Integer> frequencyTable = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyTable.put(c, frequencyTable.getOrDefault(c, 0) + 1);
        }
        return frequencyTable;
    }

    /**
     * Builds a custom frequency table by prompting the user for character frequencies.
     *
     * @return a map containing characters as keys and their manually inputted frequencies as values
     * @author Hyowon Arzil Bernabe
     * */
    public Map<Character, Integer> buildCustomFrequencyTable() {
        Scanner scanner = new Scanner(System.in);
        Map<Character, Integer> frequencyTable = new HashMap<>();

        boolean continueAdding = true;

        while (continueAdding) {
            // Prompt for character
            System.out.print("\nWhat char: ");
            char character = scanner.next().charAt(0);

            // Prompt for frequency
            System.out.print("What frequency: ");
            int frequency = scanner.nextInt();

            // Add or update the frequency in the map
            frequencyTable.put(character, frequencyTable.getOrDefault(character, 0) + frequency);

            // Ask if the user wants to add more data
            System.out.print("Do you want to add more? (y/n): ");
            String response = scanner.next();

            // Check if the user wants to continue adding data
            if (!response.equalsIgnoreCase("y")) {
                continueAdding = false;
            }
        }

        return frequencyTable;
    }

    /**
     * Method to build the Huffman Tree using the frequency table.
     *
     * @param frequencyTable map of characters and their respective frequencies
     * @return the root node of the Huffman tree
     * @author Jim Hendrix Bag-eo
     * */
    public Node buildHuffmanTree(Map<Character, Integer> frequencyTable) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(Node::getFrequency));

        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet())
            queue.add(new Node(entry.getKey(), entry.getValue()));

        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            assert right != null;
            Node parent = new Node(left.getFrequency() + right.getFrequency(), left, right);
            queue.add(parent);
        }

        return queue.poll();
    }

    /**
     * Method that traverses the Huffman Tree to make the binary code for each character (Makes use of pre-order traversal).
     *
     * @param node the current node in the Huffman tree
     * @param code the current Huffman code being generated
     * @param huffmanCode map to store the generated Huffman codes for each character
     * @author Xylon Saipen
     * */
    public void buildHuffmanCode(Node node, String code, Map<Character, String> huffmanCode) {
        if (node.isLeaf()){
            huffmanCode.put(node.getData(), code);
            return;
        }

        if (node.getLeftNode() != null){
            buildHuffmanCode(node.getLeftNode(), code + "0", huffmanCode);
        }

        if (node.getRightNode() != null){
            buildHuffmanCode(node.getRightNode(), code + "1", huffmanCode);
        }
    }

    /**
     * Method that calculates the efficiency of the Huffman Code compared to the original size.
     *
     * @param text the input text to analyze
     * @param encodingBits the number of bits used for fixed-length encoding (e.g., 7 for ASCII, 8 for EBCDIC)
     * @return a string representing the efficiency percentage
     * @author Joeffrey Edrian Carani
     * */
    public String calculateEfficiency(String text, Map<Character, String> huffmanCode, int encodingBits) {
        int originalSize = text.length() * encodingBits;
        int compressedSize = 0;

        Map<Character, Integer> frequencyTable = buildFrequencyTable(text);

        for (Map.Entry<Character, String> e : huffmanCode.entrySet()) { // Iterates through map and getting value pair's key and value
            char character = e.getKey();
            String code = e.getValue();

            compressedSize += frequencyTable.get(character) * code.length();
        }

        double efficiency = (double)(originalSize - compressedSize) / originalSize * 100; // Calculates for the percentage of storage savings

        return String.format("Efficiency: %.2f%%", efficiency);
    }

    /**
     * Method to encode a given text using the generated Huffman Code.
     *
     * @param text the input text to encode
     * @return the Huffman-encoded string
     * @throws InvalidEncodedTextException if the text contains invalid characters not found in the Huffman code
     * @author Hyowon Arzil Bernabe
     * */
    public String encode(String text, Map<Character, String> huffmanCode) throws InvalidEncodedTextException {
        StringBuilder encodedString = new StringBuilder();
        for (char data : text.toCharArray()) {

            String code = huffmanCode.get(data);
            if (code == null)
                throw new InvalidDecodedTextException("Invalid Action. Some character/s in the text doesn't match any of the provided character codes.");

            encodedString.append(huffmanCode.get(data));
        }
        return encodedString.toString();
    }

    /**
     * Method to decode a given text using the generated Huffman Code.
     *
     * @param root the root node used to base the current node
     * @param text the text to be decoded
     * @return string representing the decoded text
     * @throws InvalidEncodedTextException if the encoded text is invalid 
     * @author Aaron Miguel Cardenas
     * */
    public String decode(String text, Node root) throws InvalidEncodedTextException {
        StringBuilder decodedString = new StringBuilder();
        Node currentNode = root;
        for (char bit : text.toCharArray()) {
            if (bit != '0' && bit != '1')
                throw new InvalidEncodedTextException("Invalid Action. The decoded text contains invalid code/s.");

            currentNode = (bit == '0') ? currentNode.getLeftNode() : currentNode.getRightNode();
            if (currentNode.isLeaf()) {
                decodedString.append(currentNode.getData());
                currentNode = root;
            }
        }

        if (!text.isEmpty() && decodedString.isEmpty())
            throw new InvalidEncodedTextException("Invalid action. The encoded text doesn't match any of the codes. ");

        return decodedString.toString();
    }
}