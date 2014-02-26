package net.krglok.realms.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import net.krglok.realms.core.Item;
import net.krglok.realms.core.Warehouse;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.JScrollBar;
import java.awt.ScrollPane;
import java.awt.Scrollbar;


public class WarehouseList extends JDialog
{
	
	
//	private Warehouse warehouse;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private static String[][] dataRows;
	private String[] colHeader = new String[] {	"Items", "Amount", "Value"};
		
	/**
	 * Launch the application.
	 */
	public static void showMe(String[][] data)
	{
		try
		{
			dataRows = data;
			WarehouseList dialog = new WarehouseList();
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
	public WarehouseList()
	{
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				showData();
			}
		});
		setBounds(100, 100, 403, 453);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setAutoscrolls(true);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setName("items");
			table.setModel(new DefaultTableModel(
				dataRows,
				colHeader
			) {
				Class[] columnTypes = new Class[] {
					String.class, Integer.class, Double.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(175);
			table.getColumnModel().getColumn(0).setMinWidth(35);
			table.getColumnModel().getColumn(0).setMaxWidth(250);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(2).setPreferredWidth(105);
			table.getColumnModel().getColumn(2).setMaxWidth(175);
			contentPanel.add(table, BorderLayout.CENTER);
		}
		{
			JScrollPane scrollPane = new JScrollPane(table);
			contentPanel.add(scrollPane, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Close");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});
				okButton.setIcon(new ImageIcon(WarehouseList.class.getResource("/net/krglok/realms/gui/_tcheck - Kopie.gif")));
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
		
	}
}
