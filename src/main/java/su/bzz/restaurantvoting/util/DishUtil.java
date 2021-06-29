package su.bzz.restaurantvoting.util;

import su.bzz.restaurantvoting.model.Dish;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.to.DishTo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DishUtil {
    public static Dish getDish(DishTo dishTo, Restaurant restaurant) {
        return new Dish(dishTo.getName(), dishTo.getPriceInDollars(), LocalDate.now(), restaurant);
    }

    public static DishTo getDishTo(Dish dish) {
        return new DishTo(dish.getName(), dish.getPriceInDollars());
    }

    public static List<Dish> getDishes(List<DishTo> dishTos, Restaurant restaurant) {
        return dishTos.stream().map(d -> getDish(d, restaurant)).collect(Collectors.toList());
    }
}
