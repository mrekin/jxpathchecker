package ru.rmm.home;

import net.miginfocom.swing.MigLayout;
import org.apache.commons.jxpath.JXPathContext;
import org.irsn.javax.swing.CodeEditorPane;
import org.irsn.javax.swing.DefaultSyntaxColorizer;
import org.jdesktop.swingx.JXTextArea;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Launch extends JFrame {

    CodeEditorPane filter = new CodeEditorPane();
    CodeEditorPane message = new CodeEditorPane();
    JXTextArea logArea = new JXTextArea();

    public static void main(String[] args) {
        new Launch().launch();
    }


    private String check(String f, String m) {
        String result = "";
        try {
            JSONObject object = new JSONObject(m);
            Map<String, Object> map = Utils.jsonToMap(object);
            JXPathContext ctx = JXPathContext.newContext(map);
            Object resultObj = ctx.getValue(f);
            if (resultObj != null) {
                result = resultObj.toString();
                log("Result = " + result);
            } else {
                result = "Path not found";
                log(result);
            }
        } catch (Exception ee) {
            log(ee.getMessage());
        }
        return result;
    }

    ;

    public boolean launch() {

        System.setProperty("sun.java2d.dpiaware", "false");


        setTitle("JXPath Checker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 600));


        JLabel filterLabel = new JLabel("Filter:");
        JLabel messageLabel = new JLabel("Json message:");


        filter.setAutoscrolls(true);
        final HashMap<String, Color> syntax = new DefaultSyntaxColorizer.RegExpHashMap();
        syntax.put("contains", Color.BLUE);

        filter.setKeywordColor(syntax);
        filter.setFont(new Font(filter.getFont().getFontName(), 3, 12));

        JButton check = new JButton("Full check");

        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check(filter.getText(), message.getText());
            }
        });


        JButton checkSelected = new JButton("< Check selected");
        checkSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check(filter.getSelectedText(), message.getText());
            }
        });

        JButton formatFilter = new JButton("<<< Format");
        formatFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String t = filter.getText();
                t = t.replaceAll("( ){2,}", " ")
                        .replaceAll("\n", "");
                char[] source = t.toCharArray();
                StringBuilder sb = new StringBuilder();
                int i = 0;
                for (char c : source) {
                    if (c == '(') {
                        sb.append(c);
                        sb.append('\n');
                        i++;
                        for (int j = 0; j < i; j++) {
                            sb.append("  ");
                        }

                    } else if (c == ')') {
                        sb.append(c);
                        sb.append('\n');
                        i--;
                        for (int j = 0; j < i; j++) {
                            sb.append("  ");
                        }


                    } else {
                        sb.append(c);
                    }
                }

                filter.setText(sb.toString());
            }
        });

        JButton formatMessage = new JButton("Format >>>");
        formatMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject o = new JSONObject(message.getText());
                message.setText(o.toString(2));
            }
        });

        Image saveIcon = null, openIcon = null;
        try {
            saveIcon = ImageIO.read(getClass().getClassLoader().getResource("save.png")).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
            openIcon = ImageIO.read(getClass().getClassLoader().getResource("open.png")).getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH);
        } catch (
                IOException ioe
        ) {
            ioe.printStackTrace();
        }


        JButton saveFilterButton = new JButton();
        saveFilterButton.setToolTipText("Save filter");
        saveFilterButton.setIcon(new ImageIcon(saveIcon));
        saveFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filter.getText().length() > 0) {
                    saveForm(0, filter.getText(), "Save filter");
                }
            }
        });

        JButton saveMessageButton = new JButton();
        saveMessageButton.setToolTipText("Save message");
        saveMessageButton.setIcon(new ImageIcon(saveIcon));
        saveMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (message.getText().length() > 0) {
                    saveForm(1, message.getText(), "Save message");
                }
            }
        });


        JButton loadFilterButton = new JButton();
        loadFilterButton.setToolTipText("Open filter");
        loadFilterButton.setIcon(new ImageIcon(openIcon));
        loadFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openForm(0, "Open filter");
            }
        });
        JButton loadMessageButton = new JButton();
        loadMessageButton.setToolTipText("Open message");
        loadMessageButton.setIcon(new ImageIcon(openIcon));
        loadMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openForm(1, "Open message");
            }
        });

        JScrollPane logSP = new JScrollPane(logArea);
        logArea.setFont(new Font(logArea.getFont().getFontName(), 3, 12));
        //logSP.setMinimumSize(new Dimension(1000,100));

        message.setAutoscrolls(true);
        message.setFont(new Font(message.getFont().getFontName(), 3, 12));
        message.setKeywordColor(new DefaultSyntaxColorizer.RegExpHashMap());


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new MigLayout());
        buttonsPanel.add(check, "wrap, span 2, align center");
        buttonsPanel.add(checkSelected, "align center, wrap, span 2");
//        buttonsPanel.add(formatFilter, "align center");
//        buttonsPanel.add(formatMessage, "align center, wrap");

        MigLayout ml = new MigLayout();

        JPanel panel = new JPanel(ml);
        DBLayer.checkDriver();
        if (DBLayer.driverLoaded) {

            panel.add(filterLabel, "split 4, grow");
            panel.add(formatFilter);
            panel.add(saveFilterButton, "w 18:20:22, h 18:20:22");
            panel.add(loadFilterButton, "w 18:20:22, h 18:20:22");
            panel.add(new Panel());
            panel.add(messageLabel, "split 4, grow");
            panel.add(formatMessage);
            panel.add(saveMessageButton, "w 18:20:22, h 18:20:22");
            panel.add(loadMessageButton, "w 18:20:22, h 18:20:22, wrap");

            panel.add(filter.getContainerWithLines(), "grow, width 45%, height 80%");
            panel.add(buttonsPanel);
            panel.add(message.getContainerWithLines(), "grow, width 45%, wrap");
            panel.add(logSP, "align center, grow, span 3, height 15%");
            //panel.setPreferredSize(new Dimension(1200,600));
        } else {

            JButton downButton = new JButton("Download sqllite driver");
            downButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    downForm("Download driver");
                }
            });

            buttonsPanel.add(downButton, "wrap");

            panel.add(filterLabel, "split 2, grow");
            panel.add(formatFilter);
            panel.add(new Panel());
            panel.add(messageLabel, "split 2, grow");
            panel.add(formatMessage, "wrap");
            panel.add(filter.getContainerWithLines(), "grow, width 45%, height 80%");
            panel.add(buttonsPanel);
            panel.add(message.getContainerWithLines(), "grow, width 45%, wrap");
            panel.add(logSP, "align center, grow, span 3, height 15%");
        }

        //JScrollPane jsp = new JScrollPane(panel);

        log("NOTE: Use Ctrl+MouseWheel to zoom.");

        add(panel);
        pack();
        setVisible(true);
        return true;
    }

    public void log(String msg) {

        logArea.append((new SimpleDateFormat("dd.MM HH:mm:ss:SSS")).format(new Date()) + "::" + msg + "\n");
    }

    public void setFilter(String filter) {
        this.filter.setText(filter);
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    private void saveForm(int type, String text, String title) {
        new SaveForm(this, type, text, title);
    }

    private void openForm(int type, String title) {
        new OpenForm(this, type, title);
    }

    private void downForm(String title) {
        new DownloadDriverForm(this, "http://central.maven.org/maven2/org/xerial/sqlite-jdbc/3.27.2.1/sqlite-jdbc-3.27.2.1.jar", title);
    }

}
