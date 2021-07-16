package su.bzz.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import su.bzz.restaurantvoting.to.Menu;
import su.bzz.restaurantvoting.util.exception.IllegalRequestDataException;

import java.util.List;

@UtilityClass
@Slf4j
public class ValidationUtil {

    public static List<Menu> checkNotFound(List<Menu> menu, Integer id) {
        if (menu == null || menu.size() == 0) {
            log.error("Not found menu of restaurant with id {}", id);
            throw new IllegalRequestDataException("Not found menu of restaurant with id " + id);
        }
        return menu;
    }
}
