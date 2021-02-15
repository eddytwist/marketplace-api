package by.edik.car_api.model;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String username;
    private String email;
    private String password;
}
