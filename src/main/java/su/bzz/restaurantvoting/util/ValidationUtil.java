package su.bzz.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import su.bzz.restaurantvoting.to.Menu;
import su.bzz.restaurantvoting.util.exception.IllegalRequestDataException;

import java.util.List;

@UtilityClass
public class ValidationUtil {

    public static List<Menu> checkNotFound(List<Menu> menu, Integer id) {
        if (menu == null || menu.size() == 0) {
            throw new IllegalRequestDataException("Not found restaurant with id " + id);
        }
        return menu;
    }
}
