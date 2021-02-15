package by.edik.car_api.model;

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
public class Ad {
    private Long adId;
    private Long userId;
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
