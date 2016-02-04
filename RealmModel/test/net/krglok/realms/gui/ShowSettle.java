package net.krglok.realms.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.BoardItem;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Item;
import net.krglok.realms.core.ItemList;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.TradeOrder;
import net.krglok.realms.model.RealmModel;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JScrollPane;
import com.jgoodies.forms.layout.Sizes;

public class ShowSettle extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField text_ID;
	private JTextField text_Name;
	private JTextField text_Biome;
	private JTextField text_SettleType;
	private Settlement settle;
	private JTextField text_BedSize;
	private JTextField text_SettlerSize;
	private JTextField text_WorkerSize;
	private JTextField text_BuildingSize;
	private JTextField text_Bank;
	private JTextField text_StoreValue;
	private JTextField text_Age;
	private JTextField text_BuyOrder;
	private JTextField text_Required;
	private JTextField textSellOrder;
	private JTable table_1;
	private RealmModel rModel;
	
	/**
	 * Launch the application.
	 */
	
	public static void showMe(Settlement settle, RealmModel rModel)
	{
		try
		{
			ShowSettle dialog = new ShowSettle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.settle = settle;
			dialog.rModel = rModel;
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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				setText();
				printRequired();
			}
		});
		setTitle("Settlement");
		setBounds(100, 100, 820, 591);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("right:70px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("65px:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(41dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("104px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("86px:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("89px:grow"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("50px"),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("left:103px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.UNRELATED_GAP_ROWSPEC,
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
				new RowSpec(RowSpec.CENTER, Sizes.bounded(Sizes.DEFAULT, Sizes.constant("189dlu", false), Sizes.constant("200dlu", false)), 0),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		{
			JLabel lblNewLabel = new JLabel("ID/Name");
			contentPanel.add(lblNewLabel, "1, 2, right, center");
		}
		{
			text_ID = new JTextField();
			text_ID.setMinimumSize(new Dimension(6, 10));
			contentPanel.add(text_ID, "3, 2, left, top");
			text_ID.setColumns(10);
		}
		{
			text_Name = new JTextField();
			contentPanel.add(text_Name, "5, 2, 5, 1, fill, top");
			text_Name.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("   Biome   ");
			contentPanel.add(lblNewLabel_2, "11, 2, right, center");
		}
		{
			text_Biome = new JTextField();
			contentPanel.add(text_Biome, "13, 2, fill, top");
			text_Biome.setColumns(10);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("Type   ");
			contentPanel.add(lblNewLabel_3, "15, 2, right, center");
		}
		{
			text_SettleType = new JTextField();
			contentPanel.add(text_SettleType, "17, 2, fill, top");
			text_SettleType.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Beds : ");
			contentPanel.add(lblNewLabel_1, "1, 4, right, default");
		}
		{
			text_BedSize = new JTextField();
			text_BedSize.setMinimumSize(new Dimension(6, 10));
			text_BedSize.setColumns(10);
			contentPanel.add(text_BedSize, "3, 4, fill, default");
		}
		{
			JLabel lblSettler = new JLabel("Settler : ");
			lblSettler.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblSettler, "5, 4, right, default");
		}
		{
			text_SettlerSize = new JTextField();
			text_SettlerSize.setMinimumSize(new Dimension(6, 10));
			text_SettlerSize.setColumns(10);
			contentPanel.add(text_SettlerSize, "7, 4, fill, default");
		}
		{
			JLabel lblWorker = new JLabel("Worker : ");
			lblWorker.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblWorker, "11, 4, right, default");
		}
		{
			text_WorkerSize = new JTextField();
			text_WorkerSize.setMinimumSize(new Dimension(6, 10));
			text_WorkerSize.setColumns(10);
			contentPanel.add(text_WorkerSize, "13, 4, fill, default");
		}
		{
			JLabel lblAge = new JLabel("Age : ");
			lblAge.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(lblAge, "15, 4, right, default");
		}
		{
			text_Age = new JTextField();
			text_Age.setMinimumSize(new Dimension(6, 10));
			text_Age.setColumns(10);
			contentPanel.add(text_Age, "17, 4, fill, default");
		}
		{
			{
				JLabel lblBank = new JLabel("Bank : ");
				contentPanel.add(lblBank, "1, 6, right, default");
			}
			{
				text_Bank = new JTextField();
				text_Bank.setMinimumSize(new Dimension(6, 10));
				text_Bank.setColumns(10);
				contentPanel.add(text_Bank, "3, 6, fill, default");
			}
		}
		{
			JLabel lblStore = new JLabel("StoreValue : ");
			contentPanel.add(lblStore, "11, 6, right, default");
		}
		{
			text_StoreValue = new JTextField();
			text_StoreValue.setMinimumSize(new Dimension(6, 10));
			text_StoreValue.setColumns(10);
			contentPanel.add(text_StoreValue, "13, 6, fill, default");
		}
		{
			JButton btnNewButton = new JButton("Warehouse");
			btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
			btnNewButton.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doWarehouseList();
				}
			});
			contentPanel.add(btnNewButton, "17, 6");
		}
		{
		}
		{
			JLabel lblRequired = new JLabel("Required : ");
			contentPanel.add(lblRequired, "1, 8, right, default");
		}
		{
			text_Required = new JTextField();
			text_Required.setMinimumSize(new Dimension(6, 10));
			text_Required.setColumns(10);
			contentPanel.add(text_Required, "3, 8, fill, default");
		}
		JButton btnProduction = new JButton("Production");
		btnProduction.setHorizontalAlignment(SwingConstants.LEFT);
		btnProduction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doOverview();
			}
		});
		btnProduction.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
		contentPanel.add(btnProduction, "7, 8");
		{
			JLabel lblBuildings = new JLabel("Buildings");
			contentPanel.add(lblBuildings, "11, 8, right, default");
		}
		{
			text_BuildingSize = new JTextField();
			text_BuildingSize.setMinimumSize(new Dimension(6, 10));
			text_BuildingSize.setColumns(10);
			contentPanel.add(text_BuildingSize, "13, 8, fill, default");
		}
		{
			JButton btnBuildings = new JButton("Buildings");
			btnBuildings.setHorizontalAlignment(SwingConstants.LEFT);
			btnBuildings.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
			btnBuildings.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doBuildingList();
				}
			});
			contentPanel.add(btnBuildings, "17, 8");
		}
		{
			JLabel lblBuyorder = new JLabel("BuyOrder : ");
			contentPanel.add(lblBuyorder, "1, 10, right, default");
		}
		{
			text_BuyOrder = new JTextField();
			text_BuyOrder.setText("0");
			text_BuyOrder.setMinimumSize(new Dimension(6, 10));
			text_BuyOrder.setColumns(10);
			contentPanel.add(text_BuyOrder, "3, 10, fill, default");
		}
		JButton btnBuyorder = new JButton("BuyOrder");
		btnBuyorder.setHorizontalAlignment(SwingConstants.LEFT);
		btnBuyorder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doBuyOrderList();
			}
		});
		{
			JButton btnSellorder = new JButton("SellOrder");
			btnSellorder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					doSellOrder();
				}
			});
			btnSellorder.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
			contentPanel.add(btnSellorder, "5, 10");
		}
		btnBuyorder.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
		contentPanel.add(btnBuyorder, "7, 10");
		{
			JLabel lbl_SellOrder = new JLabel("Sell Order");
			contentPanel.add(lbl_SellOrder, "1, 12, right, default");
		}
		{
			textSellOrder = new JTextField();
			textSellOrder.setText("0");
			contentPanel.add(textSellOrder, "3, 12, fill, default");
			textSellOrder.setColumns(10);
		}
		{
			JButton buttonSellOrder = new JButton("Overstock");
			buttonSellOrder.setHorizontalAlignment(SwingConstants.LEFT);
			buttonSellOrder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//
					doOverstock();
//					doSellOrder();
				}
			});
			{
				JButton btnTransport = new JButton("DontSell");
				btnTransport.setHorizontalAlignment(SwingConstants.LEFT);
				btnTransport.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					//
						doDontSell();
					}
				});
				btnTransport.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
				contentPanel.add(btnTransport, "5, 12");
			}
			buttonSellOrder.setIcon(new ImageIcon(ShowSettle.class.getResource("/net/krglok/realms/gui/check.png")));
			contentPanel.add(buttonSellOrder, "7, 12");
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, "1, 16, 7, 1, fill, fill");
			{
				table_1 = new JTable();
				table_1.setModel(new DefaultTableModel(
						new Object[][] { },
						new String[] {"Material", "Day", "Week", "Storage"}
					));
				
				scrollPane.setViewportView(table_1);
				table_1.setFont(new Font("Courier New", Font.PLAIN, 10));

			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Cancel");
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

	private void doDontSell()
	{
//		Object[][] dataRows = new Object[settle.getTrader().getOrders().size()][4];
		ItemList dontSell = settle.settleManager().getDontSell();
		String[][] dataRows = new String[dontSell.size()][4];
		int index = 0;
		for (Item item : dontSell.values())
		{
//			if (index <100)
			{
				dataRows[index][0] = item.ItemRef();
				dataRows[index][1] = String.valueOf(item.value());
				dataRows[index][2] = String.valueOf(settle.getWarehouse().getItemList().getValue(item.ItemRef()));
				dataRows[index][3] = "";
			}
			index ++;
		}
		String[] colHeader = new String[] {	"Item", "dontSell", "Stock"," "};
		Class[] columnTypes = new Class[] {String.class, String.class, String.class, String.class};

		WarehouseList.showMe(dataRows, columnTypes, colHeader, "Overstock List");
	}
	
	private void doOverstock()
	{
//		Object[][] dataRows = new Object[settle.getTrader().getOrders().size()][4];
		ItemList overStock = settle.settleManager().getOverStock(rModel, settle);
		String[][] dataRows = new String[overStock.size()][4];
		int index = 0;
		for (Item item : overStock.values())
		{
//			if (index <100)
			{
				dataRows[index][0] = item.ItemRef();
				dataRows[index][1] = String.valueOf(item.value());
				dataRows[index][2] = String.valueOf(settle.getWarehouse().getItemList().getValue(item.ItemRef()));
				dataRows[index][3] = "";
			}
			index ++;
		}
		String[] colHeader = new String[] {	"Item", "overStock", "Stock"," "};
		Class[] columnTypes = new Class[] {String.class, String.class, String.class, String.class};

		WarehouseList.showMe(dataRows, columnTypes, colHeader, "Overstock List");
	}
	

	private void doSellOrder()
	{
//		Object[][] dataRows = new Object[settle.getTrader().getOrders().size()][4];
		rModel.getTradeMarket().getSettleOrders(settle.getId(), settle.getSettleType());
		String[][] dataRows = new String[rModel.getTradeMarket().getSettleOrders(settle.getId(), settle.getSettleType()).size()][4];
		int index = 0;
		for (TradeMarketOrder order : rModel.getTradeMarket().getSettleOrders(settle.getId(), settle.getSettleType()).values())
		{
//			if (index <100)
			{
				dataRows[index][0] = order.ItemRef();
				dataRows[index][1] = String.valueOf(order.value());
				dataRows[index][2] = String.valueOf(settle.getWarehouse().getItemList().getValue(order.ItemRef()));
				dataRows[index][3] = "";
			}
			index ++;
		}
		String[] colHeader = new String[] {	"Item", "Amount", "Stock"," "};
		Class[] columnTypes = new Class[] {String.class, String.class, String.class, String.class};

		WarehouseList.showMe(dataRows, columnTypes, colHeader, "SellOrder List");
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
		Class[] columnTypes = new Class[] {String.class, Integer.class, Double.class};

		WarehouseList.showMe(dataRows, columnTypes, colHeader, "Buy Orders");
	}
	
	
	private void doBuildingList()
	{
		Object[][] dataRows = new Object[settle.getBuildingList().size()][4];
		int index = 0;
		for (Building building : settle.getBuildingList().values())
		{
			if (BuildPlanType.getBuildGroup(building.getBuildingType())>100)
			{
				dataRows[index][0] = building.getBuildingType().name();
				dataRows[index][1] = String.valueOf(building.getSettler());
				dataRows[index][2] = building.getWorkerNeeded();
				dataRows[index][3] = building.isEnabled();
				index ++;
			}
		}
		String[] colHeader = new String[] {	"Building", "Beds", "Worker","Enabled"};
		Class[] columnTypes = new Class[] {String.class, Integer.class, Double.class};

		WarehouseList.showMe(dataRows, columnTypes, colHeader, "Building List");
	}
	
	private void doWarehouseList()
	{
		Object[][] dataRows = new Object[settle.getWarehouse().getItemList().size()][3];
		int index = 0;
		for (String itemRef : settle.getWarehouse().getItemList().sortItems())
		{
			Item item = settle.getWarehouse().getItemList().getItem(itemRef);
//			if (index <100)
			{
				dataRows[index][0] = item.ItemRef();
				dataRows[index][1] = item.value();
			}
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
		Class[] columnTypes = new Class[] {String.class, Integer.class, String.class};
		WarehouseList.showMe(dataRows, columnTypes, colHeader, "Warehouse List");
	}

	private void printRequired()
	{
		String[] colHeader = new String[] {"ID", "Material", "Amount" };
		String[][] dataRows = new String[settle.getRequiredProduction().size()][3];
		int index = 0;
		for (Item item : settle.getRequiredProduction().values())
		{
//			if (index <100)
			{
				dataRows[index][0] = ConfigBasis.setStrright(index,3);
				dataRows[index][1] = item.ItemRef();
				dataRows[index][2] = ConfigBasis.setStrright(item.value() ,6);
			}
			index ++;
		}
		table_1.setModel(new DefaultTableModel(dataRows,colHeader));
	}
	
	private void doOverview()
	{
		String[][] dataRows = new String[settle.getProductionOverview().size()][5];
		int index = 0;
		for (BoardItem item : settle.getProductionOverview().values())
		{
//			if (index <100)
			{
				dataRows[index][0] = item.getName();
				dataRows[index][1] = ConfigBasis.setStrright(item.getInputValue(),5);
				dataRows[index][2] = ConfigBasis.setStrright(item.getInputSum(),6);
				dataRows[index][3] = ConfigBasis.setStrright(item.getPeriodSum(),7);
				dataRows[index][4] = ConfigBasis.setStrright(settle.getWarehouse().getItemList().getValue(item.getName()),6);
			}
			index ++;
		}
		String[] colHeader =new String[] {"Material", "Last", "Sum", "Period", "Storage"};

		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] {String.class, Integer.class, Integer.class, Integer.class, Integer.class};
		
		

		OverviewList.showMe(dataRows, columnTypes, colHeader, "Production Overview");
		
	}
	
	private void closeDialog()
	{
		this.dispose();
	}
	
}
