package ru.rmm.home;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadDriverForm extends JFrame {
    int type = 0;  //0 -filter, 1 - message

    public DownloadDriverForm(Launch pf, String url, String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(this.getParent());
        //setPreferredSize(new Dimension(400, 20));

        MigLayout ml = new MigLayout("w 250::");

        JLabel lb = new JLabel("URL:");

        JTextField nameField = new JTextField(url);
        JButton saveB = new JButton("Download");

        saveB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Files.createDirectories(Paths.get("lib"));

                    URL website = new URL(nameField.getText());
                    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                    FileOutputStream fos = new FileOutputStream("lib/sqlite_jdbc.jar");
                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                } catch (MalformedURLException me) {
                    System.out.println(me.getLocalizedMessage());
                } catch (FileNotFoundException fe) {
                    System.out.println(fe.getLocalizedMessage());
                } catch (IOException ioe) {
                    System.out.println(ioe.getLocalizedMessage());
                }
                System.out.println("Downloaded..");
                DBLayer.checkDriver();
                pf.log("Successfully downloaded, please RESTART application");
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

        setLayout(ml);
        add(lb, "span 2, wrap, grow, w 100%");
        add(nameField, "span 2, wrap, grow, w 100%");
        add(saveB, "align right");
        add(cancel, "align right");

        pack();

        setVisible(true);

    }


}
