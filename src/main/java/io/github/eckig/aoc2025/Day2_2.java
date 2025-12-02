package io.github.eckig.aoc2025;

import java.util.Arrays;
import java.util.stream.LongStream;

public class Day2_2
{
    static void main()
    {
        final var input = "";
        final var idEntries = Arrays.stream(input.split(",")).map(IdEntry::of).toList();

        long sum = 0;
        for (final var entry : idEntries)
        {
            sum += LongStream.rangeClosed(Long.parseLong(entry.start), Long.parseLong(entry.end)).filter(Day2_2::isInvalid).sum();
        }
        System.out.println(sum);
    }

    private static boolean isInvalid(final long pNumber)
    {
        final var str = String.valueOf(pNumber);
        final var maxPartitions = str.length() / 2;

        for (int partitionSize = 1; partitionSize <= maxPartitions; partitionSize++)
        {
            if (partitions(str, partitionSize))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean partitions(String pText, int pSize)
    {
        final var length = pText.length();
        String first = null;
        int matches = 0;
        int partitions = 0;
        for (int i = 0; i < length; i += pSize)
        {
            final var str = pText.substring(i, Math.min(length, i + pSize));
            if (first == null)
            {
                first = str;
            }
            if (first.equals(str))
            {
                matches++;
            }
            partitions++;
        }
        return matches == partitions;
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
