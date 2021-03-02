package by.edik.car_api.web.controller.error;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {
    private String errorMessage = "";
}
