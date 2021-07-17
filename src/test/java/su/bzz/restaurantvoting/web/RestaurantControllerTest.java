package su.bzz.restaurantvoting.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import su.bzz.restaurantvoting.RestaurantTestUtil;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.repository.RestaurantRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static su.bzz.restaurantvoting.RestaurantTestUtil.*;
import static su.bzz.restaurantvoting.TestData.*;
import static su.bzz.restaurantvoting.util.JsonUtil.writeValue;
import static su.bzz.restaurantvoting.web.RestaurantController.URL_RESTAURANT;

class RestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantRepository restaurantRepository;

    @WithUserDetails(value = USER_MAIL)
    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_RESTAURANT))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(restaurants, RestaurantTestUtil::assertEquals));
    }

    @WithUserDetails(value = USER_MAIL)
    @Test
    void getById() throws Exception {
        int id = restaurant1.id();
        perform(MockMvcRequestBuilders.get(URL_RESTAURANT + "/" + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonMatcher(restaurant1, RestaurantTestUtil::assertEquals));
    }

    @WithUserDetails(value = ADMIN_MAIL)
    @Test
    void create() throws Exception {
        Restaurant restaurantNew = new Restaurant("newRestaurant");
        Restaurant restaurantCreated = asRestaurant(perform(MockMvcRequestBuilders.post(URL_RESTAURANT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(restaurantNew)))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn());

        int id = restaurantCreated.id();
        restaurantNew.setId(id);
        assertEquals(restaurantNew, restaurantCreated);

        Restaurant restaurantDb = restaurantRepository.findById(id).orElseThrow();
        assertEquals(restaurantNew, restaurantDb);
    }

    @WithUserDetails(value = ADMIN_MAIL)
    @Test
    void delete() throws Exception {
        int id = restaurant1.id();
        perform(MockMvcRequestBuilders.delete(URL_RESTAURANT + "/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        Assertions.assertFalse(restaurantRepository.findById(id).isPresent());
    }

    @WithUserDetails(value = ADMIN_MAIL)
    @Test
    void update() throws Exception {
        int id = restaurant1.id();
        Restaurant restaurantUpdate = new Restaurant("RestaurantUpdate");
        Restaurant restaurantUpdated = asRestaurant(perform(MockMvcRequestBuilders
                .put(URL_RESTAURANT + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(restaurantUpdate)))
                .andDo(print())
                .andExpect(status().isNoContent()).andReturn());

        Assertions.assertEquals(restaurantUpdated.getName(), restaurantUpdate.getName());
        Restaurant restaurantDb = restaurantRepository.findById(id).orElseThrow();
        Assertions.assertEquals(restaurantUpdated.getName(), restaurantDb.getName());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_RESTAURANT))
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = USER_MAIL)
    @Test
    void putUsersAuth() throws Exception {
        perform(MockMvcRequestBuilders.put(URL_RESTAURANT))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL_RESTAURANT))
                .andExpect(status().isUnauthorized());
    }

    @WithUserDetails(value = USER_MAIL)
    @Test
    void postUserAuth() throws Exception {
        perform(MockMvcRequestBuilders.put(URL_RESTAURANT))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getRestaurantWithInvalidValue() throws Exception {
        perform(MockMvcRequestBuilders.get(URL_RESTAURANT + "/200"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value(422))
                .andExpect(jsonPath("$.message").value("422 UNPROCESSABLE_ENTITY " +
                        "\"Not found restaurant with id 200\""));
    }
}