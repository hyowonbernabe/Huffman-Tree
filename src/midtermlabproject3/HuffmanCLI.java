package midtermlabproject3;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Command-line interface for creating a Huffman tree, encoding/decoding text, and displaying efficiency.
 * This program allows users to upload a file, manually input text, or use a predefined frequency table to create a Huffman encoding.
 * It also provides options to encode or decode messages using an existing Huffman tree.
 */
public class HuffmanCLI {

    public static void main(String[] args) {
        HuffmanEncoder huffman = new HuffmanEncoder();
        Scanner scanner = new Scanner(System.in);
        String text = null;

        while (true) {
            System.out.print("""
                    CREATE HUFFMAN TREE
                    1. Upload a file with text.
                    2. Manually write text.
                    3. Manually fill frequency table.
                    4. Use existing default huffman tree.
                    5. Exit.
                    
                    Choice:\s""");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) { // 1. Upload a file with text.
                System.out.println("\nThere are existing example files in the project folder for testing purposes inside the 'res' folder.");
                System.out.println("These files range from file 1 - 6. Example: res/file1.txt\n");
                System.out.print("Enter the file path: ");
                String filePath = scanner.nextLine();
                text = readTextFromFile(filePath);
                if (text.isEmpty()) {
                    System.out.println("\nFailed to read file or file is empty.\n");
                    continue;
                }
            } else if (choice == 2) { // 2. Manually write text.
                System.out.print("\nEnter the text: ");
                text = scanner.nextLine();
            } else if (choice == 3) { // 3. Manually fill frequency table.
                huffmanCustomFrequencyTableCreation(huffman);
                continue;
            } else if (choice == 4) { // 4. Use existing default huffman tree.
                text = readTextFromFile("res/file6.txt");
                huffmanCreation(huffman, text);
                continue;
            } else if (choice == 5) { // 5. Exit.
                System.out.println("\nExiting...");
                System.exit(0);
            } else{
                System.out.println("\nInvalid choice. Please try again.\n");
                continue;
            }

            huffmanCreation(huffman, text);
        }
    }

    /**
     * Method that reads from a file and extracts the text.
     *
     * @param filePath the path of the file to read
     * @return the content of the file as a string, or an empty string if an error occurs
     * @author Franz Carlo Angelo
     */
    public static String readTextFromFile(String filePath) {
        StringBuilder text = new StringBuilder();

        try {
            // Read all lines from the file and append them to the StringBuilder
            Files.lines(Paths.get(filePath)).forEach(line -> text.append(line).append("\n"));
        } catch (IOException e) {
            // Print error message if reading fails
            System.out.println("Error reading file: " + e.getMessage());
            return "";
        }

        return text.toString().trim(); // Trim to remove any trailing newline
    }

    /**
     * Method that creates the frequency table, Huffman tree, Huffman codes, and calculates the efficiency.
     *
     * @param huffman the Huffman object for encoding/decoding
     * @param text the input text used for creating the Huffman encoding
     * @author Hyowon Arzil Bernabe
     */
    public static void huffmanCreation(HuffmanEncoder huffman, String text) {
        Map<Character, Integer> frequencyTable = huffman.buildFrequencyTable(text);
        Node root = huffman.buildHuffmanTree(frequencyTable);

        Map<Character, String> huffmanCode = new HashMap<>();
        huffman.buildHuffmanCode(root, "", huffmanCode);

        System.out.println("\nEncoded Text: \n" + huffman.encode(text, huffmanCode));

        HuffmanGUI gui = new HuffmanGUI(root, frequencyTable, huffmanCode);
        gui.show();  // Show GUI with JTree and JTable

        displayTable(frequencyTable, huffmanCode);

        displayHuffmanEfficiency(huffman, text, huffmanCode);

        encodeOrDecode(huffman, huffmanCode, root);
    }

    /**
     * Method for creating a custom frequency table for the Huffman encoding.
     *
     * @param huffman the Huffman object for encoding/decoding
     * @author Hyowon Arzil Bernabe
     */
    public static void huffmanCustomFrequencyTableCreation(HuffmanEncoder huffman) {
        Map<Character, Integer> frequencyTable = huffman.buildCustomFrequencyTable();
        Node root = huffman.buildHuffmanTree(frequencyTable);

        Map<Character, String> huffmanCode = new HashMap<>();
        huffman.buildHuffmanCode(root, "", huffmanCode);

        HuffmanGUI gui = new HuffmanGUI(root, frequencyTable, huffmanCode);
        SwingUtilities.invokeLater(() -> {
            gui.show();
        });

        displayTable(frequencyTable, huffmanCode);

        encodeOrDecode(huffman, huffmanCode, root);
    }

    /**
     * Method to display the Huffman encoding efficiency for different encoding standards.
     *
     * @param huffman the Huffman object for encoding/decoding
     * @param text the input text used for efficiency calculation
     * @param huffmanCode the map of characters to Huffman codes
     * @author Hyowon Arzil Bernabe
     */
    private static void displayHuffmanEfficiency(HuffmanEncoder huffman, String text, Map<Character, String> huffmanCode) {
        System.out.println();
        System.out.println("Entire Huffman Efficiency");
        System.out.println("ASCII " + huffman.calculateEfficiency(text, huffmanCode, 7));
        System.out.println("EBCDIC " + huffman.calculateEfficiency(text, huffmanCode, 8));
        System.out.println("Unicode " + huffman.calculateEfficiency(text, huffmanCode, 16));
        System.out.println();
    }

    /**
     * Method that displays a table consisting of the character, frequency, and its binary code.
     *
     * @param frequencyTable map containing the frequency of each character in the text
     * @param huffmanCode map containing the Huffman codes for each character
     * @author Hyowon Arzil Bernabe
     */
    private static void displayTable(Map<Character, Integer> frequencyTable, Map<Character, String> huffmanCode) {
        System.out.println("\nHuffman Encoding Table:");
        System.out.printf("%-15s%-15s%-15s%n", "Character", "Frequency", "Huffman Code");

        for (Map.Entry<Character, Integer> entry : frequencyTable.entrySet()) {
            String huffmanCodeString = huffmanCode.get(entry.getKey());

            System.out.printf("%-15s%-15d%-15s%n", entry.getKey(), entry.getValue(), huffmanCodeString);
        }
    }

    /**
     * Method that gives the user a menu to ask if they want to encode or decode a message.
     *
     * @param huffman the Huffman object for encoding/decoding
     * @param huffmanCode the map of characters to Huffman codes
     * @param root the root node of the Huffman tree, used for decoding messages
     * @author Hyowon Arzil Bernabe
     */
    public static void encodeOrDecode(HuffmanEncoder huffman, Map<Character, String> huffmanCode, Node root) {
        Scanner scanner = new Scanner(System.in);
        String text;

        while (true) {
            System.out.print(""" 
                    FROM THE EXISTING HUFFMAN CODE:
                    1. Encode a message.
                    2. Decode a message.
                    3. Back to main menu.
                    4. Exit
                     
                    Choice:\s""");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                if (choice == 1) { // 1. Encode a message.
                    System.out.print("\nEnter the text to encode: ");
                    text = scanner.nextLine();
                    System.out.println("\nEncoded Text: \n" + huffman.encode(text, huffmanCode) + "\n");
                    System.out.println();
                } else if (choice == 2) { // 2. Decode a message.
                    System.out.print("\nEnter the text to decode: ");
                    text = scanner.nextLine();
                    System.out.println("\nDecoded Text: \n" + huffman.decode(text, root) + "\n");
                } else if (choice == 3) { // 3. Back to main Menu.
                    break;
                } else if (choice == 4) { // 4. Exit.
                    System.out.println("\nExiting...");
                    System.exit(0);
                } else {
                    System.out.println("\nInvalid choice. Please try again.\n");
                }
            } catch (InvalidEncodedTextException e) {
                System.out.println(e.getMessage() + "\n");
            }
        }
    }
}