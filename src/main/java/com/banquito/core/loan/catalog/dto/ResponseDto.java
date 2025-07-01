package com.banquito.core.loan.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto<T> {

    private boolean success;
    private String message;
    private T data;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, "Operación exitosa", data);
    }

    public static <T> ResponseDto<T> success(T data, String message) {
        return new ResponseDto<>(true, message, data);
    }

    public static <T> ResponseDto<T> error(String message) {
        return new ResponseDto<>(false, message, null);
    }
}
