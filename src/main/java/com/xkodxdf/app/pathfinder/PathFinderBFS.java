package com.xkodxdf.app.pathfinder;

import com.xkodxdf.app.worldmap.Coordinates;

import java.util.*;

public class PathFinderBFS implements PathFinder<Coordinates> {

    @Override
    public Set<Coordinates> getPath(Coordinates source, Coordinates target, Set<Coordinates> freeCoordinates) {
        Queue<Coordinates> queue = new LinkedList<>();
        Map<Coordinates, Coordinates> cameFrom = new HashMap<>();
        Set<Coordinates> visited = new HashSet<>();
        queue.add(source);
        visited.add(source);
        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();
            if (current.equals(target)) {
                return reconstructPath(cameFrom, current);
            }
            for (Coordinates neighbor : getNeighbors(current, freeCoordinates)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return Collections.emptySet();
    }

    private Set<Coordinates> reconstructPath(Map<Coordinates, Coordinates> cameFrom, Coordinates current) {
        List<Coordinates> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return new LinkedHashSet<>(path);
    }

    private Set<Coordinates> getNeighbors(Coordinates coordinates, Set<Coordinates> freeCoordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        Set<Coordinates> neighbors = new HashSet<>();
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                if (!(row == 0 && col == 0)) {
                    Coordinates neighbor = new Coordinates(x + row, y + col);
                    if (freeCoordinates.contains(neighbor)) {
                        neighbors.add(neighbor);
                    }
                }
            }
        }
        return neighbors;
    }
}
