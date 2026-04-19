/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trac;

/**
 *
 * @author nguyentrac
 * Generic MinHeap class
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinHeapPriorityQueue<T extends Comparable<? super T>> {
    private List<T> heap;
    private Map<T, Integer> positionMap; // Tracks the position of each element for decrease-key operations

    public MinHeapPriorityQueue() {
        heap = new ArrayList<>();
        positionMap = new HashMap<>();
    }

    // Insert a new element into the heap
    public void insert(T item) {
        heap.add(item);
        int index = heap.size() - 1;
        positionMap.put(item, index);
        percolateUp(index);
    }

    // Extract the minimum element from the heap
    public T extractMin() {
        if (heap.isEmpty()) {
            return null;
        }
        T minItem = heap.get(0);
        T lastItem = heap.remove(heap.size() - 1);
        positionMap.remove(minItem);

        if (!heap.isEmpty()) {
            heap.set(0, lastItem);
            positionMap.put(lastItem, 0);
            percolateDown(0);
        }

        return minItem;
    }

    // Decrease the key of a given element
    public void decreaseKey(T item, T newItem) {
        int index = positionMap.get(item);
        heap.set(index, newItem);
        positionMap.remove(item);
        positionMap.put(newItem, index);
        percolateUp(index);
    }

    // Percolate up to maintain heap properties
    private void percolateUp(int index) {
        T value = heap.get(index);
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            T parentValue = heap.get(parentIndex);
            if (value.compareTo(parentValue) >= 0) {
                break;
            }
            // Swap with parent
            heap.set(index, parentValue);
            positionMap.put(parentValue, index);
            index = parentIndex;
        }
        heap.set(index, value);
        positionMap.put(value, index);
    }

    // Percolate down to maintain heap properties
    private void percolateDown(int index) {
        T value = heap.get(index);
        int heapSize = heap.size();
        int childIndex = 2 * index + 1;

        while (childIndex < heapSize) {
            int minIndex = childIndex;
            if (childIndex + 1 < heapSize && heap.get(childIndex + 1).compareTo(heap.get(childIndex)) < 0) {
                minIndex = childIndex + 1;
            }

            if (heap.get(minIndex).compareTo(value) >= 0) {
                break;
            }

            // Swap with the child
            heap.set(index, heap.get(minIndex));
            positionMap.put(heap.get(minIndex), index);
            index = minIndex;
            childIndex = 2 * index + 1;
        }
        heap.set(index, value);
        positionMap.put(value, index);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    public void insertOrUpdate(T item) {
    if (positionMap.containsKey(item)) {
        decreaseKey(item, item); // Update distance
    } else {
        insert(item); // Insert new
    }
}

    public Map<T, Integer> getPositionMap() {
        return positionMap;
    }

    
}
