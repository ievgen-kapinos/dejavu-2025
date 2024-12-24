package org.example.D_2024_10_30__P_121;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/
 *
 * <pre>
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 *
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the
 * future to sell that stock.
 *
 * Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.
 *
 *
 *
 * Example 1:
 *
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
 * Example 2:
 *
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 *
 *
 * Constraints:
 *
 * 1 <= prices.length <= 105
 * 0 <= prices[i] <= 104
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @Test
    public void case1() {
        assertEquals(5, solution.maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
    }

    @Test
    public void case2() {
        assertEquals(0, solution.maxProfit(new int[]{7,6,4,3,1}));
    }
}

class Solution {
    public int maxProfit0(int[] prices) {
        var maxProfit = 0;
        for (int buy = 0; buy < prices.length; buy++) {
            for (int sell = buy + 1; sell < prices.length; sell++) {
                if (prices[buy] < prices[sell]) {
                    int profit = prices[sell] - prices[buy];
                    if (profit > maxProfit) {
                        maxProfit = profit;
                    }
                }
            }
        }
        return maxProfit;
    }

    public int maxProfit(int[] prices) {
        var maxProfit = 0;
        var minPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            var price = prices[i];
            var profit = price - minPrice;
            if (profit > maxProfit) {
                maxProfit = profit;
            }

            if (minPrice > price) {
                minPrice = price;
            }
        }
        return maxProfit;
    }
}
