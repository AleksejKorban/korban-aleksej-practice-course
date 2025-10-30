package com.aleksej.repository;

import com.aleksej.domain.CardInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface CardInfoRepository extends JpaRepository<CardInfo, Long> {
    List<CardInfo> findByUserId(Long userId);
}