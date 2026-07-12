package com.kevin.be_laptop4you.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST(
            HttpStatus.BAD_REQUEST,
            "INVALID_REQUEST",
            "Yêu cầu không hợp lệ"
    ),

    VALIDATION_ERROR(
            HttpStatus.BAD_REQUEST,
            "VALIDATION_ERROR",
            "Dữ liệu đầu vào không hợp lệ"
    ),

    BUSINESS_ERROR(
            HttpStatus.BAD_REQUEST,
            "BUSINESS_ERROR",
            "Không thể thực hiện thao tác"
    ),

    UNAUTHENTICATED(
            HttpStatus.UNAUTHORIZED,
            "UNAUTHENTICATED",
            "Bạn chưa đăng nhập"
    ),

    ACCESS_DENIED(
            HttpStatus.FORBIDDEN,
            "ACCESS_DENIED",
            "Bạn không có quyền thực hiện thao tác này"
    ),

    RESOURCE_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "RESOURCE_NOT_FOUND",
            "Không tìm thấy dữ liệu"
    ),

    RESOURCE_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "RESOURCE_ALREADY_EXISTS",
            "Dữ liệu đã tồn tại"
    ),

    DATA_CONFLICT(
            HttpStatus.CONFLICT,
            "DATA_CONFLICT",
            "Dữ liệu đang được sử dụng hoặc bị xung đột"
    ),

    FILE_TOO_LARGE(
            HttpStatus.PAYLOAD_TOO_LARGE,
            "FILE_TOO_LARGE",
            "Dung lượng file vượt quá giới hạn"
    ),

    EXTERNAL_SERVICE_ERROR(
            HttpStatus.BAD_GATEWAY,
            "EXTERNAL_SERVICE_ERROR",
            "Dịch vụ bên ngoài đang gặp lỗi"
    ),

    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "INTERNAL_SERVER_ERROR",
            "Hệ thống đang gặp lỗi"
    );

    private final HttpStatus status;
    private final String code;
    private final String message;
}
