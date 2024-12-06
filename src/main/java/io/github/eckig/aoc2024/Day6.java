package io.github.eckig.aoc2024;

import java.util.HashSet;
import java.util.List;

public class Day6
{

    private record Pos(int x, int y)
    {
        static final Pos FINISH = new Pos(-1, -1);
    }

    private enum Direction
    {
        UP, DOWN, LEFT, RIGHT
    }

    private record Order(Pos pos, Direction dir, boolean hitBumper)
    {

    }

    private static void part2()
    {
        final var inMap = """
        """;
        var map = inMap.lines().map(String::toCharArray).toList();
        var pos = new Order(find(map, '^'), Direction.UP, false);

        var obstacles = 0;
        for (int x = 0; x < map.size(); x++)
        {
            final var mapRow = map.get(x);
            for (int y = 0; y < mapRow.length; y++)
            {
                if (mapRow[y] == '.')
                {
                    mapRow[y] = 'O';
                    if (testMap(map, pos))
                    {
                        obstacles++;
                    }
                    mapRow[y] = '.';
                }
            }
        }
        System.out.println(obstacles);
    }

    private static boolean testMap(final List<char[]> pMap, final Order pOrder)
    {
        final var visits = new HashSet<Order>();
        var pos = pOrder;
        boolean bumpedInto = false;
        while (true)
        {
            final var next = getNext(pMap, pos);
            bumpedInto |= next.hitBumper;
            if (bumpedInto && visits.contains(next))
            {
                return true;
            }
            if (next.pos == Pos.FINISH)
            {
                return false;
            }
            else
            {
                pos = next;
            }
            visits.add(pos);
        }
    }

    private static void part1()
    {
        final var inMap = """
        """;
        final var map = inMap.lines().map(String::toCharArray).toList();
        final var walked = new HashSet<Pos>();
        var pos = new Order(find(map, '^'), Direction.UP, false);
        while (true)
        {
            var nextPos = getNext(map, pos);
            walked.add(nextPos.pos);
            if (nextPos.pos == Pos.FINISH)
            {
                break;
            }
            else
            {
                pos = nextPos;
            }

        }
        System.out.println(walked.size());
    }

    private static Order getNext(final List<char[]> pMap, final Order pOrder)
    {
        var nextPos = getNextPos(pOrder);
        var next = get(pMap, nextPos);
        if (next == '.' || next == '^')
        {
            return new Order(nextPos, pOrder.dir, pOrder.hitBumper);
        }
        else if (next == '#')
        {
            // switch direction
            return getNext(pMap, new Order(pOrder.pos, getNextDirection(pOrder.dir), pOrder.hitBumper));
        }
        else if (next == 'O')
        {
            // switch direction
            return getNext(pMap, new Order(pOrder.pos, getNextDirection(pOrder.dir), true));
        }
        return new Order(Pos.FINISH, pOrder.dir, pOrder.hitBumper);
    }

    private static Pos getNextPos(final Order pOrder)
    {
        return switch (pOrder.dir)
        {
            case UP -> new Pos(pOrder.pos.x - 1, pOrder.pos.y);
            case DOWN -> new Pos(pOrder.pos.x + 1, pOrder.pos.y);
            case LEFT -> new Pos(pOrder.pos.x, pOrder.pos.y - 1);
            case RIGHT -> new Pos(pOrder.pos.x, pOrder.pos.y + 1);
        };
    }

    private static Direction getNextDirection(final Direction pDirection)
    {
        return switch (pDirection)
        {
            case UP -> Direction.RIGHT;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
            case RIGHT -> Direction.DOWN;
        };
    }

    private static char get(final List<char[]> pMap, final Pos pSearch)
    {
        try
        {
            return pMap.get(pSearch.x)[pSearch.y];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return ' ';
        }
    }

    private static Pos find(final List<char[]> pMap, final char pSearch)
    {
        for (int x = 0; x < pMap.size(); x++)
        {
            final var mapRow = pMap.get(x);
            for (int y = 0; y < mapRow.length; y++)
            {
                if (mapRow[y] == pSearch)
                {
                    return new Pos(x, y);
                }
            }
        }
        return null;
    }

    public static void main(String[] args)
    {
        part2();
    }
}
