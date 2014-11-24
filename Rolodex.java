// 
// David Buff
// 
// Description: 
// 		A simple rolodex that reads caontact information from a text file and displays it using a JTabbedPane.
//		Also utilizes a JMenuBar.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;

class Rolodex implements ActionListener {

    JTabbedPane tabs;
    JFrame frame; //declared outside of the constructor so that showMessageDialog can access it to center on the frame.

    Rolodex() {

		frame = new JFrame("Rolodex", null);
		frame.getContentPane().setLayout(new GridLayout(1,1));
		frame.setSize(524, 180); //426x144
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		open.setEnabled(false);
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic('x');
		exit.addActionListener(this);

		JMenu tabsMenu = new JMenu("Tabs");
		tabsMenu.setMnemonic('T');

		JMenu placement = new JMenu("Placement");
		JMenuItem top = new JMenuItem("Top");
		top.addActionListener(this);
		JMenuItem right = new JMenuItem("Right");
		right.addActionListener(this);
		JMenuItem bottom = new JMenuItem("Bottom");
		bottom.addActionListener(this);
		JMenuItem left = new JMenuItem("Left");
		left.addActionListener(this);

		JMenu layout = new JMenu("Layout Policy");
		JMenuItem scroll = new JMenuItem("Scroll");
		scroll.addActionListener(this);
		JMenuItem wrap = new JMenuItem("Wrap");
		wrap.addActionListener(this);

		JMenuItem defaultMenu = new JMenuItem("Defaults");
		defaultMenu.setMnemonic('D');
		defaultMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		defaultMenu.addActionListener(this);

		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(this);

		file.add(open);
		file.add(exit);
		menu.add(file);

		placement.add(top);
		placement.add(right);
		placement.add(bottom);
		placement.add(left);
		tabsMenu.add(placement);

		layout.add(scroll);
		layout.add(wrap);
		tabsMenu.add(layout);
		tabsMenu.addSeparator();
		tabsMenu.add(defaultMenu);
		menu.add(tabsMenu);

		help.add(about);
		menu.add(help);
		
		tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		JPanel myPanel = new JPanel(new GridLayout(1, 2)); //tab panel, left side holds the picture, right side holds a panel

		ImageIcon myImage = null;
		if (new File("dfbuff.jpg").isFile()) {
			myImage = new ImageIcon("dfbuff.jpg");
		}
		else {
			myImage = new ImageIcon("nopic.jpg");
		}
		JLabel myImageLabel = new JLabel(myImage);
		myImageLabel.setHorizontalAlignment(JLabel.CENTER);
		myPanel.add(myImageLabel);

		JPanel myRightPanel = new JPanel(new GridLayout(2, 1)); 
		JPanel myTopPanel = new JPanel(new FlowLayout());
		JPanel myBottomPanel = new JPanel(new FlowLayout());

		myTopPanel.add( new JLabel("Name: "));
		myTopPanel.add(new JTextField("Buff, David", 15));
		myBottomPanel.add(new JLabel("Email: "));
		myBottomPanel.add(new JTextField("dfbuff@buffinc.com", 15));
		myRightPanel.add(myTopPanel);
		myRightPanel.add(myBottomPanel);
		myPanel.add(myRightPanel);
		tabs.addTab("Buff, David", myPanel);

		try {
			BufferedReader reader = new BufferedReader(new java.io.FileReader("contacts.txt"));
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				//I was going to have a data structure to store all the contact data, but because we aren't manipulating it there's no need.
				String[] parts = line.split("~"); //parts[0] name, parts[1] email, parts[2] image

				JPanel panel = new JPanel(new GridLayout(1, 2)); //tab panel, left side holds the picture, right side holds a panel

				ImageIcon image = null;
				if (new File(parts[2]).isFile()) {
					image = new ImageIcon(parts[2]);
				}
				else {
					image = new ImageIcon("nopic.jpg");
				}
				JLabel imageLabel = new JLabel(image);
				imageLabel.setHorizontalAlignment(JLabel.CENTER);
				panel.add(imageLabel);

				JPanel rightPanel = new JPanel(new GridLayout(2, 1)); //it makes sense to do a 4x4 but you can;t put a JTextField in a grid because it will resize
				JPanel topPanel = new JPanel(new FlowLayout());
				JPanel bottomPanel = new JPanel(new FlowLayout());

				topPanel.add( new JLabel("Name: "));
				topPanel.add(new JTextField(parts[0], 15));
				bottomPanel.add(new JLabel("Email: "));
				bottomPanel.add(new JTextField(parts[1], 15));
				rightPanel.add(topPanel);
				rightPanel.add(bottomPanel);
				panel.add(rightPanel);
				tabs.addTab(parts[0], panel);
			}
		}
		catch (IOException ie) {
			JOptionPane.showMessageDialog(null, "File not found. Make sure contacts.txt is in the same directory.", "Error!", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		frame.setJMenuBar(menu);
		frame.add(tabs);
        frame.setVisible(true);
    }

	public void actionPerformed(ActionEvent ae) {
		String comStr = ae.getActionCommand();
		switch(comStr) {
		case "Exit": System.exit(0);
			break;
		case "Top": tabs.setTabPlacement(JTabbedPane.TOP);
			break;
		case "Right": tabs.setTabPlacement(JTabbedPane.RIGHT);
			break;
		case "Bottom": tabs.setTabPlacement(JTabbedPane.BOTTOM);
			break;
		case "Left": tabs.setTabPlacement(JTabbedPane.LEFT);
			break;
		case "Scroll": tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			break;
		case "Wrap": tabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
			break;
		case "Defaults": tabs.setTabPlacement(JTabbedPane.TOP);
						tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);			
			break;
		case "About": JOptionPane.showMessageDialog(frame, "Rolodex version 0.1\n(c) 2014 Buff");
			break;
		}
	}

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Rolodex();
            }
        });

    }
}