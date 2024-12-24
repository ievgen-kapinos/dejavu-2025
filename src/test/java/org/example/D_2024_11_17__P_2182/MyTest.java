package org.example.D_2024_11_17__P_2182;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/construct-string-with-repeat-limit
 *
 * <pre>
 * Medium
 * Topics
 * Companies
 * Hint
 * You are given a string s and an integer repeatLimit. Construct a new string repeatLimitedString using the characters of s such that no letter appears more than repeatLimit times in a row. You do not have to use all characters from s.
 *
 * Return the lexicographically largest repeatLimitedString possible.
 *
 * A string a is lexicographically larger than a string b if in the first position where a and b differ,
 * string a has a letter that appears later in the alphabet than the corresponding letter in b.
 * If the first min(a.length, b.length) characters do not differ, then the longer string is the
 * lexicographically larger one.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "cczazcc", repeatLimit = 3
 * Output: "zzcccac"
 * Explanation: We use all of the characters from s to construct the repeatLimitedString "zzcccac".
 * The letter 'a' appears at most 1 time in a row.
 * The letter 'c' appears at most 3 times in a row.
 * The letter 'z' appears at most 2 times in a row.
 * Hence, no letter appears more than repeatLimit times in a row and the string is a valid repeatLimitedString.
 * The string is the lexicographically largest repeatLimitedString possible so we return "zzcccac".
 * Note that the string "zzcccca" is lexicographically larger but the letter 'c' appears more than 3 times in a row,
 * so it is not a valid repeatLimitedString.
 * Example 2:
 *
 * Input: s = "aababab", repeatLimit = 2
 * Output: "bbabaa"
 * Explanation: We use only some of the characters from s to construct the repeatLimitedString "bbabaa".
 * The letter 'a' appears at most 2 times in a row.
 * The letter 'b' appears at most 2 times in a row.
 * Hence, no letter appears more than repeatLimit times in a row and the string is a valid repeatLimitedString.
 * The string is the lexicographically largest repeatLimitedString possible so we return "bbabaa".
 * Note that the string "bbabaaa" is lexicographically larger but the letter 'a' appears more than 2 times in a row,
 * so it is not a valid repeatLimitedString.
 *
 *
 * Constraints:
 *
 * 1 <= repeatLimit <= s.length <= 105
 * s consists of lowercase English letters.
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_17__P_2182.csv", numLinesToSkip = 1, maxCharsPerColumn=500000)
    public void myTest(String expected, String s, int repeatLimit) {
        assertEquals(expected, solution.repeatLimitedString(s, repeatLimit));
    }
}

class Solution {
    public String repeatLimitedString(String s, int repeatLimit) {
        int[] counts = new int['z'-'a' + 1];

        for (int i = 0; i<s.length(); i++) {
            var c = s.charAt(i);
            counts[c-'a']++;
        }

        var repeatLimitedString = new StringBuffer();

        for (int currIdx=(counts.length-1); currIdx>=0; currIdx--) {
            var repeatCount = 0;
            while (counts[currIdx] > 0) {

                if (repeatCount == repeatLimit) {

                    for (var nextInx=(currIdx-1); nextInx>=0; nextInx--) {
                        if (counts[nextInx] > 0) {
                            repeatLimitedString.append((char)(nextInx + 'a'));
                            counts[nextInx]--;
                            repeatCount = 0;
                            break;
                        }
                    }

                    if (repeatCount != 0) {
                        // cannot find next element
                        return repeatLimitedString.toString();
                    }
                }

                repeatLimitedString.append((char)(currIdx + 'a'));
                counts[currIdx]--;
                repeatCount++;
            }
        }

        return repeatLimitedString.toString();
    }
}
