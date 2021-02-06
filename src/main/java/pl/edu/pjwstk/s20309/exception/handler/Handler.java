package pl.edu.pjwstk.s20309.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.edu.pjwstk.s20309.exception.exist.ClientAlreadyExistException;
import pl.edu.pjwstk.s20309.exception.exist.TaskAlreadyExistException;
import pl.edu.pjwstk.s20309.exception.exist.UserAlreadyExistException;
import pl.edu.pjwstk.s20309.exception.notFound.ClientNotFoundException;
import pl.edu.pjwstk.s20309.exception.notFound.NotEmptyException;
import pl.edu.pjwstk.s20309.exception.notFound.TaskNotFoundException;
import pl.edu.pjwstk.s20309.exception.notFound.UserNotFoundException;


@RestControllerAdvice
public class Handler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleRuntimeException(UserNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Object> handleRuntimeException(ClientNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> handleRuntimeException(TaskNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(ClientAlreadyExistException.class)
    public ResponseEntity<Object> handleRuntimeException(ClientAlreadyExistException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FOUND);
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleRuntimeException(UserAlreadyExistException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FOUND);
    }
    @ExceptionHandler(TaskAlreadyExistException.class)
    public ResponseEntity<Object> handleRuntimeException(TaskAlreadyExistException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FOUND);
    }
    @ExceptionHandler(NotEmptyException.class)
    public ResponseEntity<Object> equipmentHandleRuntimeException(NotEmptyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
