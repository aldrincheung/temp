import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Name: Aldrin Cheung
 * ID:  A16950300
 * Email: y1cheung@ucsd.edu
 * Sources used: 
 * Zybooks chapter 6.2
 * https://learn.zybooks.com/zybook/UCSDCSE12Winter2022/chapter/6/section/2
 * 
 * 2-4 sentence file description here
 */

// Your import statements

/**
 * TODO: Add class header
 */
public class MyMinHeap<E extends Comparable<E>> 
                            implements MinHeapInterface <E>{

    public ArrayList<E> data;

    /**
     * TODO: Method header
     */
    public MyMinHeap(){
        data = new ArrayList<>();
    }

    /**
     * TODO: 
     * @param c
     */
    public MyMinHeap(Collection<? extends E> collection){
        if(collection == null) throw new NullPointerException();
        data = new ArrayList<>(collection);

        brute_force_make_min_heap_valid();
    }

    private void brute_force_make_min_heap_valid(){
        for(int i = data.size() - 1; i >= 0; i--){
            if(data.get(i) == null) throw new NullPointerException();
            percolateDown(i);
        }
    }

    
    /** 
     * @param element
     */
    @Override
    public void insert(E element) {
        if(element == null) throw new NullPointerException();

        data.add(element);
        percolateUp(data.size() - 1);
    }

    
    /** 
     * @return E
     */
    @Override
    public E getMin() {
        if(data.size() == 0) return null;
        else return data.get(0);
    }

    
    /** 
     * @return E
     */
    @Override
    public E remove() {
        if(data.size() <= 0) return null;
        return deleteIndex(0);
    }

    
    /** 
     * @return int
     */
    @Override
    public int size() {
        return data.size();
    }

    @Override
    public void clear() {
        data.clear();
    }

    //@region Helper functions
    /** 
     * @param from
     * @param to
     */
    protected void swap(int from, int to){
        E tempElem = data.get(from);
        data.set(from, data.get(to));
        data.set(to, tempElem);
    }
    
    /** 
     * @param index
     * @return int
     */
    protected int getParentIdx(int index){
        return (index - 1)/2;
    }
    
    /** 
     * @param index
     * @return int
     */
    protected int getLeftChildIdx(int index){
        return (2 * index) + 1;
    }
    
    /** 
     * @param index
     * @return int
     */
    protected int getRightChildIdx(int index){
        return (2 * index) + 2;
    }
    
    /** 
     * @param index
     * @return int
     */
    protected int getMinChildIdx(int index){
        int leftIdx = getLeftChildIdx(index);
        int rightIdx = getRightChildIdx(index);

        E leftValue = leftIdx < data.size() ? data.get(leftIdx) : null;
        E rightValue = rightIdx < data.size() ? data.get(rightIdx) : null;

        if(leftValue == null && rightValue == null) return -1;
        else if(leftValue == null) return rightIdx; // shouldn't happen
        else if(rightValue == null) return leftIdx;

        if(leftValue.compareTo(rightValue) <= 0) return leftIdx;
        else return rightIdx;
    }
    
    /** 
     * @param index
     */
    protected void percolateUp(int index){
        int parentIdx  = getParentIdx(index);
        if(parentIdx < 0) return;
        E currValue = data.get(index); //assume index is within bounds

        while(currValue.compareTo(data.get(parentIdx)) < 0){
            swap(parentIdx, index);

            index = parentIdx;
            currValue = data.get(index);
            if((parentIdx = getParentIdx(index)) < 0) break;
        }
    }
    
    /** 
     * @param index
     */
    protected void percolateDown(int index){
        int minChildIdx;
        while((minChildIdx = getMinChildIdx(index)) > 0){
            E currValue = data.get(index);
            E minChildValue = data.get(minChildIdx);
            if(currValue.compareTo(minChildValue) <= 0) break;
            swap(minChildIdx, index);

            index = minChildIdx;
        }
    }
    
    /** 
     * @param index
     * @return E
     */
    protected E deleteIndex(int index){
        E removedElem = data.get(index);
        int lastElemIdx = data.size()-1;

        swap(index, lastElemIdx);
        data.remove(lastElemIdx);

        if(index < data.size() && index >= 0){
            percolateDown(index);
            percolateUp(index);
        }
        return removedElem;
    }
}