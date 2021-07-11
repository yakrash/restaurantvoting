package su.bzz.restaurantvoting.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ValidationErrorResponse {

    private final List<Violation> violations;

}

