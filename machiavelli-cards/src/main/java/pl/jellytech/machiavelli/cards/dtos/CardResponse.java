package pl.jellytech.machiavelli.cards.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.jellytech.machiavelli.cards.entities.Card;
import pl.jellytech.machiavelli.cards.entities.CardType;

@Getter
@Setter
public class CardResponse {
    private byte[] image;
    private Long cardId;
    private CardType cardType;
    private String name;
    private String description;
    public CardResponse(Card card){
        this.setCardId(card.getCardId());
        this.setCardType(card.getType());
        this.setName(card.getName());
        this.setImage(card.getImage());
        this.setDescription(card.getDescription());
    }
}
