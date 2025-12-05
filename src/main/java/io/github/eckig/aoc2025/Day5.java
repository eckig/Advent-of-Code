package io.github.eckig.aoc2025;

import java.util.ArrayList;

public class Day5
{
    static void main()
    {
        final var input = """
""";
        final var ranges = new ArrayList<IdRange>();

        boolean parseIds = false;
        int fresh = 0;
        for (final var iter = input.lines().iterator(); iter.hasNext(); )
        {
            final var line = iter.next();
            if (parseIds)
            {
                final var id = Long.parseLong(line);
                if (ranges.stream().anyMatch(r -> r.contains(id)))
                {
                    fresh++;
                }
            }
            else
            {
                if(line.isBlank())
                {
                    parseIds = true;
                }
                else
                {
                    ranges.add(IdRange.of(line));
                }
            }
        }
        System.out.println(fresh);
    }

    record IdRange(long start, long end)
    {
        static IdRange of(String line)
        {
            final var split = line.split("-");
            return new IdRange(Long.parseLong(split[0]), Long.parseLong(split[1]));
        }

        boolean contains(final long id)
        {
            return id >= start && id <= end;
        }
    }
}
