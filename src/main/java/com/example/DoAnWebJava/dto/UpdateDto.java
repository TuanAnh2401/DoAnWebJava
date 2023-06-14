package com.example.DoAnWebJava.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDto {
    private String password;
    private String name;
    private String avt;
    private String address;
}
