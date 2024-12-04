package io.github.eckig.aoc2024;

import java.util.function.IntUnaryOperator;

public class Day4
{
    private static void day4_part1()
    {
        final var input = """
                """;
        final char[][] grid = new char[(int) input.lines().count()][input.lines().iterator().next().length()];
        int line = 0;
        for (final var iter = input.lines().iterator(); iter.hasNext(); )
        {
            final var l = iter.next();
            for (int j = 0; j < l.length(); j++)
            {
                grid[line][j] = l.charAt(j);
            }
            line++;
        }
        int matches = 0;
        for (int i = 0; i < grid.length; i++)
        {
            final var chars = grid[i];
            for (int j = 0; j < chars.length; j++)
            {
                // vertical
                if (day4_gridSearch(new StringBuilder(), grid, i, j, x -> x + 1, IntUnaryOperator.identity()))
                {
                    matches++;
                }
                // vertical backwards
                if (day4_gridSearch(new StringBuilder(), grid, i, j, x -> x - 1, IntUnaryOperator.identity()))
                {
                    matches++;
                }
                // horizontal
                if (day4_gridSearch(new StringBuilder(), grid, i, j, IntUnaryOperator.identity(), y -> y + 1))
                {
                    matches++;
                }
                // horizontal backwards
                if (day4_gridSearch(new StringBuilder(), grid, i, j, IntUnaryOperator.identity(), y -> y - 1))
                {
                    matches++;
                }
                // diagonal a
                if (day4_gridSearch(new StringBuilder(), grid, i, j, x -> x + 1, y -> y + 1))
                {
                    matches++;
                }
                // diagonal b
                if (day4_gridSearch(new StringBuilder(), grid, i, j, x -> x + 1, y -> y - 1))
                {
                    matches++;
                }
                // diagonal c
                if (day4_gridSearch(new StringBuilder(), grid, i, j, x -> x - 1, y -> y + 1))
                {
                    matches++;
                }
                // diagonal d
                if (day4_gridSearch(new StringBuilder(), grid, i, j, x -> x - 1, y -> y - 1))
                {
                    matches++;
                }
            }
        }
        System.out.println(matches);
    }

    private static void day4_part2()
    {
        final var input = """
                """;
        final char[][] grid = new char[(int) input.lines().count()][input.lines().iterator().next().length()];
        int line = 0;
        for (final var iter = input.lines().iterator(); iter.hasNext(); )
        {
            final var l = iter.next();
            for (int j = 0; j < l.length(); j++)
            {
                grid[line][j] = l.charAt(j);
            }
            line++;
        }
        int matches = 0;
        for (int i = 0; i < grid.length; i++)
        {
            final var chars = grid[i];
            for (int j = 0; j < chars.length; j++)
            {
                if (day4_matchXMas(i, j, grid))
                {
                    matches++;
                }
            }
        }
        System.out.println(matches);
    }

    private static boolean day4_matchXMas(final int pX, final int pY, final char[][] pGrid)
    {
        try
        {
            int x = pX, y = pY;
            final char[] topLeftToBottomRight = new char[3];
            topLeftToBottomRight[0] = pGrid[x++][y++];
            topLeftToBottomRight[1] = pGrid[x++][y++];
            topLeftToBottomRight[2] = pGrid[x][y];

            final char[] topRightToBottomLeft = new char[3];
            x = pX;
            y = pY + 2;
            topRightToBottomLeft[0] = pGrid[x++][y--];
            topRightToBottomLeft[1] = pGrid[x++][y--];
            topRightToBottomLeft[2] = pGrid[x][y];
            final var s1 = new String(topLeftToBottomRight);
            final var s2 = new String(topRightToBottomLeft);
            if ((s1.equals("MAS") || s1.equals("SAM")) && (s2.equals("MAS") || s2.equals("SAM")))
            {
                return true;
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            //ignore
        }
        return false;
    }

    private static boolean day4_gridSearch(final StringBuilder pString, final char[][] pGrid, final int pX, final int pY, final IntUnaryOperator pNextX, final IntUnaryOperator pNextY)
    {
        try
        {
            int x = pX;
            int y = pY;
            final char current = pGrid[x][y];

            if (current != ' ' && current == day4_getNextChar(pString))
            {
                pString.append(current);
            }
            else
            {
                return false;
            }
            if (pString.toString().equals("XMAS"))
            {
                return true;
            }
            x = pNextX.applyAsInt(x);
            y = pNextY.applyAsInt(y);
            return day4_gridSearch(pString, pGrid, x, y, pNextX, pNextY);
        }
        catch (ArrayIndexOutOfBoundsException pE)
        {
            //ignore
            return false;
        }
    }

    private static char day4_getNextChar(final StringBuilder pChar)
    {
        return switch (pChar.toString())
        {
            case "" -> 'X';
            case "X" -> 'M';
            case "XM" -> 'A';
            case "XMA" -> 'S';
            default -> ' ';
        };
    }

    public static void main(String[] args)
    {
        day4_part2();
    }
}
