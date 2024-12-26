package org.example.D_2024_11_26__P_494_M;

import org.example.MyTestConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/target-sum
 *
 * <pre>
 * 494. Target Sum
 * Medium
 * Topics
 * Companies
 * You are given an integer array nums and an integer target.
 *
 * You want to build an expression out of nums by adding one of the symbols '+' and '-' before each integer in nums and then concatenate all the integers.
 *
 * For example, if nums = [2, 1], you can add a '+' before 2 and a '-' before 1 and concatenate them to build the expression "+2-1".
 * Return the number of different expressions that you can build, which evaluates to target.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,1,1,1,1], target = 3
 * Output: 5
 * Explanation: There are 5 ways to assign symbols to make the sum of nums be target 3.
 * -1 + 1 + 1 + 1 + 1 = 3
 * +1 - 1 + 1 + 1 + 1 = 3
 * +1 + 1 - 1 + 1 + 1 = 3
 * +1 + 1 + 1 - 1 + 1 = 3
 * +1 + 1 + 1 + 1 - 1 = 3
 * Example 2:
 *
 * Input: nums = [1], target = 1
 * Output: 1
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 20
 * 0 <= nums[i] <= 1000
 * 0 <= sum(nums[i]) <= 1000
 * -1000 <= target <= 1000
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_26__P_494_M.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(int expected, @MyTestConverter int[] nums, int target) {

        var result = solution.findTargetSumWays(nums, target);

        assertEquals(expected, result);
    }
}

class Solution {

    private int[] nums;
    private int target;
    private int result = 0;

    public int findTargetSumWays(int[] nums, int target) {
        this.nums = nums;
        this.target = target;

        next(0, 0);

        return result;
    }

    private void next(int idx, int sum) {
        if (idx == nums.length) {
            if (sum == target) {
                result++;
            }
            return;
        }

        next(idx + 1, sum + nums[idx]);
        next(idx + 1, sum - nums[idx]);
    }
}