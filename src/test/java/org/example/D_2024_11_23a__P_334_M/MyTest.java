package org.example.D_2024_11_23a__P_334_M;

import org.example.MyTestUtils;
import org.example.TreeNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/increasing-triplet-subsequence/description/
 *
 * <pre>
 * 334. Increasing Triplet Subsequence
 * Medium
 * Topics
 * Companies
 * Given an integer array nums, return true if there exists a triple of indices (i, j, k) such that i < j < k and nums[i] < nums[j] < nums[k]. If no such indices exists, return false.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4,5]
 * Output: true
 * Explanation: Any triplet where i < j < k is valid.
 * Example 2:
 *
 * Input: nums = [5,4,3,2,1]
 * Output: false
 * Explanation: No triplet exists.
 * Example 3:
 *
 * Input: nums = [2,1,5,0,4,6]
 * Output: true
 * Explanation: The triplet (3, 4, 5) is valid because nums[3] == 0 < nums[4] == 4 < nums[5] == 6.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 5 * 105
 * -231 <= nums[i] <= 231 - 1
 *
 *
 * Follow up: Could you implement a solution that runs in O(n) time complexity and O(1) space complexity?
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_23a__P_334_M.csv", numLinesToSkip = 1, maxCharsPerColumn=500000)
    public void myTest(boolean expected, String numsStr) {
        var nums = MyTestUtils.parseArray(numsStr);

        var result = solution.increasingTriplet(nums);

        assertEquals(expected, result);
    }
}

// O (N)
class Solution {
    public boolean increasingTriplet(int[] nums) {
        Integer oneMin = null;
        Integer twoMin = null;
        for (var num: nums) {
            if (oneMin == null || oneMin > num) {
                oneMin = num;
            }

            if (num > oneMin) {
                if (twoMin == null || twoMin > num) {
                    twoMin = num;
                }
            }

            if (twoMin != null && num > twoMin) {
                return true;
            }

            // System.out.println("num=" + num + ", oneMin=" + oneMin + ", twoMin=" + twoMin);
        }

        return false;
    }
}

// O(N^3)
class Solution0 {
    public boolean increasingTriplet(int[] nums) {
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i; j < nums.length - 1; j++) {
                for (int k = j; k < nums.length; k++) {
                    if (nums[i] < nums[j] && nums[j] < nums[k]) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}