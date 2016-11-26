package org.palermo.ezpz.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import org.palermo.ezpz.console.Console;
import org.palermo.ezpz.shell.command.FixClearCommand;
import org.palermo.ezpz.shell.command.FixCommand;
import org.palermo.ezpz.shell.command.ImageDeleteCommand;
import org.palermo.ezpz.shell.command.ImageListCommand;
import org.palermo.ezpz.shell.command.ImageLoadCommand;
import org.palermo.ezpz.shell.command.ImageRenameCommand;
import org.palermo.ezpz.shell.command.ImageSaveCommand;
import org.palermo.ezpz.shell.command.RegionDeleteCommand;
import org.palermo.ezpz.shell.command.RegionListCommand;
import org.palermo.ezpz.shell.command.RegionSaveCommand;
import org.palermo.ezpz.shell.command.ScreenSaveCommand;
import org.palermo.ezpz.shell.command.interfaces.Command;

public class PromptWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextArea history = new JTextArea(5, 20);
	private JTextField textField = new JTextField(20);

	private Console console = new Console();

	private static ArrayList<Command> commandList = new ArrayList<Command>();

	static {
		commandList.add(new ImageDeleteCommand());
		commandList.add(new ImageListCommand());
		commandList.add(new ImageLoadCommand());
		commandList.add(new ImageSaveCommand());
		commandList.add(new ImageRenameCommand());
		
		commandList.add(new ScreenSaveCommand());
		
		commandList.add(new RegionSaveCommand());
		commandList.add(new RegionListCommand());
		commandList.add(new RegionDeleteCommand());
		
		commandList.add(new FixCommand());
		commandList.add(new FixClearCommand());
	}

	public PromptWindow() {
		super();

		this.setTitle("Prompt Window");

		Font font = new Font("Courier", Font.PLAIN, 12);

		history.setEditable(false);
		history.setFont(font);
		
		DefaultCaret caret = (DefaultCaret)history.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane areaScrollPane = new JScrollPane(history);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		areaScrollPane.setPreferredSize(new Dimension(250, 250));

		this.add(areaScrollPane, BorderLayout.CENTER);

		textField.setFont(font);
		this.add(textField, BorderLayout.PAGE_END);

		this.setBounds(10, 10, 400, 250);

		textField.addActionListener(new InternalActionListener());
		
		history.append("Interactive Shell\n");
		history.append("=========== =====\n\n");
	}

	private class InternalActionListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			String stringCommand = textField.getText();
			
			console.info("# %s ", stringCommand);

			boolean knowCommand = false;
			
			for (Command command : commandList) {
				if (command.executed(stringCommand)) {
					knowCommand = true;
					break;
				}
			}

			if (!knowCommand) {
				console.error("Unknow command [%s]!", stringCommand);
			}

			textField.setText("");
		}
	}
	
	public void log(String text) {
		history.append(text);
		
		
	}

}
