package pos.lab.OrderModule.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String id) {
        super(String.valueOf(id));
    }
}
