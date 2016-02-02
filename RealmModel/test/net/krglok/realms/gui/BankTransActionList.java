package net.krglok.realms.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.ComponentOrientation;
import javax.swing.JEditorPane;

import net.krglok.realms.core.Bank;

import java.awt.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;

/**
 * Show the internal MessageList of the bank object.
 * @author olaf.duda
 *
 */
public class BankTransActionList extends JDialog
{

	private Bank bank;
	
	private final JPanel contentPanel = new JPanel();
	private List list_transactions;

	/**
	 * Launch the application.
	 */
	public static void showMe(Bank bank)
	{
		try
		{
			BankTransActionList dialog = new BankTransActionList();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.bank = bank;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BankTransActionList()
	{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				showData();
			}
		});
		setTitle("Bank Transactions");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			list_transactions = new List();
			contentPanel.add(list_transactions);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Close");
				okButton.setIcon(new ImageIcon(BankTransActionList.class.getResource("/net/krglok/realms/gui/delete.png")));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	private void closeDialog()
	{
		this.dispose();
	}

	private void showData()
	{
//		for(String s : bank.getTransactionList().getLogList())
//		{
//			list_transactions.add(s);
//		}
	}
}
