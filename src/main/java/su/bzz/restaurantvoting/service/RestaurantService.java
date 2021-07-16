package su.bzz.restaurantvoting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.repository.RestaurantRepository;
import su.bzz.restaurantvoting.util.exception.IllegalRequestDataException;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant getRestaurant(int restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(new Supplier<IllegalRequestDataException>() {
                    @Override
                    public IllegalRequestDataException get() {
                        log.error("Not found restaurant with id {}", restaurantId);
                        return new IllegalRequestDataException("Not found restaurant with id " + restaurantId);
                    }
                });
    }
}
