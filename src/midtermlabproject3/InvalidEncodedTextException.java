package midtermlabproject3;

/**
 * Exception class for handling invalid encoded text in Huffman encoding/decoding.
 * @author Jim Hendrix Bag-eo
 */
public class InvalidEncodedTextException extends RuntimeException {
    /**
     * Constructor to create an exception with a specific message.
     *
     * @param message the detail message of the exception
     */
    public InvalidEncodedTextException(String message) {
        super(message);
    }
}