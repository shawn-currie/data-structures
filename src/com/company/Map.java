package com.company;

public interface Map<K, V> {
    V get(K key);
    void put(K key, V value);
}
