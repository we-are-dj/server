package com.dj.server.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ErrorResponseDTO {
    private String message;
    private Integer errorCode;
    private HttpStatus httpStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors;

    private ErrorResponseDTO() {}

    public ErrorResponseDTO(String message, Integer errorCode, HttpStatus httpStatus, List<CustomFieldError> customFieldErrors) {
        this.message = message;
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.customFieldErrors = customFieldErrors;
    }

    public static ErrorResponseDTO.ErrorResponseDTOBuilder builder() {
        return new ErrorResponseDTO.ErrorResponseDTOBuilder();
    }

    // builder pattern
    public static class ErrorResponseDTOBuilder {
        private String message;
        private Integer errorCode;
        private HttpStatus httpStatus;
        private List<CustomFieldError> customFieldErrors;

        private ErrorResponseDTOBuilder() {}

        public ErrorResponseDTO.ErrorResponseDTOBuilder errorCode(Integer code) {
            this.errorCode = code;
            return this;
        }

        public ErrorResponseDTO.ErrorResponseDTOBuilder httpStatus(HttpStatus status) {
            this.httpStatus = status;
            return this;
        }

        public ErrorResponseDTO.ErrorResponseDTOBuilder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * errors 메서드는 (@Valid 또는 @Validated)와 BindingResult를 사용할 때만 사용해야 합니다.
         *
         * @param errors BindingResult.getFieldErrors() 메소드를 통해 전달받은 fieldErrors
         * @return 빌더 메서드 체이닝 ErrorResponseDTO
         */
        public ErrorResponseDTO.ErrorResponseDTOBuilder errors(Errors errors) {
            setCustomFieldErrors(errors.getFieldErrors());
            return this;
        }

        /**
         * BindingResult.getFieldErrors() 메소드를 통해 전달받은 fieldErrors
         */
        private void setCustomFieldErrors(List<FieldError> fieldErrors) {

            customFieldErrors = new ArrayList<>();

            for (FieldError fieldError : fieldErrors) {
                customFieldErrors.add(new CustomFieldError(
                                        Objects.requireNonNull(fieldError.getCodes())[0],
                                        fieldError.getRejectedValue(),
                                        fieldError.getDefaultMessage()));
            }
        }

        public ErrorResponseDTO build() { return new ErrorResponseDTO(this.message,this.errorCode, this.httpStatus, this.customFieldErrors); }
    }

    /**
     *  // @Valid에 의한 parameter 검증에 통과하지 못한 필드가 담긴 클래스.
     */
    @Getter
    @RequiredArgsConstructor
    public static class CustomFieldError {
        private final String field;
        private final Object value;
        private final String reason;
    }
}
