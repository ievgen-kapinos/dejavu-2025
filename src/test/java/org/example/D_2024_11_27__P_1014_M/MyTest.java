package org.example.D_2024_11_27__P_1014_M;

import org.example.MyTestConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/best-sightseeing-pair
 *
 * <pre>
 * 1014. Best Sightseeing Pair
 * Medium
 * Topics
 * Companies
 * Hint
 * You are given an integer array values where values[i] represents the value of the ith sightseeing spot. Two sightseeing spots i and j have a distance j - i between them.
 *
 * The score of a pair (i < j) of sightseeing spots is values[i] + values[j] + i - j: the sum of the values of the sightseeing spots, minus the distance between them.
 *
 * Return the maximum score of a pair of sightseeing spots.
 *
 *
 *
 * Example 1:
 *
 * Input: values = [8,1,5,2,6]
 * Output: 11
 * Explanation: i = 0, j = 2, values[i] + values[j] + i - j = 8 + 5 + 0 - 2 = 11
 * Example 2:
 *
 * Input: values = [1,2]
 * Output: 2
 *
 *
 * Constraints:
 *
 * 2 <= values.length <= 5 * 104
 * 1 <= values[i] <= 1000
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_27__P_1014_M.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(int expected, @MyTestConverter int[] values) {

        var result = solution.maxScoreSightseeingPair(values);

        assertEquals(expected, result);
    }
}

class Solution {
    public int maxScoreSightseeingPair(int[] values) {
        var maxScore = -1;
        var maxLeft = 0;
        for (int i = 0; i < values.length; i++) {
            var currRight = values[i] - i;
            maxScore = Math.max(maxScore, maxLeft + currRight);
            maxLeft = Math.max(maxLeft, values[i] + i);
        }
        return maxScore;
    }
}

class Solution0 {
    public int maxScoreSightseeingPair(int[] values) {
        var maxScore = -1;
        for (int i = 0; i < values.length-1; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (j-i > 2000) {
                    break;
                }
                var score = values[i] + values[j] + i - j;
                maxScore = Math.max(maxScore, score);
            }
        }
        return maxScore;
    }
}