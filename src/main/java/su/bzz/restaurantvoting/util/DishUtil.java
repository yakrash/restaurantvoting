package su.bzz.restaurantvoting.util;

import lombok.extern.slf4j.Slf4j;
import su.bzz.restaurantvoting.model.Dish;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.to.DishTo;
import su.bzz.restaurantvoting.to.ValidList;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static su.bzz.restaurantvoting.util.DateUtil.atStartOfNextDay;
import static su.bzz.restaurantvoting.util.DateUtil.isTimeVoting;

@Slf4j
public class DishUtil {
    public static Dish getDishFromDishToToday(DishTo dishTo, Restaurant restaurant) {
        LocalDate localDate = isTimeVoting() ? LocalDate.now() : atStartOfNextDay();
        log.info("get Dish From DishTo on date {}", localDate);
        return new Dish(dishTo.getName(), dishTo.getPriceInDollars(), localDate, restaurant);
    }

    public static Dish setDishFromDishTo(DishTo dishTo, Dish dish) {
        if (dishTo.getDate() != null) {
            dish.setDate(dishTo.getDate());
        }
        dish.setName(dishTo.getName());
        dish.setPriceInDollars(dishTo.getPriceInDollars());
        return dish;
    }

    public static List<Dish> getDishesFromListDishToToday(ValidList<DishTo> dishTos, Restaurant restaurant) {
        log.info("getDishesFromListDishToToday");
        return dishTos.getValues().stream().map(d -> getDishFromDishToToday(d, restaurant)).collect(Collectors.toList());
    }

    public static List<DishTo> getDishesToFromListDish(List<Dish> dishes) {
        log.info("getDishesToFromListDish");
        return dishes.stream().map(DishUtil::getDishToFromDish).collect(Collectors.toList());
    }

    public static DishTo getDishToFromDish(Dish dish) {
        return new DishTo(dish.getName(), dish.getPriceInDollars(), dish.getDate(), dish.id());
    }
}
