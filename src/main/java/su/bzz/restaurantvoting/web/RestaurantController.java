package su.bzz.restaurantvoting.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.repository.RestaurantRepository;
import su.bzz.restaurantvoting.util.exception.IllegalRequestDataException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static su.bzz.restaurantvoting.web.RestaurantController.URL_RESTAURANT;

@RestController
@RequestMapping(value = URL_RESTAURANT, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Validated
public class RestaurantController {

    public static final String URL_RESTAURANT = "/api/restaurant";
    private final RestaurantRepository restaurantRepository;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get restaurants");
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable Integer id) {
        log.info("get restaurant by id: " + id);
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalRequestDataException("Not found restaurant with id " + id));
    }

    @PostMapping
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);

        Restaurant newRestaurant = restaurantRepository.save(restaurant);
        int restaurantId = newRestaurant.id();

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL_RESTAURANT + "/" + restaurantId)
                .buildAndExpand(restaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant by id: " + id);
        restaurantRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Restaurant update(@PathVariable int id, @Valid @RequestBody Restaurant restaurant) {
        log.info("update restaurant by id: " + id);
        Restaurant restaurantUpdate = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalRequestDataException("Not found restaurant with id " + id));
        restaurantUpdate.setName(restaurant.getName());
        return restaurantRepository.save(restaurantUpdate);
    }
}
