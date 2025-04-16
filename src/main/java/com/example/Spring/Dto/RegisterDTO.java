package com.example.Spring.Dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotBlank(message = "Username không được để trống")
    private String username;

    @NotBlank(message = "Password không được để trống")
    private String password;

    @NotBlank(message = "Vui lòng nhập lại mật khẩu")
    private String passwordCheck;

    @NotBlank(message = "Fullname không được để trống")
    private String fullname;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ!")
    private String email;
}
