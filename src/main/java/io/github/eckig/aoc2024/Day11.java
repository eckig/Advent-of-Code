package io.github.eckig.aoc2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day11
{
    public static void main(String[] args)
    {
        part1();
        part2();
    }

    private static void part2()
    {
        final var input = "";
        Map<Long, Long> stones = new HashMap<>();
        final Map<Long, Long> finalStones = stones;
        Arrays.stream(input.split(" ")).mapToLong(Long::parseLong).forEach(l -> finalStones.compute(l, (_, value) -> 1 + (value == null ? 0 : value)));

        for (int i = 0; i < 75; i++)
        {
            stones = blink(stones);
        }
        long stoneCount = stones.values().stream().mapToLong(l -> l).sum();
        System.out.println(stoneCount);
    }

    private static void part1()
    {
        final var input = "";
        Map<Long, Long> stones = new HashMap<>();
        final Map<Long, Long> finalStones = stones;
        Arrays.stream(input.split(" ")).mapToLong(Long::parseLong).forEach(l -> finalStones.compute(l, (_, value) -> 1 + (value == null ? 0 : value)));

        for (int i = 0; i < 25; i++)
        {
            stones = blink(stones);
        }
        long stoneCount = stones.values().stream().mapToLong(l -> l).sum();
        System.out.println(stoneCount);
    }

    private static Map<Long, Long> blink(final Map<Long, Long> pStones)
    {
        final Map<Long, Long> compute = new HashMap<>(pStones);
        for (final var entry : pStones.entrySet())
        {
            final var stone = entry.getKey();
            final String s;
            if (stone == 0)
            {
                compute.compute(stone, (_, value) -> value == null ? null : value - entry.getValue());
                compute.compute(1L, (_, value) -> entry.getValue() + (value == null ? 0 : value));
            }
            else if ((s = Long.toString(stone)).length() % 2 == 0)
            {
                final var mid = s.length() / 2;
                final var l1 = Long.parseLong(s.substring(0, mid));
                final var l2 = Long.parseLong(s.substring(mid));

                compute.compute(stone, (_, value) -> value == null ? null : value - entry.getValue());
                compute.compute(l1, (_, value) -> entry.getValue() + (value == null ? 0 : value));
                compute.compute(l2, (_, value) -> entry.getValue() + (value == null ? 0 : value));
            }
            else
            {
                compute.compute(stone, (_, value) -> value == null ? null : value - entry.getValue());
                compute.compute(stone * 2024, (_, value) -> entry.getValue() + (value == null ? 0 : value));
            }
        }
        return compute;
    }
}
