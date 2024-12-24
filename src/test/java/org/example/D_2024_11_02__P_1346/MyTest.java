package org.example.D_2024_11_02__P_1346;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * https://leetcode.com/problems/check-if-n-and-its-double-exist/
 *
 * <pre>
 * Given an array arr of integers, check if there exist two indices i and j such that :
 *
 * i != j
 * 0 <= i, j < arr.length
 * arr[i] == 2 * arr[j]
 *
 *
 * Example 1:
 *
 * Input: arr = [10,2,5,3]
 * Output: true
 * Explanation: For i = 0 and j = 2, arr[i] == 10 == 2 * 5 == 2 * arr[j]
 * Example 2:
 *
 * Input: arr = [3,1,7,11]
 * Output: false
 * Explanation: There is no i and j that satisfy the conditions.
 *
 *
 * Constraints:
 *
 * 2 <= arr.length <= 500
 * -103 <= arr[i] <= 103
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @Test
    public void case1() {
        assertEquals(true, solution.checkIfExist(new int[]{7, 1, 5, 3, 6, 4}));
    }

    @Test
    public void case2() {
        assertEquals(false, solution.checkIfExist(new int[]{3, 1, 7, 11}));
    }
}

class Solution {
    public boolean checkIfExist(int[] arr) {
        boolean[] negative = new boolean[1001];
        boolean[] positive = new boolean[1001];
        boolean hasZero = false;
        for (int v : arr) {
            if (v > 0) {
                positive[v] = true;
            } else if (v < 0) {
                negative[-v] = true;
            } else {
                if (hasZero) {
                    return true;
                } else {
                    hasZero = true;
                }
            }
        }
        for (int v : arr) {
            if (v > 0 && v <= 500) {
                if (positive[2 * v]) {
                    return true;
                }
            } else if (v >= -500 && v < 0) {
                if (negative[-2 * v]) {
                    return true;
                }
            }
            // Skip rest
        }
        return false;
    }
}