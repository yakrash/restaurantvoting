package su.bzz.restaurantvoting.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import su.bzz.restaurantvoting.model.Dish;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Cacheable("dish")
    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.restaurant.id=:id AND d.date = CURRENT_DATE()")
    List<Dish> findAllByRestaurantIdByToday(@Param("id") Integer id);

    @Cacheable("dish")
    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.date = CURRENT_DATE()")
    List<Dish> findAllByToday();

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.id=:dishId")
    Optional<Dish> getDishById(@Param("dishId") Integer dishId);

    @Transactional
    @Modifying
    @CacheEvict("dish")
    @Query("DELETE FROM Dish d WHERE d.id=:dishId")
    int deleteByIdDish(@Param("dishId") Integer dishId);
}
