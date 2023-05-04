package com.maid.cafe.c.maidcafec.Wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWrapper {

    private Integer id;
    private String contactNumber;
    private String email;
    private String name;
    private String status;

    public UserWrapper(Integer id, String name, String email, String contactNumber, String status){
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.status = status;
    }
}
