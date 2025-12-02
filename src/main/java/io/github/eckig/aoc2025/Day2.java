package io.github.eckig.aoc2025;

import java.util.Arrays;
import java.util.stream.LongStream;

public class Day2
{
    static void main()
    {
        final var input = "";
        final var idEntries = Arrays.stream(input.split(",")).map(IdEntry::of).toList();

        long sum = 0;
        for (final var entry : idEntries)
        {
            sum += LongStream.rangeClosed(Long.parseLong(entry.start), Long.parseLong(entry.end)).filter(Day2::isInvalid).sum();
        }
        System.out.println(sum);

    }

    private static boolean isInvalid(final long pNumber)
    {
        final var str = String.valueOf(pNumber);
        if (str.length() % 2 != 0)
        {
            return false;
        }

        final var left = str.substring(0, str.length() / 2);
        final var right = str.substring(str.length() / 2);
        return left.equals(right);
    }

    record IdEntry(String start, String end)
    {
        public static IdEntry of(final String pArg)
        {
            final var split = pArg.split("-");
            return new IdEntry(split[0], split[1]);

        }
    }
}
