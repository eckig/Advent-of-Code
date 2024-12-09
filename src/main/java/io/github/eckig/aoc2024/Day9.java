package io.github.eckig.aoc2024;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9
{

    private static void part2() throws URISyntaxException, IOException
    {
        final var input = Files.readString(Paths.get(""), StandardCharsets.UTF_8);
        final var diskMap = new ArrayList<DiskMapEntry>();

        int id = 0;
        for (int i = 0; i < input.length(); i += 2)
        {
            final var freeSpace = input.length() > i + 1 ? Character.getNumericValue(input.charAt(i + 1)) : 0;
            final var numericValue = Character.getNumericValue(input.charAt(i));
            diskMap.add(new DiskMapEntry(id++, numericValue, freeSpace));
        }

        int tailIdx;
        Set<Integer> skip = new HashSet<>();
        while ((tailIdx = getLast(diskMap, skip)) != -1)
        {
            final var tail = diskMap.get(tailIdx);
            for (int i = 0; i < tailIdx; i++)
            {
                final var f = diskMap.get(i);
                if (f.freeSpace >= tail.fileLength)
                {
                    final var remainingFree = f.freeSpace - tail.fileLength;
                    diskMap.set(i, new DiskMapEntry(f.id, f.fileLength, 0));
                    diskMap.remove(tailIdx);
                    diskMap.add(tailIdx, new DiskMapEntry(-1, 0, tail.freeSpace + tail.fileLength));
                    diskMap.add(i + 1, new DiskMapEntry(tail.id, tail.fileLength, remainingFree));
                    break;
                }
            }
            // entry can not be moved
            skip.add(tail.id);
        }

        long checksum = 0;
        long index = 0;
        for (final var entry : diskMap)
        {
            if (entry.id != -1)
            {
                for (int j = 0; j < entry.fileLength; j++)
                {
                    checksum += entry.id() * index;
                    index++;
                }
            }
            else
            {
                index += entry.fileLength;
            }
            index += entry.freeSpace;
        }
        System.out.println(diskMap.stream().map(m -> Integer.toString(m.id()).repeat(m.fileLength) + Character.toString('.').repeat(m.freeSpace)).reduce((s1, s2) -> s1 + s2));
        System.out.println(checksum);
    }

    private static int getLast(final ArrayList<DiskMapEntry> pDiskIndex, final Set<Integer> pBlacklist)
    {
        for (int i = pDiskIndex.size() - 1; i >= 0; i--)
        {
            final var entry = pDiskIndex.get(i);
            if (entry.fileLength > 0 && entry.id > 0 && !pBlacklist.contains(entry.id))
            {
                return i;
            }
        }
        return -1;
    }

    private static void part1() throws URISyntaxException, IOException
    {
        final var input = Files.readString(Paths.get(""), StandardCharsets.UTF_8);
        final var diskMap = new ArrayDeque<DiskMapEntry>();

        int id = 0;
        for (int i = 0; i < input.length(); i += 2)
        {
            final var freeSpace = input.length() > i + 1 ? Character.getNumericValue(input.charAt(i + 1)) : 0;
            final var numericValue = Character.getNumericValue(input.charAt(i));
            diskMap.add(new DiskMapEntry(id++, numericValue, freeSpace));
        }

        final var diskIndex = new ArrayList<Fragment>();
        final var first = diskMap.pollFirst();
        put(diskIndex, first);
        FrontEntry frontEntry = new FrontEntry(first, first.freeSpace);
        DiskMapEntry tailEntry;
        defrag:
        while ((tailEntry = diskMap.pollLast()) != null)
        {
            for (int i = 0; i < tailEntry.fileLength; i++)
            {
                    while (frontEntry.remainingFreeSpace <= 0)
                    {
                        frontEntry = frontEntry.take(diskMap, diskIndex);
                        if (frontEntry == null)
                        {
                            for (; i < tailEntry.fileLength; i++)
                            {
                                diskIndex.add(new Fragment(tailEntry.id));
                            }
                            break defrag;
                        }
                    }
                    diskIndex.add(new Fragment(tailEntry.id));
                    frontEntry = frontEntry.take(diskMap, diskIndex);
                    if (frontEntry == null)
                    {
                        for (; i < tailEntry.fileLength; i++)
                        {
                            diskIndex.add(new Fragment(tailEntry.id));
                        }
                        break defrag;
                    }
            }
        }
        long checksum = 0;
        for (int i = 0; i < diskIndex.size(); i++)
        {
            final var idx = diskIndex.get(i);
            checksum += idx.id() * (long) i;
        }
        System.out.println(checksum);
    }

    private static void put(final List<Fragment> fragments, final DiskMapEntry entry)
    {
        for (int i = 0; i < entry.fileLength; i++)
        {
            fragments.add(new Fragment(entry.id));
        }
    }

    private record FrontEntry(DiskMapEntry entry, int remainingFreeSpace)
    {

        FrontEntry take(final ArrayDeque<DiskMapEntry> diskMap, final List<Fragment> fragments)
        {
            if (remainingFreeSpace <= 0)
            {
                while (true)
                {
                    final var nextEntry = diskMap.pollFirst();
                    if (nextEntry == null)
                    {
                        return null;
                    }
                    put(fragments, nextEntry);
                    if (nextEntry.freeSpace > 0)
                    {
                        return new FrontEntry(nextEntry, nextEntry.freeSpace);
                    }
                }
            }
            return new FrontEntry(entry, remainingFreeSpace - 1);
        }
    }

    private record Fragment(long id){

    }

    private record DiskMapEntry(int id, int fileLength, int freeSpace)
    {
    }

    public static void main(String[] args)
    {
        try
        {
            part2();
        }
        catch (URISyntaxException | IOException e)
        {
            e.printStackTrace();
        }
    }
}
