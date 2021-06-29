package su.bzz.restaurantvoting.to;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DishTo implements Serializable {
    private String name;
    private Long priceInDollars;
}
