package com.pranav.ecommers.user.DTO;

import com.pranav.ecommers.address.DTO.AddressDTO;
import com.pranav.ecommers.user.entity.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private AddressDTO address;
}
