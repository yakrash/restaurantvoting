package su.bzz.restaurantvoting.util;

import javassist.NotFoundException;
import su.bzz.restaurantvoting.to.Menu;

import java.util.List;

public class ValidationUtil {

    public static List<Menu> checkNotFound(List<Menu> menu, Integer id) {
        if (menu == null || menu.size() == 0) {
            try {
                throw new NotFoundException("Not found restaurant with id " + id);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
        return menu;
    }
}
