package org.example.D_2024_11_13__P_2593;

import org.example.MyTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/find-score-of-an-array-after-marking-all-elements
 *
 * <pre>
 *Medium
 * Topics
 * Companies
 * Hint
 * You are given an array nums consisting of positive integers.
 *
 * Starting with score = 0, apply the following algorithm:
 *
 * Choose the smallest integer of the array that is not marked. If there is a tie, choose the one with the smallest refIndex.
 * Add the value of the chosen integer to score.
 * Mark the chosen element and its two adjacent elements if they exist.
 * Repeat until all the array elements are marked.
 * Return the score you get after applying the above algorithm.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,1,3,4,5,2]
 * Output: 7
 * Explanation: We mark the elements as follows:
 * - 1 is the smallest unmarked element, so we mark it and its two adjacent elements: [2,1,3,4,5,2].
 * - 2 is the smallest unmarked element, so we mark it and its left adjacent element: [2,1,3,4,5,2].
 * - 4 is the only remaining unmarked element, so we mark it: [2,1,3,4,5,2].
 * Our score is 1 + 2 + 4 = 7.
 * Example 2:
 *
 * Input: nums = [2,3,5,1,3,2]
 * Output: 5
 * Explanation: We mark the elements as follows:
 * - 1 is the smallest unmarked element, so we mark it and its two adjacent elements: [2,3,5,1,3,2].
 * - 2 is the smallest unmarked element, since there are two of them, we choose the left-most one, so we mark the one at refIndex 0 and its right adjacent element: [2,3,5,1,3,2].
 * - 2 is the only remaining unmarked element, so we mark it: [2,3,5,1,3,2].
 * Our score is 1 + 2 + 2 = 5.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * 1 <= nums[i] <= 106
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_13__P_2593.csv", numLinesToSkip = 1, maxCharsPerColumn=500000)
    public void myTest(long expected, String numsStr) {
        var nums = MyTestUtils.parseArray(numsStr);
        assertEquals(expected, solution.findScore(nums));
    }
}

// O(n^2)
class Solution0 {
    public long findScore(int[] nums) {
        var score = 0L;

        while(true) {
            var minIndex = -1;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == -1) {
                    continue; // marked
                }
                if (minIndex == -1 || nums[i] < nums[minIndex]) {
                    minIndex = i;
                }
            }

            if (minIndex >= 0) {
                score += nums[minIndex];
                nums[minIndex] = -1;
                if (minIndex > 0) {
                    nums[minIndex-1] = -1;
                }
                if (minIndex < nums.length-1) {
                    nums[minIndex+1] = -1;
                }
            } else {
                break;
            }

        };

        return score;
    }
}

// O(n Log n)
class Solution {

    public long findScore(int[] nums) {
        var score = 0L;

        // [0] -> value
        // [1] -> ref to original index
        var sorted = new int[nums.length][];
        for (int originalIndex = 0; originalIndex < nums.length; originalIndex++) {
            sorted[originalIndex] = new int[]{nums[originalIndex], originalIndex};
        }
        Arrays.sort(sorted, Comparator.comparingInt(a -> a[0]));

        // [0] -> ref to sorted index
        var original = new int[nums.length];
        for (int sortedIndex = 0; sortedIndex < nums.length; sortedIndex++) {
            var originalIndex = sorted[sortedIndex][1];
            original[originalIndex] = sortedIndex;
        }

        for (int sortedIndex = 0; sortedIndex < nums.length; sortedIndex++) {
            var minValue = sorted[sortedIndex][0];
            if (minValue == -1) {
                continue; // marked
            }
            score += minValue;
            sorted[sortedIndex][0] = -1; // mark current item (not necessary)

            // find and mark current and prev item
            var originalIndex = sorted[sortedIndex][1];
            if (originalIndex > 0) {
                var prevSortedIndex = original[originalIndex-1];
                sorted[prevSortedIndex][0] = -1; // mark prev item
            }
            if (originalIndex < nums.length-1) {
                var nextSortedIndex = original[originalIndex+1];
                sorted[nextSortedIndex][0] = -1; // mark next item
            }
        }

        return score;
    }
}