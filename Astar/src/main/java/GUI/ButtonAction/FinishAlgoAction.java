package GUI.ButtonAction;

import Algo.AWithStar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class FinishAlgoAction extends AbstractAction {
    private AWithStar algo;

    public FinishAlgoAction(AWithStar algo){
        this.algo = algo;
    }
    public void actionPerformed(ActionEvent event){
        algo.resetAlgo();
        ArrayList<String> path = algo.doAlgo(algo.getStart(), algo.getFinish());
        for(String label : path){
            System.out.print(label + " ");
        }
        System.out.println();
    }
}
