package pl.jellytech.machiavelli.cards.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cards", schema = "cards")
public class Card implements Serializable {
    @Id
    @Column(name = "card_id", unique = true, nullable = false)
    private long cardId;
    @Column(name = "type", nullable = false)
    private CardType type;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "image", unique = true, nullable = false)
    private byte[] image;
    @Column(name = "description", unique = true, nullable = false)
    private String description;
    public Card(){}
    public Card(CardType type, String name, byte[] image, String description){
        this.type = type;
        this.name = name;
        this.image = image;
        this.description = description;
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
}
