package pl.jellytech.machiavelli.cards.controllers;

import com.codahale.metrics.MetricRegistry;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jellytech.machiavelli.cards.entities.Card;
import pl.jellytech.machiavelli.cards.entities.CardType;
import pl.jellytech.machiavelli.cards.services.CardService;
import pl.jellytech.machiavelli.cards.utils.ControllerUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("api/card")
@Slf4j
public class CardController {
    private final CardService cardService;
    private final ModelMapper modelMapper;
    private final MetricRegistry metricRegistry;

    @Autowired
    public CardController(CardService cardService, ModelMapper modelMapper, MetricRegistry metricRegistry) {
        this.cardService = cardService;
        this.modelMapper = modelMapper;
        this.metricRegistry = metricRegistry;
    }

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity createOrUpdate(@RequestPart("cardType") String cardType, @RequestPart("name") String name,
            @RequestPart("description") String description, @RequestPart(value = "cardId", required = false) Optional<Long> cardId,
            @RequestPart("image") MultipartFile image) {

        try {
            final byte[] imageBytes = image.getBytes();
            Supplier<Card> func = () -> this.cardService
                    .createOrUpdate(new Card(CardType.valueOf(cardType.toUpperCase()), name, imageBytes, description, cardId));
            final Card card = ControllerUtils.FunctionLogMeasureWrapper(func, String.format("Saving Card entity with name %s", name),
                    String.format("Card %s saved", name), metricRegistry);
            return ControllerUtils.SuccessResponse(card.convertToDto(modelMapper));
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity getAll() {
        try {
            Supplier<List<Card>> callableFunc = this.cardService::getAll;
            List<Card> cards = ControllerUtils.FunctionLogMeasureWrapper(callableFunc, "Get all cards started...", "Get all cards finished...", metricRegistry);
            return ControllerUtils.SuccessResponse(cards.stream().map(c -> c.convertToDto(modelMapper)).collect(Collectors.toList()));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("id")
    public ResponseEntity getById(@RequestParam long cardId) {
        try {
            Supplier<Card> function = () -> this.cardService.getById(cardId);
            final String generalLogMsg = String.format("Get card by id %s from DB", cardId);
            final Card card = ControllerUtils.FunctionLogMeasureWrapper(function, String.format("%s started...", generalLogMsg),
                    String.format("%s finished...", generalLogMsg), metricRegistry);
            if (card == null) {
                return ControllerUtils.ErrorResponse(new Exception(String.format("Card with id %s not found", cardId)), HttpStatus.NOT_FOUND);
            }
            return ControllerUtils.SuccessResponse(card.convertToDto(modelMapper));
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam long cardId) {
        try {
            final String generalLogMsg = String.format("Delete card by id %s from DB", cardId);
            Runnable func = () -> this.cardService.delete(cardId);
            ControllerUtils.FunctionLogMeasureWrapper(func, String.format("%s started...", generalLogMsg), String.format("%s finished...", generalLogMsg),
                    metricRegistry);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ControllerUtils.ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ControllerUtils.SuccessResponse(true);
    }
}
