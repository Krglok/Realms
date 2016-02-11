package net.krglok.realms.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.model.RealmModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import com.jgoodies.forms.layout.Sizes;

public class ShowBuilding extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtPosition;
	private JTextField txtRadius;
	private JTextField txtMoney;
	private JTextField txtHsRegion;
	private JTextField txtList;
	private JTable table;
	
	private Building building;
	private JTextField txtSettle;
	private JTextField txtLehenid;
	private JTextField txtSale;
	
	private RealmModel rModel;

	/**
	 * Launch the application.
	 */
	public static void showMe(Building building, RealmModel rModel)
	{
		try
		{
			ShowBuilding dialog = new ShowBuilding();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.building = building;
			dialog.rModel = rModel;
			dialog.updateForm();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ShowBuilding()
	{
		setMinimumSize(new Dimension(800, 300));
		setTitle("Building");
		setBounds(100, 100, 653, 333);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("43dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:94dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(43dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				new ColumnSpec(ColumnSpec.LEFT, Sizes.bounded(Sizes.MINIMUM, Sizes.constant("70dlu", true), Sizes.constant("100dlu", true)), 0),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("123dlu"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(7dlu;default):grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			JLabel lblId = new JLabel("Id");
			contentPanel.add(lblId, "1, 2, right, default");
		}
		{
			txtId = new JTextField();
			txtId.setText("Id");
			contentPanel.add(txtId, "3, 2, left, default");
			txtId.setColumns(10);
		}
		{
			JLabel lblRequired = new JLabel("Required");
			contentPanel.add(lblRequired, "7, 2, right, default");
		}
		{
			JButton btnRequired = new JButton("Required");
			btnRequired.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//
					showRequired();
				}
			});
			contentPanel.add(btnRequired, "9, 2, fill, default");
		}
		{
			txtList = new JTextField();
			txtList.setText("List");
			contentPanel.add(txtList, "11, 2, fill, default");
			txtList.setColumns(10);
		}
		{
			JLabel lblName = new JLabel("Type");
			contentPanel.add(lblName, "1, 4, right, default");
		}
		{
			txtName = new JTextField();
			txtName.setText("Name");
			contentPanel.add(txtName, "3, 4, fill, default");
			txtName.setColumns(10);
		}
		{
			JLabel lblReagents = new JLabel("Reagents");
			contentPanel.add(lblReagents, "7, 4, right, default");
		}
		{
			JButton btnReagents = new JButton("Reagents");
			btnReagents.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//
					showReagents();
				}
			});
			contentPanel.add(btnReagents, "9, 4, fill, default");
		}
		{
			table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null},
				},
				new String[] {
					"Material", "Value"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, Integer.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
			table.getColumnModel().getColumn(0).setPreferredWidth(150);
			table.getColumnModel().getColumn(0).setMinWidth(150);
			table.getColumnModel().getColumn(1).setPreferredWidth(40);
			table.getColumnModel().getColumn(1).setMinWidth(40);
			contentPanel.add(table, "11, 4, 1, 14, fill, fill");
		}
		{
			JLabel lblPosition = new JLabel("Position");
			contentPanel.add(lblPosition, "1, 6, right, default");
		}
		{
			txtPosition = new JTextField();
			txtPosition.setMinimumSize(new Dimension(50, 20));
			txtPosition.setText("Position");
			contentPanel.add(txtPosition, "3, 6, 4, 1, fill, default");
			txtPosition.setColumns(10);
		}
		{
			JLabel lblUpkeep = new JLabel("Upkeep");
			contentPanel.add(lblUpkeep, "7, 6, right, default");
		}
		{
			JButton btnUpkeep = new JButton("Upkeep");
			btnUpkeep.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showUpkeep();
				}
			});
			contentPanel.add(btnUpkeep, "9, 6, fill, default");
		}
		{
			JLabel lblRadius = new JLabel("Radius");
			contentPanel.add(lblRadius, "1, 8, right, default");
		}
		{
			txtRadius = new JTextField();
			txtRadius.setText("Radius");
			contentPanel.add(txtRadius, "3, 8, left, default");
			txtRadius.setColumns(10);
		}
		{
			JLabel lblOutput = new JLabel("Output");
			contentPanel.add(lblOutput, "7, 8, right, default");
		}
		{
			JButton btnOutput = new JButton("Output");
			btnOutput.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showOutput();
				}
			});
			contentPanel.add(btnOutput, "9, 8, fill, default");
		}
		{
			JLabel lblHsRegion = new JLabel("HsRegion");
			contentPanel.add(lblHsRegion, "1, 10, right, default");
		}
		{
			txtHsRegion = new JTextField();
			txtHsRegion.setText("HsRegion");
			contentPanel.add(txtHsRegion, "3, 10, left, default");
			txtHsRegion.setColumns(10);
		}
		{
			JLabel lblMoney = new JLabel("Money");
			contentPanel.add(lblMoney, "7, 10, right, default");
		}
		{
			txtMoney = new JTextField();
			txtMoney.setText("Money");
			contentPanel.add(txtMoney, "9, 10, left, default");
			txtMoney.setColumns(10);
		}
		{
			JLabel lblSettle = new JLabel("SettleId");
			contentPanel.add(lblSettle, "1, 12, right, default");
		}
		{
			txtSettle = new JTextField();
			txtSettle.setText("0");
			contentPanel.add(txtSettle, "3, 12, left, default");
			txtSettle.setColumns(10);
		}
		{
			JLabel lblLehen = new JLabel("Lehen");
			contentPanel.add(lblLehen, "1, 14, right, default");
		}
		{
			txtLehenid = new JTextField();
			txtLehenid.setText("0");
			contentPanel.add(txtLehenid, "3, 14, left, default");
			txtLehenid.setColumns(10);
		}
		{
			JLabel lblSale = new JLabel("Sale");
			contentPanel.add(lblSale, "1, 16, right, default");
		}
		{
			txtSale = new JTextField();
			txtSale.setText("0.00");
			contentPanel.add(txtSale, "3, 16, left, default");
			txtSale.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ac_Ende();
					}
				});
				okButton.setIcon(new ImageIcon(ShowBuilding.class.getResource("/net/krglok/realms/gui/check.png")));
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ac_Ende();
					}
				});
				cancelButton.setIcon(new ImageIcon(ShowBuilding.class.getResource("/net/krglok/realms/gui/delete.png")));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void ac_Ende()
	{
		this.dispose();
	}
	
	private void updateForm()
	{
		txtId.setText(String.valueOf(building.getId()));
		txtName.setText(building.getBuildingType().name());
		txtPosition.setText(LocationData.toString(building.getPosition()));
		txtHsRegion.setText(String.valueOf(building.getHsRegion()));
		txtLehenid.setText(String.valueOf(building.getLehenId()));
		txtSale.setText(ConfigBasis.setStrformat2(building.getSales(), 9));
	}
	
	private void showRequired()
	{
	}

	private void showReagents()
	{
		try
		{
			String[] columnHeader = new String[] {"Material","Amount"};
			ItemList reagents = rModel.getServer().getRegionReagents(building.getBuildingType().name());
			Object[][] dataRows = new Object[reagents.size()][2];
			int row = 0;
			for (Item item : reagents.values())
			{
				dataRows[row][0] = item.ItemRef();
				dataRows[row][1] = item.value();
				row++;
			}
			table.setModel(new DefaultTableModel(
					dataRows,
					columnHeader
				));
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void showOutput()
	{
		try
		{
			String[] columnHeader = new String[] {"Material","Amount"};
			ItemList reagents = rModel.getServer().getRegionOutput(building.getBuildingType().name());
			Object[][] dataRows = new Object[reagents.size()][2];
			int row = 0;
			for (Item item : reagents.values())
			{
				dataRows[row][0] = item.ItemRef();
				dataRows[row][1] = item.value();
				row++;
			}
			table.setModel(new DefaultTableModel(
					dataRows,
					columnHeader
				));
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void showUpkeep()
	{
		try
		{
			String[] columnHeader = new String[] {"Material","Amount"};
			ItemList reagents = rModel.getServer().getRegionUpkeep(building.getBuildingType().name());
			Object[][] dataRows = new Object[reagents.size()][2];
			int row = 0;
			for (Item item : reagents.values())
			{
				dataRows[row][0] = item.ItemRef();
				dataRows[row][1] = item.value();
				row++;
			}
			table.setModel(new DefaultTableModel(
					dataRows,
					columnHeader
				));
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
