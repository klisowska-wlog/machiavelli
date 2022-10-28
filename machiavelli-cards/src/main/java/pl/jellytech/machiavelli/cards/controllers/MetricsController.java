package pl.jellytech.machiavelli.cards.controllers;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jellytech.machiavelli.cards.dtos.RequestTimerResponse;
import pl.jellytech.machiavelli.cards.utils.ControllerUtils;
import pl.jellytech.machiavelli.cards.utils.MetricRegistryToolsTypes;

@RestController()
@RequestMapping("api/metrics")
@Slf4j
public class MetricsController {
    private final MetricRegistry metricRegistry;

    @Autowired
    public MetricsController(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }

    @GetMapping("timer")
    public ResponseEntity getRequestTimer() {
        final String requestTimerName = MetricRegistryToolsTypes.RequestTimer.name();
        final Timer timer = this.metricRegistry.timer(requestTimerName);
        final long requests = timer.getCount();
        final Snapshot snapshot = timer.getSnapshot();
        return ControllerUtils.successResponse(new RequestTimerResponse(requests, snapshot));
    }
}
