package org.example.D_2024_11_12__P_2558;

import org.example.MyTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/take-gifts-from-the-richest-pile
 *
 * <pre>
 * Easy
 * Topics
 * Companies
 * Hint
 * You are given an integer array gifts denoting the number of gifts in various piles. Every second, you do the following:
 *
 * Choose the pile with the maximum number of gifts.
 * If there is more than one pile with the maximum number of gifts, choose any.
 * Leave behind the floor of the square root of the number of gifts in the pile. Take the rest of the gifts.
 * Return the number of gifts remaining after k seconds.
 *
 *
 *
 * Example 1:
 *
 * Input: gifts = [25,64,9,4,100], k = 4
 * Output: 29
 * Explanation:
 * The gifts are taken in the following way:
 * - In the first second, the last pile is chosen and 10 gifts are left behind.
 * - Then the second pile is chosen and 8 gifts are left behind.
 * - After that the first pile is chosen and 5 gifts are left behind.
 * - Finally, the last pile is chosen again and 3 gifts are left behind.
 * The final remaining gifts are [5,8,9,4,3], so the total number of gifts remaining is 29.
 * Example 2:
 *
 * Input: gifts = [1,1,1,1], k = 4
 * Output: 4
 * Explanation:
 * In this case, regardless which pile you choose, you have to leave behind 1 gift in each pile.
 * That is, you can't take any pile with you.
 * So, the total gifts remaining are 4.
 *
 *
 * Constraints:
 *
 * 1 <= gifts.length <= 103
 * 1 <= gifts[i] <= 109
 * 1 <= k <= 103
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_12__P_2558.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(long expected, String giftsStr, int k) {
        var gifts = MyTestUtils.parseArray(giftsStr);
        assertEquals(expected, solution.pickGifts(gifts, k));
    }
}

class Solution0 {
    public long pickGifts(int[] gifts, int k) {

        for (int i = 0; i < k; i++) {
            var maxGiftIndex = 0;
            for (int j = 0; j < gifts.length; j++) {
                if (gifts[j] > gifts[maxGiftIndex]) {
                    maxGiftIndex = j;
                }
            }
            gifts[maxGiftIndex] = (int) Math.sqrt(gifts[maxGiftIndex]);
        }

        return Arrays.stream(gifts).asLongStream().sum();
    }
}

class Solution {
    public long pickGifts(int[] gifts, int k) {
        var pq = new PriorityQueue<Integer>(gifts.length, Comparator.reverseOrder());
        for (int j = 0; j < gifts.length; j++) {
            pq.add(gifts[j]);
        }

        for (int i = 0; i < k; i++) {
            var maxGift = pq.poll();
            pq.add((int) Math.sqrt(maxGift));
        }

        var rez = 0L;
        while (!pq.isEmpty()) {
            rez += pq.poll();
        }

        return rez;
    }
}

