package io.github.eckig.aoc2024;

import java.util.*;

public class Day7 {
    private record Equation(long result, long[] ingredients) {
    }

    private enum Calc {ADD, MULTIPLY, CONCAT}

    private static void part2() {
        final var input = """
                """;
        final var ingredients = input.lines().map(Day7::toEquation).toList();
        long sum = 0;
        for (final var in : ingredients) {
            if (test(in, true)) {
                sum += in.result();
            }
        }
        System.out.println(sum);
    }

    private static void part1() {

        final var input = """
                """;

        final var ingredients = input.lines().map(Day7::toEquation).toList();
        long sum = 0;
        for (final var in : ingredients) {
            if (test(in, false)) {
                sum += in.result();
            }
        }
        System.out.println(sum);
    }

    private static boolean test(Equation equation, boolean pConcat) {
        final var ingredients = equation.ingredients();
        int col = equation.ingredients().length - 1;
        Calc[] states = new Calc[col];
        Arrays.fill(states, Calc.ADD);
        State state = new State(states);
        while (true) {
            if (calc(ingredients, state.states()) == equation.result()) {
                return true;
            }
            state = state.next(pConcat);
            if (state == null) {
                return false;
            }
        }
    }

    record State(Calc[] states) {
        State next(final boolean pConcat) {
            Calc[] next = new Calc[states.length];
            System.arraycopy(states, 0, next, 0, next.length);
            for (int i = 0; i < next.length; i++) {
                final var n = getNext(states[i], pConcat);
                if (n != null) {
                    next[i] = n;
                    return new State(next);
                } else {
                    next[i] = Calc.ADD;
                }
            }
            return null;
        }
    }

    private static Calc getNext(final Calc pCalc, final boolean pConcat) {
        if(pConcat) {
            return switch (pCalc) {
                case ADD -> Calc.MULTIPLY;
                case MULTIPLY -> Calc.CONCAT;
                case CONCAT -> null;
            };
        }
        else{
            return switch (pCalc) {
                case ADD -> Calc.MULTIPLY;
                case MULTIPLY, CONCAT -> null;
            };
        }
    }

    private static long calc(final long[] pNumbers, final Calc[] pTest) {
        long result = 0;
        for (int i = 1; i < pNumbers.length; i++) {
            final var calc = pTest[i - 1];
            final var p1 = i == 1 ? pNumbers[i - 1] : result;
            final var p2 = pNumbers[i];
            result = calc(p1, p2, calc);
        }
        return result;
    }

    private static long calc(final long p1, final long p2, final Calc pCalc) {
        return switch (pCalc) {
            case ADD -> p1 + p2;
            case MULTIPLY -> p1 * p2;
            case CONCAT -> Long.parseLong(p1 + Long.toString(p2));
        };
    }

    private static Equation toEquation(String line) {
        final var main = line.split(":");
        final var parts = main[1].trim().split(" ");
        return new Equation(Long.parseLong(main[0]), Arrays.stream(parts).mapToLong(Long::parseLong).toArray());
    }

    public static void main(String[] args) {
        part2();
    }
}
