package org.example.D_2024_11_04__P_258;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/add-digits/description/
 *
 * <pre>
 * Given an integer num, repeatedly add all its digits until the result has only one digit, and return it.
 *
 *
 *
 * Example 1:
 *
 * Input: num = 38
 * Output: 2
 * Explanation: The process is
 * 38 --> 3 + 8 --> 11
 * 11 --> 1 + 1 --> 2
 * Since 2 has only one digit, return it.
 * Example 2:
 *
 * Input: num = 0
 * Output: 0
 *
 *
 * Constraints:
 *
 * 0 <= num <= 231 - 1
 *
 *
 * Follow up: Could you do it without any loop/recursion in O(1) runtime?
 */
class MyTest {

    Solution solution = new Solution();

    @Test
    public void case1() {
        assertEquals(2, solution.addDigits(38));
    }

    @Test
    public void case2() {
        assertEquals(0, solution.addDigits(0));
    }
    @Test
    public void case3() {
        assertEquals(1, solution.addDigits(Integer.MAX_VALUE));
    }
}

class Solution {
    public int addDigits(int num) {
        do {
            num = num / 10 + num % 10;
        } while (num > 9);
        return num;
    }
}
