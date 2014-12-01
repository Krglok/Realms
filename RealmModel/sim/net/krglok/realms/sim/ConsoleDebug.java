package net.krglok.realms.sim;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class ConsoleDebug
{
	private SimpleAttributeSet keyWord = new SimpleAttributeSet();
	private JTextPane txtField;
	private StyledDocument doc;
	
	public ConsoleDebug(JTextPane txtField)
	{
		this.txtField = txtField;
		doc = txtField.getStyledDocument();
		this.txtField.setText("New Session\n");
	}
	
	public void debug(String[] msg)
	{
		// nothing to print
		if (msg == null) {return; }
		if (msg.length == 0) {return; }
		try
		{
			for (int i = 0; i < msg.length-1; i++)
			{
				doc.insertString(doc.getLength(),"[DEBUG] "+ msg[i]+"\n", keyWord);
			}
			doc.insertString(doc.getLength(), "[DEBUG] "+ msg[msg.length-1]+"\n", keyWord);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void debug(String msg)
	{
		// nothing to print
		if (msg == null) {return; }
		if (msg.length() == 0) {return; }
		try
		{
			doc.insertString(doc.getLength(), msg +"\n", keyWord);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}
	
	public void info(String[] msg)
	{
		// nothing to print
		if (msg == null) {return; }
		if (msg.length == 0) {return; }
		try
		{
			for (int i = 0; i < msg.length-1; i++)
			{
				doc.insertString(doc.getLength(),msg[i]+"\n", keyWord);
			}
			doc.insertString(doc.getLength(), msg[msg.length-1]+"\n", keyWord);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void info(String msg)
	{
		// nothing to print
		if (msg == null) {return; }
		if (msg.length() == 0) {return; }
		try
		{
			doc.insertString(doc.getLength(), msg +"\n", keyWord);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void error(String[] msg)
	{
		// nothing to print
		if (msg == null) {return; }
		if (msg.length == 0) {return; }
		try
		{
			for (int i = 0; i < msg.length-1; i++)
			{
				doc.insertString(doc.getLength(),msg[i]+"\n", keyWord);
			}
			doc.insertString(doc.getLength(), msg[msg.length-1]+"\n", keyWord);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void error(String msg)
	{
		// nothing to print
		if (msg == null) {return; }
		if (msg.length() == 0) {return; }
		try
		{
			doc.insertString(doc.getLength(), msg +"\n", keyWord);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	public void println(String msg)
	{
		// nothing to print
		if (msg == null) {return; }
		if (msg.length() == 0) {return; }
		try
		{
			doc.insertString(doc.getLength(), msg +"\n", keyWord);
		} catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}
	
}
