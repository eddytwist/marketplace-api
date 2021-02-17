package by.edik.car_api.web.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneDto {
    private long phoneNumberId;
    private long userId;
    private String phoneNumber;
}
