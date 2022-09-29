package pl.jellytech.machiavelli.cards.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import pl.jellytech.machiavelli.cards.dtos.BaseResponseWrapper;
import pl.jellytech.machiavelli.cards.dtos.ExceptionPayload;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ControllerUtils {
    public static <T> ResponseEntity<BaseResponseWrapper<T>>
    SuccessResponse(T successPayload){
        final BaseResponseWrapper<T> response = new BaseResponseWrapper<T>(successPayload, null);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }
    public static ResponseEntity<BaseResponseWrapper> ErrorResponse(Exception exception,
                                                                    HttpStatus status){

        final ExceptionPayload exceptionPayload = new ExceptionPayload(
                Timestamp.valueOf(LocalDateTime.now()),
                status, exception.getMessage(),exception.getStackTrace()
        );
        final BaseResponseWrapper response = new BaseResponseWrapper(null,exceptionPayload);

        return new ResponseEntity<BaseResponseWrapper>(response, status);
    }

}
