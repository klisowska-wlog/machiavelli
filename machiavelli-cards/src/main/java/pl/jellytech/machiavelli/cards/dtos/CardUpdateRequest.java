package pl.jellytech.machiavelli.cards.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.jellytech.machiavelli.cards.entities.Card;

@Getter @Setter
public class CardUpdateRequest extends CardCreateRequest{
    private Long cardId;
}
