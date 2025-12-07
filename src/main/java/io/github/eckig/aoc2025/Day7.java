package io.github.eckig.aoc2025;

import java.util.*;

public class Day7
{
    static void main()
    {
        final var input = """
""";
        final var map = input.lines().map(String::toCharArray).toList();
        final var positions = new HashSet<Pos>();
        final var start = start(map);
        traverseAndCountManifolds(map, start, positions);
        System.out.println(positions);
        System.out.println(positions.size());
    }

    private static Pos[] start(final List<char[]> pMap)
    {
        for (int i = 0; i < pMap.size(); i++)
        {
            var line = pMap.get(i);
            for (int j = 0; j < line.length; j++)
            {
                var col = line[j];
                if (col == 'S')
                {
                    return new Pos[]{new Pos(i, j)};
                }
            }
        }
        return null;
    }

    private static void traverseAndCountManifolds(final List<char[]> pMap, final Pos[] pPosition, final Collection<Pos> pSink)
    {
        if (pPosition != null)
        {
            for (final var pos : pPosition)
            {
                final var rec = pos.moveToNextManifold(pMap, pSink);
                traverseAndCountManifolds(pMap, rec, pSink);
            }
        }
    }

    record Pos(int a, int b)
    {
        Pos[] moveToNextManifold(final List<char[]> pMap, final Collection<Pos> pSink)
        {
            for (int i = a; i < pMap.size(); i++)
            {
                final var line = pMap.get(i);
                final var col = line[b];
                if (col == '^')
                {
                    return pSink.add(new Pos(i, b)) ? new Pos[]{new Pos(i + 1, b - 1), new Pos(i + 1, b + 1)} : null;
                }
            }
            return null;
        }
    }
}
