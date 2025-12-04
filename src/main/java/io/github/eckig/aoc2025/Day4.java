package io.github.eckig.aoc2025;

public class Day4
{
    static void main()
    {
        final var input = """
""";
        final var lines = input.lines().toList();
        final var map = new char[lines.getFirst().length()][lines.size()];
        for (int i = 0; i < lines.size(); i++)
        {
            for (int j = 0; j < lines.get(i).length(); j++)
            {
                map[i][j] = lines.get(i).charAt(j);
            }
        }

        // part 1:
        int positions = 0;
        for (int a = 0; a < map.length; a++)
        {
            for (int b = 0; b < map[a].length; b++)
            {
                final var val = map[a][b];
                if (val == '@' && countAdjacentRollsOfPaper(a, b, map) < 4)
                {
                    positions++;
                }
            }
        }
        System.out.println(positions);

        // part 2:
        int removed = 0;
        boolean changes;
        do
        {
            changes = false;
            for (int a = 0; a < map.length; a++)
            {
                for (int b = 0; b < map[a].length; b++)
                {
                    final var val = map[a][b];
                    if (val == '@' && countAdjacentRollsOfPaper(a, b, map) < 4)
                    {
                        map[a][b] = 'x';
                        removed++;
                        changes = true;
                    }
                }
            }
        }
        while (changes);
        System.out.println(removed);
    }

    private static int countAdjacentRollsOfPaper(final int a, final int b, final char[][] map)
    {
        int adjacentRolls = 0;
        for (final var dir1 : Direction.values())
        {
            for (final var dir2 : Direction.values())
            {
                if (dir1 == Direction.CURRENT && dir2 == Direction.CURRENT)
                {
                    continue;
                }
                try
                {
                    final char val = map[dir1.apply(a)][dir2.apply(b)];
                    if (val == '@')
                    {
                        adjacentRolls++;
                    }
                }
                catch (ArrayIndexOutOfBoundsException _) {}
            }
        }
        return adjacentRolls;
    }

    enum Direction
    {
        NEXT,
        CURRENT,
        PREVIOUS;

        int apply(final int pPos)
        {
            return switch (this)
            {
                case NEXT -> pPos + 1;
                case PREVIOUS -> pPos - 1;
                case CURRENT -> pPos;
            };
        }
    }
}
