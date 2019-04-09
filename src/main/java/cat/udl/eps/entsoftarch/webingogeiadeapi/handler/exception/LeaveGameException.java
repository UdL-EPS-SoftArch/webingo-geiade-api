package cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LeaveGameException extends RuntimeException{
    public LeaveGameException(String message) { super(message);}
}
