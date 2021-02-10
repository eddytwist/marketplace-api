package by.edik.car_api.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationDto {
    private long userId;
    private String name;
}
