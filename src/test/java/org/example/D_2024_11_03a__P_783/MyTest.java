package org.example.D_2024_11_03a__P_783;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * https://leetcode.com/problems/minimum-distance-between-bst-nodes/
 *
 * <pre>
 * Given the root of a Binary Search Tree (BST), return the minimum difference between the values of any two different nodes in the tree.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [4,2,6,1,3]
 * Output: 1
 * Example 2:
 *
 *
 * Input: root = [1,0,48,null,null,12,49]
 * Output: 1
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [2, 100].
 * 0 <= Node.val <= 105
 *
 *
 * Note: This question is the same as 530: https://leetcode.com/problems/minimum-absolute-difference-in-bst/
 * </pre>
 *
 * Case 3
 * [27,null,34,null,58,50,null,44] -> 6
 */
class MyTest {

    Solution solution = new Solution();

//    @Test
//    public void case1() {
//        assertEquals(1, solution.canBeTypedWords("hello world",  "ad"));
//    }
//
//    @Test
//    public void case2() {
//        assertEquals(1, solution.canBeTypedWords("leet code",  "lt"));
//    }
//
//    @Test
//    public void case3() {
//        assertEquals(0, solution.canBeTypedWords("leet code",  "e"));
//    }
}

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}



class Solution {
    public int minDiffInBST(TreeNode root) {
        return getResult(root).minDiff;
    }

    public Result getResult(TreeNode root) {
        int minValue = root.val;
        int maxValue = root.val;
        int minDiff = Integer.MAX_VALUE;

        if (root.left != null) {
            var result = getResult(root.left);
            minValue = result.minValue;
            minDiff = Math.min(Math.min(root.val - result.maxValue, result.minDiff), minDiff);
        }

        if (root.right != null) {
            var result = getResult(root.right);
            maxValue = result.maxValue;
            minDiff = Math.min(Math.min(result.minValue - root.val, result.minDiff), minDiff);
        }

        return new Result(minValue, maxValue, minDiff);
    }

    class Result {
        int minValue;
        int maxValue;
        int minDiff;

        public Result(int minVal, int maxVal, int minDiff) {
            this.minValue = minVal;
            this.maxValue = maxVal;
            this.minDiff = minDiff;
        }
    }
}
