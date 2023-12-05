package sw_semester.todolist.loginpackage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class UserRequestExceptionHandler {

    @ExceptionHandler(value = {UserRequestException.class})
    public Map<String,String> handleUserRequestException(UserRequestException ex){

        return ex.getErrorMap();
    }
}
