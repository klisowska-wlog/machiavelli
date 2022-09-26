package pl.jellytech.machiavelli.cards.entities;

import org.modelmapper.ModelMapper;
import pl.jellytech.machiavelli.cards.dtos.CardResponse;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

@Entity
@Table(name = "cards")
public class Card implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "card_id", unique = true, nullable = false)
    private long cardId;
    @Column(name = "type", nullable = false)
    private CardType type;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "image", unique = true, nullable = false ,columnDefinition = "TEXT")
    @Lob
    private byte[] image;
    @Column(name = "description", unique = true, nullable = false)
    private String description;
    public Card(){}
    public Card(CardType type, String name, byte[] image, String description, Optional<Long> cardId){
        this.type = type;
        this.name = name;
        this.image = image;
        this.description = description;
        cardId.ifPresent(aLong -> this.cardId = aLong);

    }
    public long getCardId(){
        return this.cardId;
    }
    public CardType getType(){
        return this.type;
    }
    public String getName(){
        return this.name;
    }
    public byte[] getImage() {
        return this.image;
    }
    public String getDescription(){
        return this.description;
    }

    public void setType(CardType type){
        this.type = type;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setImage(byte[] image){
        this.image = image;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public CardResponse convertToDto(Card this, ModelMapper modelMapper){
        CardResponse dto = modelMapper.map(this, CardResponse.class);
        return dto;
    }

}
