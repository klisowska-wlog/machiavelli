package pl.jellytech.machiavelli.cards.dtos;

import com.codahale.metrics.Snapshot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestTimerResponse {
    private long requests;
    private Snapshot snapshot;
}
