package org.example.D_2024_11_10__P_2054;

import org.example.MyTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/two-best-non-overlapping-events
 *
 * <pre>
 * Medium
 * Topics
 * Companies
 * Hint
 * You are given a 0-indexed 2D integer array of events where events[i] = [startTimei, endTimei, valuei].
 * The ith event starts at startTimei and ends at endTimei, and if you attend this event, you will receive
 * a value of valuei. You can choose at most two non-overlapping events to attend such that the sum of
 * their values is maximized.
 *
 * Return this maximum sum.
 *
 * Note that the start time and end time is inclusive: that is, you cannot attend two events where one of
 * them starts and the other ends at the same time. More specifically, if you attend an event with end time t,
 * the next event must start at or after t + 1.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: events = [[1,3,2],[4,5,2],[2,4,3]]
 * Output: 4
 * Explanation: Choose the green events, 0 and 1 for a sum of 2 + 2 = 4.
 * Example 2:
 *
 * Example 1 Diagram
 * Input: events = [[1,3,2],[4,5,2],[1,5,5]]
 * Output: 5
 * Explanation: Choose event 2 for a sum of 5.
 * Example 3:
 *
 *
 * Input: events = [[1,5,3],[1,5,1],[6,6,5]]
 * Output: 8
 * Explanation: Choose events 0 and 2 for a sum of 3 + 5 = 8.
 *
 *
 * Constraints:
 *
 * 2 <= events.length <= 105
 * events[i].length == 3
 * 1 <= startTimei <= endTimei <= 109
 * 1 <= valuei <= 106
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_10__P_2054.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(int expected, String eventsStr) {
        var events = MyTestUtils.parseArray2D(eventsStr);
        assertEquals(expected, solution.maxTwoEvents(events));
    }
}

class Solution {
    public int maxTwoEvents(int[][] events) {
        var tailMax = new TreeMap<Integer, Integer>();
        for (var event : events) {
            var startTime = event[0];
            var value = event[2];
            tailMax.merge(startTime, value, Integer::max);
        }
        var lastTailMax = 0;
        for (var entry : tailMax.reversed().entrySet()) {
            lastTailMax = Math.max(lastTailMax, entry.getValue());
            entry.setValue(lastTailMax);
        }

        var max = 0;
        for (int i = 0; i < events.length; i++) {
            var event1 = events[i];
            var endTime1 = event1[1];
            var value1 = event1[2];

            var value2 = Optional
                    .ofNullable(tailMax.higherEntry(endTime1))
                    .map(Map.Entry::getValue)
                    .orElse(0);
            max = Math.max(max, value1 + value2);
        }
        return max;
    }
}

