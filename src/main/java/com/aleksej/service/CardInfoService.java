package com.aleksej.service;

import com.aleksej.dto.CardInfoDto;

import java.util.List;

public interface CardInfoService {
    CardInfoDto createCard(Long userId, CardInfoDto cardInfoDto);
    CardInfoDto getCardById(Long cardId);
    List<CardInfoDto> getAllCardsByUserId(Long userId);
    CardInfoDto updateCard(Long cardId, CardInfoDto cardInfoDto);
    void deleteCard(Long cardId);
}