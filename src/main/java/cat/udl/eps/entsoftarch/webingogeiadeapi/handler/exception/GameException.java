package cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE)
public class GameException extends RuntimeException{

    public GameException(String message){ super(message); }
}
