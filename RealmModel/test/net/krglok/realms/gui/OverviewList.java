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
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.ComponentOrientation;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;

import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;

import net.krglok.realms.core.Settlement;
import net.krglok.realms.model.RealmModel;

import java.awt.Font;

public class OverviewList extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 679818721067803828L;
	private final JPanel contentPanel = new JPanel();
	public JTable table;
	public OverviewList dialog ;
	private static Object[][] dataRows; // = new String[][] {{	"0", "1", "2","3"}, {	"1", "11", "12","13"}};
	private static String[] colHeader = new String[] {	"ID", "Name", "2", "3","4"};
	private static Class[] columnTypes = new Class[] {String.class, Integer.class, Integer.class, Integer.class, String.class};
	private Class detailType;
	private Settlement settle;
	RealmModel rModel;
	

	/**
	 * Launch the application.
	 */
	public static void showMe(Object[][] data, Class[] colTypes, String[] header, String titel)
	{
		try
		{
			dataRows = data;
			colHeader = header;
			columnTypes = colTypes;

			OverviewList dialog = new OverviewList();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.setTitle(titel);
			dialog.detailType = Object.class;
			if (dialog.detailType == Object.class)
			{
				for ( Component comp : dialog.getComponents())
				{
					System.out.println(comp.getName());
//					if (comp.getName().equalsIgnoreCase("btn_Select"))
//					{
//						comp.setVisible(false);
//					}
				}
			}
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(OverviewList.class.getResource("/net/krglok/realms/gui/_tinfo.gif")));
		setTitle("DatenListe");
		setBounds(100, 100, 491, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JToolBar toolBar = new JToolBar();
			toolBar.setAlignmentY(Component.CENTER_ALIGNMENT);
			getContentPane().add(toolBar, BorderLayout.NORTH);
			{
				JButton ok_Cancel = new JButton("Close");
				ok_Cancel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				ok_Cancel.setHorizontalAlignment(SwingConstants.LEADING);
				ok_Cancel.setHorizontalTextPosition(SwingConstants.LEFT);
				toolBar.add(ok_Cancel);
				ok_Cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						OverviewList.this.dispose();
					}
				});
				ok_Cancel.setIcon(new ImageIcon(OverviewList.class.getResource("/net/krglok/realms/gui/delete.png")));
				ok_Cancel.setActionCommand("OK");
				getRootPane().setDefaultButton(ok_Cancel);
			}
			{
				JButton btn_Select = new JButton("Edit");
				btn_Select.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ShowSettlement();
						
					}
				});
				btn_Select.setVerticalAlignment(SwingConstants.BOTTOM);
				btn_Select.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
				btn_Select.setHorizontalTextPosition(SwingConstants.LEFT);
				btn_Select.setIcon(new ImageIcon(OverviewList.class.getResource("/net/krglok/realms/gui/check.png")));
				btn_Select.setToolTipText("Command sell");
				btn_Select.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				toolBar.add(btn_Select);
			}
		}
		contentPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
				contentPanel.add(scrollPane);
				table = new JTable();
				table.setFont(new Font("Courier New", Font.PLAIN, 10));
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(table);
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
			}
		}
	}

	private void ShowSettlement()
	{
			int rowIndex = table.getSelectedRow();
			if (rowIndex > 0)
			{
				int id =  Integer.valueOf(table.getModel().getValueAt(rowIndex, 0).toString());
				settle = rModel.getData().getSettlements().getSettlement(id);
				if (settle != null)
				{
					ShowSettle.showMe(settle, rModel);
				}
			}
	}
	
	public void setDetailClass(Class detailClass)
	{
		this.detailType = detailClass;
	}
}
