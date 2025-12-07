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

        // part 1:
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
        System.out.println(dataByColumn.values().stream().mapToLong(Day6::computePart1).sum());

        // part 2:
        System.out.println(dataByColumn.values().stream().mapToLong(Day6::computePart2).sum());
    }

    private static long computePart1(final List<String> pData)
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
        pData.addLast(operator);
        return result;
    }

    private static long computePart2(final List<String> pData)
    {
        final var operator = pData.removeLast();
        final var len = pData.stream().mapToInt(String::length).max().orElseThrow();
        final var numbers = new ArrayList<Long>();
        for (int i = len - 1; i >= 0; i--)
        {
            String number = null;
            for (String d : pData)
            {
                while (d.length() < len)
                {
                    d = " " + d;
                }
                final var num = i >= d.length() ? 0 : Character.getNumericValue(d.charAt(i));
                if (num > 0)
                {
                    number = number == null ? String.valueOf(num) : number + num;
                }
            }
            numbers.add(Long.parseLong(number));
        }

        return switch (operator)
        {
            case "+" -> numbers.stream().mapToLong(l -> l).sum();
            case "*" -> numbers.stream().mapToLong(l -> l).reduce(1, Math::multiplyExact);
            default -> throw new IllegalStateException();
        };
    }
}
