package by.edik.car_api.web.dto;

import by.edik.car_api.model.Condition;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Setter
@Getter
@Builder (toBuilder = true)
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
    private LocalDateTime creationTime;
    private String ownerName;
    private Integer picturesNumber;
}
