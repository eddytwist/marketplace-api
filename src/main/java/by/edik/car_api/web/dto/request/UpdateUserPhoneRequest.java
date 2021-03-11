package by.edik.car_api.web.dto.request;

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
public class UpdateUserPhoneRequest {
    private Long phoneNumberId;
    private String phoneNumber;
    private Long userId;
}
