package midtermlabproject3;

/**
 * Exception class for handling invalid decoded text in Huffman encoding/decoding.
 * @author Jim Hendrix Bag-eo
 */
public class InvalidDecodedTextException extends RuntimeException {
    /**
     * Constructor to create an exception with a specific message.
     *
     * @param message the detail message of the exception
     */
    public InvalidDecodedTextException(String message) {
        super(message);
    }
}