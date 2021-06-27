package su.bzz.restaurantvoting.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import su.bzz.restaurantvoting.model.Dish;
import su.bzz.restaurantvoting.repository.DishRepository;

import java.util.List;

@RestController
@RequestMapping("/api/dish")
@AllArgsConstructor
@Slf4j
public class DishController {
    private final DishRepository repository;

    @GetMapping("/{id}")
    public List<Dish> getAllByRestaurantIdByToday(@PathVariable Integer id) {
        return repository.findAllByRestaurantIdByToday(id);
    }

    @GetMapping
    public List<Dish> getAll() {
        return repository.findAll();
    }
}
