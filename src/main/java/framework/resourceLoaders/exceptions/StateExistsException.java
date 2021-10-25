package framework.resourceLoaders.exceptions;

public class StateExistsException extends RuntimeException{
    public StateExistsException(String message) {
        super(message);
    }
}
