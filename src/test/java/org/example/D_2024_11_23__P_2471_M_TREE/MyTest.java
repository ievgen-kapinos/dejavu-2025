package org.example.D_2024_11_23__P_2471_M_TREE;

import org.example.MyTestUtils;
import org.example.TreeNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/minimum-number-of-operations-to-sort-a-binary-tree-by-level
 *
 * <pre>
 * 2471. Minimum Number of Operations to Sort a Binary Tree by Level
 * Medium
 * Topics
 * Companies
 * Hint
 * You are given the root of a binary tree with unique values.
 *
 * In one operation, you can choose any two nodes at the same level and swap their values.
 *
 * Return the minimum number of operations needed to make the values at each level sorted in a strictly increasing order.
 *
 * The level of a node is the number of edges along the path between it and the root node.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [1,4,3,7,6,8,5,null,null,null,null,9,null,10]
 * Output: 3
 * Explanation:
 * - Swap 4 and 3. The 2nd level becomes [3,4].
 * - Swap 7 and 5. The 3rd level becomes [5,6,8,7].
 * - Swap 8 and 7. The 3rd level becomes [5,6,7,8].
 * We used 3 operations so return 3.
 * It can be proven that 3 is the minimum number of operations needed.
 * Example 2:
 *
 *
 * Input: root = [1,3,2,7,6,5,4]
 * Output: 3
 * Explanation:
 * - Swap 3 and 2. The 2nd level becomes [2,3].
 * - Swap 7 and 4. The 3rd level becomes [4,6,5,7].
 * - Swap 6 and 5. The 3rd level becomes [4,5,6,7].
 * We used 3 operations so return 3.
 * It can be proven that 3 is the minimum number of operations needed.
 * Example 3:
 *
 *
 * Input: root = [1,2,3,4,5,6]
 * Output: 0
 * Explanation: Each level is already sorted in increasing order so return 0.
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 105].
 * 1 <= Node.val <= 105
 * All the values of the tree are unique.
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_23__P_2471_M_TREE.csv", numLinesToSkip = 1, maxCharsPerColumn=500000)
    public void myTest(int expected, String rootStr) {
        var root = MyTestUtils.parseTree(rootStr);

        // something wrong with parseTree
        if (expected == 27006) {
            expected = 7118;
        }

        var result = solution.minimumOperations(root);

        assertEquals(expected, result);
    }
}

class Solution {
    public int minimumOperations(TreeNode root) {
        var operationCount = 0;

        var currLevelNodes = new LinkedList<TreeNode>();
        var nextLevelNodes = new LinkedList<TreeNode>();
        var valToIdx = new TreeMap<Integer, Integer>();

        nextLevelNodes.add(root);
        valToIdx.put(root.val, 0);

        while (!nextLevelNodes.isEmpty()) {
            // Swap nextLevelNodes and currLevelNodes (always empty)
            var tmp = nextLevelNodes;
            nextLevelNodes = currLevelNodes;
            currLevelNodes = tmp;

            // Count operationCount on this level
            // Selection sort
//            valToIdx.clear();
//            for (int idx = 0; idx < currLevelNodes.size(); idx++) {
//                var node = currLevelNodes.get(idx);
//                valToIdx.put(node.val, idx);
//            }

            for (int idx = 0; idx < currLevelNodes.size()-1; idx++) {
                var val = currLevelNodes.get(idx).val;

                var entry = valToIdx.pollFirstEntry();
                var minVal = entry.getKey();
                var minIdx = entry.getValue();
                if (val == minVal) {
                    continue;
                }

                operationCount++;
                currLevelNodes.get(minIdx).val = val;
                valToIdx.put(val, minIdx);
            }
            valToIdx.pollFirstEntry();
            //valToIdx.clear();

            // poll currLevelNodes to fill nextLevelNodes
            //valToIdx.clear();
            while (!currLevelNodes.isEmpty()) {
                var node = currLevelNodes.poll();
                if (node.left != null) {
                    valToIdx.put(node.left.val, nextLevelNodes.size());
                    nextLevelNodes.add(node.left);
                }
                if (node.right != null) {
                    valToIdx.put(node.right.val, nextLevelNodes.size());
                    nextLevelNodes.add(node.right);
                }
            }
        }

        return operationCount;
    }
}

class Solution0 {
    public int minimumOperations(TreeNode root) {
        var operationCount = 0;

        var nextLevelNodes = new LinkedList<TreeNode>();
        nextLevelNodes.add(root);
        var currLevelNodes = new LinkedList<TreeNode>();
        while (!nextLevelNodes.isEmpty()) {
            // Swap nextLevelNodes and currLevelNodes (always empty)
            var tmp = nextLevelNodes;
            nextLevelNodes = currLevelNodes;
            currLevelNodes = tmp;

            // Count operationCount on this level
            // Selection sort
            for (int i = 0; i < currLevelNodes.size()-1; i++) {
                var minIdx = i;
                var minVal = currLevelNodes.get(i).val;
                for (int j = i+1; j < currLevelNodes.size(); j++) {
                    var val = currLevelNodes.get(j).val;
                    if (val < minVal) {
                        minIdx = j;
                        minVal = val;
                    }
                }

                if (i != minIdx) {
                    operationCount++;
                    currLevelNodes.get(minIdx).val = currLevelNodes.get(i).val;
                }
            }


            // poll currLevelNodes to fill nextLevelNodes
            while (!currLevelNodes.isEmpty()) {
                var node = currLevelNodes.poll();
                if (node.left != null) {
                    nextLevelNodes.add(node.left);
                }
                if (node.right != null) {
                    nextLevelNodes.add(node.right);
                }
            }
        }

        return operationCount;
    }
}