package org.example.D_2024_11_19__P_3152;

import org.example.MyTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


/**
 * https://leetcode.com/problems/final-prices-with-a-special-discount-in-a-shop
 *
 * <pre>
 * 3152. Special Array II
 * Medium
 * Topics
 * Companies
 * Hint
 * An array is considered special if every pair of its adjacent elements contains two numbers with different parity.
 *
 * You are given an array of integer nums and a 2D integer matrix queries, where for queries[i] = [fromi, toi] your task is to check that
 * subarray
 *  nums[fromi..toi] is special or not.
 *
 * Return an array of booleans answer such that answer[i] is true if nums[fromi..toi] is special.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [3,4,1,2,6], queries = [[0,4]]
 *
 * Output: [false]
 *
 * Explanation:
 *
 * The subarray is [3,4,1,2,6]. 2 and 6 are both even.
 *
 * Example 2:
 *
 * Input: nums = [4,3,1,6], queries = [[0,2],[2,3]]
 *
 * Output: [false,true]
 *
 * Explanation:
 *
 * The subarray is [4,3,1]. 3 and 1 are both odd. So the answer to this query is false.
 * The subarray is [1,6]. There is only one pair: (1,6) and it contains numbers with different parity. So the answer to this query is true.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * 1 <= nums[i] <= 105
 * 1 <= queries.length <= 105
 * queries[i].length == 2
 * 0 <= queries[i][0] <= queries[i][1] <= nums.length - 1
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_19__P_3152.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(String expectedStr, String numsStr, String queriesStr) {
        var expected = MyTestUtils.parseArrayBoolean(expectedStr);
        var nums = MyTestUtils.parseArray(numsStr);
        var queries = MyTestUtils.parseArray2D(queriesStr);

        assertArrayEquals(expected, solution.isArraySpecial(nums, queries));
    }
}

class Solution {
    public boolean[] isArraySpecial(int[] nums, int[][] queries) {

        var ranges = new TreeMap<Integer, Integer>();
        var rangeFrom = 0;
        for (int rangeTo = 1; rangeTo < nums.length; rangeTo++) {
            if ((nums[rangeTo - 1] % 2) == (nums[rangeTo] % 2)) {
                ranges.put(rangeFrom, rangeTo - 1);
                rangeFrom = rangeTo;
            }
        }
        ranges.put(rangeFrom, nums.length - 1);

        var answer = new boolean[queries.length];
        for (int i = 0; i < queries.length; i++) {
            var from = queries[i][0];
            var to = queries[i][1];
            var range = ranges.floorEntry(from);

            if (range != null) {
                if (to <= range.getValue()) {
                    answer[i] = true;
                }
            }
        }
        return answer;
    }
}
