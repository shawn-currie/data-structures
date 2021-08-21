package com.company;

import java.util.Set;

public interface Map<K, V> {
    V get(K key);
    void put(K key, V value);
    Set<K> keySet();
    int size();
    boolean containsKey(K key);
    V getOrDefault(K key, V defaultValue);
}
