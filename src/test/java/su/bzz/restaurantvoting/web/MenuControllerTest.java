package su.bzz.restaurantvoting.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import su.bzz.restaurantvoting.MenuTestUtil;
import su.bzz.restaurantvoting.model.Dish;
import su.bzz.restaurantvoting.repository.DishRepository;
import su.bzz.restaurantvoting.to.DishTo;
import su.bzz.restaurantvoting.to.ValidList;
import su.bzz.restaurantvoting.util.exception.IllegalRequestDataException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static su.bzz.restaurantvoting.MenuTestUtil.*;
import static su.bzz.restaurantvoting.TestData.*;
import static su.bzz.restaurantvoting.util.DateUtil.atStartOfNextDay;
import static su.bzz.restaurantvoting.util.DateUtil.isTimeVoting;
import static su.bzz.restaurantvoting.util.JsonUtil.writeValue;
import static su.bzz.restaurantvoting.web.MenuController.URL_MENU;


class MenuControllerTest extends AbstractControllerTest {

    @Autowired
    DishRepository dishRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantIdByToday() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_MENU + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(menusRestaurant1, MenuTestUtil::assertEquals));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByRestaurantWhereIdNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_MENU + "/500"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllMenuToday() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_MENU))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(menus, MenuTestUtil::assertEquals));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createMenuTodayOrNextDayIfVotingTimeIsEnd() throws Exception {
        int idRestaurant = 2;
        final ValidList<DishTo> newMenu = new ValidList<>(dishTo5, dishTo6);
        List<Dish> newDishes = asDishes(perform(MockMvcRequestBuilders.post(URL_MENU + "/" + idRestaurant)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn());

        Integer newIdDish1 = newDishes.get(0).getId();
        Integer newIdDish2 = newDishes.get(1).getId();

        Dish dish1InDB = getDish(newIdDish1);
        Dish dish2InDB = getDish(newIdDish2);

        assertThat(dish1InDB.getRestaurant().getId()).isEqualTo(idRestaurant);
        assertThat(dish2InDB.getRestaurant().getId()).isEqualTo(idRestaurant);

        assertThat(dish1InDB.getName()).isEqualTo(dishTo5.getName());
        assertThat(dish2InDB.getName()).isEqualTo(dishTo6.getName());

        assertThat(dish1InDB.getPriceInDollars()).isEqualTo(dishTo5.getPriceInDollars());
        assertThat(dish2InDB.getPriceInDollars()).isEqualTo(dishTo6.getPriceInDollars());

        if (isTimeVoting()) {
            assertThat(dish1InDB.getDate()).isEqualTo(LocalDate.now());
        } else {
            assertThat(dish1InDB.getDate()).isEqualTo(atStartOfNextDay());
        }
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createMenuWithDate() throws Exception {
        int idRestaurant = 2;
        final ValidList<DishTo> newMenu = new ValidList<>(dishTo5WithDate, dishTo6WithDate);
        List<Dish> newDishes = asDishes(perform(MockMvcRequestBuilders
                .post(URL_MENU + "/" + idRestaurant + "/every-day")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn());

        Integer newIdDish1 = newDishes.get(0).getId();
        Integer newIdDish2 = newDishes.get(1).getId();

        Dish dish1InDB = getDish(newIdDish1);
        Dish dish2InDB = getDish(newIdDish2);

        assertThat(dish1InDB.getRestaurant().getId()).isEqualTo(idRestaurant);
        assertThat(dish2InDB.getRestaurant().getId()).isEqualTo(idRestaurant);

        assertThat(dish1InDB.getName()).isEqualTo(dishTo5.getName());
        assertThat(dish2InDB.getName()).isEqualTo(dishTo6.getName());

        assertThat(dish1InDB.getPriceInDollars()).isEqualTo(dishTo5.getPriceInDollars());
        assertThat(dish2InDB.getPriceInDollars()).isEqualTo(dishTo6.getPriceInDollars());

        assertThat(dish1InDB.getDate()).isEqualTo(dishTo5WithDate.getDate());
        assertThat(dish2InDB.getDate()).isEqualTo(dishTo6WithDate.getDate());
    }

//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void createMenuWithInvalidValue() throws Exception {
//        final ValidList<DishTo> newMenu = new ValidList<>(dishToInvalid);
//        perform(MockMvcRequestBuilders.post(URL_MENU + "/2")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(writeValue(newMenu)))
//                .andDo(print())
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("$.violations[0].message").value("size must be between 3 and 128"))
//                .andExpect(jsonPath("$.violations[1].message").value("must be between 1 and 500"));
//    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createMenuWithEqualsNameOnSameDay() throws Exception {
        final ValidList<DishTo> newMenu = new ValidList<>(dishTo5WithDate, dishTo5WithDateCopy);
        perform(MockMvcRequestBuilders.post(URL_MENU + "/2/every-day")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMenu)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.violations[0].message").value("The names of the dishes " +
                        "must be different on the same day "));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteDish() throws Exception {
        int dishId = 2;
        perform(MockMvcRequestBuilders.delete(URL_MENU + "/dish/" + dishId))
                .andDo(print())
                .andExpect(status().isNoContent());

        Assertions.assertFalse(dishRepository.findById(dishId).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDish() throws Exception {
        int idDish = dish1.id();
        Dish newDish = asDish(perform(MockMvcRequestBuilders.put(URL_MENU + "/dish/" + idDish)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(dishForUpdate)))
                .andDo(print())
                .andExpect(status().isNoContent()).andReturn());

        assertThat(newDish.getName()).isEqualTo(dishForUpdate.getName());
        assertThat(newDish.getPriceInDollars()).isEqualTo(dishForUpdate.getPriceInDollars());

        Dish dishInDb = getDish(idDish);
        assertThat(newDish.getName()).isEqualTo(dishInDb.getName());
        assertThat(newDish.getPriceInDollars()).isEqualTo(dishInDb.getPriceInDollars());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDishWithInvalidValue() throws Exception {
        perform(MockMvcRequestBuilders.put(URL_MENU + "/dish/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(dishToInvalid)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_MENU))
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = USER_MAIL)
    @Test
    void putUsersAuth() throws Exception {
        perform(MockMvcRequestBuilders.put(URL_MENU))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_MENU))
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = USER_MAIL)
    @Test
    void postUserAuth() throws Exception {
        perform(MockMvcRequestBuilders.put(URL_MENU))
                .andExpect(status().isForbidden());
    }

    private Dish getDish(Integer dishId) {
        return dishRepository.getDishById(dishId)
                .orElseThrow(() -> new IllegalRequestDataException("Not found dish with id " + dishId));
    }
}