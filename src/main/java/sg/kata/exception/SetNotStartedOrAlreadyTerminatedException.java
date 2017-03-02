package sg.kata.exception;

/**
 * Created by nassimus on 12/07/2016.
 */
public class SetNotStartedOrAlreadyTerminatedException extends RuntimeException {
    public SetNotStartedOrAlreadyTerminatedException(String msg){
        super(msg);
    }
}
