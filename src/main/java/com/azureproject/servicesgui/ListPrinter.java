package com.azureproject.servicesgui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;

import javafx.scene.control.Control;
import javafx.scene.control.ListView;

/**
 * @param <T> should be the type of the data to be printed
 */
public class ListPrinter<T> extends GUIWorker<T> {

    public ListPrinter(ListView<T> usersConnected, BlockingQueue<T> queue) {
        super(usersConnected, queue);

    }

    /**
     * Implmentation of <i>print</i> method from <i>PrintWorker</i> to add an
     * element to a <i>ListView</i>
     */
    @Override
    public void modifyGUI(T text, Control target) {
        ((ListView<T>) target).getItems().add(text);

    }

}
