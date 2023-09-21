package telran.java48.accounting.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserConflictException extends RuntimeException {

	private static final long serialVersionUID = 3124329591970799884L;

}
