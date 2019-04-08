package cat.udl.eps.entsoftarch.webingogeiadeapi.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE)
public class DepositMoneyException extends RuntimeException{

    public DepositMoneyException(String message){ super(message); }
}