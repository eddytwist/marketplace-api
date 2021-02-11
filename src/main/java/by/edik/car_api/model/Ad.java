package by.edik.car_api.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
    private long adId;
    private long userId;
    private short year;
    private String brand;
    private String model;
    private int engineVolume;
    private Condition condition;
    private long mileage;
    private int enginePower;
    private Date creationTime;
    private Date editingTime;

}
