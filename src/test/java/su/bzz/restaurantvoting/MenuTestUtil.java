package su.bzz.restaurantvoting;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import su.bzz.restaurantvoting.model.Dish;
import su.bzz.restaurantvoting.to.Menu;
import su.bzz.restaurantvoting.util.JsonUtil;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuTestUtil {

    public static void assertEquals(List<Menu> actual, List<Menu> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static List<Menu> asMenus(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValues(jsonActual, Menu.class);
    }

    public static List<Dish> asDishes(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValues(jsonActual, Dish.class);
    }

    public static Dish asDish(MvcResult mvcResult) throws IOException {
        String jsonActual = mvcResult.getResponse().getContentAsString();
        return JsonUtil.readValue(jsonActual, Dish.class);
    }

    public static ResultMatcher jsonMatcher(List<Menu> expected, BiConsumer<List<Menu>, List<Menu>> equalsAssertion) {
        return mvcResult -> equalsAssertion.accept(asMenus(mvcResult), expected);
    }
}
