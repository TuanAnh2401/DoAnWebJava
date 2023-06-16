package com.example.DoAnWebJava.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonAbstractDto {
    private Date createdDate;
    private Date modifiedDate;
}
