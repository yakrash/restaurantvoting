package su.bzz.restaurantvoting.to;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DishTo implements Serializable {
    @NotBlank
    @Size(min = 3, max = 128)
    private String name;

    @NotNull
    @Range(min = 1, max = 500)
    private Long priceInDollars;

    private LocalDate date;

    private Integer id;

    public DishTo(String name, Long priceInDollars) {
        this.name = name;
        this.priceInDollars = priceInDollars;
    }

    public DishTo(String name, Long priceInDollars, LocalDate date) {
        this.name = name;
        this.priceInDollars = priceInDollars;
        this.date = date;
    }
}
