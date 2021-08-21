package com.company;

public interface List<E> {
    E get(int index);
    void add(E element);
    void add(int index, E element);
    void remove(int index);
    int size();
}
