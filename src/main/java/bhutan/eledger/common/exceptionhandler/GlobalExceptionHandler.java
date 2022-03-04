package bhutan.eledger.common.exceptionhandler;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.Violation;
import am.iunetworks.lib.common.validation.ViolationException;
import com.jsunsoft.http.ResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
@Log4j2
@RequiredArgsConstructor
//todo define error response structure, use ErrorsCollector to collect validation errors
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public String handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.trace(e.getMessage(), e);
        return "Request body is missing, incorrect or some request expectation like content-type is missing or incorrect:" +
                e.getMessage();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationError onConstraintValidationException(
            ConstraintViolationException e) {

        log.trace(e.getMessage());

        ValidationError error = new ValidationError();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            error.addViolation(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }

        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationError onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        log.trace(e.getMessage());

        ValidationError error = new ValidationError();

        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            error.addViolation(
                    new Violation(fe.getField(), fe.getDefaultMessage()));
        }

        return error;
    }

    @ExceptionHandler(ViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationError onViolationException(ViolationException e) {
        log.trace(e.getMessage());

        return e.getValidationError();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationError onMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.trace(e.getMessage());

        return new ValidationError(
                List.of(
                        new Violation(e.getName(), "Illegal value for parameter: [" + e.getName() + "]. The value: [" + e.getValue() + "] can't be converted to " + e.getParameter().getParameterType().getSimpleName())
                )
        );
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    String onRecordNotFoundException(RecordNotFoundException e) {
        log.trace(e.getMessage());

        return e.getHumanMessage();
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(ResponseException.class)
    @ResponseBody
    public String handleUnexpectedStatusCodeException(ResponseException e) {
        log.error(e.getMessage(), e);
        return HttpStatus.BAD_GATEWAY.getReasonPhrase();
    }
}
