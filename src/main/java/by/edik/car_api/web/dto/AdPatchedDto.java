package by.edik.car_api.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdPatchedDto {
    private Long adId;
    private Integer year;
    private String brand;
    private String model;
    private Integer engineVolume;
    private Long mileage;
    private Integer enginePower;
}
