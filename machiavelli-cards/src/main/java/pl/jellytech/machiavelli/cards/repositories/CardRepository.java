package pl.jellytech.machiavelli.cards.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jellytech.machiavelli.cards.entities.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

}
