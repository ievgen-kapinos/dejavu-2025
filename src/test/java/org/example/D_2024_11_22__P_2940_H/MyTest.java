package org.example.D_2024_11_22__P_2940_H;

import org.example.MyTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


/**
 * https://leetcode.com/problems/find-building-where-alice-and-bob-can-meet
 *
 * <pre>
 * 2940. Find Building Where Alice and Bob Can Meet
 * Hard
 * Topics
 * Companies
 * Hint
 * You are given a 0-indexed array heights of positive integers, where heights[i] represents the height of the ith building.
 *
 * If a person is in building i, they can move to any other building j if and only if i < j and heights[i] < heights[j].
 *
 * You are also given another array queries where queries[i] = [ai, bi]. On the ith query, Alice is in building ai while Bob is in building bi.
 *
 * Return an array ans where ans[i] is the index of the leftmost building where Alice and Bob can meet on the ith query. If Alice and Bob cannot move to a common building on query i, set ans[i] to -1.
 *
 *
 *
 * Example 1:
 *
 * Input: heights = [6,4,8,5,2,7], queries = [[0,1],[0,3],[2,4],[3,4],[2,2]]
 * Output: [2,5,-1,5,2]
 * Explanation: In the first query, Alice and Bob can move to building 2 since heights[0] < heights[2] and heights[1] < heights[2].
 * In the second query, Alice and Bob can move to building 5 since heights[0] < heights[5] and heights[3] < heights[5].
 * In the third query, Alice cannot meet Bob since Alice cannot move to any other building.
 * In the fourth query, Alice and Bob can move to building 5 since heights[3] < heights[5] and heights[4] < heights[5].
 * In the fifth query, Alice and Bob are already in the same building.
 * For ans[i] != -1, It can be shown that ans[i] is the leftmost building where Alice and Bob can meet.
 * For ans[i] == -1, It can be shown that there is no building where Alice and Bob can meet.
 * Example 2:
 *
 * Input: heights = [5,3,8,2,6,1,4,6], queries = [[0,7],[3,5],[5,2],[3,0],[1,6]]
 * Output: [7,6,-1,4,6]
 * Explanation: In the first query, Alice can directly move to Bob's building since heights[0] < heights[7].
 * In the second query, Alice and Bob can move to building 6 since heights[3] < heights[6] and heights[5] < heights[6].
 * In the third query, Alice cannot meet Bob since Bob cannot move to any other building.
 * In the fourth query, Alice and Bob can move to building 4 since heights[3] < heights[4] and heights[0] < heights[4].
 * In the fifth query, Alice can directly move to Bob's building since heights[1] < heights[6].
 * For ans[i] != -1, It can be shown that ans[i] is the leftmost building where Alice and Bob can meet.
 * For ans[i] == -1, It can be shown that there is no building where Alice and Bob can meet.
 *
 *
 *
 * Constraints:
 *
 * 1 <= heights.length <= 5 * 104
 * 1 <= heights[i] <= 109
 * 1 <= queries.length <= 5 * 104
 * queries[i] = [ai, bi]
 * 0 <= ai, bi <= heights.length - 1
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_22__P_2940_H.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(String expectedStr, String heightsStr, String queriesStr) {
        var expected = MyTestUtils.parseArray(expectedStr);
        var heights = MyTestUtils.parseArray(heightsStr);
        var queries = MyTestUtils.parseArray2D(queriesStr);

        var result = solution.leftmostBuildingQueries(heights, queries);

        assertArrayEquals(expected, result);
    }
}


class Solution {

    record QEntry(int qIdx, int maxIdx, int maxHeight) {
    }

    public int[] leftmostBuildingQueries(int[] heights, int[][] queries) {
        int[] ans = new int[queries.length];
        var queriesByMaxIdx = new HashMap<Integer, List<QEntry>>();
        for (int qIdx = 0; qIdx < queries.length; qIdx++) {

            var aIdx = queries[qIdx][0];
            var bIdx = queries[qIdx][1];
            var aHeight = heights[aIdx];
            var bHeight = heights[bIdx];

            if (aIdx == bIdx) {
                ans[qIdx] = aIdx;
            } else if (aIdx < bIdx && aHeight < bHeight) {
                ans[qIdx] = bIdx;
            } else if (bIdx < aIdx && bHeight < aHeight) {
                ans[qIdx] = aIdx;
            } else {
                ans[qIdx] = -1;
                var maxIdx = Math.max(aIdx, bIdx);
                var maxHeight = Math.max(aHeight, bHeight);

                var qEntry = new QEntry(qIdx, maxIdx, maxHeight);
                queriesByMaxIdx
                        .computeIfAbsent(maxIdx, k -> new ArrayList<>())
                        .add(qEntry);
            }
        }

        var queriesByMaxHeight = new PriorityQueue<QEntry>((a, b) -> a.maxHeight - b.maxHeight);
        for (var hIdx = 0; hIdx < heights.length; hIdx++) {
            var height = heights[hIdx];

            // Process pending queries
            while (!queriesByMaxHeight.isEmpty()) {
                var qEntry = queriesByMaxHeight.peek();
                if (qEntry.maxHeight >= height) {
                    break;
                }
                ans[qEntry.qIdx] = hIdx;
                queriesByMaxHeight.poll();
            }

            // add pending, all have maxIdx< hdx
            var queriesWithMaxIdxEqualHIdx = queriesByMaxIdx.remove(hIdx);
            if (queriesWithMaxIdxEqualHIdx != null) {
                queriesByMaxHeight.addAll(queriesWithMaxIdxEqualHIdx);
            }
        }

        return ans;
    }
}

class Solution0 {
    public int[] leftmostBuildingQueries(int[] heights, int[][] queries) {
        TreeSet<Integer>[] idxToCanMoveToSet = new TreeSet[heights.length];
        var heightToIdx = new TreeMap<Integer, List<Integer>>();
        for (int fromIdx = heights.length - 1; fromIdx >= 0; fromIdx--) {
            var higherAndOnTheRight = heightToIdx.tailMap(heights[fromIdx], false); // exclusive

            var canMoveToSet = new TreeSet<Integer>();
            canMoveToSet.add(fromIdx);
            for (var idxList : higherAndOnTheRight.values()) {
                for (var toIdx : idxList) {
                    canMoveToSet.addAll(idxToCanMoveToSet[toIdx]);
                }
            }

            idxToCanMoveToSet[fromIdx] = canMoveToSet;

            heightToIdx
                    .computeIfAbsent(heights[fromIdx], k -> new ArrayList<>())
                    .add(fromIdx);
        }

        var ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {

            var aStartIdx = queries[i][0];
            var bStartIdx = queries[i][1];

            var aCanMoveToSet = idxToCanMoveToSet[aStartIdx];
            var bCanMoveToSet = idxToCanMoveToSet[bStartIdx];

            var minIndex = Math.min(aStartIdx, bStartIdx);
            var aTail = aCanMoveToSet.tailSet(minIndex, true);
            var bTail = bCanMoveToSet.tailSet(minIndex, true);

            var aIterator = aTail.iterator();
            var bIterator = bTail.iterator();

            var aIdx = aIterator.next();
            var bIdx = bIterator.next();
            ans[i] = -1;
            while (true) {
                if (aIdx < bIdx) {
                    if (aIterator.hasNext()) {
                        aIdx = aIterator.next();
                    } else {
                        break;
                    }
                } else if (aIdx > bIdx) {
                    if (bIterator.hasNext()) {
                        bIdx = bIterator.next();
                    } else {
                        break;
                    }
                } else {
                    ans[i] = aIdx;
                    break;
                }
            }
        }
        return ans;
    }
}