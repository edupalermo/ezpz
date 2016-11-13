package org.palermo.ezpz.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PromptWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextArea history = new JTextArea(5,20);
	private JTextField textField = new JTextField(20);
	
	public PromptWindow() {
		super();
		
		this.setTitle("Prompt Window");
		
		history.setEditable(false);
		
		JScrollPane areaScrollPane = new JScrollPane(history);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 250));
		
		this.add(areaScrollPane, BorderLayout.CENTER);
		
		this.add(textField, BorderLayout.PAGE_END);
		
		this.setBounds(10, 10, 400, 250);
		
		textField.addActionListener(new InternalActionListener());
	}
	
	private class InternalActionListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			history.append(textField.getText());
			history.append("\n");
			
			textField.setText("");
		}
		
	}
	
	

}
