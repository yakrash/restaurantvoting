package su.bzz.restaurantvoting.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import su.bzz.restaurantvoting.model.Dish;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.repository.DishRepository;
import su.bzz.restaurantvoting.repository.RestaurantRepository;
import su.bzz.restaurantvoting.to.DishTo;
import su.bzz.restaurantvoting.to.Menu;
import su.bzz.restaurantvoting.to.ValidList;
import su.bzz.restaurantvoting.util.MenuUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static su.bzz.restaurantvoting.util.DishUtil.*;
import static su.bzz.restaurantvoting.util.ValidationUtil.checkNotFound;
import static su.bzz.restaurantvoting.web.MenuController.URL_MENU;

@RestController
@RequestMapping(value = URL_MENU, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Validated
public class MenuController {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    public static final String URL_MENU = "/api/restaurant";

    @GetMapping("/{restaurantId}/menu")
    public List<Menu> getAllByRestaurantIdByToday(@PathVariable Integer restaurantId) {
        log.info("get All By RestaurantId {} By Today", restaurantId);
        List<Menu> menu = MenuUtil.getMenus(dishRepository.findAllByRestaurantIdByToday(restaurantId));
        return checkNotFound(menu, restaurantId);
    }

    @GetMapping("/all-menu-today")
    public List<Menu> getAllMenuToday() {
        log.info("Get all menu today");
        return MenuUtil.getMenus(dishRepository.findAllByToday());
    }

    //    https://stackoverflow.com/a/64061936/15422633
    @PostMapping("/{restaurantId}/menu")
    public ResponseEntity<List<Dish>> createMenuTodayOrNextDayIfVotingTimeIsEnd(
            @Valid @RequestBody ValidList<DishTo> dishesTo,
            @PathVariable Integer restaurantId) {

        log.info("create dish(es) {} for restaurantId: {}", dishesTo, restaurantId);

        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        List<Dish> dishes = dishRepository.saveAll(getDishesFromListDishToToday(dishesTo, restaurant));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL_MENU + "/{restaurantId}/menu")
                .buildAndExpand(restaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(dishes);
    }

    @PostMapping("/{restaurantId}/menu-every-day")
    public ResponseEntity<List<Dish>> createMenuWithDate(
            @Valid @RequestBody ValidList<DishTo> dishesTo,
            @PathVariable Integer restaurantId) {

        log.info("create dish(es) {} for restaurantId: {}", dishesTo, restaurantId);

        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        List<Dish> dishes = dishRepository.saveAll(getDishesFromListDishToWithDate(dishesTo, restaurant));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL_MENU + "/{restaurantId}/menu")
                .buildAndExpand(restaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(dishes);
    }

    @DeleteMapping("/dish/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable Integer dishId) {
        log.info("Delete dish with id {}", dishId);
        dishRepository.deleteById(dishId);
    }

    @PutMapping("/dish/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Dish updateDish(@PathVariable Integer dishId, @Valid @RequestBody DishTo dishTo) {
        log.info("Update dish with id {}", dishId);
        Dish dish = dishRepository.getDishById(dishId);
        setDishFromDishTo(dishTo, dish);
        return dishRepository.save(dish);
    }
}
