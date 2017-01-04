package com.projects.BSTStorm;


/**
 * node for three
 */
public class BSTNode<T> {

    public T data;
    public BSTNode<T> left, right;

    public BSTNode(T data, BSTNode<T> l, BSTNode<T> r) {
        left = l;
        right = r;
        this.data = data;
    }

    public BSTNode(T data) {
        this(data, null, null);
    }

    public String toString() {
        return data.toString();
    }

}
