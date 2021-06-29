package su.bzz.restaurantvoting.util;

import org.junit.jupiter.api.Test;
import su.bzz.restaurantvoting.to.Menu;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static su.bzz.restaurantvoting.TestData.dishesAll;
import static su.bzz.restaurantvoting.TestData.menus;
import static su.bzz.restaurantvoting.util.MenuUtil.getMenus;

class MenuUtilTest {

    @Test
    void methodGetMenus() {
        List<Menu> actualMenu = getMenus(dishesAll);
        assertThat(menus).usingRecursiveComparison().isEqualTo(actualMenu);
    }
}