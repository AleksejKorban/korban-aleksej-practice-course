package com.aleksej.controller;



import com.aleksej.dto.CardInfoDto;
import com.aleksej.service.CardInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CardInfoController {

    private static final Logger log = LoggerFactory.getLogger(CardInfoController.class);
    private final CardInfoService cardInfoService;


    @PostMapping("/api/users/{userId}/cards")
    public ResponseEntity<CardInfoDto> createCard(
            @PathVariable Long userId,
            @Valid @RequestBody CardInfoDto cardInfoDto) {

        log.info("Received request to create card for user ID: {}", userId);
        CardInfoDto createdCard = cardInfoService.createCard(userId, cardInfoDto);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }



    @GetMapping("/api/cards/{cardId}")
    public ResponseEntity<CardInfoDto> getCardById(@PathVariable Long cardId) {
        log.info("Received request to get card by ID: {}", cardId);
        CardInfoDto cardInfoDto = cardInfoService.getCardById(cardId);
        return ResponseEntity.ok(cardInfoDto);
    }


    @GetMapping("/api/users/{userId}/cards")
    public ResponseEntity<List<CardInfoDto>> getAllCardsByUserId(@PathVariable Long userId) {
        log.info("Received request to get all cards for user ID: {}", userId);
        List<CardInfoDto> cards = cardInfoService.getAllCardsByUserId(userId);
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/api/cards/{cardId}")
    public ResponseEntity<CardInfoDto> updateCard(
            @PathVariable Long cardId,
            @Valid @RequestBody CardInfoDto cardInfoDto) {

        log.info("Received request to update card with ID: {}", cardId);
        CardInfoDto updatedCard = cardInfoService.updateCard(cardId, cardInfoDto);
        return ResponseEntity.ok(updatedCard);
    }


    @DeleteMapping("/api/cards/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId) {
        log.info("Received request to delete card with ID: {}", cardId);
        cardInfoService.deleteCard(cardId);
        return ResponseEntity.noContent().build();
    }
}