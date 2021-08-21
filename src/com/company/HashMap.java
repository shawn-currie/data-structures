package com.company;


import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class HashMap<K, V> implements Map<K, V>{

    class Entry {
        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private final int RESIZE_FACTOR = 2;
    private List<List<Entry>> buckets;
    private int capacity;
    private double loadFactor;
    private int size;

    HashMap() {
        this.capacity = 8;
        this.buckets = createEmptyBucketList(this.capacity);
        this.loadFactor = .75;
        this.size = 0;
    }

    @Override
    public V get(K key) {
        for (Entry entry: buckets.get(getArrayIndex(key))) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        if (shouldResize()) {
            resize();
        }

        List<Entry> collisions = buckets.get(getArrayIndex(key));

        ListIterator<Entry> itr = collisions.listIterator();
        while (itr.hasNext()) {
            if (itr.next().getKey().equals(key)) {
                itr.remove();
                size--;
                break;
            }
        }
        collisions.add(new Entry(key, value));
        size++;
    }

    private void put(Entry entry) {
        buckets.get(getArrayIndex(entry.getKey())).add(entry);
    }

    private boolean shouldResize() {
        return size > capacity * loadFactor;
    }

    // TODO: will this ever be bad?
    private void resize() {
        capacity = capacity * RESIZE_FACTOR;
        List<List<Entry>> oldBuckets = buckets;
        buckets = createEmptyBucketList(capacity);
        transferEntries(oldBuckets);
    }

    private void transferEntries(List<List<Entry>> oldBuckets) {
        for(List<Entry> collisions: oldBuckets) {
            for(Entry entry: collisions) {
                put(entry);
            }
        }
    }

    private int getArrayIndex(K key) {
        return getHashCode(key) % capacity;
    }

    private int getHashCode(K key) {
        return key.hashCode();
    }

    private List<List<Entry>> createEmptyBucketList(int capacity) {
        List<List<Entry>> buckets = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            buckets.add(new LinkedList<>());
        }
        return buckets;
    }
}
