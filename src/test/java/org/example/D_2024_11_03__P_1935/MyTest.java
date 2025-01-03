package org.example.D_2024_11_03__P_1935;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * https://leetcode.com/problems/maximum-number-of-words-you-can-type/
 *
 * <pre>
 * There is a malfunctioning keyboard where some letter keys do not work. All other keys on the keyboard work properly.
 *
 * Given a string text of words separated by a single space (no leading or trailing spaces) and a string brokenLetters
 * of all distinct letter keys that are broken, return the number of words in text you can fully type using this keyboard.
 *
 *
 *
 * Example 1:
 *
 * Input: text = "hello world", brokenLetters = "ad"
 * Output: 1
 * Explanation: We cannot type "world" because the 'd' key is broken.
 * Example 2:
 *
 * Input: text = "leet code", brokenLetters = "lt"
 * Output: 1
 * Explanation: We cannot type "leet" because the 'l' and 't' keys are broken.
 * Example 3:
 *
 * Input: text = "leet code", brokenLetters = "e"
 * Output: 0
 * Explanation: We cannot type either word because the 'e' key is broken.
 *
 *
 * Constraints:
 *
 * 1 <= text.length <= 104
 * 0 <= brokenLetters.length <= 26
 * text consists of words separated by a single space without any leading or trailing spaces.
 * Each word only consists of lowercase English letters.
 * brokenLetters consists of distinct lowercase English letters.
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @Test
    public void case1() {
        assertEquals(1, solution.canBeTypedWords("hello world", "ad"));
    }

    @Test
    public void case2() {
        assertEquals(1, solution.canBeTypedWords("leet code", "lt"));
    }

    @Test
    public void case3() {
        assertEquals(0, solution.canBeTypedWords("leet code", "e"));
    }
}

class Solution {
    public int canBeTypedWords(String text, String brokenLetters) {
        var brokenBoolArr = new boolean[26];
        for (var c : brokenLetters.toCharArray()) {
            brokenBoolArr[c - 'a'] = true;
        }

        var isWordBroken = false;
        var count = 0;
        for (char c : text.toCharArray()) {
            if (c == ' ') {
                if (!isWordBroken) {
                    count++;
                }
                isWordBroken = false;
            } else {
                isWordBroken = isWordBroken || brokenBoolArr[c - 'a'];
            }
        }
        if (!isWordBroken) {
            count++;
        }
        return count;
    }
}
