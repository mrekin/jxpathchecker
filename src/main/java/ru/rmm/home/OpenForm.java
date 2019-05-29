package ru.rmm.home;

import javafx.embed.swing.SwingFXUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OpenForm extends JFrame {

    int type = 0;  //0 -filter, 1 - message
    public OpenForm(Launch pf, int type, String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this.getParent());
        //setPreferredSize(new Dimension(400, 200));

        MigLayout ml = new MigLayout();

        JLabel lb = new JLabel("Filter name:");

        JList<String> nameField = new JList<String>(DBLayer.getInstance().getFilersList().toArray(new String[0]));

        JButton saveB = new JButton("Open");
        String name = "";

        saveB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getSelectedValue();
                if(type==0){
                    String f = DBLayer.getInstance().getFilter(name);
                   pf.setFilter(f);
                }else{

                }
            }
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setLayout(ml);
        add(lb,"span 2, wrap, grow, w 100%");
        add(new JScrollPane(nameField),"span 2, wrap, grow, w 100%, h :100:");
        add(saveB,"align right");
        add(cancel,"align right");

        pack();

        setVisible(true);

    }


}
