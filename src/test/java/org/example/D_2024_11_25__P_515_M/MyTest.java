package org.example.D_2024_11_25__P_515_M;

import org.example.MyTestUtils;
import org.example.TreeNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/find-largest-value-in-each-tree-row
 *
 * <pre>
 * 515. Find Largest Value in Each Tree Row
 * Medium
 * Topics
 * Companies
 * Given the root of a binary tree, return an array of the largest value in each row of the tree (0-indexed).
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [1,3,2,5,3,null,9]
 * Output: [1,3,9]
 * Example 2:
 *
 * Input: root = [1,2,3]
 * Output: [1,3]
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree will be in the range [0, 104].
 * -231 <= Node.val <= 231 - 1
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_25__P_515_M.csv", numLinesToSkip = 1, maxCharsPerColumn=500000)
    public void myTest(String expectedStr, String rootStr) {
        var expected = Arrays.stream(MyTestUtils.parseArray(expectedStr)).boxed().toList();
        var root = MyTestUtils.parseTree(rootStr);

        var result = solution.largestValues(root);

        assertEquals(expected, result);
    }
}

class Solution {
    public List<Integer> largestValues(TreeNode root) {
        var result = new ArrayList<Integer>(1);
        dfs(root, 0, result);
        return result;
    }

    void dfs(TreeNode node, int levelIdx, List<Integer>result) {
        if (node == null) {
            return;
        }

        if (result.size() == levelIdx) {
            result.add(node.val);
        } else {
            var maxVal = result.get(levelIdx);
            if (maxVal < node.val) {
                result.set(levelIdx, node.val);
            }
        }

        var nextLevel = levelIdx + 1;
        dfs(node.left, nextLevel, result);
        dfs(node.right, nextLevel, result);
    }
}