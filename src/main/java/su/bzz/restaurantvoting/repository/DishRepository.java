package su.bzz.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import su.bzz.restaurantvoting.model.Dish;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.restaurant.id=:id AND d.date = CURRENT_DATE()")
    List<Dish> findAllByRestaurantIdByToday(@Param("id") Integer id);

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.date = CURRENT_DATE()")
    List<Dish> findAllByToday();

    @Query("SELECT d FROM Dish d JOIN FETCH d.restaurant WHERE d.id=:dishId")
    Dish getDishById(@Param("dishId") Integer dishId);
}
