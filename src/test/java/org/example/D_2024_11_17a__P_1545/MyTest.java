package org.example.D_2024_11_17a__P_1545;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/find-kth-bit-in-nth-binary-string
 *
 * <pre>
 * Medium
 * Topics
 * Companies
 * Hint
 * Given two positive integers n and k, the binary string Sn is formed as follows:
 *
 * S1 = "0"
 * Si = Si - 1 + "1" + reverse(invert(Si - 1)) for i > 1
 * Where + denotes the concatenation operation, reverse(x) returns the reversed string x, and invert(x) inverts all the bits in x (0 changes to 1 and 1 changes to 0).
 *
 * For example, the first four strings in the above sequence are:
 *
 * S1 = "0"
 * S2 = "011"
 * S3 = "0111001"
 * S4 = "011100110110001"
 * Return the kth bit in Sn. It is guaranteed that k is valid for the given n.
 *
 *
 *
 * Example 1:
 *
 * Input: n = 3, k = 1
 * Output: "0"
 * Explanation: S3 is "0111001".
 * The 1st bit is "0".
 * Example 2:
 *
 * Input: n = 4, k = 11
 * Output: "1"
 * Explanation: S4 is "011100110110001".
 * The 11th bit is "1".
 *
 *
 * Constraints:
 *
 * 1 <= n <= 20
 * 1 <= k <= 2n - 1
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_17a__P_1545.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(char expected, int n, int k) {
        assertEquals(expected, solution.findKthBit(n, k));
    }
}

class Solution {
    public char findKthBit(int n, int k) {

        var middleChar = '1';
        var level = n;
        while (level > 1) {
            var lengthPlus1 = 2 << (level - 1);
            var middleIndex = lengthPlus1 / 2;

            System.out.println("level=" + level);
            System.out.println(" * k=" + k);
            System.out.println(" * middleChar=" + middleChar);
            System.out.println(" * lengthPlus1=" + lengthPlus1);
            System.out.println(" * middleIndex=" + middleIndex);

            if (k == middleIndex) {
                return middleChar;
            } else if (k < middleIndex) {
                middleChar = '1';
            } else {
                middleChar = '0';
                k -= middleIndex;
            }

            level--;
        }
        return (middleChar == '1') ? '0' : '1';
    }
}

