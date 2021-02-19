package by.edik.car_api.web.dto;

import by.edik.car_api.model.Condition;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime creationTime;
    private LocalDateTime editingTime;
}
