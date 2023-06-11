package com.example.DoAnWebJava.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "tb_SystemSetting")
@RequiredArgsConstructor
@AllArgsConstructor
public class SystemSetting {
    @Id
    @Size(max = 50)
    private String settingKey;

    @Size(max = 4000)
    private String settingValue;
}

