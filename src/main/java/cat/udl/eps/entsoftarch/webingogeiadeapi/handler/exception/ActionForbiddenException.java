package cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ActionForbiddenException extends RuntimeException{
    public ActionForbiddenException(String message) { super(message);}
}
