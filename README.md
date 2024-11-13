# Huffman Coding CLI & GUI Application

This project implements Huffman coding, a compression algorithm that creates variable-length codes based on character frequencies in a text. The program provides both a **Command-Line Interface (CLI)** and a **Graphical User Interface (GUI)** to build, visualize, and test Huffman encoding and decoding.

## Features

### CLI Menu Options
1. **Upload a Text File** - Load text from a file and generate Huffman codes based on character frequencies.
2. **Manually Enter Text** - Input text directly through the CLI to create Huffman codes.
3. **Custom Frequency Table** - Define a custom frequency table to create specific Huffman codes.
4. **Use Default Huffman Tree** - Automatically use a predefined file (`res/file6.txt`) to generate Huffman codes.
5. **Encode/Decode Messages** - Interactively encode or decode messages based on the generated Huffman codes.

### GUI Features
- **Huffman Tree Visualization** - The GUI uses `JTree` to display the Huffman Tree, showing nodes, frequencies, and characters.
- **Frequency Table Display** - A `JTable` presents each character’s frequency and its corresponding Huffman code.
- **Efficiency Calculation** - Calculates storage efficiency of the Huffman encoding compared to ASCII, EBCDIC, and Unicode standards.

## Project Structure

- `HuffmanCLI.java` - The main entry point for the CLI. Handles file input, text encoding/decoding, and user interactions.
- `HuffmanEncoder.java` - Contains methods for generating frequency tables, building the Huffman Tree, encoding/decoding text, and calculating efficiency.
- `HuffmanGUI.java` - The GUI component that visualizes the Huffman Tree and frequency table, providing an interactive interface.