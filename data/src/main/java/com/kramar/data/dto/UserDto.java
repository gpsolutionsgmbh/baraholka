package com.kramar.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kramar.data.type.UserRole;
import com.kramar.data.type.UserStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(NON_NULL)
public class UserDto extends AbstractDto {

    @Length(max = 100)
    @NotBlank
    @Email(regexp = ".+@.+\\.[a-z]+")
    private String email;

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
    private String password;

    @Length(max = 50)
    @NotBlank
    private String userName;

    @Length(max = 50)
    @NotBlank
    private String userSurname;

    @NotNull
    private UserStatus status;

    private List<UserRole> userRoles;
}

