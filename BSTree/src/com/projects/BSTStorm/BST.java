package com.projects.BSTStorm;


import java.util.Comparator;

/**
 */
public class BST<T extends Comparable<T>> {

    private BSTNode<T> root;

    private Comparator<T> comparator;
    private int size;

    public BST() {
        root = null;
        comparator = null;
    }

    public BST(Comparator<T> comp) {
        root = null;
        comparator = comp;
    }

    public void insert(T data) {
        root = insert(root, data);
    }

    private BSTNode<T> insert(BSTNode<T> p, T toInsert) {
        if (p == null) {
            size++;
            return new BSTNode<T>(toInsert);
        }

        if (compare(toInsert, p.data) == 0) {
            return p;
        }

        if (compare(toInsert, p.data) < 0) {
            p.left = insert(p.left, toInsert);
        } else {
            p.right = insert(p.right, toInsert);
        }

        size++;
        return p;
    }

    public boolean search(T toSearch) {
        return search(root, toSearch);
    }

    private boolean search(BSTNode<T> p, T toSearch) {
        if (p == null) {
            return false;
        } else if (compare(toSearch, p.data) == 0) {
            return true;
        } else if (compare(toSearch, p.data) < 0) {
            return search(p.left, toSearch);
        } else {
            return search(p.right, toSearch);
        }
    }

    public BSTNode<T>  searchNode(T toSearch) {
        return searchNode(root, toSearch);
    }

    private BSTNode<T>  searchNode(BSTNode<T> p, T toSearch) {
        if (p == null) {
            return null;
        } else if (compare(toSearch, p.data) == 0) {
            return p;
        } else if (compare(toSearch, p.data) < 0) {
            return searchNode(p.left, toSearch);
        } else {
            return searchNode(p.right, toSearch);
        }
    }

    public void delete(T toDelete) {
        root = delete(root, toDelete);
        size--;
    }

    private BSTNode<T> delete(BSTNode<T> p, T toDelete) {
        if (p == null) {
            throw new RuntimeException("cannot delete.");
        } else if (compare(toDelete, p.data) < 0) {
            p.left = delete(p.left, toDelete);
        } else if (compare(toDelete, p.data) > 0) {
            p.right = delete(p.right, toDelete);
        } else {
            if (p.left == null) {
                return p.right;
            } else if (p.right == null) {
                return p.left;
            } else {
                // get data from the rightmost node in the left subtree
                p.data = retrieveData(p.left);
                // delete the rightmost node in the left subtree
                p.left = delete(p.left, p.data);
            }
        }
        //
        return p;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public BSTNode<T> getRoot() {
        return this.root;
    }

    public void preOrderTraversal() {
        preOrderHelper(root);
    }

    private void preOrderHelper(BSTNode r) {
        if (r != null) {
            System.out.print(r + " ");
            preOrderHelper(r.left);
            preOrderHelper(r.right);
        }
    }

    public void inOrderTraversal() {
        inOrderHelper(root);
    }

    private void inOrderHelper(BSTNode r) {
        if (r != null) {
            inOrderHelper(r.left);
            System.out.print(r + " ");
            inOrderHelper(r.right);
        }
    }

    public int height() {
        return height(root);
    }

    private int height(BSTNode<T> p) {
        if (p == null) {
            return -1;
        } else {
            return 1 + Math.max(height(p.left), height(p.right));
        }
    }

    public int countLeaves() {
        return countLeaves(root);
    }

    private int countLeaves(BSTNode<T> p) {
        if (p == null) {
            return 0;
        } else if (p.left == null && p.right == null) {
            return 1;
        } else {
            return countLeaves(p.left) + countLeaves(p.right);
        }
    }


    private T retrieveData(BSTNode<T> p) {
        while (p.right != null) {
            p = p.right;
        }

        return p.data;
    }

    private int compare(T x, T y) {
        if (comparator == null) {
            return x.compareTo(y);
        } else {
            return comparator.compare(x, y);
        }
    }

}
