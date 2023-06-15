package com.example.DoAnWebJava.support;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonAbstract {

    @Column
    private Date createdDate;

    @Column
    private Date modifiedDate;
}
