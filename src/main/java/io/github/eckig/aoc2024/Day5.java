package io.github.eckig.aoc2024;

import java.util.ArrayList;
import java.util.Arrays;

public class Day5
{
    private static void day5()
    {
        final var inRules = """
        """;
        final var inData = """
        """;

        final var rules = inRules.lines().map(line -> Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).toArray()).toList();
        final var data = inData.lines().map(line -> Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray()).toList();
        int validCount = 0;
        int fixedCount = 0;
        lines:
        for (final var line : data)
        {
            boolean valid = true;
            for (int i = 0; i < rules.size(); i++)
            {
                final var rule = rules.get(i);
                final var i1 = search(line, rule[0]);
                final var i2 = search(line, rule[1]);
                if (i1 >= 0 && i2 >= 0)
                {
                    if (i2 < i1)
                    {
                        // rule violated, go to next line
                        valid = false;
                        fixup(line, i1, i2);
                        i = 0; // recompute rules
                    }
                }
            }

            if (valid)
            {
                validCount += line[line.length / 2];
            }
            else
            {
                fixedCount += line[line.length / 2];
            }
        }

        System.out.println(validCount);
        System.out.println(fixedCount);
    }

    private static void fixup(final int[] pArray, final int pIndex1, final int pIndex2)
    {
        final int val1 = pArray[pIndex1];
        final int val2 = pArray[pIndex2];
        pArray[pIndex1] = val2;
        pArray[pIndex2] = val1;
    }

    private static int search(final int[] pArray, final int pSearch)
    {
        for (int i = 0; i < pArray.length; i++)
        {
            if (pArray[i] == pSearch)
            {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args)
    {
        day5();
    }
}
