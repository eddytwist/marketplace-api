package by.edik.car_api.web.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPhoneDto {
    private Long phoneNumberId;
    private String phoneNumber;
}
