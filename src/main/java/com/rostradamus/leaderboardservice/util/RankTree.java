package com.rostradamus.leaderboardservice.util;

import com.rostradamus.leaderboardservice.model.LeaderboardRow;
import org.springframework.lang.NonNull;

import java.util.*;

// AVL Tree Representation of Rank Structure
public class RankTree {
    private RankNode root;
    private static class RankNode {
        private RankNode left, right;
        private LeaderboardRow data;
        private int size, height;

        private RankNode(LeaderboardRow data) {
            this.data = data;
            this.size = 1;
            this.height = 0;
        }
    }

    public RankTree() {
        this.root = null;
    }

    public void insert(@NonNull LeaderboardRow data) {
        this.root = insert(root, data);
    }

    private RankNode insert(RankNode node, LeaderboardRow data) {
        if (node == null) return new RankNode(data);
        int comparison = data.compareTo(node.data);
        if (comparison < 0) {
            node.left = insert(node.left, data);
        } else if (comparison > 0) {
            node.right = insert(node.right, data);
        }
        node = balance(node);
        data.setRank(rank(root, data));
        return node;
    }

    public void remove(@NonNull LeaderboardRow data) {
        this.root = this.remove(this.root, data);
    }

    private RankNode remove(RankNode node, LeaderboardRow data) {
        if (node == null) return null;
        int comparison = data.compareTo(node.data);
        if (comparison < 0) {
            node.left = this.remove(node.left, data);
        } else if (comparison > 0) {
            node.right = this.remove(node.right, data);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                RankNode rightMinNode = minNode(node.right);
                node.data = rightMinNode.data;
                node.right = remove(node.right, node.data);
            }
        }
        if (node != null) {
            node = balance(node);
            data.setRank(rank(root, data));
        }
        return node;
    }

    public LeaderboardRow search(@NonNull LeaderboardRow data) {
        RankNode found = search(root, data);
        if (found == null) return null;
        return found.data;
    }

    private RankNode search(RankNode node, LeaderboardRow data) {
        if (node == null) return null;
        System.out.println("Current Node is, id: " + node.data.getUserId() + ", score: " + node.data.getScore());
        int comparison = data.compareTo(node.data);
        if (comparison == 0) return node;
        if (comparison < 0) {
            return search(node.left, data);
        } else {
            return search(node.right, data);
        }
    }

    private RankNode minNode(RankNode node) {
        if (node.left == null) return node;
        return minNode(node.left);
    }

    public LeaderboardRow getNth(int targetIndex) {
        return getNth(root, targetIndex).data;
    }

    private RankNode getNth(RankNode node, int targetIndex) {
        if (node == null) return null;
        int leftSize = size(node.left);
        if (leftSize > targetIndex) return getNth(node.left, targetIndex);
        else if (leftSize < targetIndex) return getNth(node.right, targetIndex - leftSize - 1);
        else return node;
    }

    public List<LeaderboardRow> topN(int n) {
        LinkedList<LeaderboardRow> topList = new LinkedList<>();
        topN(root, n, topList);
        return topList;
    }

    private void topN(RankNode node, int n, LinkedList<LeaderboardRow> topList) {
        if (node == null) return;
        int leftSize = size(node.left);
        if (leftSize > n) topN(node.left, n, topList);
        else if (leftSize < n) {
            topN(node.left, n, topList);
            topList.addLast(node.data);
            topN(node.right, n - leftSize - 1, topList);
        }
        else {
            topN(node.left, n, topList);
            topList.addLast(node.data);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }

    private int getBalance(RankNode node) {
        return (node == null) ? 0 : height(node.right) - height(node.left);
    }

    private int height(RankNode node) {
        return (node == null) ? -1 : node.height;
    }

    private void updateHeight(RankNode node) {
        node.height = Math.max(height(node.right), height(node.left)) + 1;
    }

    private int rank(RankNode node, LeaderboardRow data) {
        if (node == null) return 0;
        int comparison = data.compareTo(node.data);
        if (comparison < 0) return rank(node.left, data);
        else if (comparison > 0) return 1 + size(node.left) + rank(node.right, data);
        else return size(node.left);
    }

    public int size() {
        return size(root);
    }

    public int height() {
        return this.root.height;
    }

    private int size(RankNode node) {
        if (node == null) return 0;
        return node.size;
    }

    private void updateSize(RankNode node) {
        node.size = size(node.left) + size(node.right) + 1;
    }

    private RankNode balance(RankNode node) {
        updateSize(node);
        updateHeight(node);
        int balance = getBalance(node);
        if (balance > 1) {
            if (height(node.right.right) <= height(node.right.left)) {
                node.right = rotateRight(node.right);
            }
            node = rotateLeft(node);
        } else if (balance < -1) {
            if (height(node.left.left) <= height(node.left.right)) {
                node.left = rotateLeft(node.left);
            }
            node = rotateRight(node);
        }

        return node;
    }

    private RankNode rotateLeft(RankNode node) {
        RankNode right = node.right;
        RankNode rightLeft = right.left;
        right.left = node;
        node.right = rightLeft;
        right.size = node.size;
        updateSize(node);
        updateHeight(node);
        updateHeight(right);
        return right;
    }

    private RankNode rotateRight(RankNode node) {
        RankNode left = node.left;
        RankNode leftRight = left.right;
        left.right = node;
        node.left = leftRight;
        left.size = node.size;
        updateSize(node);
        updateHeight(node);
        updateHeight(left);
        return left;
    }

    public void print() {
        print(root);
    }
    private void print(RankNode node) {
        if (node == null) return;
        print(node.left);
        System.out.println(node.data.getUserId() + ": " + node.data.getScore() + ", size = " + node.size);
        print(node.right);
    }
}


