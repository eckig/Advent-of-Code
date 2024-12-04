package io.github.eckig.aoc2024;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day1
{
    public static void main(String[] args)
    {
        final var input = """
        """;
        final var lines = input.lines().toList();
        final int[] listLeft = new int[lines.size()];
        final int[] listRight = new int[lines.size()];

        for (int i = 0; i < lines.size(); i++)
        {
            final var parts = lines.get(i).split("   ");
            listLeft[i] = Integer.parseInt(parts[0]);
            listRight[i] = Integer.parseInt(parts[1]);
        }

        Arrays.sort(listLeft);
        Arrays.sort(listRight);

        int distance = 0;
        for (int i = 0; i < lines.size(); i++)
        {
            final var theDist = Math.abs(listRight[i] - listLeft[i]);
            distance += theDist;
        }
        System.out.println(distance);

        final Map<Integer, Integer> similarities = new HashMap<>();
        for (int i = 0; i < lines.size(); i++)
        {
            final var number = listLeft[i];
            final var matches = (int)Arrays.stream(listRight).filter(ele -> ele== number).count();
            similarities.compute(number, (k, v) -> v == null ? matches : v + matches);
        }
        int similarity=0;
        for(final var entry : similarities.entrySet())
        {
            similarity += entry.getKey().intValue()*entry.getValue().intValue();
        }

        System.out.println(similarities);
        System.out.println(similarity);
    }
}
