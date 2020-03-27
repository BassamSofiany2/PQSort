package PQSort;


import java.util.LinkedList;
import java.util.Random;


class LinkedListItem
{
    public LinkedListItem next;
    public int key;
    public int value;
    public LinkedListItem(int key, int value)
    {
        this.key = key;
        this.value = value;
        this.next = null;
    }
} 

class LinkedListPriorityQueue
{
    private LinkedListItem head;
    public LinkedListPriorityQueue()
    {
        this.head = null;
    }
    public int pop()
    {
        LinkedListItem temp = head; 
        head = head.next;
        int result = temp.value;
        return result;
    }
    public void add(int key, int value)
    {
        LinkedListItem current = head;
        LinkedListItem temp = new LinkedListItem(key, value);
        if (head == null)
        {
            head = temp;
        }
        else if (current.key > key)
        {
            temp.next = head; 
            head = temp; 
        }
        else
        { 
            while (current.next != null && current.next.key < key)
            { 
                current = current.next; 
            }
            temp.next = current.next; 
            current.next = temp; 
        }
    }
}


class HeapItem
{
    public int key;
    public int value;
    public HeapItem(int key, int value)
    {
        this.key = key;
        this.value = value;
    }
}

class HeapPriorityQueue
{
    private HeapItem heap[];
    private int size;
    private int max_size;
    public HeapPriorityQueue(int size) 
    {
        heap = new HeapItem[size];
        this.size = 0;
        this.max_size = size;
    }

    private void heapify_down(int pos) 
    {
        int smallest = pos;
        int left = 2 * pos + 1;
        int right = 2 * pos + 2;

        if (left < size && heap[left].key < heap[smallest].key)
        {
            smallest = left; 
        }

        if (right < size && heap[right].key < heap[smallest].key)
        {
            smallest = right;
        }

        if (smallest != pos)
        { 
            HeapItem temp = heap[pos];
            heap[pos] = heap[smallest]; 
            heap[smallest] = temp;
            heapify_down(smallest); 
        }  
    }
    private void heapify_up(int pos)
    {
        int parent = (pos - 1) / 2;
        if (pos > 0 && heap[parent].key > heap[pos].key) 
        {
            HeapItem temp = heap[pos];
            heap[pos] = heap[parent]; 
            heap[parent] = temp;
            heapify_up(parent);
        }
    }
    public void add(int key, int value)
    {
        if (size >= max_size)
        {
            return;
        }
        HeapItem new_item = new HeapItem(key, value);
        heap[size] = new_item;
        heapify_up(size);
        size++;
    }
    public int pop() 
    {
        int result = heap[0].value;
        heap[0] = heap[--size];
        heapify_down(0);
        return result; 
    }
}


public class PQSort 
{
    public static LinkedList<Integer> randomList(int size)
    {
        Random random = new Random();
        LinkedList<Integer> result = new LinkedList();
        for (int i = 0 ; i < size ; i++)
        {
            int temp = random.nextInt();
            result.addLast(temp);
        }
        return result;
    }
    public static void main(String args[]) 
    {
        int size = 1000;
        long startTime;
        long endTime;
        long duration;
        System.out.println("Size\t\tHeapPQ\t\tLinkedListPQ");
        while (size < 100000)
        {
            LinkedListPriorityQueue pq_list = new LinkedListPriorityQueue();
            HeapPriorityQueue pq_heap = new HeapPriorityQueue(size);
            LinkedList<Integer> input_1 = randomList(size);
            LinkedList<Integer> input_2 = (LinkedList)input_1.clone();
            startTime = System.nanoTime();
            for (int i = 0 ; i < size ; i++)
            {
                int temp = input_1.pop();
                pq_heap.add(temp, temp);
            }
            for (int i = 0 ; i < size ; i++)
            {
                int temp = pq_heap.pop();
                input_1.addLast(temp);
            }
            endTime = System.nanoTime();
            duration = endTime - startTime;
            System.out.print(size + "\t\t" + duration);

            startTime = System.nanoTime();
            for (int i = 0 ; i < size ; i++)
            {
                int temp = input_2.pop();
                pq_list.add(temp, temp);
            }
            for (int i = 0 ; i < size ; i++)
            {
                int temp = pq_list.pop();
                input_2.addLast(temp);
            }
            endTime = System.nanoTime();
            duration = endTime - startTime;
            System.out.println("\t\t" + duration);
            size = (int)(size * 1.5);
        }
    } 
} 
