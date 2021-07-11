package su.bzz.restaurantvoting;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import su.bzz.restaurantvoting.model.Dish;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.to.DishTo;
import su.bzz.restaurantvoting.to.Menu;
import su.bzz.restaurantvoting.util.JsonUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static su.bzz.restaurantvoting.util.DishUtil.getDishesToFromListDish;

public class TestData {
    public static final LocalDate dateForDish = LocalDate.parse("2021-07-11");

    public static final Restaurant restaurant1 = new Restaurant(1, "Milk&Meat");
    public static final Restaurant restaurant2 = new Restaurant(2, "BBQ Ribs");

    public static final Dish dish1 = new Dish(1, "Soup", 5L, dateForDish, restaurant1);
    public static final Dish dish2 = new Dish(2, "Steak", 10L, dateForDish, restaurant1);
    public static final Dish dish3 = new Dish(3, "Dessert", 12L, dateForDish, restaurant1);
    public static final Dish dish4 = new Dish(5, "Pizza", 15L, dateForDish, restaurant2);

    public static final DishTo dishTo5 = new DishTo("Tea", 1L);
    public static final DishTo dishTo6 = new DishTo("Soup", 6L);
    public static final DishTo dishToInvalid = new DishTo("S", 6000L);
    public static final DishTo dishForUpdate = new DishTo("Steak", 10L);
    public static final DishTo dishTo5WithDate = new DishTo("Tea", 1L, dateForDish);
    public static final DishTo dishTo6WithDate = new DishTo("Soup", 6L, dateForDish);

    public static final List<Dish> dishesRestaurant1Today = List.of(dish1, dish2, dish3);
    public static final List<Dish> dishesRestaurant2Today = List.of(dish4);
    public static final List<Dish> dishesAllToday = List.of(dish1, dish2, dish3, dish4);

    public static final Menu menu1 = new Menu(restaurant1, getDishesToFromListDish(dishesRestaurant1Today));
    public static final Menu menu2 = new Menu(restaurant2, getDishesToFromListDish(dishesRestaurant2Today));

    public static final List<Menu> menus = List.of(menu1, menu2);
    public static final List<Menu> menusRestaurant1 = List.of(menu1);

    public static void assertEquals(List<Menu> actual, List<Menu> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static List<Menu> asMenus(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValues(jsonActual, Menu.class);
    }

    public static List<Dish> asDishes(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValues(jsonActual, Dish.class);
    }

    public static Dish asDish(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, Dish.class);
    }

    public static ResultMatcher jsonMatcher(List<Menu> expected, BiConsumer<List<Menu>, List<Menu>> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asMenus(mvcResult), expected);
    }
}
