package com.kramar.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(NON_NULL)
public class ChangePasswordDto {

    @NotBlank
    @Length(max = 1024)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}",
            message = "To create a stronger and more complex password, use passwords that include characters of the groups listed below:\n" +
                    "- a digit must occur at least once\n" +
                    "- a lower case letter must occur at least once\n" +
                    "- an upper case letter must occur at least once\n" +
                    "- a special character must occur at least once\n" +
                    "- no whitespace allowed in the entire string\n" +
                    "- anything, at least eight places though")
    private String oldPassword;

    @NotBlank
    @Length(max = 1024)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}",
            message = "To create a stronger and more complex password, use passwords that include characters of the groups listed below:\n" +
                    "- a digit must occur at least once\n" +
                    "- a lower case letter must occur at least once\n" +
                    "- an upper case letter must occur at least once\n" +
                    "- a special character must occur at least once\n" +
                    "- no whitespace allowed in the entire string\n" +
                    "- anything, at least eight places though")
    private String newPassword;
}