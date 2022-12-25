package com.azureproject.servicesgui;

import java.util.concurrent.BlockingQueue;

import javafx.scene.control.Control;
import javafx.scene.control.Labeled;

public class LabeledPrinter<T> extends GUIWorker<T> {

    public LabeledPrinter(Control target, BlockingQueue<T> queue) {
        super(target, queue);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void modifyGUI(T object, Control target) {
        // TODO Auto-generated method stub
        ((Labeled) target).setText(object.toString());

    }

}
