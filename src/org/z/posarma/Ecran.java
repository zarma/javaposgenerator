package org.z.posarma;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.util.*;

class Ecran extends JPanel implements ActionListener {
	JButton buttonPaste = new JButton("Paste");
	JButton buttonProcess = new JButton("Process");
	JEditorPane textPane = new JEditorPane();
	JEditorPane textPane2 = new JEditorPane();
	Clipboard pressepapier = Toolkit.getDefaultToolkit().getSystemClipboard();
	Transferable Contenu = pressepapier.getContents(null);

	Ecran() {
		JPanel p;

		// TODO Auto-generated constructor stub
		setBackground(Color.yellow);
		setOpaque(true);
		setLayout(new BorderLayout());

		p = new JPanel();
		p.setOpaque(false);
		p.add(buttonPaste);
		p.add(textPane2);
		p.add(buttonProcess);
		p.add(textPane);

		textPane.setSize(300, 500);
		textPane2.setSize(300, 500);

		add(p);

		buttonPaste.addActionListener(this);
		buttonProcess.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == buttonPaste) {
			String text = null;
			Contenu = pressepapier.getContents(null);
			try {
				text = (String) Contenu
						.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			textPane.setText("");
			textPane2.setText(text);
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(
						"data.xml"));
				out.write("<doc>" + text + "</doc>");
				out.close();
			} catch (Exception e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

			System.out.println(e.paramString());
		}
		if (source == buttonProcess) {
			DocumentARMA doc = new DocumentARMA(parseXmlFile("data.xml", false));
			doc.printDocument(doc.getDocument().getDocumentElement());
			String text = "//\n";
			text+="// GENERATED BY ZPOSGENERATOR\n";
			text+="// =[A*C]= Z\n";
			text+="//\n\n";
			text+="if (!isServer) exitWith {};"+ "\n\n";
			for (int i = 0; i < doc.getSqftext().length; i++) {
				if (doc.getSqftext()[i] != null) {
					text += doc.getSqftext()[i] + "\n";
				}

			}
			
			text += "\n\n\n_codes = [";
			for (int i = 0; i < doc.getNbunit(); i++) {
				if (doc.getCodeblocks()[i] != null) {
					text += doc.getCodeblocks()[i] ;
					if (i!=doc.getNbunit()-1) {
						text += ",";
					}
				}
			}
			text += "];"+ "\n";
			text += "{\n";
			text += "     _nil = call _x\n";
			text += "} foreach _codes;\n";
			
			
			
			textPane.setText(text);
			System.out.println(e.paramString());
		}

	}

	// Parses an XML file and returns a DOM document.
	// If validating is true, the contents is validated against the DTD
	// specified in the file.
	public static Document parseXmlFile(String filename, boolean validating) {
		try {
			// Create a builder factory
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setValidating(validating);

			// Create the builder and parse the file
			Document doc = factory.newDocumentBuilder().parse(
					new File(filename));
			return doc;
		} catch (SAXException e) {
			// A parsing error occurred; the xml input is not valid
		} catch (ParserConfigurationException e) {
		} catch (IOException e) {
		}
		return null;
	}

}
