package su.bzz.restaurantvoting.to;

import lombok.*;
import su.bzz.restaurantvoting.model.Restaurant;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
public class Menu implements Serializable {
    private final Restaurant restaurant;
    private final List<DishTo> dishes;
}
