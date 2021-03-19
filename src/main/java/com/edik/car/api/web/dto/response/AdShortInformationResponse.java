package com.edik.car.api.web.dto.response;

import com.edik.car.api.config.DateConstants;
import com.edik.car.api.dao.model.Condition;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdShortInformationResponse {
    private Long adId;
    private Integer year;
    private String brand;
    private String model;
    private Condition condition;
    private Long mileage;
    @JsonFormat(pattern = DateConstants.LOCAL_DATE_TIME_PATTERN_Z, timezone = DateConstants.UTC_TIME_ZONE)
    private LocalDateTime creationTime;
    private String ownerName;
    private Integer picturesNumber;
}