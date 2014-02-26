package net.krglok.realms.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OverviewList extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private static Object[][] dataRows; // = new String[][] {{	"0", "1", "2","3"}, {	"1", "11", "12","13"}};
	private static String[] colHeader = new String[] {	"0", "1", "2", "3","4"};
	private static Class[] columnTypes = new Class[] {String.class, Integer.class, Integer.class, Integer.class, String.class};

	/**
	 * Launch the application.
	 */
	public static void showMe(Object[][] data, Class[] colTypes, String[] header)
	{
		try
		{
			dataRows = data;
			colHeader = header;
			columnTypes = colTypes;

			OverviewList dialog = new OverviewList();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OverviewList()
	{
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			table = new JTable();
			table.setModel(new DefaultTableModel(
				dataRows,
				colHeader
			));
			table.getColumnModel().getColumn(0).setPreferredWidth(175);
			table.getColumnModel().getColumn(0).setMinWidth(75);
			table.getColumnModel().getColumn(0).setMaxWidth(175);
			table.getColumnModel().getColumn(1).setMaxWidth(175);
			table.getColumnModel().getColumn(2).setMaxWidth(175);
			table.getColumnModel().getColumn(3).setMaxWidth(175);
			table.getColumnModel().getColumn(4).setPreferredWidth(15);
			table.getColumnModel().getColumn(4).setMinWidth(5);
			contentPanel.add(table);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Close");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						OverviewList.this.dispose();
					}
				});
				okButton.setIcon(new ImageIcon(OverviewList.class.getResource("/net/krglok/realms/gui/delete.png")));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
