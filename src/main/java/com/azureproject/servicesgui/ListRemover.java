package com.azureproject.servicesgui;

import java.util.concurrent.BlockingQueue;

import javafx.scene.control.Control;
import javafx.scene.control.ListView;

/**
 * 
 * 
 * @param <T> should be the type of the element to remove. It also accepts
 *            integers
 * @param <V> should be the type of the elements on the <i>ListView</i>
 * 
 * 
 */
public class ListRemover<T, V> extends GUIWorker<T> {

    public ListRemover(ListView<V> usersConnected, BlockingQueue<T> queue) {
        super(usersConnected, queue);

    }

    /**
     * Implmentation of <i>print</i> method from <i>PrintWorker</i> to add an
     * element to a <i>ListView</i>
     */
    @Override
    public void modifyGUI(T idObject, Control target) {
        ((ListView<T>) target).getItems().remove(idObject);
    }

}
