package fr.univrennes.istic.l2gen.visustats.view.dataset;

import fr.univrennes.istic.l2gen.geometry.Point;

public final class HeatMapDataViewTest extends AbstractDataSetViewTest<HeatMapDataSetView> {

    @Override
    public HeatMapDataSetView create() {
        return new HeatMapDataSetView(new Point(500, 500), 20, 4);
    }
}
