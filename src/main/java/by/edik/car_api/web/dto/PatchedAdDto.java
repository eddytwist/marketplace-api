package by.edik.car_api.web.dto;

import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchedAdDto {
    private Long adId;
    private Integer year;
    private String brand;
    private String model;
    private Integer engineVolume;
    private Long mileage;
    private Integer enginePower;
}
