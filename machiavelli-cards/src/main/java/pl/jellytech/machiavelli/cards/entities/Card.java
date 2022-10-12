package pl.jellytech.machiavelli.cards.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import pl.jellytech.machiavelli.cards.dtos.CardResponse;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
public class Card implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", unique = true, nullable = false)
    // id with entity acronym naming convention
    private long cardId;
    @Column(name = "type", nullable = false)
    private CardType type;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "image", unique = true, nullable = false, columnDefinition = "TEXT")
    @Lob
    private byte[] image;
    @Column(name = "description", unique = true, nullable = false)
    private String description;

    public Card(CardType type, String name, byte[] image, String description, Optional<Long> cardId) {
        this.setType(type);
        this.setName(name);
        this.setImage(image);
        this.setDescription(description);
        cardId.ifPresent(aLong -> this.setCardId(cardId.get()));
    }

    public CardResponse convertToDto(Card this,ModelMapper modelMapper) {
        CardResponse dto = modelMapper.map(this, CardResponse.class);
        return dto;
    }

}
