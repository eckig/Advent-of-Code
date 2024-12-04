package io.github.eckig.aoc2024;

import java.util.regex.Pattern;

public class Day3
{
    public static void main(String[] args)
    {
        final var input = """
        """;
        final var pattern = Pattern.compile("don't\\(\\)|do\\(\\)|mul\\(([0-9]{0,3}),([0-9]{0,3})\\)");
        final var matcher = pattern.matcher(input);
        int result =0;
        boolean enable = true;
        while (matcher.find())
        {
            final var theMatch = matcher.group(0);
            if(theMatch.equals("don't()"))
            {
                enable = false;
            }
            else if(theMatch.equals("do()"))
            {
                enable = true;
            }
            else if (enable)
            {
                final var match = Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
                result += match;
            }
        }
        System.out.println(result);
    }
}
