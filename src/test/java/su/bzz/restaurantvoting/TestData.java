package su.bzz.restaurantvoting;

import su.bzz.restaurantvoting.model.Dish;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.to.Menu;

import java.time.LocalDate;
import java.util.List;

public class TestData {

    public static final Restaurant restaurant1 = new Restaurant(1, "Milk&Meat");
    public static final Restaurant restaurant2 = new Restaurant(2, "BBQ Ribs");

    public static final Dish dish1 = new Dish("Soup", 3L, LocalDate.parse("2021-06-29"), restaurant1);
    public static final Dish dish2 = new Dish("Steak", 10L, LocalDate.parse("2021-06-29"), restaurant1);
    public static final Dish dish3 = new Dish("Dessert", 5L, LocalDate.parse("2021-06-29"), restaurant1);
    public static final Dish dish4 = new Dish("Soup", 5L, LocalDate.parse("2021-06-29"), restaurant2);

    public static final List<Dish> dishesRestaurant1 = List.of(dish1, dish2, dish3);
    public static final List<Dish> dishesRestaurant2 = List.of(dish4);
    public static final List<Dish> dishesAll = List.of(dish1, dish2, dish3, dish4);

    public static final Menu menu1 = new Menu(restaurant1, dishesRestaurant1);
    public static final Menu menu2 = new Menu(restaurant2, dishesRestaurant2);

    public static final List<Menu> menus = List.of(menu1, menu2);
}
