package su.bzz.restaurantvoting;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import su.bzz.restaurantvoting.model.Restaurant;
import su.bzz.restaurantvoting.util.JsonUtil;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestUtil {

    public static void assertEquals(List<Restaurant> actual, List<Restaurant> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertEquals(Restaurant actual, Restaurant expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static List<Restaurant> asRestaurants(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValues(jsonActual, Restaurant.class);
    }

    public static Restaurant asRestaurant(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, Restaurant.class);
    }

    public static ResultMatcher jsonMatcher(List<Restaurant> expected, BiConsumer<List<Restaurant>, List<Restaurant>> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asRestaurants(mvcResult), expected);
    }

    public static ResultMatcher jsonMatcher(Restaurant expected, BiConsumer<Restaurant, Restaurant> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asRestaurant(mvcResult), expected);
    }

}
