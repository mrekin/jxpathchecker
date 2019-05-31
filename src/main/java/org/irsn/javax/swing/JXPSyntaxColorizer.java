package org.irsn.javax.swing;

import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.HashMap;

public class JXPSyntaxColorizer extends DefaultSyntaxColorizer {

    private MutableAttributeSet normal;
    private MutableAttributeSet comment;
    private MutableAttributeSet quote;
    private MutableAttributeSet operator;
    private MutableAttributeSet numbers;
    private Element rootElement;


    public JXPSyntaxColorizer(JXTextPane component, HashMap<String, Color> keywords) {
        super(component, keywords);
        this.component = component;
        this.doc = component.getStyledDocument();
        this.undo = component.getUndoableEditListener();
        this.rootElement = this.doc.getDefaultRootElement();
        this.doc.putProperty("__EndOfLine__", "\n");
        this.normal = new SimpleAttributeSet();
        StyleConstants.setForeground(this.normal, Color.black);
        this.comment = new SimpleAttributeSet();
        StyleConstants.setForeground(this.comment, Color.gray);
        StyleConstants.setItalic(this.comment, true);
        this.quote = new SimpleAttributeSet();
        StyleConstants.setForeground(this.quote, Color.green);
        this.operator = new SimpleAttributeSet();
        StyleConstants.setForeground(this.operator, Color.blue);
        this.numbers = new SimpleAttributeSet();
        StyleConstants.setForeground(this.numbers, Color.red);
        this.setKeywordColor(keywords);
    }
}
