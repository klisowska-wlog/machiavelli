package pl.jellytech.machiavelli.cards.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jellytech.machiavelli.cards.entities.Card;
import pl.jellytech.machiavelli.cards.repositories.CardRepository;

import java.util.List;

@Service
@Slf4j
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public Card createOrUpdate(Card card) {
        return this.cardRepository.save(card);
    }

    public Card getById(Long cardId) {
        return this.cardRepository.getReferenceById(cardId);
    }

    public List<Card> getAll() {
        return this.cardRepository.findAll(Sort.by(Sort.Direction.ASC, "type"));
    }

    @Transactional
    public void delete(Long cardId) {
        final Card card = this.getById(cardId);
        this.cardRepository.delete(card);
    }
}
