package pl.jellytech.machiavelli.cards.utils;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import pl.jellytech.machiavelli.cards.dtos.BaseResponseWrapper;
import pl.jellytech.machiavelli.cards.dtos.ExceptionPayload;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Supplier;

@Slf4j
public class ControllerUtils {
    final static String requestTimerName = MetricRegistryToolsTypes.RequestTimer.name();

    public static <T> ResponseEntity<BaseResponseWrapper<T>> successResponse(T successPayload) {
        final BaseResponseWrapper<T> response = new BaseResponseWrapper<T>(successPayload, null);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    public static ResponseEntity<BaseResponseWrapper> errorResponse(Exception exception, HttpStatus status) {

        final ExceptionPayload exceptionPayload = new ExceptionPayload(Timestamp.valueOf(LocalDateTime.now()), status, exception.getMessage(),
                exception.getStackTrace());
        final BaseResponseWrapper response = new BaseResponseWrapper(null, exceptionPayload);

        return new ResponseEntity<BaseResponseWrapper>(response, status);
    }

    public static <T> T functionLogMeasureWrapper(Supplier<T> func, String startMessage, String endMessage,
                                                  MetricRegistry metricRegistry) {
        Timer.Context context = startTimer(metricRegistry, startMessage);
        T result = func.get();
        endTimer(context, endMessage);
        return result;
    }

    public static void functionLogMeasureWrapper(Runnable func, String startMessage, String endMessage,
                                                 MetricRegistry metricRegistry) {
        Timer.Context context = startTimer(metricRegistry, startMessage);
        func.run();
        endTimer(context, endMessage);

    }
    private static Timer.Context startTimer(MetricRegistry metricRegistry, String startMessage){
        final Timer timer = metricRegistry.timer(requestTimerName);
        Timer.Context context = timer.time();
        log.info(startMessage);
        return context;
    }
    private static void endTimer(Timer.Context context, String endMessage){
        final long elapsed = context.stop();
        final long ms = TimeUtils.TicksToMilliseconds(elapsed);
        log.info("{} : Duration {} ms", endMessage, ms);
    }
}
