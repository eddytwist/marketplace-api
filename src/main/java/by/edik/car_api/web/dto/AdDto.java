package by.edik.car_api.web.dto;

import by.edik.car_api.dao.model.Condition;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static by.edik.car_api.config.DateConstants.LOCAL_DATE_TIME_PATTERN_Z;
import static by.edik.car_api.config.DateConstants.UTC_TIME_ZONE;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdDto {
    private Long adId;
    private Integer year;
    private String brand;
    private String model;
    private Integer engineVolume;
    private Condition condition;
    private Long mileage;
    private Integer enginePower;
    @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN_Z, timezone = UTC_TIME_ZONE)
    private LocalDateTime creationTime;
    @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN_Z, timezone = UTC_TIME_ZONE)
    private LocalDateTime editingTime;
}
