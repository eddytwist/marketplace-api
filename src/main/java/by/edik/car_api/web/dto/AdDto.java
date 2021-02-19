package by.edik.car_api.web.dto;

import by.edik.car_api.model.Condition;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime creationTime;
    private LocalDateTime editingTime;
}
