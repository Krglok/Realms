package net.krglok.realms.sim;

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
import javax.swing.table.TableColumn;
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
import javax.swing.JTextField;
import javax.swing.JLabel;


public class ShowList extends JDialog
{
	
	
//	private Warehouse warehouse;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private static Object[][] dataRows; // = new String[][] {{	"0", "1", "2","3"}, {	"1", "11", "12","13"}};
	private static String[] colHeader = new String[] {	"0"};
	private static int[] columnSize = new int[] { 105 };
	private JTextField txtRef;

	/**
	 * Launch the application.
	 */
	public static void showMe(Object[][] data, String[] header, int[] colSize, String titel )
	{
		try
		{
			dataRows = data;
			colHeader = header;
			columnSize = colSize;
			
			ShowList dialog = new ShowList();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setModal(true);
			dialog.setVisible(true);
			dialog.setTitle(titel);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ShowList()
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
			));
//			for (int i = 1; i < columnSize.length; i++)
//			{
//				table.getColumnModel().addColumn(new TableColumn(0));
//			}
			for (int i = 0; i < columnSize.length; i++)
			{
				table.getColumnModel().getColumn(i).setPreferredWidth(columnSize[i]);
				table.getColumnModel().getColumn(i).setMinWidth(20);
				table.getColumnModel().getColumn(i).setMaxWidth(columnSize[i]*3);
				table.getColumnModel().getColumn(i).setCellEditor(null);
			}
			
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
				{
					JLabel lblCount = new JLabel("count");
					buttonPane.add(lblCount);
				}
				{
					txtRef = new JTextField();
					txtRef.setEditable(false);
					txtRef.setText(String.valueOf(table.getModel().getRowCount()));
					buttonPane.add(txtRef);
					txtRef.setColumns(5);
				}
				okButton.setIcon(new ImageIcon(ShowList.class.getResource("/net/krglok/realms/gui/delete.png")));
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
