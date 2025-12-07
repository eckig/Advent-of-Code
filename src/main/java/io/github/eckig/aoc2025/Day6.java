package io.github.eckig.aoc2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day6
{
    static void main()
    {
        final var input = """
""";

        final var dataByColumn = new HashMap<Integer, List<String>>();
        for (final var iter = input.lines().iterator(); iter.hasNext();)
        {
            final var line = iter.next();
            final var cols = Arrays.stream(line.split(" ")).filter(s -> !s.isBlank()).toList();
            for (int i = 0; i < cols.size(); i++)
            {
                dataByColumn.computeIfAbsent(i, _ -> new ArrayList<>()).add(cols.get(i));
            }
        }
        System.out.println(dataByColumn.values().stream().mapToLong(Day6::compute).sum());
    }

    private static long compute(final List<String> pData)
    {
        final var operator = pData.removeLast();
        long result = -1;
        for (int i = 1; i < pData.size(); i++)
        {
            final long v1 = result == -1 ? Long.parseLong(pData.get(i - 1)) : result;
            final long v2 = Long.parseLong(pData.get(i));
            result = switch (operator)
            {
                case "*" -> v1 * v2;
                case "+" -> v1 + v2;
                default -> throw new IllegalStateException();
            };
        }
        return result;
    }
}
