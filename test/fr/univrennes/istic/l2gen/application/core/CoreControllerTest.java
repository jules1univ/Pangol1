package fr.univrennes.istic.l2gen.application.core;

import org.junit.Assert;
import org.junit.Test;

public class CoreControllerTest {

    @Test
    public void testAbstractControllerLifecycle() {
        class DummyController extends CoreController {
            boolean started;
            boolean stopped;

            @Override
            public void onStart() {
                started = true;
            }

            @Override
            public void onStop() {
                stopped = true;
            }
        }

        DummyController controller = new DummyController();
        controller.onStart();
        controller.onStop();

        Assert.assertTrue(controller.started);
        Assert.assertTrue(controller.stopped);
    }
}
