package fr.univrennes.istic.l2gen.visustats.view.datagroup;

import fr.univrennes.istic.l2gen.geometry.Group;
import fr.univrennes.istic.l2gen.geometry.IShape;
import fr.univrennes.istic.l2gen.geometry.Point;
import fr.univrennes.istic.l2gen.geometry.base.Rectangle;
import fr.univrennes.istic.l2gen.geometry.base.Text;
import fr.univrennes.istic.l2gen.svg.color.Color;
import fr.univrennes.istic.l2gen.svg.interfaces.field.SVGField;
import fr.univrennes.istic.l2gen.svg.interfaces.tag.SVGTag;
import fr.univrennes.istic.l2gen.visustats.data.DataGroup;
import fr.univrennes.istic.l2gen.visustats.data.DataSet;
import fr.univrennes.istic.l2gen.visustats.data.Label;
import fr.univrennes.istic.l2gen.visustats.view.dataset.IDataSetView;

@SVGTag("g")
public abstract class AbstractDataGroupView extends Group implements IDataGroupView {
    public static final double LEGEND_WIDTH = 25;
    public static final double LEGEND_HEIGHT = 10;
    public static final double LEGEND_SPACING = 50;

    protected DataGroup data;
    protected boolean horizontalLegend;

    @SVGField("data-spacing")
    protected double spacing;

    @SVGField({ "data-center-x", "data-center-y" })
    protected Point center;

    public AbstractDataGroupView(DataGroup data, Point center, double spacing, boolean horizontalLegend) {
        this.data = data;
        this.center = center;
        this.spacing = spacing;
        this.horizontalLegend = horizontalLegend;
    }

    protected abstract double getTotalElementsHeight();

    protected double getTotalElementsWidth() {
        return this.getElementWidth() * this.data.size() + this.spacing * Math.max(0, this.data.size() - 1);
    }

    protected abstract boolean isAxisEnabled();

    protected abstract IShape getAxisElement();

    protected abstract double getElementWidth();

    protected abstract IDataSetView createElement(Point position);

    private Point getElementCenterAt(int index) {
        double startX = center.getX() - (this.getTotalElementsWidth() / 2);
        double centerX = startX + index * (this.getElementWidth() + this.spacing) + this.getElementWidth() / 2;

        return new Point(centerX, this.center.getY());
    }

    private Group getLegendElement() {
        Group legends = new Group();

        for (int i = 0; i < this.data.legends().size(); i++) {
            Label label = this.data.legends().get(i);
            Color color = this.data.datasets().get(i).getColor(i);

            Point legendPosition;

            if (this.horizontalLegend) {
                legendPosition = new Point(
                        (center.getX() - this.getElementWidth()) + (i - 1) * (LEGEND_WIDTH + LEGEND_SPACING),
                        center.getY() + this.getTotalElementsHeight() / 2 + LEGEND_HEIGHT * 2);
            } else {
                legendPosition = new Point(
                        center.getX() + this.getTotalElementsWidth() / 2 + LEGEND_SPACING + LEGEND_WIDTH,
                        (center.getY()) + i * (LEGEND_HEIGHT + LEGEND_SPACING / 2));
            }

            Rectangle legendBox = new Rectangle(legendPosition, LEGEND_WIDTH, LEGEND_HEIGHT);
            legendBox.getStyle()
                    .fillColor(color)
                    .strokeColor(Color.BLACK)
                    .strokeWidth(1);
            legends.add(legendBox);

            Text legendLabel = label.createText(
                    new Point(legendPosition.getX() + LEGEND_WIDTH, legendPosition.getY() + LEGEND_HEIGHT * 2));
            legends.add(legendLabel);
        }

        return legends;

    }

    protected final void update() {
        this.elements.clear();

        if (this.data.size() == 0) {
            return;
        }

        for (int i = 0; i < this.data.size(); i++) {
            IDataSetView element = createElement(getElementCenterAt(i));
            element.setData(this.data.get(i));

            this.elements.add(element);
        }

        if (this.isAxisEnabled()) {
            this.elements.add(this.getAxisElement());
        }

        Point titlePoint = new Point(center.getX(), center.getY() - this.getTotalElementsHeight() * 0.85);
        this.elements.add(this.data.title().createTitle(titlePoint));
        this.elements.add(this.getLegendElement());
    }

    @Override
    public final void setTitle(Label title) {
        this.data = new DataGroup(this.data.datasets(), title, this.data.legends());
        this.update();
    }

    @Override
    public final void setData(DataGroup group) {
        this.data = group;
        this.update();
    }

    @Override
    public final void addData(DataSet data) {
        this.data.add(data);
        this.update();
    }

    @Override
    public final void addLegend(Label legend) {
        this.data.add(legend);
        this.update();
    }

    @Override
    public final void resize(double px, double py) {
        this.transform.scale(px, py);
    }

    @Override
    public final void rotate(double deg) {
        this.transform.rotate(deg, this.getCenter().getX(), this.getCenter().getY());
    }
}
