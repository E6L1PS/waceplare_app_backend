package com.itacademy.waceplare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank(message = "firstname должен быть заполнен")
    private String firstname;

    @NotBlank(message = "firstname должен быть заполнен")
    private String lastname;

    @NotBlank(message = "number должен быть заполнен")
    private String number;

    @NotBlank(message = "email должен быть заполнен")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Некоректный email")
    private String email;

    @NotBlank(message = "password должен быть заполнен")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$",
            message = "Некоректный password")
    private String password;

}
