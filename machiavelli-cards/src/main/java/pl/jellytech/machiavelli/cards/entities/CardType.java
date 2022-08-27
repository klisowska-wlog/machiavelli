package pl.jellytech.machiavelli.cards.entities;

public enum CardType {
    Building("01_Building"),
    Action("02_Action"),
    Character("03_Character");

    private String value;

    CardType(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
