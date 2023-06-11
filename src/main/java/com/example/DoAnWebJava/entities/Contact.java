package com.example.DoAnWebJava.entities;

import com.example.DoAnWebJava.support.CommonAbstract;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "tb_Contact")
@RequiredArgsConstructor
@AllArgsConstructor
public class Contact extends CommonAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 150, message = "Không được vượt quá 150 ký tự")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Xin vui lòng nhập địa chỉ Email của bạn")
    @Email(message = "Xin vui lòng nhập đúng định dạng địa chỉ Email")
    private String email;

    @Pattern(regexp = "^(0|84)([0-9]{9})$", message = "Xin vui lòng nhập đúng định dạng số điện thoại")
    private String phoneNumber;

    @Size(max = 4000, message = "Tin nhắn quá dài")
    private String message;

    @Column
    private boolean isRead;

    @Column
    private boolean isActivate;

}

