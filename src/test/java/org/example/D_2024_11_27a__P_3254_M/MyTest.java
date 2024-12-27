package org.example.D_2024_11_27a__P_3254_M;

import org.example.MyTestConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/find-the-power-of-k-size-subarrays-i/description/?envType=daily-question&envId=2024-12-27
 *
 * <pre>
 * 3254. Find the Power of K-Size Subarrays I
 * Medium
 * Topics
 * Companies
 * Hint
 * You are given an array of integers nums of length n and a positive integer k.
 *
 * The power of an array is defined as:
 *
 * Its maximum element if all of its elements are consecutive and sorted in ascending order.
 * -1 otherwise.
 * You need to find the power of all
 * subarrays
 *  of nums of size k.
 *
 * Return an integer array results of size n - k + 1, where results[i] is the power of nums[i..(i + k - 1)].
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4,3,2,5], k = 3
 *
 * Output: [3,4,-1,-1,-1]
 *
 * Explanation:
 *
 * There are 5 subarrays of nums of size 3:
 *
 * [1, 2, 3] with the maximum element 3.
 * [2, 3, 4] with the maximum element 4.
 * [3, 4, 3] whose elements are not consecutive.
 * [4, 3, 2] whose elements are not sorted.
 * [3, 2, 5] whose elements are not consecutive.
 * Example 2:
 *
 * Input: nums = [2,2,2,2,2], k = 4
 *
 * Output: [-1,-1]
 *
 * Example 3:
 *
 * Input: nums = [3,2,3,2,3,2], k = 2
 *
 * Output: [-1,3,-1,3,-1]
 *
 *
 *
 * Constraints:
 *
 * 1 <= n == nums.length <= 500
 * 1 <= nums[i] <= 105
 * 1 <= k <= n
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_27a__P_3254_M.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(@MyTestConverter int[] expected, @MyTestConverter int[] nums, int k) {

        var result = solution.resultsArray(nums, k);

        assertArrayEquals(expected, result);
    }
}

class Solution {
    public int[] resultsArray(int[] nums, int k) {

        var result = new int[nums.length-k+1];

        result[0] = nums[0];
        var sequenceStartIdx = 0;
        for (int i = 1; i < nums.length; i++) {
            var prevValue = nums[i - 1];
            var value = nums[i];
            if (prevValue + 1 != value) {
                sequenceStartIdx = i;
            }
            var resultIdx = i - k + 1;
            if (resultIdx >= 0) {
                if (resultIdx >= sequenceStartIdx) {
                    result[resultIdx] = value;
                } else {
                    result[resultIdx] = -1;
                }

            }
        }

        return result;
    }
}

class Solution0 {
    public int[] resultsArray(int[] nums, int k) {

        var result = new int[nums.length-k+1];

        for (int i = 0; i < nums.length; i++) {
            var nextValue = nums[i];
            var minJ = Math.max(0, i-k+1);
            var maxJ = Math.min(result.length-1, i);
            for (int j = minJ; j <= maxJ; j++) {
                var maxValue = result[j];
                if (maxValue == -1) {
                    continue; // already broken
                } else if (maxValue == 0) {
                    result[j] = nextValue; // init for the first time
                } else if (maxValue + 1 == nextValue)  {
                    result[j] = nextValue; // continue
                } else {
                    result[j] = -1; // broken sequence
                }
            }
        }

        return result;
    }
}