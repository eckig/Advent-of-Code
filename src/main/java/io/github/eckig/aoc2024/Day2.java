package io.github.eckig.aoc2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2
{
    public static void main(String[] args)
    {
        final var data = """
                    """;
        int safeReports=0;
        loop:
        for (final var iter = data.lines().iterator(); iter.hasNext();)
        {
            final var list = new ArrayList<Integer>();
            final var parts = iter.next().split(" ");
            Arrays.stream(parts).map(Integer::parseInt).forEach(list::add);
            if (day2Validate(list))
            {
                safeReports++;
            }
            else
            {
                for (final var part : list)
                {
                    final var copy = new ArrayList<>(list);
                    copy.remove(part);
                    if (day2Validate(copy))
                    {
                        safeReports++;
                        continue loop;
                    }
                }
            }
        }
        System.out.println(safeReports);
    }

    private static boolean day2Validate(final List<Integer> pParts)
    {
        enum Dir
        {
            DEC, INC;
        }
        Dir dir = null;
        for (int i = 1; i < pParts.size(); i++)
        {
            final var part = pParts.get(i);
            final var lastPart = pParts.get(i - 1);
            final var dist = Math.abs(lastPart - part);
            if (dist > 0 && dist <= 3)
            {
                final var direction = part > lastPart ? Dir.DEC : Dir.INC;
                if (dir != null && dir != direction)
                {
                    return false;
                }
                dir = direction;
            }
            else
            {
                return false;
            }
        }
        return true;
    }
}
