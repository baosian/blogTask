package baosian.task;

import lombok.Data;

import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class User {

    private Integer id;
    @NotBlank(message = "enter first name")
    private String first_name;
    @NotBlank(message = "enter last name")
    private String last_name;
    @NotBlank(message = "enter password name")
    @Size(min=6, message = "Password must contain at least 6 characters")
    private String password;
    @NotBlank(message = "enter e-mail")
    @Email
    private String email;
    private Date created_at;


}
