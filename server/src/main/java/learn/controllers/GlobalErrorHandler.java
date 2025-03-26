package learn.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalErrorHandler {
    private String userErrorMessage = "Invalid. Please reformat and try again.";
    private String appErrorMessage = "Something went wrong on our end. Please wait and try again.";

    @ExceptionHandler({HttpMessageNotReadableException.class, JsonProcessingException.class, NullPointerException.class})
    public ResponseEntity<List<String>> handleUserErrors(Exception e) {
        return new ResponseEntity<>(List.of(userErrorMessage), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({BadSqlGrammarException.class, InvalidDataAccessApiUsageException.class, SQLIntegrityConstraintViolationException.class, SQLException.class, HttpServerErrorException.InternalServerError.class})
    public ResponseEntity<List<String>> handleAppError(Exception e) {
        return new ResponseEntity<>(List.of(appErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<List<String>> handleAnyError(Exception e) {
        return new ResponseEntity<>(List.of("Something went wrong. Please wait and try again."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
