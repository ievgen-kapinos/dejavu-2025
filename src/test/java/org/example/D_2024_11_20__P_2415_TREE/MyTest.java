package org.example.D_2024_11_20__P_2415_TREE;

import org.example.MyTestUtils;
import org.example.TreeNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;



/**
 * https://leetcode.com/problems/reverse-odd-levels-of-binary-tree
 *
 * <pre>
 * 2415. Reverse Odd Levels of Binary Tree
 * Medium
 * Topics
 * Companies
 * Hint
 * Given the root of a perfect binary tree, reverse the node values at each odd level of the tree.
 *
 * For example, suppose the node values at level 3 are [2,1,3,4,7,11,29,18], then it should become [18,29,11,7,4,3,1,2].
 * Return the root of the reversed tree.
 *
 * A binary tree is perfect if all parent nodes have two children and all leaves are on the same level.
 *
 * The level of a node is the number of edges along the path between it and the root node.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [2,3,5,8,13,21,34]
 * Output: [2,5,3,8,13,21,34]
 * Explanation:
 * The tree has only one odd level.
 * The nodes at level 1 are 3, 5 respectively, which are reversed and become 5, 3.
 * Example 2:
 *
 *
 * Input: root = [7,13,11]
 * Output: [7,11,13]
 * Explanation:
 * The nodes at level 1 are 13, 11, which are reversed and become 11, 13.
 * Example 3:
 *
 * Input: root = [0,1,2,0,0,0,0,1,1,1,1,2,2,2,2]
 * Output: [0,2,1,0,0,0,0,2,2,2,2,1,1,1,1]
 * Explanation:
 * The odd levels have non-zero values.
 * The nodes at level 1 were 1, 2, and are 2, 1 after the reversal.
 * The nodes at level 3 were 1, 1, 1, 1, 2, 2, 2, 2, and are 2, 2, 2, 2, 1, 1, 1, 1 after the reversal.
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 214].
 * 0 <= Node.val <= 105
 * root is a perfect binary tree.
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_20__P_2415_TREE.csv", numLinesToSkip = 1, maxCharsPerColumn=500000)
    public void myTest(String expectedStr, String rootStr) {
        var expectedArr = MyTestUtils.parseArray(expectedStr);
        var root = MyTestUtils.parseTree(rootStr);

        var resultRoot = solution.reverseOddLevels(root);

        var resultArr = MyTestUtils.treeToArray(resultRoot);
        assertArrayEquals(expectedArr, resultArr);
    }


//        var maxLevel = Integer.numberOfTrailingZeros(rootArr.length+1); // 1,2...N
//        var root = createTree(maxLevel);
//        Queue<TreeNode> queue = new LinkedList<>();
//        queue.add(root);
//        for (var i = 0; i < rootArr.length; i++) {
//            var node = queue.poll();
//            node.val = rootArr[i];
//            if (node.left != null) {
//                queue.add(node.left);
//            }
//            if (node.right != null) {
//                queue.add(node.right);
//            }
//        }
//    TreeNode createTree(int subLevelsCount) {
//        if (subLevelsCount == 0) {
//            return null;
//        }
//        return new TreeNode(
//        -1,
//            createTree(subLevelsCount-1),
//            createTree(subLevelsCount-1)
//        );
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
class Solution {
    public TreeNode reverseOddLevels(TreeNode root) {

        // Serialize
        var nodes = new LinkedList<TreeNode>();
        nodes.add(root);
        var nodesIndex = 0;
        while (nodesIndex < nodes.size()) {
            var node = nodes.get(nodesIndex);
            if (node.left != null && node.right != null) {
                nodes.add(node.left);
                nodes.add(node.right);
            }
            nodesIndex++;
        }

        // Reverse
        var level = 0;
        nodesIndex = 0;
        while (nodesIndex < nodes.size()) {
            level++;
            var levelNodesCount = 1<<(level-1);
            var nextLevelNodeIndex = nodesIndex + levelNodesCount;
            if (level % 2 == 0) {
                Collections.reverse(nodes.subList(nodesIndex, nextLevelNodeIndex));
            }
            nodesIndex = nextLevelNodeIndex;
        }

        // Deserialize
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(nodes.poll()); // root
        while (!nodes.isEmpty()) {
            var parentNode = queue.poll();
            parentNode.left = nodes.poll();
            parentNode.right = nodes.poll();
            queue.add(parentNode.left);
            queue.add(parentNode.right);
        }

        return root;
    }
}
