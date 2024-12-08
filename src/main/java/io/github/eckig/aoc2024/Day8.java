package io.github.eckig.aoc2024;

import java.util.*;
import java.util.List;

public class Day8 {

    public static void main(String[] args) {
        part2();
    }

    private static void part2() {
        final var input = """
                """;

        final List<List<Point>> points = new ArrayList<>();
        final Map<Character, Set<SignalPoint>> signals = new HashMap<>();
        final Set<Coordinate> antimodes = new HashSet<>();

        {
            int x = 0;
            for (final var iter = input.lines().iterator(); iter.hasNext(); ) {
                final var line = iter.next();
                final List<Point> p = new ArrayList<>();
                for (int y = 0; y < line.length(); y++) {
                    final var point = getPoint(line.charAt(y), new Coordinate(x, y));
                    p.add(point);
                    if (point instanceof SignalPoint sp) {
                        signals.computeIfAbsent(sp.frequency, k -> new HashSet<>()).add(sp);
                    }
                }
                points.add(p);
                x++;
            }
        }
        for (final var entry : signals.entrySet()) {
            for (final var signal : entry.getValue()) {
                for (final var otherSignal : entry.getValue()) {
                    if (signal != otherSignal && signal.frequency == otherSignal.frequency) {
                        getAntimodes(signal.coordinate, otherSignal.coordinate, points, antimodes);
                        antimodes.add(signal.coordinate);
                        antimodes.add(otherSignal.coordinate);
                    }
                }
            }
        }

        System.out.println(antimodes.size());
    }

    private static void getAntimodes(final Coordinate p1, Coordinate p2, final List<List<Point>> map, final Collection<Coordinate> pCollector) {
        final var highX = Math.max(p1.x, p2.x);
        final var highY = Math.max(p1.y, p2.y);
        final var lowX = Math.min(p1.x, p2.x);
        final var lowY = Math.min(p1.y, p2.y);
        final var distanceX = highX - lowX;
        final var distanceY = highY - lowY;
        int run = 1;
        while (true) {
            final var transformX = distanceX * run;
            final var transformY = distanceY * run;
            final var a1 = new Coordinate(p1.x > p2.x ? lowX - transformX : highX + transformX, p1.y < p2.y ? highY + transformY : lowY - transformY);
            final var a2 = new Coordinate(p1.x < p2.x ? lowX - transformX : highX + transformX, p1.y > p2.y ? highY + transformY : lowY - transformY);
            if (isValid(a1, map)) {
                pCollector.add(a1);
            }
            if (isValid(a2, map)) {
                pCollector.add(a2);
            }
            if (!isValid(a1, map) && !isValid(a2, map)) {
                return;
            }
            run++;
        }
    }

    private static void part1() {
        final var input = """
                """;

        final List<List<Point>> points = new ArrayList<>();
        final List<SignalPoint> signals = new ArrayList<>();
        final Set<Coordinate> antimodes = new HashSet<>();

        int x = 0;
        for (final var iter = input.lines().iterator(); iter.hasNext(); ) {
            final var line = iter.next();
            final List<Point> p = new ArrayList<>();
            for (int y = 0; y < line.length(); y++) {
                final var point = getPoint(line.charAt(y), new Coordinate(x, y));
                p.add(point);
                if (point instanceof SignalPoint sp) {
                    signals.add(sp);
                }
            }
            points.add(p);
            x++;
        }

        for (final var signal : signals) {
            for (final var otherSignal : signals) {
                if (signal != otherSignal && signal.frequency == otherSignal.frequency) {
                    final var antiModePos = getAntimodes(signal.coordinate, otherSignal.coordinate, points);
                    antimodes.addAll(antiModePos);
                }
            }
        }

        System.out.println(antimodes.size());
    }

    private static List<Coordinate> getAntimodes(Coordinate p1, Coordinate p2, final List<List<Point>> map) {
        final var highX = Math.max(p1.x, p2.x);
        final var highY = Math.max(p1.y, p2.y);
        final var lowX = Math.min(p1.x, p2.x);
        final var lowY = Math.min(p1.y, p2.y);
        final var distanceX = highX - lowX;
        final var distanceY = highY - lowY;
        final var a1 = new Coordinate(p1.x > p2.x ? lowX - distanceX : highX + distanceX, p1.y < p2.y ? highY + distanceY : lowY - distanceY);
        final var a2 = new Coordinate(p1.x < p2.x ? lowX - distanceX : highX + distanceX, p1.y > p2.y ? highY + distanceY : lowY - distanceY);

        if (isValid(a1, map) && isValid(a2, map)) {
            return List.of(a1, a2);
        }
        if (isValid(a1, map)) {
            return List.of(a1);
        }
        if (isValid(a2, map)) {
            return List.of(a2);
        }
        return List.of();
    }

    private static boolean isValid(final Coordinate coordinate, final List<List<Point>> map) {
        return coordinate.x >= 0 && coordinate.y >= 0 && coordinate.x < map.size() && coordinate.y < map.getFirst().size();
    }

    private static Point getPoint(final char pPoint, final Coordinate pCoordinate) {
        return switch (pPoint) {
            case '.' -> EmptyPoint.EMPTY;
            default -> new SignalPoint(pPoint, pCoordinate);
        };
    }

    private record Coordinate(double x, double y) {
    }

    private sealed interface Point permits EmptyPoint, SignalPoint {

    }

    private static final class EmptyPoint implements Point {
        private static final Point EMPTY = new EmptyPoint();

    }

    private record SignalPoint(char frequency, Coordinate coordinate) implements Point {
    }
}
