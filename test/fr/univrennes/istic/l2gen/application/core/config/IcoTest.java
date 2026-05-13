package fr.univrennes.istic.l2gen.application.core.config;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class IcoTest {

    @Test
    public void testGetIconInDarkMode() {
        Config.DARK_MODE = true;

        FlatSVGIcon icon = Ico.get("home.svg");

        assertEquals("home_light.svg", icon.getName());
    }

    @Test
    public void testGetIconInLightMode() {
        Config.DARK_MODE = false;

        FlatSVGIcon icon = Ico.get("settings.svg");

        assertEquals("settings_dark.svg", icon.getName());
    }

    @Test
    public void testIconSize() {
        FlatSVGIcon icon = Ico.get("test.svg");

        assertEquals(14, icon.getIconWidth());
        assertEquals(14, icon.getIconHeight());
    }
}
