package api.components.map.course.transpool.graph.layout;

import com.fxgraph.graph.Graph;
import com.fxgraph.layout.Layout;
import api.components.map.course.transpool.graph.component.coordinate.CoordinatesManager;
import api.components.map.course.transpool.graph.component.station.StationManager;

//A class containing the map of stops and the map of coordinates using coordinatesManager and stationManger.
public class MapGridLayout implements Layout {

    private final int SCALE = 33;
    private StationManager stationManager;

    public MapGridLayout(StationManager stationManager) {
        this.stationManager = stationManager;
    }


    /**Overrides from the Layout interface.
     * for every coordinateNode it gets the coordinate, scales it and places it on the Region
     * @param graph
     */

    @Override
    public void execute(Graph graph) {
        final int STATION_FIX_X = -15;
        final int STATION_FIX_Y = -15;
        stationManager.getAllCoordinates().forEach(stationNode -> {
            int x = stationNode.getX();
            int y = stationNode.getY();
            graph.getGraphic(stationNode).relocate(x * SCALE + STATION_FIX_X, y * SCALE + STATION_FIX_Y);
        });
    }

}
