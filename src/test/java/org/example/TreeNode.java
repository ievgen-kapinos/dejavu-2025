package org.example;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    TreeNode() {}
    public TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        if (left == null && right == null) {
            return "["+ val + "]";
        } else {
            return "(" + (left == null ? "-" : left) + " " + val + " "+ (right == null ? "-" : right) + ")";
        }
    }
}
