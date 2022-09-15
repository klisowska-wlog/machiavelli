package pl.jellytech.machiavelli.cards.services;

import pl.jellytech.machiavelli.cards.entities.Card;

import java.util.List;

public interface CardService {
    Card createOrUpdate(Card card);
    List<Card> getAll();
    Card getById(Long cardId);
    void delete(Long cardId);
}
