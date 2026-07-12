package com.kevin.be_laptop4you.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
     * Lỗi nghiệp vụ do chúng ta chủ động throw:
     * - Không tìm thấy dữ liệu
     * - Dữ liệu đã tồn tại
     * - Không đủ tồn kho
     * - Trạng thái đơn hàng không hợp lệ...
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiErrorResponse> handleAppException(
            AppException exception,
            HttpServletRequest request
    ) {
        ErrorCode errorCode = exception.getErrorCode();

        log.warn(
                "{} | {} | {} {} | {}",
                errorCode.getCode(),
                errorCode.getStatus().value(),
                request.getMethod(),
                request.getRequestURI(),
                exception.getMessage()
        );

        return buildResponse(
                errorCode.getStatus(),
                errorCode.getCode(),
                exception.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    /*
     * Lỗi validation của @Valid trong RequestBody.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        for (FieldError fieldError :
                exception.getBindingResult().getFieldErrors()) {

            errors.putIfAbsent(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }

        log.warn(
                "VALIDATION_ERROR | 400 | {} {} | {}",
                request.getMethod(),
                request.getRequestURI(),
                errors
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR.getCode(),
                ErrorCode.VALIDATION_ERROR.getMessage(),
                request.getRequestURI(),
                errors
        );
    }

    /*
     * Validation cho @RequestParam và @PathVariable.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        exception.getConstraintViolations().forEach(violation -> {
            String property = violation.getPropertyPath().toString();

            errors.putIfAbsent(
                    property,
                    violation.getMessage()
            );
        });

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR.getCode(),
                ErrorCode.VALIDATION_ERROR.getMessage(),
                request.getRequestURI(),
                errors
        );
    }

    /*
     * JSON sai cú pháp, sai kiểu dữ liệu hoặc enum không tồn tại.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadableJson(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        log.warn(
                "INVALID_REQUEST | 400 | {} {} | JSON không hợp lệ",
                request.getMethod(),
                request.getRequestURI()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ErrorCode.INVALID_REQUEST.getCode(),
                "JSON không đúng định dạng hoặc chứa kiểu dữ liệu không hợp lệ",
                request.getRequestURI(),
                null
        );
    }

    /*
     * Ví dụ API yêu cầu Long nhưng client truyền /brands/abc.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        errors.put(
                exception.getName(),
                "Giá trị '" + exception.getValue()
                        + "' không đúng kiểu dữ liệu"
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ErrorCode.INVALID_REQUEST.getCode(),
                "Tham số không hợp lệ",
                request.getRequestURI(),
                errors
        );
    }

    /*
     * Thiếu RequestParam bắt buộc.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingParameter(
            MissingServletRequestParameterException exception,
            HttpServletRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        errors.put(
                exception.getParameterName(),
                "Tham số này là bắt buộc"
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ErrorCode.INVALID_REQUEST.getCode(),
                "Thiếu tham số bắt buộc",
                request.getRequestURI(),
                errors
        );
    }

    /*
     * Vi phạm unique key, foreign key hoặc constraint database.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException exception,
            HttpServletRequest request
    ) {
        log.error(
                "DATA_CONFLICT | 409 | {} {} | {}",
                request.getMethod(),
                request.getRequestURI(),
                exception.getMostSpecificCause().getMessage()
        );

        return buildResponse(
                HttpStatus.CONFLICT,
                ErrorCode.DATA_CONFLICT.getCode(),
                "Dữ liệu đã tồn tại hoặc đang được dữ liệu khác sử dụng",
                request.getRequestURI(),
                null
        );
    }

    /*
     * Dung lượng file upload vượt giới hạn.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleMaxUploadSize(
            MaxUploadSizeExceededException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.PAYLOAD_TOO_LARGE,
                ErrorCode.FILE_TOO_LARGE.getCode(),
                ErrorCode.FILE_TOO_LARGE.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    /*
     * Gọi sai HTTP method.
     * Ví dụ endpoint dùng POST nhưng client gọi GET.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                "METHOD_NOT_ALLOWED",
                "Phương thức " + exception.getMethod()
                        + " không được hỗ trợ cho API này",
                request.getRequestURI(),
                null
        );
    }

    /*
     * Sai Content-Type.
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException exception,
            HttpServletRequest request
    ) {
        return buildResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "UNSUPPORTED_MEDIA_TYPE",
                "Content-Type không được hỗ trợ",
                request.getRequestURI(),
                null
        );
    }

    /*
     * IllegalArgumentException thường phát sinh khi tham số
     * truyền vào method không hợp lệ.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
            IllegalArgumentException exception,
            HttpServletRequest request
    ) {
        log.warn(
                "INVALID_REQUEST | 400 | {} {} | {}",
                request.getMethod(),
                request.getRequestURI(),
                exception.getMessage()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ErrorCode.INVALID_REQUEST.getCode(),
                exception.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    /*
     * Handler cuối cùng cho mọi lỗi chưa dự đoán được.
     *
     * Không trả exception.getMessage() cho frontend vì có thể
     * làm lộ thông tin database, source code hoặc hệ thống.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpectedException(
            Exception exception,
            HttpServletRequest request
    ) {
        log.error(
                "INTERNAL_SERVER_ERROR | 500 | {} {} | {}",
                request.getMethod(),
                request.getRequestURI(),
                exception.getMessage(),
                exception
        );

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                request.getRequestURI(),
                null
        );
    }

    /*
     * Hàm tạo response dùng chung.
     */
    private ResponseEntity<ApiErrorResponse> buildResponse(
            HttpStatus status,
            String code,
            String message,
            String path,
            Map<String, String> errors
    ) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .code(code)
                .message(message)
                .path(path)
                .errors(errors)
                .build();

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
