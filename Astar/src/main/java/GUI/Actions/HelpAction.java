package GUI.Actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class HelpAction extends AbstractAction {
    private JFrame frame;
    private JLabel text;

    public HelpAction(){
        super();

        text = new JLabel();

        frame = new JFrame("Help");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(null);
        frame.add(text);
        frame.setBounds(100, 50, 1000, 600);

        text.setBounds(10,10, 980, 580);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent){
        frame.setVisible(true);
        text.setText("<html>1) Modifying graph<br>" +
                "-To load graph fro file input filename in corresponding text field" +
                " and press button 'Load graph from file'<br>" +
                "-To create random graph input vertex amount, edge amount, minimal and maximal edge weights as " +
                "integer decimal numbers in corresponding fields and press button 'Create random graph'.<br>" +
                "Remember that maximal amount of vertexes is ceil(10/max_weight)*ceil(9/max_weight);" +
                " min weight is positive and not bigger than max_weight; " +
                "edge amount is less than 2*vertex_amount-1; " +
                "max weight is less than 9<br>" +
                "-To use mouse for making graph modification:<br>" +
                "* Move mouse into graph area on the left side of the application window<br>" +
                "* Fast left button click creates vertex if this position is free<br>" +
                "* Pressing left button for 2-5 seconds on position with vertex marks this vertex as start if start vertex doesn't" +
                " mark. Else vertex marked as finish<br>" +
                "* Pressing left button for more than 7 seconds on position with vertex deletes this vertex<br>" +
                "* For making new edge</html>");
    }
}
