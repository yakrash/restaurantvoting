package su.bzz.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.repository.RestaurantRepository;
import su.bzz.restaurantvoting.util.exception.IllegalRequestDataException;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant getRestaurant(int restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException("Not found restaurant with id " + restaurantId));
    }
}
