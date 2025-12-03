package io.github.eckig.aoc2025;

public class Day3
{
    static void main()
    {
        final var input = """
""";
        final var banks = input.lines().map(BatteryBank::of).toList();

        int sum = 0;
        for(final var b : banks)
        {
            sum += b.highestJoltage2();
        }
        System.out.println(sum);
    }

    record BatteryBank(int[] jolts)
    {
        static BatteryBank of(final String pLine)
        {
            final int[] jolts = new int[pLine.length()];
            for(int i = 0; i < pLine.length(); i++)
            {
                jolts[i] = Character.getNumericValue(pLine.charAt(i));
            }
            return new BatteryBank(jolts);
        }

        int highestJoltage2()
        {
            int max = 0;
            for (int i = 0; i < jolts.length; i++)
            {
                final var n1 = jolts[i];
                for (int j = 0; j < jolts.length; j++)
                {
                    final var n2 = jolts[j];
                    if (j > i)
                    {
                        max = Math.max(max, number(n1, n2));
                    }
                }
            }
            return max;
        }

        int number(final int p1, final int p2)
        {
            return Integer.parseInt(p1 + "" + p2);
        }
    }
}
