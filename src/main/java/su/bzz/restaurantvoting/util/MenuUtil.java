package su.bzz.restaurantvoting.util;

import lombok.extern.slf4j.Slf4j;
import su.bzz.restaurantvoting.model.Dish;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.to.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class MenuUtil {
    public static List<Menu> getMenus(List<Dish> dishes) {
        log.info("Get menu from dishes: {}", dishes);
        Map<Restaurant, List<Dish>> menuMap = dishes.stream().collect(Collectors.groupingBy(Dish::getRestaurant));
        List<Menu> menus = new ArrayList<>();

        for (Map.Entry<Restaurant, List<Dish>> menu : menuMap.entrySet()) {
            menus.add(getMenu(menu.getKey(), menu.getValue()));
        }
        return menus;
    }

    public static Menu getMenu(Restaurant restaurant, List<Dish> dishes) {
        return new Menu(restaurant, dishes);
    }
}
