package fr.univrennes.istic.l2gen.application.core.notebook;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.naming.SizeLimitExceededException;

import org.duckdb.DuckDBConnection;

import fr.univrennes.istic.l2gen.application.core.config.Log;
import fr.univrennes.istic.l2gen.application.core.filter.Filter;
import fr.univrennes.istic.l2gen.application.core.filter.FilterBuilder;
import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.core.services.TableService;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import fr.univrennes.istic.l2gen.geometry.Point;
import fr.univrennes.istic.l2gen.io.svg.SVGExport;
import fr.univrennes.istic.l2gen.io.xml.model.XMLAttribute;
import fr.univrennes.istic.l2gen.io.xml.model.XMLTag;
import fr.univrennes.istic.l2gen.svg.color.Color;
import fr.univrennes.istic.l2gen.visustats.data.DataGroup;
import fr.univrennes.istic.l2gen.visustats.data.DataSet;
import fr.univrennes.istic.l2gen.visustats.data.Label;
import fr.univrennes.istic.l2gen.visustats.data.Value;
import fr.univrennes.istic.l2gen.visustats.view.DataViewType;
import fr.univrennes.istic.l2gen.visustats.view.datagroup.AbstractDataGroupView;
import fr.univrennes.istic.l2gen.visustats.view.datagroup.AreaDataGroupView;
import fr.univrennes.istic.l2gen.visustats.view.datagroup.BarDataGroupView;
import fr.univrennes.istic.l2gen.visustats.view.datagroup.ColumnsDataGroupView;
import fr.univrennes.istic.l2gen.visustats.view.datagroup.HeatMapDataGroupView;
import fr.univrennes.istic.l2gen.visustats.view.datagroup.LineDataGroupView;
import fr.univrennes.istic.l2gen.visustats.view.datagroup.PieDataGroupView;
import fr.univrennes.istic.l2gen.visustats.view.datagroup.ScatterDataGroupView;
import fr.univrennes.istic.l2gen.visustats.view.datagroup.SpiderDataGroupView;
import fr.univrennes.istic.l2gen.visustats.view.datagroup.axis.DataAxisViewScaleType;
import fr.univrennes.istic.l2gen.visustats.view.dataset.AreaDataSetView;
import fr.univrennes.istic.l2gen.visustats.view.dataset.BarDataSetView;
import fr.univrennes.istic.l2gen.visustats.view.dataset.ColumnsDataSetView;
import fr.univrennes.istic.l2gen.visustats.view.dataset.HeatMapDataSetView;
import fr.univrennes.istic.l2gen.visustats.view.dataset.IDataSetView;
import fr.univrennes.istic.l2gen.visustats.view.dataset.LineDataSetView;
import fr.univrennes.istic.l2gen.visustats.view.dataset.PieDataSetView;
import fr.univrennes.istic.l2gen.visustats.view.dataset.ScatterDataSetView;
import fr.univrennes.istic.l2gen.visustats.view.dataset.SpiderDataSetView;

public final class NoteBookChart implements NoteBookValue {
    private static final int MAX_GROUP_LENGTH = 20;
    private static final String SVG_CHAR_ERROR = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"500\" height=\"100\"><rect width=\"100%\" height=\"100%\" fill=\"#f8d7da\"/><text x=\"50%\" y=\"50%\" dominant-baseline=\"middle\" text-anchor=\"middle\" fill=\"#721c24\" font-family=\"Arial, sans-serif\" font-size=\"14\">ERROR_MESSAGE</text></svg>";

    private DataTable table;
    private boolean includeFilters;
    private boolean percentage;

    private DataViewType type;
    private String title;
    private boolean stacked;

    private boolean showLegend;
    private boolean horizontalLegend;

    private int tickCount;
    private DataAxisViewScaleType scale;

    private boolean showXAxis;
    private String xAxisLabel;
    private boolean showYAxis;
    private String yAxisLabel;

    private Optional<Integer> biggerGroupColumn;
    private int groupColumn;
    private int valueColumn;

    private List<Color> colors;
    private List<String> labels;

    private String cachedSVG;

    public NoteBookChart(
            DataViewType type,
            String title,
            boolean stacked,

            boolean showLegend,
            boolean horizontalLegend,

            int tickCount,
            DataAxisViewScaleType scale,

            boolean showXAxis,
            String xAxisLabel,
            boolean showYAxis,
            String yAxisLabel,

            DataTable table,
            Optional<Integer> biggerGroupColumn,
            int groupColumn,
            int valueColumn,

            boolean includeFilters,
            boolean percentage,

            List<Color> colors) {

        this.type = type;
        this.title = title;
        this.stacked = stacked;

        this.showLegend = showLegend;
        this.horizontalLegend = horizontalLegend;
        this.colors = colors;

        this.tickCount = tickCount;
        this.scale = scale;

        this.showXAxis = showXAxis;
        this.xAxisLabel = xAxisLabel;
        this.showYAxis = showYAxis;
        this.yAxisLabel = yAxisLabel;

        this.table = table;
        this.biggerGroupColumn = biggerGroupColumn;
        this.groupColumn = groupColumn;
        this.valueColumn = valueColumn;

        this.includeFilters = includeFilters;
        this.percentage = percentage;

        this.cachedSVG = null;

    }

    private void createShape() {
        List<Filter> filters = new ArrayList<>(table.getFilters());
        if (!includeFilters) {
            table.clearFilters();
        }

        if (biggerGroupColumn.isEmpty()) {
            createDataSetViewShape();
        } else {
            createDataGroupViewShape();
        }

        if (!includeFilters) {
            table.addFilters(filters);
        }
    }

    private void createDataSetViewShape() {
        if (groupColumn < 0 || valueColumn < 0) {
            this.cachedSVG = SVG_CHAR_ERROR.replace("ERROR_MESSAGE", Lang.get("report.setting.chart.no_data"));
            return;
        }

        String groupColumnName = table.getSQLColumnName(groupColumn);
        String valueColumnName = table.getSQLColumnName(valueColumn);

        StringBuilder sqlSelectClause = new StringBuilder("SELECT ");
        sqlSelectClause.append(groupColumnName).append(", ");
        if (percentage) {
            sqlSelectClause.append("SUM(").append(valueColumnName)
                    .append(") * 100.0 / (SELECT SUM(")
                    .append(valueColumnName).append(") FROM ").append(table.getSQLName()).append(")");
        } else {
            sqlSelectClause.append("SUM(").append(valueColumnName).append(")");
        }
        sqlSelectClause.append(" FROM");

        StringBuilder queryBuilder = FilterBuilder.base(sqlSelectClause.toString(), table, false);
        queryBuilder.append(" GROUP BY ").append(groupColumnName);
        String query = queryBuilder.toString();

        try (DuckDBConnection connection = (DuckDBConnection) DriverManager.getConnection("jdbc:duckdb:");
                Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);
            DataSet dataSet = new DataSet(new Label(title));
            boolean hasRows = false;
            int colorIndex = 0;
            while (resultSet.next()) {
                if (colors.size() <= colorIndex) {
                    colors.add(Color.random());
                }
                Color color = colors.get(colorIndex++);
                dataSet.values().add(new Value(resultSet.getDouble(2), color));
                hasRows = true;
            }

            if (hasRows) {
                IDataSetView chart;
                switch (type) {
                    case PIE -> {
                        chart = new PieDataSetView(new Point(), 200);
                    }
                    case BAR -> {
                        chart = new BarDataSetView(new Point(), 20, 400);
                    }
                    case COLUMNS -> {
                        chart = new ColumnsDataSetView(new Point(), 10, 20, 400);
                    }
                    case SCATTER -> {
                        chart = new ScatterDataSetView(new Point(), 20, 400, 5);
                    }
                    case LINE -> {
                        chart = new LineDataSetView(new Point(), 20, 400, 5);
                    }
                    case AREA -> {
                        chart = new AreaDataSetView(new Point(), 20, 400, 5);
                    }
                    case SPIDER -> {
                        chart = new SpiderDataSetView(new Point(), 200, 5, 5);
                    }
                    case HEATMAP -> {
                        chart = new HeatMapDataSetView(new Point(), 20, 400);
                    }
                    default -> throw new Exception("Unsupported chart type: " + type);
                }

                chart.setData(dataSet);

                int margin = 50;
                XMLTag svgTag = new XMLTag("svg");
                svgTag.addAttribute(new XMLAttribute("xmlns", "http://www.w3.org/2000/svg"));
                svgTag.addAttribute(new XMLAttribute("version", "1.1"));
                svgTag.addAttribute(new XMLAttribute("width", String.valueOf(chart.getWidth() + 2 * margin)));
                svgTag.addAttribute(new XMLAttribute("height", String.valueOf(chart.getHeight() + 2 * margin)));

                chart.move(chart.getWidth() / 2 + margin, chart.getHeight() / 2 + margin);

                XMLTag chartTag = SVGExport.convert(chart);
                svgTag.appendChild(chartTag);
                this.cachedSVG = svgTag.toString();
            } else {
                throw new Exception("No data to display");
            }
        } catch (Exception e) {
            if (e instanceof SQLException) {
                Log.debug(query);
            }
            this.cachedSVG = SVG_CHAR_ERROR.replace("ERROR_MESSAGE", Lang.get("report.setting.chart.no_data"));
            Log.debug("Failed to create chart SVG", e);
        }
    }

    private void createDataGroupViewShape() {
        if (groupColumn < 0 || valueColumn < 0) {
            this.cachedSVG = SVG_CHAR_ERROR.replace("ERROR_MESSAGE", Lang.get("report.setting.chart.no_data"));
            return;
        }

        String biggerGroupColumnName = table.getSQLColumnName(biggerGroupColumn.get());
        String groupColumnName = table.getSQLColumnName(groupColumn);
        String valueColumnName = table.getSQLColumnName(valueColumn);

        StringBuilder sqlSelectClause = new StringBuilder("SELECT ");
        sqlSelectClause.append(biggerGroupColumnName).append(", ");
        sqlSelectClause.append(groupColumnName).append(", ");
        if (percentage) {
            sqlSelectClause.append("SUM(").append(valueColumnName)
                    .append(") * 100.0 / (SELECT SUM(")
                    .append(valueColumnName).append(") FROM ").append(table.getSQLName()).append(")");
        } else {
            sqlSelectClause.append("SUM(").append(valueColumnName).append(")");
        }
        sqlSelectClause.append(" FROM");

        StringBuilder queryBuilder = FilterBuilder.base(sqlSelectClause.toString(), table, false);
        queryBuilder.append(" GROUP BY ")
                .append(biggerGroupColumnName)
                .append(", ")
                .append(groupColumnName);
        queryBuilder.append(" ORDER BY ")
                .append(biggerGroupColumnName)
                .append(", ")
                .append(groupColumnName);
        String query = queryBuilder.toString();

        try (DuckDBConnection connection = (DuckDBConnection) DriverManager.getConnection("jdbc:duckdb:");
                Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);

            Map<String, Map<String, Double>> groupedValues = new LinkedHashMap<>();
            Map<String, Color> legendColors = new LinkedHashMap<>();
            List<String> legendOrder = new ArrayList<>();

            boolean hasRows = false;
            int colorIndex = 0;
            while (resultSet.next()) {
                String biggerGroupValue = resultSet.getString(1);
                String groupValue = resultSet.getString(2);
                double value = resultSet.getDouble(3);

                groupedValues
                        .computeIfAbsent(biggerGroupValue, key -> new LinkedHashMap<>())
                        .put(groupValue, value);

                if (!legendColors.containsKey(groupValue)) {
                    if (colors.size() <= colorIndex) {
                        colors.add(Color.random());
                    }
                    legendColors.put(groupValue, colors.get(colorIndex++));
                    legendOrder.add(groupValue);
                }

                hasRows = true;
            }
            labels = legendOrder;

            if (hasRows) {
                DataGroup dataGroup = new DataGroup(new Label(title));

                for (Map.Entry<String, Map<String, Double>> entry : groupedValues.entrySet()) {
                    DataSet dataSet = new DataSet(new Label(entry.getKey()));
                    Map<String, Double> valuesByLegend = entry.getValue();

                    for (String legend : legendOrder) {
                        double value = valuesByLegend.getOrDefault(legend, 0.0);
                        dataSet.values().add(new Value(value, legendColors.get(legend)));
                    }

                    if (dataGroup.size() < MAX_GROUP_LENGTH) {
                        dataGroup.add(dataSet);
                    } else {
                        throw new SizeLimitExceededException();
                    }
                }

                if (showLegend) {
                    for (String legend : legendOrder) {
                        dataGroup.add(new Label(legend));
                    }
                }

                AbstractDataGroupView chart;
                switch (type) {
                    case PIE -> {
                        chart = new PieDataGroupView(dataGroup, new Point(), 50, 200, horizontalLegend);
                    }
                    case BAR -> {
                        chart = new BarDataGroupView(dataGroup, new Point(), 50, 20, 400, horizontalLegend);
                    }
                    case COLUMNS -> {
                        chart = new ColumnsDataGroupView(dataGroup, new Point(), 50, 20, 400, horizontalLegend);
                    }
                    case SCATTER -> {
                        chart = new ScatterDataGroupView(dataGroup, new Point(), 50, 20, 400, 5, horizontalLegend);
                    }
                    case LINE -> {
                        chart = new LineDataGroupView(dataGroup, new Point(), 50, 20, 400, 5, horizontalLegend);
                    }
                    case AREA -> {
                        chart = new AreaDataGroupView(dataGroup, new Point(), 50, 20, 400, 5, stacked,
                                horizontalLegend);
                    }
                    case SPIDER -> {
                        chart = new SpiderDataGroupView(dataGroup, new Point(), 50, 200, horizontalLegend);
                    }
                    case HEATMAP -> {
                        chart = new HeatMapDataGroupView(dataGroup, new Point(), 50, 20, 400, horizontalLegend);
                    }
                    default -> throw new Exception("Unsupported chart type: " + type);
                }

                chart.setAxisStepCount(tickCount);
                chart.setAxisScaleType(scale);
                chart.setShowXAxis(showXAxis);
                chart.setXAxisLabel(xAxisLabel);
                chart.setShowYAxis(showYAxis);
                chart.setYAxisLabel(yAxisLabel);

                int margin = 50;
                XMLTag svgTag = new XMLTag("svg");
                svgTag.addAttribute(new XMLAttribute("xmlns", "http://www.w3.org/2000/svg"));
                svgTag.addAttribute(new XMLAttribute("version", "1.1"));
                svgTag.addAttribute(new XMLAttribute("width", String.valueOf(chart.getWidth() + 2 * margin)));
                svgTag.addAttribute(new XMLAttribute("height", String.valueOf(chart.getHeight() + 2 * margin)));

                chart.move(chart.getWidth() / 2 + margin, chart.getHeight() / 2 + margin);

                XMLTag chartTag = SVGExport.convert(chart);
                svgTag.appendChild(chartTag);

                SVGExport.export(chartTag, "test.svg");

                this.cachedSVG = svgTag.toString();
            } else {
                throw new Exception("No data to display");
            }
        } catch (SizeLimitExceededException e) {
            this.cachedSVG = SVG_CHAR_ERROR.replace("ERROR_MESSAGE", Lang.get("report.setting.chart.size_limit"));
            Log.debug("Failed to create chart SVG: too much groups", e);
        } catch (Exception e) {
            if (e instanceof SQLException) {
                Log.debug(query);
            }

            this.cachedSVG = SVG_CHAR_ERROR.replace("ERROR_MESSAGE", Lang.get("report.setting.chart.no_data"));
            Log.debug("Failed to create chart SVG", e);
        }
    }

    public String getSVG() {
        if (cachedSVG == null) {
            createShape();
        }
        return cachedSVG;
    }

    public DataViewType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public boolean isLegendVisible() {
        return showLegend;
    }

    public boolean isLegendHorizontal() {
        return horizontalLegend;
    }

    public int getTickCount() {
        return tickCount;
    }

    public DataAxisViewScaleType getScale() {
        return scale;
    }

    public boolean isXVisible() {
        return showXAxis;
    }

    public String getXLabel() {
        return xAxisLabel;
    }

    public boolean isYVisible() {
        return showYAxis;
    }

    public String getYLabel() {
        return yAxisLabel;
    }

    public DataTable getTable() {
        return table;
    }

    public Optional<Integer> getBiggerGroupColumn() {
        return biggerGroupColumn;
    }

    public int getGroupColumn() {
        return groupColumn;
    }

    public int getValueColumn() {
        return valueColumn;
    }

    public boolean isIncludeFilters() {
        return includeFilters;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<String> getColorLabels() {
        return labels;
    }

    @Override
    public void exportHTML(StringBuilder html) {

    }

    @Override
    public void save(DataOutputStream out) {
        try {
            out.writeUTF("CHART");
            out.writeUTF(type == null ? "" : type.name());
            out.writeUTF(title == null ? "" : title);
            out.writeBoolean(showLegend);
            out.writeInt(tickCount);
            out.writeUTF(scale == null ? "" : scale.name());
            out.writeBoolean(showXAxis);
            out.writeUTF(xAxisLabel == null ? "" : xAxisLabel);
            out.writeBoolean(showYAxis);
            out.writeUTF(yAxisLabel == null ? "" : yAxisLabel);
            out.writeUTF(table != null && table.getPath() != null ? table.getPath().getAbsolutePath() : "");
            out.writeInt(groupColumn);
            out.writeInt(valueColumn);
            out.writeBoolean(includeFilters);
        } catch (Exception e) {
            Log.debug("Failed to save chart", e);
        }
    }

    @Override
    public void load(DataInputStream in) {
        try {
            String typeName = in.readUTF();
            this.type = typeName.isEmpty() ? null : DataViewType.valueOf(typeName);
            String loadedTitle = in.readUTF();
            this.title = loadedTitle.isEmpty() ? null : loadedTitle;
            this.showLegend = in.readBoolean();
            this.tickCount = in.readInt();
            String scaleName = in.readUTF();
            this.scale = scaleName.isEmpty() ? null : DataAxisViewScaleType.valueOf(scaleName);
            this.showXAxis = in.readBoolean();
            String loadedXAxisLabel = in.readUTF();
            this.xAxisLabel = loadedXAxisLabel.isEmpty() ? null : loadedXAxisLabel;
            this.showYAxis = in.readBoolean();
            String loadedYAxisLabel = in.readUTF();
            this.yAxisLabel = loadedYAxisLabel.isEmpty() ? null : loadedYAxisLabel;
            String tablePath = in.readUTF();
            this.table = tablePath.isEmpty() ? null : TableService.get(new File(tablePath));
            int loadedGroupColumn = in.readInt();
            this.groupColumn = loadedGroupColumn == -1 ? null : loadedGroupColumn;
            int loadedValueColumn = in.readInt();
            this.valueColumn = loadedValueColumn == -1 ? null : loadedValueColumn;
            this.includeFilters = in.readBoolean();
        } catch (Exception e) {
            Log.debug("Failed to load chart", e);
        }
    }
}
