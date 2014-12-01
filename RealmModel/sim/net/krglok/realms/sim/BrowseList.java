package net.krglok.realms.sim;

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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import javax.swing.JLabel;

/**
 * <pre>
 * Data Browser mit Auswahl
 * die daten werden als array of object uebergegben
 * die header beschriftung wird uebergeben
 * die column datentypen werden uebergeben
 * die column Anzahl wird uebergeben als array mit der size uebergeben 
 * 
 * bedingt, dass in Column 1 ein primary key steht, als integer
 * - rueckgabe ueber static int 
 * - isSelected zeigt an, ob eine gueltige auswahl vorliegt
 * 
 * Achtung: isSelected muss sofort wieder zurueckgesetzt werden, wenn es ausgewertet wurde
 *  
 * @author Windu
 *</pre>
 */
public class BrowseList extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTextField txtCount;
	private static Object[][] dataRows; // = new String[Rows][cols] {{Row 0	"0", "1", "2","3"}, {Row 1	"1", "11", "12","13"}};
	private static String[] colHeader = new String[] {	"id"};
	private static int[] columnSize = new int[] { 35 };

	private static int selctedId = -1;
	private static boolean isSelected = false;

	/**
	 * Launch the application.
	 */
	public static void showMe(Object[][] data, String[] header, int[] columnSize, String titel)
	{
		try
		{
			// set data and table config
			dataRows = data;
			colHeader = header;

			BrowseList dialog = new BrowseList();
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
	public BrowseList()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(BrowseList.class.getResource("/net/krglok/realms/gui/_tinfo.gif")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				showData();
			}
		});
//		setTitle("Browser");
		setBounds(100, 100, 536, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			{
				JScrollPane scrollPane = new JScrollPane();
				contentPanel.add(scrollPane, BorderLayout.CENTER);
				{
					table = new JTable();
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent event) {
							doTableClick(event);
						}
					});
					table.setModel(new DefaultTableModel(
						dataRows,
						colHeader
					));

					for (int i = 1; i < columnSize.length; i++)
					{
						table.getColumnModel().addColumn(new TableColumn(0));
					}
					for (int i = 0; i < columnSize.length; i++)
					{
						table.getColumnModel().getColumn(i).setPreferredWidth(columnSize[i]);
						table.getColumnModel().getColumn(i).setMinWidth(20);
						table.getColumnModel().getColumn(i).setMaxWidth(columnSize[i]*3);
					}
					scrollPane.setViewportView(table);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Close");
				okButton.setIcon(new ImageIcon(BrowseList.class.getResource("/net/krglok/realms/gui/delete.png")));
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
					txtCount = new JTextField();
					txtCount.setEditable(false);
					txtCount.setAlignmentX(Component.LEFT_ALIGNMENT);
					txtCount.setPreferredSize(new Dimension(25, 21));
					txtCount.setMaximumSize(new Dimension(50, 21));
					buttonPane.add(txtCount);
					txtCount.setColumns(4);
					txtCount.setText(String.valueOf(table.getModel().getRowCount()));
				}
				{
					JButton btnSelect = new JButton("Select");
					btnSelect.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							doOk();
						}
					});
					btnSelect.setIcon(new ImageIcon(BrowseList.class.getResource("/net/krglok/realms/gui/check.png")));
					btnSelect.setActionCommand("OK");
					buttonPane.add(btnSelect);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	
	/**
	 * @return the selctedId
	 */
	public static int getSelctedId()
	{
		return selctedId;
	}

	/**
	 * @param selctedId the selctedId to set
	 */
	public static void setSelctedId(int selctedId)
	{
		BrowseList.selctedId = selctedId;
	}

	/**
	 * @return the isSelected
	 */
	public static boolean isSelected()
	{
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public static void setSelected(boolean isSelected)
	{
		BrowseList.isSelected = isSelected;
	}

	private void doOk()
	{
		if (table.getSelectedRow() >= 0)
		{
			selctedId = (int) dataRows[table.getSelectedRow()][0];
			isSelected = true;
			this.dispose();
		}
	}
	
	private void closeDialog()
	{
		selctedId = 0;
		isSelected = false;
		this.dispose();
	}

	private void showData()
	{
		
	}
	
	private void doTableClick(MouseEvent event)
	{
		if (event.getButton() == MouseEvent.BUTTON1)
		{
			selctedId = table.getSelectedRow();
		}
	}
	
}
