package by.edik.car_api.web.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String username;
    private String email;

}
