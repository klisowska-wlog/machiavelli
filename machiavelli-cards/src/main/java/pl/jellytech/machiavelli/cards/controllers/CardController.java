package pl.jellytech.machiavelli.cards.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jellytech.machiavelli.cards.dtos.CardResponse;
import pl.jellytech.machiavelli.cards.entities.Card;
import pl.jellytech.machiavelli.cards.entities.CardType;
import pl.jellytech.machiavelli.cards.services.CardService;
import pl.jellytech.machiavelli.cards.utils.ControllerUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping( "api/card")
@Slf4j
public class CardController {
    private final CardService cardService;
    @Autowired
    public CardController(CardService cardService){
        this.cardService = cardService;
    }

    @PostMapping(consumes ={MediaType.MULTIPART_FORM_DATA_VALUE} )
    public ResponseEntity createOrUpdate(@RequestPart("cardType") String cardType,
                     @RequestPart("name") String name,
                     @RequestPart("description") String description,
                     @RequestPart(value = "cardId",required = false) Optional<Long> cardId,
                     @RequestPart("image") MultipartFile image){
        log.debug("Saving Card entity with name {}",name);
        try{
            final Card card = this.cardService.createOrUpdate(new Card(
                    CardType.valueOf(cardType.toUpperCase()),
                    name,
                    image.getBytes(),
                    description,
                    cardId
            ));
            log.debug("Card {} saved",card.getName());
            return ControllerUtils.SuccessResponse(null);
        }
        catch (IOException ex){
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping
    public ResponseEntity getAll(){
        try{
            log.debug("Get all cards started...");
            List<Card> cards = this.cardService.getAll();
            log.debug("Get all cards finished...");
            return ControllerUtils
                    .SuccessResponse(
                            cards.stream().map(CardResponse::new).collect(Collectors.toList())
                    );
        }catch(Exception ex){
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("id")
    public ResponseEntity getById(@RequestParam long cardId){
        try{
            log.debug("Get card by id {} from DB started...",cardId);
            final Card card = this.cardService.getById(cardId);
            if(card == null){
                return ControllerUtils.ErrorResponse(
                        String.format("Card with id %s not found", cardId),
                        HttpStatus.NOT_FOUND
                );
            }
            return ControllerUtils.SuccessResponse(new CardResponse(card));
        }catch(Exception ex){
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping
    public ResponseEntity delete(@RequestParam long cardId){
        try{
            this.cardService.delete(cardId);
        }catch(Exception ex){
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ControllerUtils.SuccessResponse(true);
    }
}
