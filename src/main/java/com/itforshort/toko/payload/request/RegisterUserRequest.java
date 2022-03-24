package com.itforshort.toko.payload.request;

import com.itforshort.toko.model.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class RegisterUserRequest {
    @NotNull
    @Size(min = 3, max = 20, message = "Username tidak boleh kurang dari 4 karakter dan tidak melebihi 20 karakter")
    private String username;

    @NotNull
    @Size(min = 9, max = 40)
    private String password;

    private Set<String> role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
