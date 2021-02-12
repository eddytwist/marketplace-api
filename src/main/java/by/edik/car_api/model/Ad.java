package by.edik.car_api.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
    private long adId;
    private long userId;
    private int year;
    private String brand;
    private String model;
    private int engineVolume;
    private Condition condition;
    private long mileage;
    private int enginePower;
    private LocalDateTime creationTime;
    private LocalDateTime editingTime;

}
