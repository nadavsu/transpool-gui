package data.transpool.util;

import exception.data.TransPoolDataException;

import java.util.List;

public interface Graph<V, E> {
    void newConnection(E e);
    List<List<V>> getAllPossibleRoutes(V source, V destination) throws TransPoolDataException;
    List<List<V>> getGraph();
}
