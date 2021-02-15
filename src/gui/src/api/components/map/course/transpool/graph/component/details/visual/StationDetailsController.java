package api.components.map.course.transpool.graph.component.details.visual;

import api.components.map.course.transpool.graph.component.details.StationDetailsDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class StationDetailsController {

    private final String STATION_NAME_FORMAT = "%s ( %s ; %s)";

    @FXML private Label stationNameLabel;
    @FXML private TextArea visitingTripsTextArea;

    public void setData(StationDetailsDTO data) {
        stationNameLabel.setText(String.format(STATION_NAME_FORMAT, data.getName(), String.valueOf(data.getX()), String.valueOf(data.getY())));
        visitingTripsTextArea.setText(data.getDetails().toString());
    }

}
