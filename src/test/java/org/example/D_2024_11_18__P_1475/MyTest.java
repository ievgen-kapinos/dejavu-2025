package org.example.D_2024_11_18__P_1475;

import org.example.MyTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


/**
 * https://leetcode.com/problems/final-prices-with-a-special-discount-in-a-shop
 *
 * <pre>
 * 1475. Final Prices With a Special Discount in a Shop
 * Easy
 * Topics
 * Companies
 * Hint
 * You are given an integer array prices where prices[i] is the price of the ith item in a shop.
 *
 * There is a special discount for items in the shop. If you buy the ith item, then you will receive a discount equivalent to prices[j] where j is the minimum index such that j > i and prices[j] <= prices[i]. Otherwise, you will not receive any discount at all.
 *
 * Return an integer array answer where answer[i] is the final price you will pay for the ith item of the shop, considering the special discount.
 *
 *
 *
 * Example 1:
 *
 * Input: prices = [8,4,6,2,3]
 * Output: [4,2,4,2,3]
 * Explanation:
 * For item 0 with price[0]=8 you will receive a discount equivalent to prices[1]=4, therefore, the final price you will pay is 8 - 4 = 4.
 * For item 1 with price[1]=4 you will receive a discount equivalent to prices[3]=2, therefore, the final price you will pay is 4 - 2 = 2.
 * For item 2 with price[2]=6 you will receive a discount equivalent to prices[3]=2, therefore, the final price you will pay is 6 - 2 = 4.
 * For items 3 and 4 you will not receive any discount at all.
 * Example 2:
 *
 * Input: prices = [1,2,3,4,5]
 * Output: [1,2,3,4,5]
 * Explanation: In this case, for all items, you will not receive any discount at all.
 * Example 3:
 *
 * Input: prices = [10,1,1,6]
 * Output: [9,0,1,6]
 *
 *
 * Constraints:
 *
 * 1 <= prices.length <= 500
 * 1 <= prices[i] <= 1000
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_18__P_1475.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(String expectedStr, String pricesStr) {
        var expected = MyTestUtils.parseArray(expectedStr);
        var prices = MyTestUtils.parseArray(pricesStr);
        assertArrayEquals(expected, solution.finalPrices(prices));
    }
}


class Solution {
    public int[] finalPrices(int[] prices) {
        var answer = prices.clone();
        var stack = new Stack<Integer>();

        for (int j = 0; j < prices.length; j++) {

            while (!stack.isEmpty()) {
                var i = stack.peek();
                if (prices[i] >= prices[j]) {
                    stack.pop();
                    answer[i] -= answer[j];
                } else {
                    break;
                }
            }
            stack.push(j);

        }
        return answer;
    }
}


class Solution0 {
    public int[] finalPrices(int[] prices) {
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                if (prices[i] >= prices[j]) {
                    prices[i] -= prices[j];
                    break;
                }
            }
        }
        return prices;
    }
}

