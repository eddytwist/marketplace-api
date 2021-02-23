package by.edik.car_api.web.dto;

import by.edik.car_api.model.Condition;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import static by.edik.car_api.config.DateConstants.LOCAL_DATE_TIME_PATTERN_Z;
import static by.edik.car_api.config.DateConstants.TIME_ZONE;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdFullInformationDto {
    private Long adId;
    private Integer year;
    private String brand;
    private String model;
    private Integer engineVolume;
    private Integer enginePower;
    private Condition condition;
    private Long mileage;
    private String ownerName;
    private List<String> ownerPhoneNumbers;
    private List<String> pictureReferences;
    @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN_Z, timezone = TIME_ZONE)
    private LocalDateTime creationTime;
    @JsonFormat(pattern = LOCAL_DATE_TIME_PATTERN_Z, timezone = TIME_ZONE)
    private LocalDateTime editingTime;
}
