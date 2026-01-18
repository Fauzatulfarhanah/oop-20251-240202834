package main.java.com.upb.agripos;

public class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}
