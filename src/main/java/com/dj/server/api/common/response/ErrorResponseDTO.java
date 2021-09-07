package com.dj.server.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ControllerAdvice 에서 에러를 핸들링하기 위해 사용되는 클래스.
 * # @Valid, @Validated 애너테이션 처리를 지원합니다.
 * builder를 통해 ErrorResposeDTO를 생성할 때
 * errors 변수에 BindingResult 객체를 전달하면
 *
 * # @NotBlank 등에서 에러가 발생했을 때
 * BindingResult.getFieldErrors() 즉 field, value, error reason 등을 클라이언트에 전달할 수 있습니다.
 * 간단한 예제가 아래의 클래스와 메서드에서 사용되었습니다.
 *
 * @see com.dj.server.api.member.model.dto.request.MemberSaveRequestDTO
 * @see com.dj.server.api.member.controller.MemberController .signUp()
 * @see com.dj.server.api.member.controller.MemberControllerAdvice
 *
 * # @Valid, @Validated 애너테이션을 사용하지 않아도 사용이 가능합니다.
 * 이때 customFieldErrors는 사용되지 않으므로, ErrorResponseDTO.builder() ".errors()" 를 사용할 수 없습니다.
 * # @Valid 를 사용하지 않는 경우의 예제가 아래 클래스에서 사용되었습니다.
 *
 * @see com.dj.server.api.common.controller.MainControllerAdvice
 *
 * @author informix
 * @since 0.0.1
 */
@Getter
public class ErrorResponseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("timestamp")
    private LocalDateTime now;
    private final String message;
    private final Integer errorCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("status")
    private HttpStatus httpStatus;
    /**
     * errors(BindingResult result)와 같은 방식을 통해 customFieldErrors에 값을 설정하면
     * errors라는 json key값이 에러처리 응답메시지에 포함되어 전달됩니다. 설정하지 않으면 errors json key값은 클라이언트에 전달되지 않습니다.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("errors")
    private List<CustomFieldError> customFieldErrors;

    private ErrorResponseDTO(ErrorResponseDTO.ErrorResponseDTOBuilder builder) {
        if (builder.displayNow) this.now = LocalDateTime.now();
        this.message = builder.message;
        this.errorCode = builder.errorCode;
        this.httpStatus = builder.httpStatus;
        this.customFieldErrors = builder.customFieldErrors;
    }

    public static ErrorResponseDTO.ErrorResponseDTOBuilder builder() {
        return new ErrorResponseDTO.ErrorResponseDTOBuilder();
    }

    // builder pattern
    public static class ErrorResponseDTOBuilder {
        private boolean displayNow = true;
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
         * errors 메서드는 (@Valid 또는 @Validated)와 BindingResult 를 사용할 때만 사용해야 합니다.
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

        /**
         * now (timestamp) 출력을 비활성화합니다.
         *
         * @return now (timestamp) false
         */
        public ErrorResponseDTO.ErrorResponseDTOBuilder offDisplayNow() {
            this.displayNow = false;
            return this;
        }

        public ErrorResponseDTO build() { return new ErrorResponseDTO(this); }
    }

    /**
     * # @Valid 또는 @Validated 에 의한 parameter 검증에 통과하지 못한 필드가 담긴 클래스.
     */
    @Getter
    @RequiredArgsConstructor
    public static class CustomFieldError {
        private final String field;
        private final Object value;
        private final String reason;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
