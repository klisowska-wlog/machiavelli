package pl.jellytech.machiavelli.cards.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import pl.jellytech.machiavelli.cards.dtos.ExceptionResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ControllerUtils {
    public static <T> ResponseEntity<T> SuccessResponse(T responseBody){
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(responseBody);
    }
    public static ResponseEntity ErrorResponse(String message, HttpStatus status){
        return new ResponseEntity(new ExceptionResponse(
                Timestamp.valueOf(LocalDateTime.now()),
                status, message
        ), status);
    }

}
