package pl.jellytech.machiavelli.cards.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jellytech.machiavelli.cards.dtos.CardResponse;
import pl.jellytech.machiavelli.cards.entities.Card;
import pl.jellytech.machiavelli.cards.entities.CardType;
import pl.jellytech.machiavelli.cards.services.ICardService;
import pl.jellytech.machiavelli.cards.utils.ControllerUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
    @PostMapping()
    public ResponseEntity<CardResponse> CreateOrUpdate(@RequestParam("cardType") CardType cardType,
                                               @RequestParam("name") String name,
                                               @RequestParam("description") String description,
                                               @RequestParam(value = "cardId",required = false) Optional<Long> cardId,
                                               @RequestPart("image") MultipartFile image)
            throws IOException {
        log.info(String.format("Saving Card entity with name %s", cardType));
        try{
            final Card card = this.cardService.createOrUpdate(new Card(
                    cardType,
                    name,
                    image.getBytes(),
                    description,
                    cardId
            ));
            log.info(String.format("Card %s saved", card.getName()));
            return ControllerUtils.SuccessResponse(null);
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
