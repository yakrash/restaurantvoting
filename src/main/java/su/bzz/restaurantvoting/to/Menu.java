package su.bzz.restaurantvoting.to;

import lombok.*;
import su.bzz.restaurantvoting.model.Restaurant;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Menu implements Serializable {
    private Restaurant restaurant;
    private List<DishTo> dishes;

}
