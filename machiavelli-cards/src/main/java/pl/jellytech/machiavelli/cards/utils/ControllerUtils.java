package pl.jellytech.machiavelli.cards.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import pl.jellytech.machiavelli.cards.dtos.BaseResponseWrapper;
import pl.jellytech.machiavelli.cards.dtos.ExceptionPayload;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@Slf4j
public class ControllerUtils {
    public static <T> ResponseEntity<BaseResponseWrapper<T>> SuccessResponse(T successPayload) {
        final BaseResponseWrapper<T> response = new BaseResponseWrapper<T>(successPayload, null);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    public static ResponseEntity<BaseResponseWrapper> ErrorResponse(Exception exception, HttpStatus status) {

        final ExceptionPayload exceptionPayload = new ExceptionPayload(Timestamp.valueOf(LocalDateTime.now()), status, exception.getMessage(), exception.getStackTrace());
        final BaseResponseWrapper response = new BaseResponseWrapper(null, exceptionPayload);

        return new ResponseEntity<BaseResponseWrapper>(response, status);
    }

    public static <T> T FunctionLogMeasureWrapper(Supplier<T> func, String startMessage, String endMessage) {
        final Instant startTime = Instant.now();
        log.info(startMessage);
        T result = func.get();
        final Duration timeElapsed = Duration.between(startTime, Instant.now());
        log.info("{} : Duration {} ms", endMessage, timeElapsed.toMillis());
        return result;
    }

    public static void FunctionLogMeasureWrapper(Runnable func, String startMessage, String endMessage) {
        final Instant startTime = Instant.now();
        log.info(startMessage);
        func.run();
        final Duration timeElapsed = Duration.between(startTime, Instant.now());
        log.info("{} : Duration {} ms", endMessage, timeElapsed.toMillis());
    }
}
