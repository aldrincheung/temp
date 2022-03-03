/**
 * TODO: Add your file header
 * Name:
 * ID:
 * Email:
 * Sources used: Put "None" if you did not have any external help
 * Some example of sources used would be Tutors, Zybooks, and Lecture Slides
 * 
 * 2-4 sentence file description here
 */

import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TODO: Add your class header
 * 
 * IMPORTANT: Do not change the method names and points are awarded
 * only if your test cases cover cases that the public tester file
 * does not take into account.
 */
public class CustomTester {
    MyMinHeap<Integer> typicalMinHeap;
    Integer[] typicalHeapContent = 
        new Integer[] {2, 20, 3, 60, 50, 4, 5, 90, 80, 81, 79, 7, 6, 5};

    @Before
    public void setup(){
        typicalMinHeap = new MyMinHeap<>(Arrays.asList(typicalHeapContent));    
    }

    /**
     * Test the constructor when a null is passed as a parameter value
     */
    @Test
    public void testMyMinHeapConstructor() {
        try{
            new MyMinHeap<Integer>(null);
            fail();
        }catch(NullPointerException e) {}
    }

    /**
     * Test the getMinChildIdx method when:
     *  there's only left child -> right
     *  no child -> -1
     * 
     *  have both child -> min (already in public tester)
     * 
     * not tested:
     *  only right child -> left (should not be possible)
     */
    @Test
    public void testGetMinChildIdx() {
        assertEquals(4, typicalMinHeap.getMinChildIdx(1));
        assertEquals(5, typicalMinHeap.getMinChildIdx(2));
        assertEquals(13, typicalMinHeap.getMinChildIdx(6));
        assertEquals(-1, typicalMinHeap.getMinChildIdx(11));
    }

    /**
     * Test the percolateUp method when the item at the index doesn't need 
     * to be percolated Up
     */
    @Test
    public void testPercolateUp() {
        typicalMinHeap.percolateUp(9);
        ifEqual(typicalMinHeap.data, typicalHeapContent);
    }
    
    /**
     * Test the percolateDown method when the item at the index doesn't need
     * to be percolated down
     */
    @Test
    public void testPercolateDown() {
        typicalMinHeap.percolateUp(0);
        ifEqual(typicalMinHeap.data, typicalHeapContent);
    }

    /**
     * Test the deleteIndex method when item to delete is a leaf node, but 
     * not at the right most
     * 
     * for minHeap, to my knowledge would only delete the root node, but 
     * that is already tested in the public tester
     */
    @Test
    public void testDeleteIndex() {
        typicalMinHeap.deleteIndex(7);
        
        Integer[] expected = 
            new Integer[] {2, 5, 3, 20, 50, 4, 5, 60, 80, 81, 79, 7, 6};
        assertTrue(ifEqual(typicalMinHeap.data, expected));
    }

    /**
     * Test the deleteIndex method when deleting a node with children but not
     * root
     * https://piazza.com/class/kxr1o3f1cxq6bo?cid=1054
     */
    @Test
    public void testDeleteIndex2() {
        MyMinHeap<Integer> fromPiazza = new MyMinHeap<>(Arrays.asList(
            new Integer[] {1, 20, 10, 30, 50, 11, 15, 31, 32,
                            51, 52, 12, 13, 16, 17}
        ));
        fromPiazza.deleteIndex(4);
        Integer[] expected = new Integer[]{1, 17, 10, 30, 20, 11, 15, 31, 32,
                                            51, 52, 12, 13, 16};
        
        System.out.println(Arrays.toString(fromPiazza.data.toArray()));

        assertTrue(ifEqual(fromPiazza.data, expected));
    }

    /**
     * Test the insert method when insert is already the largest element and
     * it doesn't have to swap anymore
     */
    @Test
    public void testInsert(){
        typicalMinHeap.insert(200);
        Integer[] expected = 
            new Integer[] {2, 20, 3, 60, 50, 4, 5, 90, 
                            80, 81, 79, 7, 6, 5, 200};

        assertTrue(ifEqual(typicalMinHeap.data, expected));
    }

    /**
     * Test the insert method when the inserted element would go into the 
     * middle of the list and not perlocated all the way up
     */
    @Test
    public void testInsert2(){
        typicalMinHeap.insert(4);
        Integer[] expected = 
            new Integer[] {2, 20, 3, 60, 50, 4, 4, 90, 80, 81, 79, 7, 6, 5, 5};

        assertTrue(ifEqual(typicalMinHeap.data, expected));
    }

   
    /**
     * Test remove when the heap is empty
     * and remove multiple times on an non-empty heap
     */
    @Test
    public void testRemove(){
        MyMinHeap<Integer> emptyHeap = new MyMinHeap<>();
        assertEquals(null, emptyHeap.remove());

        assertEquals(2, (int) typicalMinHeap.remove());
        assertEquals(3, (int) typicalMinHeap.remove());
        assertEquals(4, (int) typicalMinHeap.remove());
        assertEquals(5, (int) typicalMinHeap.remove());
        assertEquals(5, (int) typicalMinHeap.remove());
        assertEquals(6, (int) typicalMinHeap.remove());
        assertEquals(7, (int) typicalMinHeap.remove());
        assertEquals(20, (int) typicalMinHeap.remove());
    }

  
    /**
     * Test getMin when empty and after couple removes()
     */
    @Test
    public void testGetMin(){
        MyMinHeap<Integer> emptyHeap = new MyMinHeap<>();
        assertEquals(null, emptyHeap.remove());

        assertEquals(2, (int) typicalMinHeap.getMin());
        assertEquals(2, (int) typicalMinHeap.remove());
        assertEquals(3, (int) typicalMinHeap.getMin());
        assertEquals(3, (int) typicalMinHeap.remove());
        assertEquals(4, (int) typicalMinHeap.getMin());
        assertEquals(4, (int) typicalMinHeap.remove());
        
    }

    public <E> boolean ifEqual(ArrayList<E> arrayList, E[] arr){
        if(arrayList.size() != arr.length) return false;
        
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == null) {
                if(arr[i] != arrayList.get(i)) return false;
                else continue;
            }

            if(!arr[i].equals(arrayList.get(i))) return false;
        }
        return true;
    }
}