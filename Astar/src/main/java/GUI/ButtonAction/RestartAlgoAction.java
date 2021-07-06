package GUI.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import Algo.AWithStar;

public class RestartAlgoAction extends AbstractAction {
    private AWithStar algo;

    public RestartAlgoAction(AWithStar algo){
        this.algo = algo;
    }
    public void actionPerformed(ActionEvent event) {
        algo.resetAlgo();
        System.out.println("Return to the start. Algorithm and graph returned to original state!");
    }
}
