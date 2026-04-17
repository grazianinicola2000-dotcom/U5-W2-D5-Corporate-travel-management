package nicolagraziani.U5_W2_D5.Corporate.travel.management.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
