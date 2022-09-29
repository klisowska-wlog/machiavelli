package pl.jellytech.machiavelli.cards.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import pl.jellytech.machiavelli.cards.dtos.BaseResponseWrapper;
import pl.jellytech.machiavelli.cards.dtos.ExceptionPayload;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Log4j2
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

    public static <T> T FunctionLogMeasureWrapper(Supplier<T> func, String startMessage, String endMessage){
        final Instant startTime = Instant.now();
        log.debug(startMessage);
        T result = func.get();
        final Duration timeElapsed = Duration.between(startTime, Instant.now());
        log.debug("{} : Duration {} ms",endMessage,timeElapsed.toMillis());
        return result;
    }

    public static void FunctionLogMeasureWrapper(Runnable func, String startMessage, String endMessage){
        final Instant startTime = Instant.now();
        log.debug(startMessage);
        func.run();
        final Duration timeElapsed = Duration.between(startTime, Instant.now());
        log.debug("{} : Duration {} ms",endMessage,timeElapsed.toMillis());
    }
}
