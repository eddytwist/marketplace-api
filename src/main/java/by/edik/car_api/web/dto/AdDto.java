package by.edik.car_api.web.dto;

import by.edik.car_api.model.Condition;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdDto {
    private long adId;
    private long userId;
    private long mileage;
    private int engineVolume;
    private int enginePower;
    private short year;
    private String brand;
    private String model;
    private String creationTime;
    private String editingTime;
    private Condition condition;
}
