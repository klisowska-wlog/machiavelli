package pl.jellytech.machiavelli.cards.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jellytech.machiavelli.cards.dtos.CardCreateRequest;
import pl.jellytech.machiavelli.cards.dtos.CardResponse;
import pl.jellytech.machiavelli.cards.dtos.CardUpdateRequest;
import pl.jellytech.machiavelli.cards.entities.Card;
import pl.jellytech.machiavelli.cards.services.ICardService;
import pl.jellytech.machiavelli.cards.utils.ControllerUtils;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping( "api/card")
@Slf4j
public class CardController {
    private final ICardService cardService;

    @Autowired
    public CardController(ICardService cardService){
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<CardResponse> Create(@RequestBody CardCreateRequest request){
        log.info(String.format("Saving Card entity with name %s", request.getName()));
        try{
            final Card card = this.cardService.createOrUpdate(new Card(
                    request.getCardType(),
                    request.getName(),
                    request.getImage(),
                    request.getDescription()
            ));
            log.info(String.format("Card %s saved", card.getName()));
            return ControllerUtils.SuccessResponse(new CardResponse(card));
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw ex;
        }
    }
    @PutMapping
    public ResponseEntity<CardResponse> Update(@RequestBody CardUpdateRequest request){
        try{
            log.info(String.format("Get card %s from DB for update started...", request.getName()));
            Card cardEntity = this.cardService.getById(request.getCardId());
            log.info(String.format("Get card %s finished...", request.getName()));
            cardEntity.setName(request.getName());
            cardEntity.setImage(request.getImage());
            cardEntity.setType(request.getCardType());
            cardEntity.setDescription(request.getDescription());
            log.info(String.format("Update Card entity with name %s...", request.getName()));
            cardEntity = this.cardService.createOrUpdate(cardEntity);
            log.info(String.format("Update card %s finished...", cardEntity.getName()));
            return ControllerUtils.SuccessResponse(new CardResponse(cardEntity));
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @GetMapping
    public ResponseEntity<List<CardResponse>> GetAll(){
        try{
            log.info("Get all cards started...");
            List<Card> cards = this.cardService.getAll();
            log.info("Get all cards finished...");
            return ControllerUtils
                    .SuccessResponse(
                            cards.stream().map(CardResponse::new).collect(Collectors.toList())
                    );
        }catch(Exception ex){
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @GetMapping("id")
    public ResponseEntity<CardResponse> GetById(@RequestParam long cardId){
        try{
            log.info(String.format("Get card by id %s from DB started...", cardId));
            final Card card = this.cardService.getById(cardId);
            if(card == null){
                return ControllerUtils.ErrorResponse(
                        String.format("Card with id %s not found", cardId),
                        HttpStatus.NOT_FOUND
                );
            }
            return ControllerUtils.SuccessResponse(new CardResponse(card));
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw ex;
        }
    }
    @DeleteMapping
    public ResponseEntity<Boolean> Delete(@RequestParam long cardId){
        try{
            this.cardService.delete(cardId);
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw ex;
        }
        return ControllerUtils.SuccessResponse(true);
    }
}
