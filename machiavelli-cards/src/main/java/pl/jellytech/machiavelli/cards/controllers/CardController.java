package pl.jellytech.machiavelli.cards.controllers;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jellytech.machiavelli.cards.dtos.BaseResponseWrapper;
import pl.jellytech.machiavelli.cards.dtos.CardResponse;
import pl.jellytech.machiavelli.cards.entities.Card;
import pl.jellytech.machiavelli.cards.entities.CardType;
import pl.jellytech.machiavelli.cards.services.CardService;
import pl.jellytech.machiavelli.cards.utils.ControllerUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping( "api/card")
@Slf4j
public class CardController {
    private final CardService cardService;
    private final ModelMapper modelMapper;
    @Autowired
    public CardController(CardService cardService, ModelMapper modelMapper){

        this.cardService = cardService;
        this.modelMapper = modelMapper;
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
            return ControllerUtils.SuccessResponse(card.convertToDto(modelMapper));
        }
        catch (IOException ex){
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping
    public ResponseEntity getAll(){
        try{
            log.debug("Get all cards started...");
            final Instant startTime = Instant.now();
            List<Card> cards = this.cardService.getAll();
            final Duration timeElapsed = Duration.between(startTime, Instant.now());
            log.debug("Get all cards finished... Duration: {} ms",timeElapsed.toMillis());
            return ControllerUtils
                    .SuccessResponse(
                            cards.stream().map(c -> c.convertToDto(modelMapper)).collect(Collectors.toList())
                    );
        }catch(Exception ex){
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("id")
    public ResponseEntity getById(@RequestParam long cardId){
        try{
            log.debug("Get card by id {} from DB started...",cardId);
            final Card card = this.cardService.getById(cardId);
            if(card == null){
                return ControllerUtils.ErrorResponse(
                        new Exception(String.format("Card with id %s not found", cardId)),
                        HttpStatus.NOT_FOUND
                );
            }
            return ControllerUtils.SuccessResponse(card.convertToDto(modelMapper));
        }catch(Exception ex){
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping
    public ResponseEntity delete(@RequestParam long cardId){
        try{
            this.cardService.delete(cardId);
        }catch(Exception ex){
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ControllerUtils.SuccessResponse(true);
    }
}
