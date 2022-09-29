package pl.jellytech.machiavelli.cards.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseWrapper<T> {
    private T successPayload;
    private ExceptionPayload exception;
}
