package com.company;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Heap<E> {

    List<E> list;
    Comparator<E> comparator;

    Heap(Comparator<E> comparator) {
        list = new ArrayList<>();
        this.comparator = comparator;
    }

    public void add(E element) {
        list.add(element);
        bubbleUp(list.size() - 1);
    }

    public E peek() {
        return size() != 0 ? list.get(0) : null;
    }

    public E poll() {
        if (size() == 0) {
            return null;
        } else if (size() == 1) {
            return list.remove(0);
        }

        E temp = list.get(0);
        list.set(0, list.remove(list.size() - 1));
        bubbleDown(0);
        return temp;
    }

    public int size() {
        return list.size();
    }
    
    private void bubbleUp(int index) {
        if (index == 0) {
            return;
        }
        int parentIndex = getParentIndex(index);

        if (isBetterCandidate(list.get(index), list.get(parentIndex))) {
            swap(index, parentIndex);
            bubbleUp(parentIndex);
        }
    }

    private void bubbleDown(int curIndex) {
        List<Integer> childIndices = getChildIndices(curIndex);
        if (childIndices.isEmpty()) {
            return;
        }

        int smallestChildIndex = childIndices.get(0);
        if (childIndices.size() == 2 && isBetterCandidate(list.get(childIndices.get(1)), list.get(childIndices.get(0)))) {
            smallestChildIndex = childIndices.get(1);
        }

        if (isBetterCandidate(list.get(smallestChildIndex), list.get(curIndex))) {
            swap(curIndex, smallestChildIndex);
            bubbleDown(smallestChildIndex);
        }
    }

    private boolean isBetterCandidate(E element1, E element2) {
        return comparator.compare(element1,  element2) <= 0;
    }

    private void swap(int index1, int index2) {
        E temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

    private Integer getParentIndex(int index) {
        if (index % 2 == 0) {
            return index / 2 - 1;
        }
        return index / 2;
    }

    private List<Integer> getChildIndices(int index) {
        List<Integer> childIndices = new ArrayList<>();

        int leftChildIndex = index * 2 + 1;
        int rightChildIndex = index * 2 + 2;

        if (leftChildIndex < size() - 1) {
            childIndices.add(leftChildIndex);
        }
        if (rightChildIndex < size() - 1) {
            childIndices.add(rightChildIndex);
        }

        return childIndices;
    }
}
