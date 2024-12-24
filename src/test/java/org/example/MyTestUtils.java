package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.params.shadow.com.univocity.parsers.common.NormalizedString.toArray;

public class MyTestUtils {

    /**
     * @param input e.g. "[2,1,3,4,5,2]"
     * @return 1D array
     */
    public static int[] parseArray(String input) {
        return input
                .substring(1, input.length() - 1)
                .replace(",", "\n")
                .lines()
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static boolean[] parseArrayBoolean(String input) {
        var boxedArray = input
                .substring(1, input.length() - 1)
                .replace(",", "\n")
                .lines()
                .map(Boolean::parseBoolean)
                .toArray(Boolean[]::new);

        var result = new boolean[boxedArray.length];
        for (int i = 0; i < boxedArray.length; i++) {
            result[i] = boxedArray[i];
        }
        return result;
    }

    /**
     * @param input e.g. "[[1,2],[3,5],[2,2]]"
     * @return 2D array
     */
    public static int[][] parseArray2D(String input) {
        return input
                .substring(2, input.length() - 2)
                .replace("],[", "\n")
                .lines()
                .map(line -> Arrays
                    .stream(line.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray()
                )
                .toArray(int[][]::new);
    }

    /**
     * @param input e.g. "[1,4,3,7,6,8,5,null,null,null,null,9,null,10]"
     * @return root of a Tree
     */
    public static TreeNode parseTree(String input) {
        var arr = input
            .substring(1, input.length() - 1)
            .replace(",", "\n")
            .lines()
            .map(val -> val.equals("null") ? null : Integer.parseInt(val))
            .toArray(Integer[]::new);

        if (arr[0] == null) {
            return null;
        }


        Queue<TreeNode> queue = new LinkedList<>();
        var root = new TreeNode(arr[0]);
        queue.add(root);
        for (var i = 1; i < arr.length; i += 2) {
            var parentNode = queue.poll();
            if (parentNode == null) {
                queue.add(null);
                queue.add(null);
            } else {
                if (arr[i] != null) {
                    parentNode.left = new TreeNode(arr[i]);
                    queue.add(parentNode.left);
                } else {
                    queue.add(null);
                }
                if ((i+1) < arr.length && arr[i+1] != null) {
                    parentNode.right = new TreeNode(arr[i+1]);
                    queue.add(parentNode.right);
                } else {
                    queue.add(null);
                }
            }
        }
        System.out.println(root);
        return root;
    }

    public static TreeNode arrayToTree(int[] arr) {
        Queue<TreeNode> queue = new LinkedList<>();
        var root = new TreeNode(arr[0]);
        queue.add(root);
        for (var i = 1; i < arr.length; i += 2) {
            var parentNode = queue.poll();
            parentNode.left = new TreeNode(arr[i]);
            parentNode.right = new TreeNode(arr[i + 1]);
            queue.add(parentNode.left);
            queue.add(parentNode.right);
        }
        return root;
    }

    public static int[] treeToArray(TreeNode root) {
        List<TreeNode> nodes = new LinkedList<>();
        nodes.add(root);
        for (int nodeIndex = 0; nodeIndex < nodes.size(); nodeIndex++) {
            var node = nodes.get(nodeIndex);
            if (node.left != null) {
                nodes.add(node.left);
            }
            if (node.right !=null) {
                nodes.add(node.right);
            }
        }
        return nodes.stream().mapToInt(node -> node.val).toArray();
    }
}
