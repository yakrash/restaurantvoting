package su.bzz.restaurantvoting.util;

import org.junit.jupiter.api.Test;
import su.bzz.restaurantvoting.to.Menu;

import java.util.List;

import static su.bzz.restaurantvoting.MenuTestUtil.assertEquals;
import static su.bzz.restaurantvoting.TestData.*;
import static su.bzz.restaurantvoting.util.MenuUtil.getMenus;

class MenuUtilTest {

    @Test
    void methodGetMenus() {
        List<Menu> actualMenu = getMenus(dishesAllToday);
        assertEquals(actualMenu, menus);
    }
}