package org.example.D_2024_11_04a__P_3300;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/minimum-element-after-replacement-with-digit-sum/description/
 *
 * <pre>
 * You are given an integer array nums.
 *
 * You replace each element in nums with the sum of its digits.
 *
 * Return the minimum element in nums after all replacements.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [10,12,13,14]
 *
 * Output: 1
 *
 * Explanation:
 *
 * nums becomes [1, 3, 4, 5] after all replacements, with minimum element 1.
 *
 * Example 2:
 *
 * Input: nums = [1,2,3,4]
 *
 * Output: 1
 *
 * Explanation:
 *
 * nums becomes [1, 2, 3, 4] after all replacements, with minimum element 1.
 *
 * Example 3:
 *
 * Input: nums = [999,19,199]
 *
 * Output: 10
 *
 * Explanation:
 *
 * nums becomes [27, 10, 19] after all replacements, with minimum element 10.
 *
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 1 <= nums[i] <= 104
 */
class MyTest {

    Solution solution = new Solution();

    @Test
    public void case1() {
        assertEquals(1, solution.minElement(new int[]{10, 12, 13, 14}));
    }

    @Test
    public void case2() {
        assertEquals(1, solution.minElement(new int[]{1, 2, 3, 4}));
    }

    @Test
    public void case3() {
        assertEquals(10, solution.minElement(new int[]{999, 19, 199}));
    }
}

class Solution {
    public int minElement(int[] nums) {
        var minSum = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            var sum = 0;
            var reminder = nums[i];
            do  {
                sum += reminder % 10;
                reminder /= 10;
            } while (reminder > 0);
            nums[i] = sum;
            minSum = Math.min(minSum, sum);
        }
        return minSum;
    }
}
