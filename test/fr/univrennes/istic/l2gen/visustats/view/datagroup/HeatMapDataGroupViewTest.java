package fr.univrennes.istic.l2gen.visustats.view.datagroup;

import fr.univrennes.istic.l2gen.geometry.Point;

public class HeatMapDataGroupViewTest extends AbstractDataGroupViewTest<HeatMapDataGroupView> {

    @Override
    public HeatMapDataGroupView create() {
        return new HeatMapDataGroupView(createDataGroup(3, 10, 150), new Point(500, 500), 20, 20, 4, true);
    }
}
