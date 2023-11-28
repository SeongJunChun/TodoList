package sw_semester.todolist.loginpackage.exception;

import java.util.Map;

public class UserRequestException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private Map<String, String> errorMap;
    public UserRequestException(String message, Map<String, String> errorMap){
        super(message);
        this.errorMap = errorMap;
    }
    public Map<String, String> getErrorMap(){
        return errorMap;
    }
}
