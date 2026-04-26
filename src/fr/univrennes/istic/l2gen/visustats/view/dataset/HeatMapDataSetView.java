package fr.univrennes.istic.l2gen.visustats.view.dataset;

import fr.univrennes.istic.l2gen.geometry.IShape;
import fr.univrennes.istic.l2gen.geometry.Point;
import fr.univrennes.istic.l2gen.geometry.base.Rectangle;
import fr.univrennes.istic.l2gen.geometry.base.Text;
import fr.univrennes.istic.l2gen.svg.color.Color;
import fr.univrennes.istic.l2gen.svg.interfaces.field.SVGField;
import fr.univrennes.istic.l2gen.svg.interfaces.tag.SVGTag;
import fr.univrennes.istic.l2gen.visustats.data.DataSet;

@SVGTag("g")
public class HeatMapDataSetView extends AbstractDataSetView {

    @SVGField("data-cell-size")
    private double cellSize;

    @SVGField("data-cell-spacing")
    private double cellSpacing;

    @SVGField("data-min-value")
    private double minValue;

    @SVGField("data-max-value")
    private double maxValue;

    private int dataSize;

    public HeatMapDataSetView() {
        super(new Point(0, 0));
        this.cellSize = 20;
        this.cellSpacing = 4;
        this.minValue = Double.NaN;
        this.maxValue = Double.NaN;
        this.dataSize = 0;
    }

    public HeatMapDataSetView(Point center, double cellSize, double cellSpacing) {
        this(center, cellSize, cellSpacing, Double.NaN, Double.NaN);
    }

    public HeatMapDataSetView(Point center, double cellSize, double cellSpacing, double minValue, double maxValue) {
        super(center);
        this.cellSize = cellSize;
        this.cellSpacing = cellSpacing;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.dataSize = 0;
    }

    @Override
    public void setData(DataSet data) {
        super.setData(data);

        this.dataSize = data.size();
        if (this.dataSize == 0) {
            return;
        }

        double effectiveMin = Double.isNaN(this.minValue) ? data.min() : this.minValue;
        double effectiveMax = Double.isNaN(this.maxValue) ? data.max() : this.maxValue;
        double range = Math.max(1e-9, effectiveMax - effectiveMin);
        if (range == 0) {
            range = 1e-9;
        }

        double totalHeight = getColumnHeight();
        double leftX = this.center.getX() - this.cellSize / 2;
        double topY = this.center.getY() - totalHeight / 2;

        for (int i = 0; i < this.dataSize; i++) {
            double value = data.getValue(i);
            double normalized = clamp((value - effectiveMin) / range, 0, 1);
            int[] fillRgb = interpolateBlueScale(normalized);

            double y = topY + i * (this.cellSize + this.cellSpacing);

            Rectangle cell = new Rectangle(leftX, y, this.cellSize, this.cellSize);
            cell.getStyle()
                    .fillColor(Color.rgb(fillRgb[0], fillRgb[1], fillRgb[2]))
                    .strokeColor(Color.BLACK)
                    .strokeWidth(1);
            this.elements.add(cell);

            Text valueText = new Text(leftX + this.cellSize / 2, y + this.cellSize / 2, formatValue(value));
            double luminance = 0.299 * fillRgb[0] + 0.587 * fillRgb[1] + 0.114 * fillRgb[2];
            valueText.getStyle()
                    .fontFamily("Arial")
                    .fontSize(Math.max(8, this.cellSize * 0.35))
                    .textAnchor("middle")
                    .alignmentBaseline("middle")
                    .fillColor(luminance < 130 ? Color.WHITE : Color.BLACK);
            this.elements.add(valueText);
        }
    }

    private String formatValue(double value) {
        if (Math.abs(value - Math.rint(value)) < 0.05) {
            return String.format("%.0f", value);
        }
        return String.format("%.1f", value);
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private int[] interpolateBlueScale(double t) {
        int[] start = { 239, 243, 255 };
        int[] end = { 8, 81, 156 };

        int r = (int) Math.round(start[0] + (end[0] - start[0]) * t);
        int g = (int) Math.round(start[1] + (end[1] - start[1]) * t);
        int b = (int) Math.round(start[2] + (end[2] - start[2]) * t);
        return new int[] { r, g, b };
    }

    private double getColumnHeight() {
        return this.dataSize * this.cellSize + Math.max(0, this.dataSize - 1) * this.cellSpacing;
    }

    @Override
    public IShape copy() {
        return new HeatMapDataSetView((Point) this.center.copy(), this.cellSize, this.cellSpacing, this.minValue,
                this.maxValue);
    }

    @Override
    public String getDescription(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(Math.max(0, indent)));
        sb.append("HeatMapView");
        return sb.toString();
    }

    @Override
    public double getHeight() {
        if (this.dataSize == 0) {
            return 0;
        }
        return getColumnHeight();
    }

    @Override
    public double getWidth() {
        if (this.dataSize == 0) {
            return 0;
        }
        return this.cellSize;
    }
}
