package org.example.D_2024_11_05__P_2825;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/make-string-a-subsequence-using-cyclic-increments
 *
 * <pre>
 * Medium
 *
 * You are given two 0-indexed strings str1 and str2.
 *
 * In an operation, you select a set of indices in str1, and for each index i in the set,
 * increment str1[i] to the next character cyclically. That is 'a' becomes 'b', 'b'
 * becomes 'c', and so on, and 'z' becomes 'a'.
 *
 * Return true if it is possible to make str2 a subsequence of str1 by performing the
 * operation at most once, and false otherwise.
 *
 * Note: A subsequence of a string is a new string that is formed from the original
 * string by deleting some (possibly none) of the characters without disturbing
 * the relative positions of the remaining characters.
 *
 *
 *
 * Example 1:
 *
 * Input: str1 = "abc", str2 = "ad"
 * Output: true
 * Explanation: Select index 2 in str1.
 * Increment str1[2] to become 'd'.
 * Hence, str1 becomes "abd" and str2 is now a subsequence. Therefore, true is returned.
 * Example 2:
 *
 * Input: str1 = "zc", str2 = "ad"
 * Output: true
 * Explanation: Select indices 0 and 1 in str1.
 * Increment str1[0] to become 'a'.
 * Increment str1[1] to become 'd'.
 * Hence, str1 becomes "ad" and str2 is now a subsequence. Therefore, true is returned.
 * Example 3:
 *
 * Input: str1 = "ab", str2 = "d"
 * Output: false
 * Explanation: In this example, it can be shown that it is impossible to make str2 a subsequence of str1 using the operation at most once.
 * Therefore, false is returned.
 *
 *
 * Constraints:
 *
 * 1 <= str1.length <= 105
 * 1 <= str2.length <= 105
 * str1 and str2 consist of only lowercase English letters.
 * </pre>
 * <p>
 * Hint 1
 * Consider the indices we will increment separately.
 * Hint 2
 * We can maintain two pointers: pointer i for str1 and pointer j for str2,
 * while ensuring they remain within the bounds of the strings.
 * Hint 3
 * If both str1[i] and str2[j] match, or if incrementing str1[i] matches str2[j],
 * we increase both pointers; otherwise, we increment only pointer i.
 * Hint 4
 * It is possible to make str2 a subsequence of str1 if j is at the end of str2,
 * after we can no longer find a match.
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_05__P_2825.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(boolean expected, String str1, String str2) {
        assertEquals(expected, solution.canMakeSubsequence(str1, str2));
    }
}

class Solution0 {
    public boolean canMakeSubsequence(String str1, String str2) {
        return match(str1.toCharArray(), str2.toCharArray(), 0, 0);
    }

    boolean match(char[] str1, char[] str2, int idx1, int idx2) {
        if (str2.length <= idx2) {
            return true;
        }
        if (str1.length <= idx1) {
            return false;
        }
        var c1 = str1[idx1];
        var c1next = c1 + 1;
        if (c1 == 'z') {
            c1next = 'a';
        }
        var c2 = str2[idx2];

        if (c2 == c1 || c2 == c1next) {
            return match(str1, str2, idx1 + 1, idx2 + 1);
        } else {
            return match(str1, str2, idx1 + 1, idx2);
        }
    }
}

class Solution {
    public boolean canMakeSubsequence(String str1, String str2) {
        char[] arr1 = str1.toCharArray();
        char[] arr2 = str2.toCharArray();
        int idx2 = 0;
        for (int idx1 = 0; idx1 < arr1.length; idx1++) {
            var c1 = arr1[idx1];
            var c1next = c1 + 1;
            if (c1 == 'z') {
                c1next = 'a';
            }
            var c2 = arr2[idx2];

            if (c2 == c1 || c2 == c1next) {
                idx2++;
                if (idx2 == arr2.length) {
                    return true;
                }
            }
        }
        return false;
    }
}

