package com.company;

public class ArrayList<E> implements List<E>{

    private final int CAPACITY_INCREASE_RATE = 2;

    private E[] array;
    private double loadFactor;
    private int size;

    ArrayList() {
        this.array = createArray(8);
        this.loadFactor = 0.75;
        this.size = 0;
    }

    ArrayList(int initialCapacity, double loadFactor) {
        this.array = createArray(initialCapacity);
        this.loadFactor = loadFactor;
        this.size = 0;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException{
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + " out of bounds. Max index: " + array.length);
        }
        return array[index];
    }

    @Override
    public void add(E element) {
        if (shouldExpandArray()) {
            expandArray();
        }
        array[size] = element;
        size++;
    }

    @Override
    public void add(int index, E element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + " out of bounds. Max value: " + size);
        }
        if (shouldExpandArray()) {
            expandArray();
        }
        unshiftArray(index);
        array[index] = element;
        size++;
    }

    @Override
    public void remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + " out of bounds. Max value: " + (size - 1));
        }
        shiftArray(index);
    }

    @Override
    public int size() {
        return size;
    }

    /* Learned about this here https://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java?page=1&tab=votes#tab-top
    My theory is I might get things from the array more often than I will set them.
    I will cast them on creation instead of on retrieval like in the stackoverflow post.
    */
    private E[] createArray(int capacity) {
        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[capacity];
        return array;
    }

    // TODO: consider how and where I want to use this
    private boolean shouldExpandArray() {
        return size >= array.length * loadFactor;
    }

    private void expandArray() {
        E[] newArray = createArray(array.length * CAPACITY_INCREASE_RATE);
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void shiftArray(int targetIndex) {
        System.arraycopy(array, targetIndex + 1, array, targetIndex, size - targetIndex);
        size--;
    }

    private void unshiftArray(int targetIndex) {
        if (shouldExpandArray()) {
            expandArray();
        }
        System.arraycopy(array, targetIndex, array, targetIndex + 1, size - targetIndex);
    }
}
