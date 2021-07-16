package su.bzz.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

@ToString
public class ValidList<E> {
    @JsonValue
    @Valid
    @NotNull
    @Size(min = 1, message = "There must be at least one dish")
    private List<E> values;

    @JsonCreator
    public ValidList(E... items) {
        this.values = Arrays.asList(items);
    }

    public List<E> getValues() {
        return values;
    }

    public void setValues(List<E> values) {
        this.values = values;
    }
}
