package su.bzz.restaurantvoting.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.repository.DishRepository;
import su.bzz.restaurantvoting.repository.RestaurantRepository;
import su.bzz.restaurantvoting.to.DishTo;
import su.bzz.restaurantvoting.to.Menu;
import su.bzz.restaurantvoting.util.MenuUtil;

import java.util.List;

import static su.bzz.restaurantvoting.util.DishUtil.getDishes;

@RestController
@RequestMapping("/api/restaurant")
@AllArgsConstructor
@Slf4j
public class MenuController {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    @GetMapping("/{restaurantId}/menu")
    public List<Menu> getAllByRestaurantIdByToday(@PathVariable Integer restaurantId) {
        log.info("get All By RestaurantId {} By Today", restaurantId);
        return MenuUtil.getMenus(dishRepository.findAllByRestaurantIdByToday(restaurantId));
    }

    @GetMapping("/all-menu-today")
    public List<Menu> getAllMenuToday() {
        log.info("Get all menu today");
        return MenuUtil.getMenus(dishRepository.findAllByToday());
    }

    @PostMapping("/{restaurantId}/menu")
    public void createMenu(@RequestBody List<DishTo> dishesTo, @PathVariable Integer restaurantId) {
        log.info("create dish(es) {} for restaurantId: {}", dishesTo, restaurantId);

        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        dishRepository.saveAll(getDishes(dishesTo, restaurant));
    }
}
