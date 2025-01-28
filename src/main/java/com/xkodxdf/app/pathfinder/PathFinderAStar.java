package com.xkodxdf.app.pathfinder;

import com.xkodxdf.app.map.Coordinates;
import com.xkodxdf.app.map.config.Config;

import java.util.*;

public class PathFinderAStar implements PathFinder<Coordinates> {

    @Override
    public Set<Coordinates> getPath(Coordinates source, Coordinates target, Set<Coordinates> freeCoordinates) {
        if (!isCoordinatesValid(source) || !isCoordinatesValid(target)) {
            throw new IllegalArgumentException("Invalid source or target coordinates");
        }
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.fScore));
        Map<Coordinates, Coordinates> cameFrom = new HashMap<>();
        Map<Coordinates, Integer> gScore = new HashMap<>();
        Map<Coordinates, Integer> fScore = new HashMap<>();
        for (Coordinates coord : freeCoordinates) {
            gScore.put(coord, Integer.MAX_VALUE);
            fScore.put(coord, Integer.MAX_VALUE);
        }
        gScore.put(source, 0);
        fScore.put(source, heuristic(source, target));
        openSet.add(new Node(source, fScore.get(source)));
        while (!openSet.isEmpty()) {
            Coordinates current = openSet.poll().coordinate;
            if (current.equals(target)) {
                return reconstructPath(cameFrom, current);
            }
            for (Coordinates neighbor : getNeighbors(current, freeCoordinates)) {
                int tentativeGScore = gScore.get(current) + 1;
                if (tentativeGScore < gScore.get(neighbor)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + heuristic(neighbor, target));
                    if (!openSet.contains(new Node(neighbor, fScore.get(neighbor)))) {
                        openSet.add(new Node(neighbor, fScore.get(neighbor)));
                    }
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

    private int heuristic(Coordinates a, Coordinates b) {
        return Math.max(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()));
    }

    private Set<Coordinates> getNeighbors(Coordinates coord, Set<Coordinates> freeCoordinates) {
        Set<Coordinates> neighbors = new HashSet<>();
        int x = coord.getX();
        int y = coord.getY();
        for (int deltaX = -1; deltaX <= 1; deltaX++) {
            for (int deltaY = -1; deltaY <= 1; deltaY++) {
                if (!(deltaX == 0 && deltaY == 0)) {
                    Coordinates neighbor = new Coordinates(x + deltaX, y + deltaY);
                    if (isCoordinatesValid(neighbor) && freeCoordinates.contains(neighbor)) {
                        neighbors.add(neighbor);
                    }
                }
            }
        }
        return neighbors;
    }

    public boolean isCoordinatesValid(Coordinates coord) {
        return coord.getX() >= 0 && coord.getX() < Config.getConfig().getWidth()
                && coord.getY() >= 0 && coord.getY() < Config.getConfig().getHeight();
    }

    private static class Node {
        Coordinates coordinate;
        int fScore;

        Node(Coordinates coordinate, int fScore) {
            this.coordinate = coordinate;
            this.fScore = fScore;
        }
    }
}
