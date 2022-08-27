package pl.jellytech.machiavelli.cards.dtos;

import lombok.Getter;
import lombok.Setter;
import pl.jellytech.machiavelli.cards.entities.Card;
import pl.jellytech.machiavelli.cards.entities.CardType;

@Getter
@Setter
public class CardResponse extends CardUpdateRequest {
    public CardResponse(Card card){
        this.setCardId(card.getCardId());
        this.setCardType(card.getType());
        this.setName(card.getName());
        this.setImage(card.getImage());
        this.setDescription(card.getDescription());
    }
}
