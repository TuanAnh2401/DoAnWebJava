package com.example.DoAnWebJava.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePaging<T> {
    private T data;
    private int totalPages;
    private int currentPage;
    private int totalItems;
}

