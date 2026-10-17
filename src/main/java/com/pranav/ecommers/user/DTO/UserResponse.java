package com.pranav.ecommers.user.DTO;

import com.pranav.ecommers.address.DTO.AddressDTO;
import com.pranav.ecommers.user.entity.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO address;
}
