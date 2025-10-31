package com.aleksej.mapper;

import com.aleksej.config.MapStructConfig;
import com.aleksej.domain.CardInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



import com.aleksej.domain.CardInfo;
import com.aleksej.dto.CardInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CardInfoMapper {


    @Mapping(source = "user.id", target = "userId")
    CardInfoDto toDto(CardInfo cardInfo);


    @Mapping(source = "userId", target = "user.id")
    CardInfo toEntity(CardInfoDto dto);

    @Mapping(source = "userId", target = "user.id")
    void updateEntityFromDto(CardInfoDto dto, @MappingTarget CardInfo entity);
}
