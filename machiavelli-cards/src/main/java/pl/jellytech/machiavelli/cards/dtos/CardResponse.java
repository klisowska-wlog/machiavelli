package pl.jellytech.machiavelli.cards.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.jellytech.machiavelli.cards.entities.Card;
import pl.jellytech.machiavelli.cards.entities.CardType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {
    private byte[] image;
    private Long cardId;
    private CardType cardType;
    private String name;
    private String description;
}
