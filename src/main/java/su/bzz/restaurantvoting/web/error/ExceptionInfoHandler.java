package su.bzz.restaurantvoting.web.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import su.bzz.restaurantvoting.util.exception.ValidationErrorResponse;
import su.bzz.restaurantvoting.util.exception.Violation;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionInfoHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onDataIntegrityViolationException(
            DataIntegrityViolationException e
    ) {
        Violation violation;
        if (e.getMessage().contains("VOTE_UNIQUE_IDX")) {
            violation = new Violation("RESTAURANT_ID", "You can vote only once a day, " +
                    "but you can update your decision before 11:00 ");
        } else if (e.getMessage().contains("DISH_UNIQUE_IDX")) {
            violation = new Violation("Name", "The names of the dishes must be different on the same day ");
        } else {
            violation = new Violation(e.getLocalizedMessage(), e.getMessage());
        }
        final List<Violation> violations = List.of(violation);
        return new ValidationErrorResponse(violations);
    }
}
