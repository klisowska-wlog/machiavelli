package pl.jellytech.machiavelli.cards.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private Timestamp timestamp;
    private HttpStatus status;
    private String message;
}

