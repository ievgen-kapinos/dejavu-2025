package org.example.D_2024_11_28__P_689_H;

import org.example.MyTestConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/description/?envType=daily-question&envId=2024-12-28
 *
 * <pre>
 * 689. Maximum Sum of 3 Non-Overlapping Subarrays
 * Hard
 * Topics
 * Companies
 * Given an integer array nums and an integer k, find three non-overlapping subarrays of length k with maximum sum and return them.
 *
 * Return the result as a list of indices representing the starting position of each interval (0-indexed). If there are multiple answers, return the lexicographically smallest one.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,1,2,6,7,5,1], k = 2
 * Output: [0,3,5]
 * Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
 * We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
 * Example 2:
 *
 * Input: nums = [1,2,1,2,1,2,1,2,1], k = 2
 * Output: [0,2,4]
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 2 * 104
 * 1 <= nums[i] < 216
 * 1 <= k <= floor(nums.length / 3)
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_28__P_689_H.csv", numLinesToSkip = 1, maxCharsPerColumn = 500000)
    public void myTest(@MyTestConverter int[] expected, @MyTestConverter int[] nums, int k) {

        var output = solution.maxSumOfThreeSubarrays(nums, k);

        assertArrayEquals(expected, output, () ->
            "\n Expected: " + Arrays.toString(expected) + "\n Output: " + Arrays.toString(output) + "\n"
        );
    }
}

class Solution {

    record SubArr(long sum, int startIdx, int endIdx) {
        boolean hasOverlap(SubArr other) {
            return Math.min(this.endIdx, other.endIdx) >= Math.max(this.startIdx, other.startIdx);
        }
    }

    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {

        var allSubArr = new SubArr[nums.length-k+1];

        long subArrSum = 0;
        for (int i = 0; i < nums.length; i++) {
            subArrSum += nums[i];
            var subArrStartIdx = i-k+1;
            if (subArrStartIdx < 0) {
                continue;
            }
            allSubArr[subArrStartIdx] = new SubArr(subArrSum, subArrStartIdx, i);
            subArrSum -= nums[subArrStartIdx];
        }


        var shift1 = 0;
        var shift2 = k;
        var shift3 = 2*k;

        var sumMax1 = -1L;
        var sumMax12 = -1L;
        var sumMax123 = -1L;

        var idxMax1 = new int[] {-1};
        var idxMax12 = new int[] {-1, -1};
        var idxMax123 = new int[] {-1, -1, -1};

        for (int i = 0; i < allSubArr.length-shift3; i++) {

            var idx1 = i + shift1;
            var idx2 = i + shift2;
            var idx3 = i + shift3;

            var sum1 = allSubArr[idx1].sum;
            if (sum1 > sumMax1) {
                sumMax1 = sum1;
                idxMax1[0] = idx1;
            }

            var sum12 = sumMax1 + allSubArr[idx2].sum;
            if (sum12 > sumMax12) {
                sumMax12 = sum12;
                idxMax12[0] = idxMax1[0];
                idxMax12[1] = idx2;
            }

            var sum123 = sumMax12 + allSubArr[idx3].sum ;
            if (sum123 > sumMax123) {
                sumMax123 = sum123;
                idxMax123[0] = idxMax12[0];
                idxMax123[1] = idxMax12[1];
                idxMax123[2] = idx3;
            }
        }

        return idxMax123;
    }
}

// Tree of array sequences
// greedy solution... Starts +/- good initial solution, finds eventually optimal solution, if put all arrays in tree.
// but that is too big tree
class Solution2 {

    record SubArr(long sum, int startIdx, int endIdx) {
        boolean hasOverlap(SubArr other) {
            return Math.min(this.endIdx, other.endIdx) >= Math.max(this.startIdx, other.startIdx);
        }
    }

    long globalMaxSum = -1L;
    int[] globalResult = null;

    class SeqTreeNode {

        SubArr subArr = null;
        long sum = 0L;
        int[] indexes = new int[0];
        List<SeqTreeNode> children = new ArrayList<>();

        SeqTreeNode() {

        }

        SeqTreeNode(SubArr subArr, long sum, int[] indexes) {
            this.subArr = subArr;
            this.sum = sum;
            this.indexes = indexes;

            if (indexes.length < 3) {
                return;
            }
            if (globalMaxSum > sum) {
                return;
            }

            var result = Arrays.copyOf(indexes, 3);
            Arrays.sort(result);

            // System.out.println(" * Candidate: " + Arrays.toString(result) + " with sum " + sum);

            if (globalMaxSum == sum) {
                // check lexicographically
                if ((result[0] > globalResult[0]) ||
                    (result[0] == globalResult[0] && result[1] > globalResult[1]) ||
                    (result[0] == globalResult[0] && result[1] == globalResult[1] && result[2] > globalResult[2])
                ) {
                    return;
                }

            } else {
                globalMaxSum = sum;
            }
            globalResult = result;
            //System.out.println(" * Candidate is winner");
        }


        void addChildren(SubArr childSubArr) {

            if (indexes.length == 3) {
                return;
            }

            if (subArr != null && subArr.hasOverlap(childSubArr)) {
                return;
            }

            for (SeqTreeNode child : this.children) {
                child.addChildren(childSubArr);
            }

            long newChildSum = sum + childSubArr.sum;
            int[] newChildIndexes;
            if (subArr == null) {
                newChildIndexes = new int[1];
            } else {
                newChildIndexes = Arrays.copyOf(indexes, indexes.length+1);
            }
            newChildIndexes[newChildIndexes.length-1] = childSubArr.startIdx;

            var newChild = new SeqTreeNode(childSubArr, newChildSum, newChildIndexes);
            children.add(newChild);
        }
    }

    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {

        var allSubArr = new SubArr[nums.length-k+1];

        long subArrSum = 0;
        for (int i = 0; i < nums.length; i++) {
            subArrSum += nums[i];
            var subArrStartIdx = i-k+1;
            if (subArrStartIdx < 0) {
                continue;
            }
            allSubArr[subArrStartIdx] = new SubArr(subArrSum, subArrStartIdx, i);
            subArrSum -= nums[subArrStartIdx];
        }

        Arrays.sort(allSubArr, (a,b) -> {
            var result = Long.compare(b.sum, a.sum); // desc by sum
            if (result != 0) {
                return result;
            }
            return Integer.compare(a.startIdx, b.startIdx); // asc by index
        });

        var root = new SeqTreeNode();

        int hasResultCount = 0;
        for (int i = 0; i < allSubArr.length; i++) {
            var subArr = allSubArr[i];

            //System.out.println("Adding " + subArr);
            root.addChildren(subArr);

            if (globalResult != null) {
                hasResultCount++;
                if (hasResultCount > 1500) { // need more
                    break;
                }
            }
        }

        // System.out.println(Arrays.toString(globalResult));
        return globalResult;
    }
}

// (n^3) brute force
class Solution1 {

    record SubArr(long sum, int startIdx, int endIdx) {
        boolean hasOverlap(SubArr other) {
            return Math.min(this.endIdx, other.endIdx) >= Math.max(this.startIdx, other.startIdx);
        }
    }

    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {

        var allSubArr = new SubArr[nums.length-k+1];

        long subArrSum = 0;
        for (int i = 0; i < nums.length; i++) {
            subArrSum += nums[i];
            var subArrStartIdx = i-k+1;
            if (subArrStartIdx < 0) {
                continue;
            }
            allSubArr[subArrStartIdx] = new SubArr(subArrSum, subArrStartIdx, i);
            subArrSum -= nums[subArrStartIdx];
        }

        var maxSum = -1L;
        var result = new int[] {-1, -1, -1};
        for (int i = 0; i < allSubArr.length-2; i++) {
            for (int j = i + k; j < allSubArr.length-1; j++) {
                for (int z = j + k; z < allSubArr.length; z++) {
                    var sum = allSubArr[i].sum + allSubArr[j].sum + allSubArr[z].sum;
                    if (sum > maxSum) {
                        maxSum = sum;

                        result[0] = i;
                        result[1] = j;
                        result[2] = z;
                    }
                }
            }
        }

        System.out.println(Arrays.toString(result));
        return result;
    }
}

// Branches - does not work
class Solution0 {

    record SubArr(long sum, int startIdx, int endIdx) {
        boolean hasOverlap(SubArr other) {
            return Math.min(this.endIdx, other.endIdx) >= Math.max(this.startIdx, other.startIdx);
        }
    }

    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {

        var allSubArr = new SubArr[nums.length-k+1];

        long subArrSum = 0;
        for (int i = 0; i < nums.length; i++) {
            subArrSum += nums[i];
            var subArrStartIdx = i-k+1;
            if (subArrStartIdx < 0) {
                continue;
            }
            allSubArr[subArrStartIdx] = new SubArr(subArrSum, subArrStartIdx, i);
            subArrSum -= nums[subArrStartIdx];
        }

        Arrays.sort(allSubArr, (a,b) -> {
            var result = Long.compare(b.sum, a.sum); // desc by sum
            if (result != 0) {
                return result;
            }
            return Integer.compare(a.startIdx, b.startIdx); // asc by index
        });

//        for (SubArr subArr : allSubArr) {
//            System.out.println(subArr);
//        }
        var branches = new ArrayList<List<SubArr>>();
        for (SubArr subArr : allSubArr) {
            var completeBranchMaxIdx = -1;
            var completeBranchMaxSum = 0L;
            //var isConnectedToBranch = false;
            for (int branchIdx = 0; branchIdx < branches.size(); branchIdx++) {
                var branch = branches.get(branchIdx);
                var hasOverlapsWithBranch = false;
                for (var branchSubArr : branch) {
                    if (branchSubArr.hasOverlap(subArr)) {
                        hasOverlapsWithBranch = true;
                        break;
                    }
                }
                if (hasOverlapsWithBranch) {
                    continue;
                }

                // connect to branch
                branch.add(subArr);
                //isConnectedToBranch = true;

                // Check is we complete
                if (branch.size() == 3) {
                    var branchSum =
                        branch.get(0).sum +
                        branch.get(1).sum +
                        branch.get(2).sum;

                    if (branchSum > completeBranchMaxSum) {
                        completeBranchMaxSum = branchSum;
                        completeBranchMaxIdx = branchIdx;
                    }
                }
            }

            if (completeBranchMaxIdx != -1) {
                var branch = branches.get(completeBranchMaxIdx);
                var result = new int[] {
                        branch.get(0).startIdx,
                        branch.get(1).startIdx,
                        branch.get(2).startIdx
                };
                Arrays.sort(result);
                return result;
            }


            //if (!isConnectedToBranch) {
                // create a new branch
                var branch = new ArrayList<SubArr>();
                branch.add(subArr);
                branches.add(branch);
            //}
        }

//        var branch = branches.get(completeBranchMaxIdx);
//        var result = new int[] {
//                branch.get(0).startIdx,
//                branch.get(1).startIdx,
//                branch.get(2).startIdx
//        };
//        Arrays.sort(result);
//        return result;

        throw new IllegalStateException("Cannot solve task");
    }
}