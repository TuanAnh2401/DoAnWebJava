package com.example.DoAnWebJava.validators;

import com.example.DoAnWebJava.validators.annotations.AllowHtml;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;


public class HtmlValidator implements ConstraintValidator<AllowHtml, String> {
    @Override
    public void initialize(AllowHtml constraintAnnotation) {
        // Không có việc khởi tạo cần thiết
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        // Kiểm tra tính hợp lệ của chuỗi HTML sử dụng Jsoup
        String cleanedHtml = Jsoup.clean(value, Whitelist.basic());

        // Kiểm tra nếu chuỗi HTML đã được làm sạch khỏi các thành phần không an toàn
        return value.equals(cleanedHtml);
    }
}




