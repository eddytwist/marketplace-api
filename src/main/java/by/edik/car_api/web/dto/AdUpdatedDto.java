package by.edik.car_api.web.dto;

import by.edik.car_api.model.Condition;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdUpdatedDto {
    private Long adId;
    private Long userId;
    private Integer year;
    private String brand;
    private String model;
    private Integer engineVolume;
    private Condition condition;
    private Long mileage;
    private Integer enginePower;
    private String ownerName;
    private List<String> ownerPhoneNumbers;
    private List<String> pictureReferences;
}
