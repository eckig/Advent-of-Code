package io.github.eckig.aoc2025;

public class Day1
{
    static void main()
    {
        final var input = """
    """;
        final var turns = input.lines().map(DialTurn::of).toList();
        int index = 50;
        int zeros = 0;
        for (final var turn : turns)
        {
            index = turn.direction().apply(index, turn.clicks());
            if (index == 0)
            {
                zeros++;
            }
        }
        System.out.println(zeros);
    }

    enum Direction
    {
        L,
        R;

        int apply(final int pIndex, int pClicks)
        {
            var newIndex = pIndex;
            for (int i = 0; i < pClicks; i++)
            {
                newIndex += this == L ? -1 : 1;
                if (newIndex < 0)
                {
                    newIndex = 99;
                }
                else if (newIndex > 99)
                {
                    newIndex = 0;
                }
            }
            return newIndex;
        }
    }

    record DialTurn(Direction direction, int clicks)
    {
        public static DialTurn of(final String pArg)
        {
            final String arg1 = pArg.substring(0, 1);
            final String arg2 = pArg.substring(1);
            return new DialTurn(Direction.valueOf(arg1), Integer.parseInt(arg2));
        }

    }
}
