package api.components.map.course.transpool.graph.component.station;

import api.components.map.course.transpool.graph.component.util.NodesManager;

import java.util.function.BiFunction;

/*
Makes sure that each Station node will e created exactly once
 */
public class StationManager extends NodesManager<StationNode> {

    public StationManager(BiFunction<Integer, Integer, StationNode> factory) {
        super(factory);
    }
}
