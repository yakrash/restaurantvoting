package su.bzz.restaurantvoting;

import su.bzz.restaurantvoting.model.*;
import su.bzz.restaurantvoting.to.DishTo;
import su.bzz.restaurantvoting.to.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static su.bzz.restaurantvoting.util.DishUtil.getDishesToFromListDish;

public class TestData {
    public static final LocalDate dateToday = LocalDate.now();

    public static final Restaurant restaurant1 = new Restaurant(1, "Milk&Meat");
    public static final Restaurant restaurant2 = new Restaurant(2, "BBQ Ribs");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2);

    public static final Dish dish1 = new Dish(1, "Soup", 5L, dateToday, restaurant1);
    public static final Dish dish2 = new Dish(2, "Steak", 10L, dateToday, restaurant1);
    public static final Dish dish3 = new Dish(3, "Dessert", 12L, dateToday, restaurant1);
    public static final Dish dish4 = new Dish(4, "Dessert", 12L, LocalDate.parse("2021-07-10"), restaurant1);
    public static final Dish dish5 = new Dish(5, "Pizza", 15L, dateToday, restaurant2);

    public static final DishTo dishTo5 = new DishTo("Tea", 1L);
    public static final DishTo dishTo6 = new DishTo("Soup", 6L);
    public static final DishTo dishToInvalid = new DishTo("S", 6000L);
    public static final DishTo dishForUpdate = new DishTo("Steak", 10L);
    public static final DishTo dishTo5WithDate = new DishTo("Tea", 1L, dateToday);
    public static final DishTo dishTo6WithDate = new DishTo("Soup", 6L, dateToday);

    public static final List<Dish> dishesRestaurant1Today = List.of(dish1, dish2, dish3);
    public static final List<Dish> dishesRestaurant2Today = List.of(dish5);
    public static final List<Dish> dishesAllToday = List.of(dish1, dish2, dish3, dish5);

    public static final Menu menu1 = new Menu(restaurant1, getDishesToFromListDish(dishesRestaurant1Today));
    public static final Menu menu2 = new Menu(restaurant2, getDishesToFromListDish(dishesRestaurant2Today));

    public static final List<Menu> menus = List.of(menu1, menu2);
    public static final List<Menu> menusRestaurant1 = List.of(menu1);

    public static final String USER_MAIL = "user@gmail.com";
    public static final String USER2_MAIL = "user2@gmail.com";
    public static final String ADMIN_MAIL = "admin@bzz.su";
    public static final User user = new User(1, USER_MAIL, "User_First", "User_Last", "password", dateToday, Set.of(Role.USER));
    public static final User user2 = new User(2, USER2_MAIL, "2User_First2", "2User_Last2", "password", dateToday, Set.of(Role.USER));
//    public static final User admin = new User(3, ADMIN_MAIL, "Admin_First", "Admin_Last", "admin", dateToday, Set.of(Role.ADMIN, Role.USER));


    public static final Vote vote1 = new Vote(5, restaurant1, user, dateToday);
    public static final Vote voteUpdate = new Vote(2, restaurant2, user2, dateToday);
//    public static final Vote vote3 = new Vote(restaurant2, admin, dateToday);
}
