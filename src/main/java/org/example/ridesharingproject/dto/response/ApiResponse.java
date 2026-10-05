package org.example.ridesharingproject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        int status,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String message,
        LocalDateTime timeStamp
) {
    public static <T> ApiResponse<T> success(T data,String message){
        return new ApiResponse<>(data,200,message,LocalDateTime.now());
    }

    public static<T> ApiResponse<T> error(int status,String message){
        return new ApiResponse<>(null,status,message,LocalDateTime.now());
    }
}

