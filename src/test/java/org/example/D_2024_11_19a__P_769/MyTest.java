package org.example.D_2024_11_19a__P_769;

import org.example.MyTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/max-chunks-to-make-sorted
 *
 * <pre>
 * 769. Max Chunks To Make Sorted
 * Medium
 * Topics
 * Companies
 * Hint
 * You are given an integer array arr of length n that represents a permutation of the integers in the range [0, n - 1].
 *
 * We split arr into some number of chunks (i.e., partitions), and individually sort each chunk. After concatenating them, the result should equal the sorted array.
 *
 * Return the largest number of chunks we can make to sort the array.
 *
 *
 *
 * Example 1:
 *
 * Input: arr = [4,3,2,1,0]
 * Output: 1
 * Explanation:
 * Splitting into two or more chunks will not return the required result.
 * For example, splitting into [4, 3], [2, 1, 0] will result in [3, 4, 0, 1, 2], which isn't sorted.
 * Example 2:
 *
 * Input: arr = [1,0,2,3,4]
 * Output: 4
 * Explanation:
 * We can split into two chunks, such as [1, 0], [2, 3, 4].
 * However, splitting into [1, 0], [2], [3], [4] is the highest number of chunks possible.
 *
 *
 * Constraints:
 *
 * n == arr.length
 * 1 <= n <= 10
 * 0 <= arr[i] < n
 * All the elements of arr are unique.
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_19a__P_769.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(int expected, String arrStr) {
        var arr = MyTestUtils.parseArray(arrStr);
        assertEquals(expected, solution.maxChunksToSorted(arr));
    }
}

class Solution {
    public int maxChunksToSorted(int[] arr) {
        var answer = 0;
        var sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += i - arr[i];
            if (sum == 0) {
                answer++;
            }
        }
        return answer;
    }
}