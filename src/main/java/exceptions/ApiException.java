package exceptions;

/**
 * Created by Guest on 8/23/17.
 */
public class ApiException extends RuntimeException{

    private final int statusCode;

    public ApiException (int statusCode, String msg){
        super(msg); //see explanation below
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
