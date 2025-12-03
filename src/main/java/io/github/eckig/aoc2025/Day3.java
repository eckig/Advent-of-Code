package io.github.eckig.aoc2025;

import java.util.Arrays;

public class Day3
{
    static void main()
    {
        final var input = """
""";
        final var banks = input.lines().map(BatteryBank::of).toList();

        // Part 1
        long sum = 0;
        for(final var b : banks)
        {
            sum += b.highestJoltage2();
        }
        System.out.println(sum);

        // Part 2
        sum = 0;
        for(final var b : banks)
        {
            sum += b.highestJoltage12();
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

        long highestJoltage12()
        {
            final var value = new int[12];
            var lastIndex = -1;
            for (int k = 0; k < 12; k++)
            {
                int next = -1;
                for (int i = lastIndex + 1; i <= jolts.length - 12 + k; i++)
                {
                    if (jolts[i] > next)
                    {
                        next = jolts[i];
                        lastIndex = i;
                    }
                }
                value[k] = next;
            }
            return number(value);
        }

        int number(final int p1, final int p2)
        {
            return Integer.parseInt(p1 + "" + p2);
        }

        long number(final int[] p1)
        {
            return Long.parseLong(Arrays.stream(p1).mapToObj(Integer::toString).reduce((a, b) -> a + b).orElseThrow());
        }
    }
}
