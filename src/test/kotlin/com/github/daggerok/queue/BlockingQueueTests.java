package com.github.daggerok.queue;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;

class QueueProcessor {

}

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BlockingQueueTests {

    /**
     * A bounded blocking queue backed by an array. This queue orders elements FIFO (first-in-first-out). The head of
     * the queue is that element that has been on the queue the longest time. The tail of the queue is that element
     * that has been on the queue the shortest time. New elements are inserted at the tail of the queue, and the queue
     * retrieval operations obtain elements at the head of the queue.
     * This is a classic "bounded buffer", in which a fixed-sized array holds elements inserted by producers and
     * extracted by consumers. Once created, the capacity cannot be changed. Attempts to put an element into a full
     * queue will result in the operation blocking; attempts to take an element from an empty queue will similarly
     * block.
     * This class supports an optional fairness policy for ordering waiting producer and consumer threads. By default,
     * this ordering is not guaranteed. However, a queue constructed with fairness set to true grants threads access in
     * FIFO order. Fairness generally decreases throughput but reduces variability and avoids starvation.
     * This class and its iterator implement all of the optional methods of the Collection and Iterator interfaces.
     * This class is a member of the Java Collections Framework.
     * Since:
     * 1.5
     */
    @Test
    void test_ArrayBlockingQueue() throws InterruptedException {
        ArrayBlockingQueue<String> messages = new ArrayBlockingQueue<>(1000, true);
        messages.put("Hello");
        messages.put("World");
        System.out.println(messages);
    }
}
