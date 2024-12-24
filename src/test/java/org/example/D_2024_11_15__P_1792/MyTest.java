package org.example.D_2024_11_15__P_1792;

import org.example.MyTestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * https://leetcode.com/problems/maximum-average-pass-ratio
 *
 * <pre>
 * Medium
 * Topics
 * Companies
 * Hint
 * There is a school that has classes of students and each class will be having a final exam. You are given a 2D integer array classes, where classes[i] = [passi, totali]. You know beforehand that in the ith class, there are totali total students, but only passi number of students will pass the exam.
 *
 * You are also given an integer extraStudents. There are another extraStudents brilliant students that are guaranteed to pass the exam of any class they are assigned to. You want to assign each of the extraStudents students to a class in a way that maximizes the average pass ratio across all the classes.
 *
 * The pass ratio of a class is equal to the number of students of the class that will pass the exam divided by the total number of students of the class. The average pass ratio is the sum of pass ratios of all the classes divided by the number of the classes.
 *
 * Return the maximum possible average pass ratio after assigning the extraStudents students. Answers within 10-5 of the actual answer will be accepted.
 *
 *
 *
 * Example 1:
 *
 * Input: classes = [[1,2],[3,5],[2,2]], extraStudents = 2
 * Output: 0.78333
 * Explanation: You can assign the two extra students to the first class. The average pass ratio will be equal to (3/4 + 3/5 + 2/2) / 3 = 0.78333.
 * Example 2:
 *
 * Input: classes = [[2,4],[3,9],[4,5],[2,10]], extraStudents = 4
 * Output: 0.53485
 *
 *
 * Constraints:
 *
 * 1 <= classes.length <= 105
 * classes[i].length == 2
 * 1 <= passi <= totali <= 105
 * 1 <= extraStudents <= 105
 * </pre>
 */
class MyTest {

    Solution solution = new Solution();

    @ParameterizedTest
    @CsvFileSource(resources = "/D_2024_11_15__P_1792.csv", numLinesToSkip = 1, maxCharsPerColumn=500000)
    public void myTest(double expected, String classesStr, int extraStudents) {
        var classes = MyTestUtils.parseArray2D(classesStr);
        assertEquals(expected, solution.maxAverageRatio(classes, extraStudents), 0.00001);
    }
}

/**
 * Time Limit Exceeded on case 3
 */
class Solution0 {
    public double maxAverageRatio(int[][] classes, int extraStudents) {

        while (extraStudents > 0) {
            extraStudents--;

            var maxDelta = -1.0;
            var maxDeltaClassIndex = -1;
            for (var classIndex = 0; classIndex < classes.length; classIndex++) {
                var oldPassRatio = ((double) classes[classIndex][0]) / (classes[classIndex][1]);
                var newPassRatio = ((double) classes[classIndex][0] + 1) / (classes[classIndex][1] + 1);
                var delta = newPassRatio - oldPassRatio;
                if (delta > maxDelta) {
                    maxDelta = delta;
                    maxDeltaClassIndex = classIndex;
                }
            }
            classes[maxDeltaClassIndex][0]++;
            classes[maxDeltaClassIndex][1]++;
        }

        var maxAvgPass = 0.0;
        for (var classIndex = 0; classIndex < classes.length; classIndex++) {
            maxAvgPass += ((double) classes[classIndex][0]) / classes[classIndex][1];
        }
        maxAvgPass /= classes.length;

        return maxAvgPass;
    }
}


class Solution {

    private record PqEntry (double delta, int classIndex) {}

    private double getDelta(int[] aClass) {
        var oldPassRatio = ((double) aClass[0]) / (aClass[1]);
        var newPassRatio = ((double) aClass[0] + 1) / (aClass[1] + 1);
        return newPassRatio - oldPassRatio;
    }

    public double maxAverageRatio(int[][] classes, int extraStudents) {
        var pq = new PriorityQueue<PqEntry>(
                (a,b) -> Double.compare(b.delta, a.delta) // max first
        );
        for (var classIndex = 0; classIndex < classes.length; classIndex++) {
            var delta = getDelta(classes[classIndex]);
            pq.add(new PqEntry(delta, classIndex));
        }

        while (extraStudents > 0) {
            extraStudents--;

            var pqEntry = pq.poll();
            classes[pqEntry.classIndex][0]++;
            classes[pqEntry.classIndex][1]++;

            var delta = getDelta(classes[pqEntry.classIndex]);
            pq.add(new PqEntry(delta, pqEntry.classIndex));
        }

        var maxAvgPass = 0.0;
        for (var classIndex = 0; classIndex < classes.length; classIndex++) {
            maxAvgPass += ((double) classes[classIndex][0]) / classes[classIndex][1];
        }
        maxAvgPass /= classes.length;

        return maxAvgPass;
    }
}

