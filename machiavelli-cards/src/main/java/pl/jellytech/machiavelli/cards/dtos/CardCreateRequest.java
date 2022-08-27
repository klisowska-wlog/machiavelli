package pl.jellytech.machiavelli.cards.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.jellytech.machiavelli.cards.entities.CardType;

@Getter @Setter
public class CardCreateRequest {
    private CardType cardType;
    private String name;
    private byte[] image;
    private String description;
}
