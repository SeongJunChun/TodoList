package sw_semester.todolist.loginpackage.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {



    @Size(min = 2, max = 20)
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;


}
