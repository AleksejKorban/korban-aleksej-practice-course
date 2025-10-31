package com.aleksej.service;

import com.aleksej.domain.CardInfo;
import com.aleksej.dto.CardInfoDto;
import com.aleksej.exception.NotFoundException;
import com.aleksej.mapper.CardInfoMapper;
import com.aleksej.repository.CardInfoRepository;
import com.aleksej.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardInfoServiceImpl implements CardInfoService {

    private final CardInfoRepository cardInfoRepository;
    private final UserRepository userRepository;
    private final CardInfoMapper cardInfoMapper;

    private static final String CACHE_NAME = "cards";

    @Override
    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public CardInfoDto createCard(Long userId, CardInfoDto cardInfoDto) {
        log.info("Creating card for user ID: {}", userId);

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with ID " + userId + " not found"));

        CardInfo cardInfo = cardInfoMapper.toEntity(cardInfoDto);
        cardInfo.getUser().setId(userId);

        return cardInfoMapper.toDto(cardInfoRepository.save(cardInfo));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = CACHE_NAME, key = "#cardId")
    public CardInfoDto getCardById(Long cardId) {
        log.info("Fetching card with ID: {}", cardId);
        return cardInfoRepository.findById(cardId)
                .map(cardInfoMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Card with ID " + cardId + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = CACHE_NAME, key = "'user-' + #userId")
    public List<CardInfoDto> getAllCardsByUserId(Long userId) {
        log.info("Fetching all cards for user ID: {}", userId);

        return cardInfoRepository.findByUserId(userId).stream()
                .map(cardInfoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_NAME, key = "#cardId", allEntries = true)
    public CardInfoDto updateCard(Long cardId, CardInfoDto cardInfoDto) {
        log.info("Updating card with ID: {}", cardId);

        CardInfo existingCard = cardInfoRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Card with ID " + cardId + " not found"));

        cardInfoMapper.updateEntityFromDto(cardInfoDto, existingCard);

        if (existingCard.getUser() == null || existingCard.getUser().getId() == null) {

            existingCard.getUser().setId(existingCard.getUser().getId());
        }

        return cardInfoMapper.toDto(cardInfoRepository.save(existingCard));
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void deleteCard(Long cardId) {
        log.info("Deleting card with ID: {}", cardId);
        if (!cardInfoRepository.existsById(cardId)) {
            throw new NotFoundException("Card with ID " + cardId + " not found for deletion");
        }
        cardInfoRepository.deleteById(cardId);
    }
}