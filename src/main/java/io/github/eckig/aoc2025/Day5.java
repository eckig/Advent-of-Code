package io.github.eckig.aoc2025;

import java.util.ArrayList;

public class Day5
{
    static void main()
    {
        final var input = """
""";
        final var ranges = new ArrayList<IdRange>();

        // Part 1
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

        // Part 2
        boolean merges;
        do
        {
            merges = false;
            outer:
            for (int i = 0; i < ranges.size(); ++i)
            {
                for (int j = 0; j < ranges.size(); j++)
                {
                    final var r1 = ranges.get(i);
                    final var r2 = ranges.get(j);
                    if (r1 != r2)
                    {
                        final var merge = r1.merge(r2);
                        if (merge != null)
                        {
                            merges = true;
                            ranges.remove(r1);
                            ranges.remove(r2);
                            ranges.addFirst(merge);
                            break outer;
                        }
                    }
                }
            }
        }
        while (merges);
        long freshIds = 0;
        for (final var r : ranges)
        {
            freshIds += r.count();
        }
        System.out.println(freshIds);
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

        boolean intersects(final IdRange other)
        {
            if (start <= other.start)
            {
                return other.start <= end;
            }
            else
            {
                return start <= other.end;
            }
        }

        IdRange merge(final IdRange other)
        {
            return intersects(other) ? new IdRange(Math.min(other.start, start), Math.max(other.end, end)) : null;
        }

        long count()
        {
            return end - start + 1;
        }
    }
}
