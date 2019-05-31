package ru.rmm.home;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenForm extends JFrame {

    int type = 0;  //0 -filter, 1 - message

    public OpenForm(Launch pf, int type, String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this.getParent());
        //setPreferredSize(new Dimension(400, 200));



        JLabel lb = new JLabel("Name:");

        JList<String> nameField1 = null;

        if (type == 0) {
            nameField1 = new JList<String>(DBLayer.getInstance().getFilersList().toArray(new String[0]));
        } else {
            nameField1 = new JList<String>(DBLayer.getInstance().getMessagesList().toArray(new String[0]));
        }
        JButton openB = new JButton("Open");
        String name = "";
        JList<String> nameField = nameField1;
        nameField.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        openB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name;
                if (nameField.getSelectedIndex() == -1) {
                    return;
                } else {
                    name = nameField.getSelectedValue();
                }
                if (type == 0) {
                    String f = DBLayer.getInstance().getFilter(name);
                    pf.setFilter(f);
                } else {
                    String f = DBLayer.getInstance().getMessage(name);
                    pf.setMessage(f);
                }

            }
        });

        JButton deleteB = new JButton("Delete");
        deleteB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name;
                if (nameField.getSelectedIndex() == -1) {
                    return;
                } else {
                    name = nameField.getSelectedValue();
                }
                if (type == 0) {
                    DBLayer.getInstance().deleteFilter(name);
                    pf.log("Filter " + name + " deleted");
                } else {
                    DBLayer.getInstance().deleteMessage(name);
                    pf.log("Message " + name + " deleted");
                }
                new OpenForm(pf,type,title);
                dispose();
            }
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        MigLayout ml = new MigLayout("w 250::","[grow][][]");
        setLayout(ml);
        add(lb, "span 3, wrap, w 100%");
        add(new JScrollPane(nameField), "span 3, wrap, grow, h :100:");
        add(openB,"");
        add(deleteB);
        add(cancel);

        pack();

        setVisible(true);

    }


}
