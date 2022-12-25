package com.azureproject.servicesgui;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Control;

/**
 * <i>PrintWorker</i> is an abstract class which is aimed to create a
 * <i>Task</i> to print
 * text into an javafx <i>Control</i> object. It implements a
 * <i>BlockingQueue</i> to add a
 * print queue into <i>target</i>, so only one string gets printed at the time.
 */
public abstract class GUIWorker<T> extends Task<Void> {
    /*
     * (non-Javadoc)
     * 
     * @see javafx.concurrent.Task#call()
     */

    public Control target;
    public BlockingQueue<T> queue;

    /**
     * 
     * @param target A javafx <i>Control</i> object which the data is going to be
     *               printed on
     * @param queue  A <i>BlockingQueue</i> implementation which is going to hold
     *               the elements to print in a sequential orden and notify this
     *               Task whenever a new element enter the queue
     * 
     */
    public GUIWorker(Control target, BlockingQueue<T> queue) {
        this.target = target;
        this.queue = queue;
    }

    /**
     * <i>modifyGUI</i> is a function to print an object in a javafx object that
     * inherits
     * from <i>Control</i>
     * 
     * @param object is the data that is going to be printed into <b>target</b>
     * @param target is the element where <i>object</i> is going to be printed
     */
    public abstract void modifyGUI(T object, Control target);

    /**
     * Implementation of <i>call</i> method from <i>Task</i> class. Provides the
     * functionality of printing queue. Also uses the <i>runLater</i> method from
     * <i>Platform</i> class to invoke <i>print</i>, making this implementation a
     * javafx thread safe for changing the elements in the GUI.
     * 
     * 
     * @return Void
     * @throws Exception
     */
    @Override
    protected Void call() throws Exception {
        // TODO Auto-generated method stub

        while (true) {
            try {
                // We wait until there is a element in the queue
                T data = queue.take();
                // Create a CountDownLatch for synchronizing the javafx GUI thread with this
                // task
                CountDownLatch latch = new CountDownLatch(1);
                // Invoke print from runLater so the changes to the GUI are done in the javafx
                // GUI thread
                Platform.runLater(() -> {
                    modifyGUI(data, target);
                    // When t is printed into target we perform a count in the latch
                    latch.countDown();
                });
                // Check if the thread is cancelled
                if (isCancelled()) {
                    break;
                }

                latch.await();

            } catch (InterruptedException ex) {
                // Check if the Task is interrupted due to some InterruptionException, caused by
                // latch.await()
                // If is in fact cancelled, then break the while loop and end the task.
                if (isCancelled()) {
                    break;
                }
            }
        }
        return null;
    }

}
