package by.edik.car_api.web.dto;

import by.edik.car_api.dao.model.Condition;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import static by.edik.car_api.config.DateConstants.LOCAL_DATE_TIME_PATTERN_Z;
import static by.edik.car_api.config.DateConstants.UTC_TIME_ZONE;

@Accessors(chain = true)
@Setter
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdShortInformationDto {
    private Long adId;
    private Integer year;
    private String brand;
    private String model;
    private Condition condition;
    private Long mileage;
    @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN_Z, timezone = UTC_TIME_ZONE)
    private LocalDateTime creationTime;
    private String ownerName;
    private Integer picturesNumber;
}
