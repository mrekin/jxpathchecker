package ru.rmm.home;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveForm extends JFrame {
    int type = 0;  //0 -filter, 1 - message
    public SaveForm(int type,String text, String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this.getParent());
        //setPreferredSize(new Dimension(400, 200));

        String date = (new SimpleDateFormat("dd.MM HH:mm:ss:SSS")).format(new Date());
        String name = "filter_"+date;

        MigLayout ml = new MigLayout();

        JLabel lb = new JLabel("Filter name:");

        JTextField nameField = new JTextField(name);
        JButton saveB = new JButton("Save");

        saveB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(type==0){
                    DBLayer.getInstance().setFilter(name,text);
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
        add(nameField,"span 2, wrap, grow, w 100%");
        add(saveB,"align right");
        add(cancel,"align right");

        pack();

        setVisible(true);

    }



}
