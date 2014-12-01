package net.krglok.realms.sim;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeOrder;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import java.awt.Toolkit;

public class ShowSettle extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField text_ID;
	private JTextField text_Name;
	private JTextField text_Biome;
	private JTextField text_SettleType;
	private JTextField text_BedSize;
	private JTextField text_SettlerSize;
	private JTextField text_WorkerSize;
	private JTextField text_BuildingSize;
	private JTextField text_Bank;
	private JTextField text_StoreValue;
	private JTextField text_Age;
	private JTextField text_BuyOrder;
	private JTextField text_Required;

	
	private Settlement settle;
	private JTextField txtPosx;
	private JTextField txtPosy;
	private JTextField txtPosz;
	private JTextField txtWolrd;

	
	/**
	 * Launch the application.
	 */
	
	public static void showMe(Settlement settle)
	{
		try
		{
			ShowSettle dialog = new ShowSettle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.settle = settle;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the dialog.
	 */
	public ShowSettle()
	{
		setMinimumSize(new Dimension(790, 220));
		setIconImage(Toolkit.getDefaultToolkit().getImage(ShowSettle.class.getResource("/net/krglok/realms/resources/settle_1ss.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				setText();
			}
		});
		setTitle("Settlement");
		setBounds(100, 100, 836, 305);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:70px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("74px:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(41dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("90px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(5dlu;default)"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("62px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("108px:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("44px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("left:120px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("7dlu:grow"),},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			JLabel lblNewLabel = new JLabel("ID/Name");
			contentPanel.add(lblNewLabel, "1, 1, right, center");
		}
		{
			text_ID = new JTextField();
			text_ID.setMinimumSize(new Dimension(6, 10));
			contentPanel.add(text_ID, "3, 1, left, top");
			text_ID.setColumns(10);
		}
		{
			text_Name = new JTextField();
			contentPanel.add(text_Name, "5, 1, fill, top");
			text_Name.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("   Biome   ");
			contentPanel.add(lblNewLabel_2, "11, 1, right, center");
		}
		{
			text_Biome = new JTextField();
			contentPanel.add(text_Biome, "13, 1, fill, top");
			text_Biome.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Type   ");
			contentPanel.add(lblNewLabel_3, "15, 1, right, center");
		}
		{
			text_SettleType = new JTextField();
			contentPanel.add(text_SettleType, "17, 1, fill, top");
			text_SettleType.setColumns(10);
		}
		{
			JLabel lblPosition = new JLabel("Position");
			contentPanel.add(lblPosition, "1, 3, right, default");
		}
		{
			txtPosx = new JTextField();
			txtPosx.setText("");
			contentPanel.add(txtPosx, "3, 3, fill, default");
			txtPosx.setColumns(10);
		}
		{
			txtPosy = new JTextField();
			txtPosy.setText("");
			contentPanel.add(txtPosy, "5, 3, fill, default");
			txtPosy.setColumns(10);
		}
		{
			txtPosz = new JTextField();
			txtPosz.setText("");
			contentPanel.add(txtPosz, "7, 3, fill, default");
			txtPosz.setColumns(10);
		}
		{
			JLabel lblWorld = new JLabel("World");
			contentPanel.add(lblWorld, "11, 3, right, default");
		}
		{
			txtWolrd = new JTextField();
			txtWolrd.setText("");
			contentPanel.add(txtWolrd, "13, 3, fill, default");
			txtWolrd.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Beds : ");
			contentPanel.add(lblNewLabel_1, "1, 5, right, default");
		}
		{
			text_BedSize = new JTextField();
			text_BedSize.setMinimumSize(new Dimension(6, 10));
			text_BedSize.setColumns(10);
			contentPanel.add(text_BedSize, "3, 5, fill, default");
		}
		{
			JLabel lblSettler = new JLabel("Settler : ");
			lblSettler.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblSettler, "5, 5, right, default");
		}
		{
			text_SettlerSize = new JTextField();
			text_SettlerSize.setMinimumSize(new Dimension(6, 10));
			text_SettlerSize.setColumns(10);
			contentPanel.add(text_SettlerSize, "7, 5, fill, default");
		}
		{
			JLabel lblWorker = new JLabel("Worker : ");
			lblWorker.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblWorker, "11, 5, right, default");
		}
		{
			text_WorkerSize = new JTextField();
			text_WorkerSize.setMinimumSize(new Dimension(6, 10));
			text_WorkerSize.setColumns(10);
			contentPanel.add(text_WorkerSize, "13, 5, fill, default");
		}
		{
			JLabel lblAge = new JLabel("Age : ");
			lblAge.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblAge, "15, 5, right, default");
		}
		{
			text_Age = new JTextField();
			text_Age.setMinimumSize(new Dimension(6, 10));
			text_Age.setColumns(10);
			contentPanel.add(text_Age, "17, 5, fill, default");
		}
		{
			JLabel lblBank = new JLabel("Bank : ");
			contentPanel.add(lblBank, "1, 7, right, default");
		}
		{
			text_Bank = new JTextField();
			text_Bank.setMinimumSize(new Dimension(6, 10));
			text_Bank.setColumns(10);
			contentPanel.add(text_Bank, "3, 7, fill, default");
		}
		{
			JButton btnTransaction = new JButton("Transaction");
			btnTransaction.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
			btnTransaction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doBankTransaction();
					
				}
			});
			contentPanel.add(btnTransaction, "5, 7, left, default");
		}
		{
			JLabel lblStore = new JLabel("StoreValue : ");
			contentPanel.add(lblStore, "11, 7, right, default");
		}
		{
			text_StoreValue = new JTextField();
			text_StoreValue.setMinimumSize(new Dimension(6, 10));
			text_StoreValue.setColumns(10);
			contentPanel.add(text_StoreValue, "13, 7, fill, default");
		}
		{
			JButton btnNewButton = new JButton("Warehouse");
			btnNewButton.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doWarehouseList();
				}
			});
			contentPanel.add(btnNewButton, "17, 7, left, fill");
		}
		{
			JButton btnProduction = new JButton("Production");
			btnProduction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doOverview();
				}
			});
			{
				JLabel lblRequired = new JLabel("Required : ");
				contentPanel.add(lblRequired, "1, 9, right, default");
			}
			{
				text_Required = new JTextField();
				text_Required.setMinimumSize(new Dimension(6, 10));
				text_Required.setColumns(10);
				contentPanel.add(text_Required, "3, 9, fill, default");
			}
			btnProduction.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
			contentPanel.add(btnProduction, "5, 9, left, default");
		}
		{
			JLabel lblBuildings = new JLabel("Buildings");
			contentPanel.add(lblBuildings, "11, 9, right, default");
		}
		{
			text_BuildingSize = new JTextField();
			text_BuildingSize.setMinimumSize(new Dimension(6, 10));
			text_BuildingSize.setColumns(10);
			contentPanel.add(text_BuildingSize, "13, 9, fill, default");
		}
		{
			JButton btnBuildings = new JButton("Buildings");
			btnBuildings.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
			btnBuildings.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doBuildingList();
				}
			});
			contentPanel.add(btnBuildings, "17, 9, left, default");
		}
		{
			JButton btnBuyorder = new JButton("BuyOrder");
			btnBuyorder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					doBuyOrderList();
				}
			});
			{
				JLabel lblBuyorder = new JLabel("BuyOrder : ");
				contentPanel.add(lblBuyorder, "1, 10, right, default");
			}
			{
				text_BuyOrder = new JTextField();
				text_BuyOrder.setMinimumSize(new Dimension(6, 10));
				text_BuyOrder.setColumns(10);
				contentPanel.add(text_BuyOrder, "3, 10, fill, default");
			}
			btnBuyorder.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
			contentPanel.add(btnBuyorder, "5, 10, left, default");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Close");
				okButton.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/delete.png")));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						closeDialog();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	private void setText()
	{
		text_ID.setText(String.valueOf(settle.getId()));
		text_Name.setText(settle.getName());
		text_Biome.setText(settle.getBiome().name());
		text_SettleType.setText(settle.getSettleType().name());
		txtPosx.setText(String.valueOf(Math.round(settle.getPosition().getX())));
		txtPosy.setText(String.valueOf(Math.round(settle.getPosition().getY())));
		txtPosz.setText(String.valueOf(Math.round(settle.getPosition().getZ())));
		txtWolrd.setText(String.valueOf(settle.getPosition().getWorld()));

		text_BedSize.setText(String.valueOf(settle.getResident().getSettlerMax()));
		text_SettlerSize.setText(String.valueOf(settle.getResident().getSettlerCount()));
		text_WorkerSize.setText(String.valueOf(settle.getTownhall().getWorkerCount()));
		text_BuildingSize.setText(String.valueOf(settle.getBuildingList().size()));
		text_Bank.setText(String.valueOf((int)settle.getBank().getKonto()));
		text_Age.setText(String.valueOf(settle.getAge()));
		text_StoreValue.setText(String.valueOf(settle.getWarehouse().getItemCount()));
		text_BuyOrder.setText(String.valueOf(settle.getTrader().getBuyOrders().size()));
		text_Required.setText(String.valueOf(settle.getRequiredProduction().size()));
		
	}
	
	
	private void doBuyOrderList()
	{
		Object[][] dataRows = new Object[settle.getTrader().getBuyOrders().size()][4];
		int index = 0;
		for (TradeOrder order : settle.getTrader().getBuyOrders().values())
		{
//			if (index <100)
			{
				dataRows[index][0] = order.ItemRef();
				dataRows[index][1] = order.value();
				dataRows[index][2] = ConfigBasis.format2(order.getBasePrice());
				dataRows[index][3] = order.getStatus().name();
			}
			index ++;
		}
		String[] colHeader = new String[] {	"Item", "amount", "price","Status"};
		int[] colSize = new int[] {150,45,45,45 };

		ShowList.showMe(dataRows,  colHeader, colSize, "Buy Orders");
	}
	
	
	private void doBuildingList()
	{
		Object[][] dataRows = new Object[settle.getBuildingList().getBuildingList().size()][4];
		int index = 0;
		for (Building building : settle.getBuildingList().getBuildingList().values())
		{
//			if (index <100)
			{
				dataRows[index][0] = building.getId();
				dataRows[index][1] = building.getBuildingType().name();
				dataRows[index][2] = String.valueOf(building.getSettler());
				dataRows[index][3] = building.getWorkerNeeded();
//				dataRows[index] = new Object[] {building.getBuildingType().name(), building.getSettler(), "0.0"};
			}
			index ++;
		}
		String[] colHeader = new String[] {	"id", "Building", "Beds", "Worker"};
		int[] colSize = new int[] {45,105,45,45 };

		BrowseList.showMe(dataRows, colHeader, colSize,  "Building List");
		if (BrowseList.isSelected())
		{
			BrowseList.setSelected(false);
			int id = BrowseList.getSelctedId();
			Building building = settle.getBuildingList().getBuildingList().get(String.valueOf(id));
			ShowBuilding.main(building);
		}
	}
	
	private void doWarehouseList()
	{
		Object[][] dataRows = new Object[settle.getWarehouse().getItemList().size()][3];
		int index = 0;
		
		for (String ref : settle.getWarehouse().sortItems())
		{
			Item item = settle.getWarehouse().getItemList().getItem(ref);
			dataRows[index][0] = item.ItemRef();
			dataRows[index][1] = item.value();
			dataRows[index][2] = "";
			index ++;
		}
		for (Item item : settle.getRequiredProduction().values())
		{
			for (int i = 0; i < dataRows.length; i++)
			{
				if (item.ItemRef().equalsIgnoreCase(String.valueOf(dataRows[i][0])))
				{
					dataRows[i][2] = item.value();
				}
			}
		}
		String[] colHeader = new String[] {	"Items", "Amount","Required"};		
		@SuppressWarnings("rawtypes")
		int[] colSize = new int[] {150,45,45 };
		ShowList.showMe(dataRows, colHeader, colSize, "Warehouse List");
	}

	private void doOverview()
	{
		Object[][] dataRows = new Object[settle.getProductionOverview().size()][5];
		int index = 0;
		for (BoardItem item : settle.getProductionOverview().values())
		{
//			if (index <100)
			{
				dataRows[index][0] = item.getName();
				dataRows[index][1] = (int)item.getLastValue();
				dataRows[index][2] = (int)item.getCycleSum();
				dataRows[index][3] = (int)item.getPeriodSum();
				dataRows[index][4] = "";
			}
			index ++;
		}
		String[] colHeader = new String[] {	"Items", "Last","Month","Year"," "};		
		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] {String.class, Integer.class, Integer.class, Integer.class, String.class};
		ShowOverviewList.showMe(dataRows, columnTypes, colHeader, "Production Overview");
		
	}
	
	private void doBankTransaction()
	{
//		BrowseList.showMe(settle.getBank());
	}
	
	private void closeDialog()
	{
		this.dispose();
	}
	
}
