package telran.java48.accounting.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserRoleException extends RuntimeException{

	private static final long serialVersionUID = 7478056159569586334L;

}
