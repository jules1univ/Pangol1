package fr.univrennes.istic.l2gen.visustats.view.datagroup;

import fr.univrennes.istic.l2gen.geometry.IShape;
import fr.univrennes.istic.l2gen.geometry.Point;
import fr.univrennes.istic.l2gen.svg.interfaces.field.SVGField;
import fr.univrennes.istic.l2gen.svg.interfaces.tag.SVGTag;
import fr.univrennes.istic.l2gen.visustats.data.DataGroup;
import fr.univrennes.istic.l2gen.visustats.view.dataset.HeatMapDataSetView;
import fr.univrennes.istic.l2gen.visustats.view.dataset.IDataSetView;

@SVGTag("g")
public class HeatMapDataGroupView extends AbstractDataGroupView {

    @SVGField("data-cell-size")
    private double cellSize;

    @SVGField("data-cell-spacing")
    private double cellSpacing;

    public HeatMapDataGroupView(DataGroup data, Point center, double spacing, double cellSize, double cellSpacing,
            boolean horizontalLegend) {
        super(data, center, spacing, horizontalLegend);
        this.cellSize = cellSize;
        this.cellSpacing = cellSpacing;
        this.update();
    }

    @Override
    protected double getTotalElementsHeight() {
        int maxSize = this.data.maxSize();
        if (maxSize == 0) {
            return 0;
        }
        return maxSize * this.cellSize + Math.max(0, maxSize - 1) * this.cellSpacing;
    }

    @Override
    protected double getElementWidth() {
        return this.cellSize;
    }

    @Override
    protected IDataSetView createElement(Point position) {
        return new HeatMapDataSetView(position, this.cellSize, this.cellSpacing, this.data.min(), this.data.max());
    }

    @Override
    public boolean isAxisEnabled() {
        return false;
    }

    @Override
    protected IShape getAxisElement() {
        return null;
    }
}
