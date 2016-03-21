package net.krglok.realms.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class MessageBrowser extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtHeader;
	private JTextArea txtrMessage;

	/**
	 * Launch the application.
	 */
	public static void showMe(String header, ArrayList<String> msg)
	{
		try
		{
			MessageBrowser dialog = new MessageBrowser();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.updateForm(header, msg);
			dialog.setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MessageBrowser()
	{
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("1dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(190dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("1dlu"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,}));
		{
			txtHeader = new JTextField();
			txtHeader.setEditable(false);
			txtHeader.setText("Header");
			contentPanel.add(txtHeader, "3, 1, fill, default");
			txtHeader.setColumns(10);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "3, 3, fill, fill");
			{
				txtrMessage = new JTextArea();
				scrollPane.setViewportView(txtrMessage);
				txtrMessage.setTabSize(4);
				txtrMessage.setText("Message");
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});
				cancelButton.setIcon(new ImageIcon(MessageBrowser.class.getResource("/net/krglok/realms/gui/error.png")));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void updateForm(String header, ArrayList<String> msg)
	{
		txtrMessage.setText("");
		for (String s : msg)
		{
			txtrMessage.append(s+'\n');
		}
		txtHeader.setText(header);
	}
	
	private void closeDialog()
	{
		this.dispose();
	}
	
}
