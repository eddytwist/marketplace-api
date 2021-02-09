package by.edik.car_api.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
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
