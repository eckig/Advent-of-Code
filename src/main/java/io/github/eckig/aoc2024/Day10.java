package io.github.eckig.aoc2024;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntUnaryOperator;

public class Day10
{
    private static void part2()
    {
        final var input = """
                """;
        final var map = input.lines().map(l -> charToInt(l.toCharArray())).toList();

        int trailScores = 0;
        for (int x = 0; x < map.size(); x++)
        {
            for (int y = 0; y < map.get(x).length; y++)
            {
                if (map.get(x)[y] == 0)
                {
                    final var trailScore = trailWalker(map, new Pos(x, y), new HashSet<>());
                    trailScores += trailScore.size();
                }
            }
        }
        System.out.println(trailScores);
    }

    private record Trail(Set<Pos> trail)
    {
    }

    private static Set<Trail> trailWalker(final List<int[]> map, final Pos pos, final Set<Pos> currentTrail)
    {
        final var trails = new HashSet<Trail>();
        trails.addAll(walkTrail(map, pos, x -> x + 1, y -> y, new HashSet<>(currentTrail)));
        trails.addAll(walkTrail(map, pos, x -> x, y -> y + 1, new HashSet<>(currentTrail)));
        trails.addAll(walkTrail(map, pos, x -> x - 1, y -> y, new HashSet<>(currentTrail)));
        trails.addAll(walkTrail(map, pos, x -> x, y -> y - 1, new HashSet<>(currentTrail)));
        return trails;
    }

    private static Set<Trail> walkTrail(final List<int[]> map, final Pos pos, final IntUnaryOperator pDirectionX, final IntUnaryOperator pDirectionY, final Set<Pos> pCurrentTrail)
    {
        final int nextX = pDirectionX.applyAsInt(pos.x);
        final int nextY = pDirectionY.applyAsInt(pos.y);
        final var nextPos = new Pos(nextX, nextY);

        final int current = get(map, pos);
        final int next = get(map, nextPos);

        if (current == 8 && next == 9)
        {
            pCurrentTrail.add(nextPos);
            return Set.of(new Trail(pCurrentTrail));
        }
        else if (next == current + 1)
        {
            pCurrentTrail.add(nextPos);
            return trailWalker(map, nextPos, pCurrentTrail);
        }
        return Set.of();
    }


    private static void part1()
    {
        final var input = """
                """;
        final var map = input.lines().map(l -> charToInt(l.toCharArray())).toList();

        int trailScores = 0;
        for (int x = 0; x < map.size(); x++)
        {
            for (int y = 0; y < map.get(x).length; y++)
            {
                if (map.get(x)[y] == 0)
                {
                    final var trailScore = walk(map, new Pos(x, y));
                    trailScores += trailScore.size();
                }
            }
        }
        System.out.println(trailScores);
    }

    private record Pos(int x, int y)
    {
    }

    private static Set<Pos> walk(final List<int[]> map, final Pos pos)
    {
        final var endPos = new HashSet<Pos>();
        endPos.addAll(walk(map, pos, x -> x + 1, y -> y));
        endPos.addAll(walk(map, pos, x -> x, y -> y + 1));
        endPos.addAll(walk(map, pos, x -> x - 1, y -> y));
        endPos.addAll(walk(map, pos, x -> x, y -> y - 1));
        return endPos;
    }

    private static Set<Pos> walk(final List<int[]> map, final Pos pos, final IntUnaryOperator pDirectionX, final IntUnaryOperator pDirectionY)
    {
        final int nextX = pDirectionX.applyAsInt(pos.x);
        final int nextY = pDirectionY.applyAsInt(pos.y);
        final var nextPos = new Pos(nextX, nextY);

        final int current = get(map, pos);
        final int next = get(map, nextPos);

        if (current == 8 && next == 9)
        {
            return Set.of(nextPos);
        }
        else if (next == current + 1)
        {
            return walk(map, nextPos);
        }
        return Set.of();
    }

    private static int get(final List<int[]> map, final Pos pos)
    {
        try
        {
            return map.get(pos.x)[pos.y];
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    private static int[] charToInt(final char[] chars)
    {
        final int[] result = new int[chars.length];
        for (int i = 0; i < chars.length; i++)
        {
            result[i] = Character.getNumericValue(chars[i]);
        }
        return result;
    }

    public static void main(String[] args)
    {
        part2();
    }
}
