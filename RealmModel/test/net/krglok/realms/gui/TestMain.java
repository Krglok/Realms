package net.krglok.realms.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.core.Owner;
import net.krglok.realms.data.ConfigData;
import net.krglok.realms.data.DataCalendar;
import net.krglok.realms.data.DataStorage;
import net.krglok.realms.kingdom.Lehen;
import net.krglok.realms.npc.NpcData;
import net.krglok.realms.science.Achivement;
import net.krglok.realms.tool.SettleManagerTest;
import net.krglok.realms.unit.Regiment;
import net.krglok.realms.unittest.ConfigTest;
import net.krglok.realms.unittest.SettlementDataTest;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.TextListener;
import java.awt.event.TextEvent;
import javax.swing.JSeparator;

import org.bukkit.configuration.file.FileConfiguration;
import java.awt.FlowLayout;
import javax.swing.JProgressBar;

/**
 * <pre>
 * GUI for Testrunning of RealmModel.
 * The GUI show many persistant plugin data shwon as List/Detail Forms 
 * as JDialog.
 * The Testrun (Loop) do OnTick for a Gameday (1200 OnTick) and a single
 * OnProduce Tick.
 * After the loop is ended the CacheLoop runs until all Data in chache are
 * write to the SQL-Database.  
 *  
 * 
 * @author olaf.duda
 *
 *</pre>
 */

public class TestMain
{

	String dataFolder  = "\\GIT\\OwnPlugins\\Realms\\plugins\\Realms"; 
	
	private JFrame frmManagetTest;
	private static BufferedReader reader;
	private static PipedOutputStream pOut;
	private static  TextArea textArea;
	Image myImage;
	private  String[][] dataRows = initDataRow();
	private String[] columnHeader = new String[] {"0", "1", "2", "3", "4","5"};
	private DataCalendar calendar;
	
	private TestManager managerTest; // = new TestManager(dataFolder);
	private JTextField text_Loops;
	private JTable table;
	private JProgressBar progressBar;
	private Runnable runProgress;
	private JTextField txtListtitel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{

		EventQueue.invokeLater(
		new Runnable()
		{
			public void run()
			{
				try
				{
					TestMain window = new TestMain();

					window.frmManagetTest.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
//		        redirectSystemStreams();
		        System.out.println("THIS IS A DEMO FOR REDIRECTING OUTPUT TO GUI");
			}
		}
		);
	}

	
	//Followings are The Methods that do the Redirect, you can simply Ignore them. 
	  private static void redirectSystemStreams() {
	    OutputStream out = new OutputStream() {
	      @Override
	      public void write(int b) throws IOException {
	        updateTextArea(String.valueOf((char) b));
	      }
	 
	      @Override
	      public void write(byte[] b, int off, int len) throws IOException {
	        updateTextArea(new String(b, off, len));
	      }
	 
	      @Override
	      public void write(byte[] b) throws IOException {
	        write(b, 0, b.length);
	      }
	    };
	 
	    System.setOut(new PrintStream(out, true));
	    System.setErr(new PrintStream(out, true));
	  }
	
	  
	/**
	 * Create the application.
	 */
	public TestMain()
	{
		managerTest = new TestManager(dataFolder);
		initialize();
		calendar = new DataCalendar(managerTest.data.getSettlements().getSettlement(1).getAge());
		text_Loops.setText(calendar.getCalendarDateGer());
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		dataRows = initDataRow();
		frmManagetTest = new JFrame();
		frmManagetTest.getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyCode());
				closeDialog();
			}
		});

		frmManagetTest.addWindowListener( new AreYouSure() );

		frmManagetTest.setTitle("Managet Test");
		frmManagetTest.setIconImage(Toolkit.getDefaultToolkit().getImage(TestMain.class.getResource("/net/krglok/realms/gui/star_blue.gif")));
		frmManagetTest.setBounds(100, 100, 1051, 662);
		frmManagetTest.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		addWindowListener( new AreYouSure() );
		
		JMenuBar menuBar = new JMenuBar();
		frmManagetTest.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Close");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Exit");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeDialog();
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(TestMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		mntmNewMenuItem_2.setSelectedIcon(new ImageIcon(TestMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenu mnSettlements = new JMenu("DataStorage");
		menuBar.add(mnSettlements);
		
		JMenuItem mntmNewhaven = new JMenuItem("Settlement");
		mntmNewhaven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//show settlement Data
				showSettleData();
			}
		});
		mnSettlements.add(mntmNewhaven);
		
		JMenuItem mntmHelnrauu = new JMenuItem("Lehen");
		mntmHelnrauu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showLehenData();
			}
		});
		mnSettlements.add(mntmHelnrauu);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Owner");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				showSettleData(3);
				showOwnerData();
			}
		});
		mnSettlements.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNetherhome = new JMenuItem("Kingdom");
		mntmNetherhome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				showSettleData(4);
			}
		});
		mnSettlements.add(mntmNetherhome);
		
		JMenuItem mntmRegimnter = new JMenuItem("Regimenter");
		mntmRegimnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				showRegimentList(table);

			}
		});
		mnSettlements.add(mntmRegimnter);
		
		JMenu mnNewMenu_1 = new JMenu("New menu");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Buildings");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				showBuildingList();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("NPC");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				showNPCList();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("PriceList");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				showPriceList(table);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_5);
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frmManagetTest.getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setRollover(true);
		panel_1.add(toolBar);
		toolBar.setMinimumSize(new Dimension(13, 33));
		toolBar.setMaximumSize(new Dimension(13, 33));
		
		JButton btn_Settle1 = new JButton("");
		btn_Settle1.setToolTipText("Show the TestSettlement : "+managerTest.data.getSettlements().getSettlement(4).getName());
		btn_Settle1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settlement settle = managerTest.data.getSettlements().getSettlement(4);
				textArea.setText("");
				for (int i = 0; i < settle.getMsg().size(); i++)
				{
					textArea.append(settle.getMsg().get(i)+"\n");
				}
				ShowSettle.showMe(settle,managerTest.rModel);
				//	showSettleData();
			}
		});
		
		JButton btn_Sell = new JButton("Init Model  ");
		btn_Sell.setMaximumSize(new Dimension(0, 37));
		btn_Sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				managerTest.rModel. OnEnable();
			}
		});
		
		JButton btn_end = new JButton("Ende  ");
		toolBar.add(btn_end);
		btn_end.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btn_end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});
		btn_end.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/delete2.png")));
		btn_end.setMaximumSize(new Dimension(0, 37));
		btn_end.setMinimumSize(new Dimension(45, 32));
		btn_Sell.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btn_Sell.setToolTipText("Command sell");
		btn_Sell.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_tcheck.gif")));
		toolBar.add(btn_Sell);
		
		JButton btnLoop = new JButton("doLoop  ");
		btnLoop.setMaximumSize(new Dimension(0, 37));
		btnLoop.setMinimumSize(new Dimension(73, 31));
		btnLoop.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_tdb.gif")));
		btnLoop.addActionListener(
				new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						text_Loops.setText("Running");
						text_Loops.paint(text_Loops.getGraphics());
						managerTest.doDayLoop(managerTest.rModel, progressBar);
						calendar.stepDay();
						text_Loops.setText(calendar.getCalendarDateGer());
						text_Loops.paint(text_Loops.getGraphics());
					}
				}
		);
		btnLoop.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.add(btnLoop);
		
		JSeparator separator = new JSeparator();
		toolBar.add(separator);
		btn_Settle1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btn_Settle1.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/resources/settle_1.png")));
		toolBar.add(btn_Settle1);
		
		JButton btn_Settle2 = new JButton("");
		btn_Settle2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btn_Settle2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				Settlement settle = managerTest.data.getSettlements().getSettlement(9);
				textArea.setText("");
				for (int i = 0; i < settle.getMsg().size(); i++)
				{
					textArea.append(settle.getMsg().get(i)+"\n");
				}
				
				ShowSettle.showMe(settle,managerTest.rModel);
			}
		});
		btn_Settle2.setToolTipText("Show the TestSettlement : "+managerTest.data.getSettlements().getSettlement(9).getName());
		btn_Settle2.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/resources/settle_1.png")));
		toolBar.add(btn_Settle2);
		
		JButton btnLehen = new JButton("");
		btnLehen.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnLehen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				Lehen lehen = managerTest.data.getLehen().getLehen(2);
				textArea.setText("");
				for (int i = 0; i < lehen.getMsg().size(); i++)
				{
					textArea.append(lehen.getMsg().get(i)+"\n");
				}
				ShowLehen.showMe(lehen,managerTest.data);
			}
		});
		btnLehen.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/resources/lehen_1.png")));
		btnLehen.setHorizontalAlignment(SwingConstants.LEFT);
		btnLehen.setToolTipText("Lehen [2]");
		toolBar.add(btnLehen);
		
		JSeparator separator_1 = new JSeparator();
		toolBar.add(separator_1);
		
		JButton btnTransport = new JButton("");
		btnTransport.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnTransport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				showTransportData();
			}
		});
		btnTransport.setToolTipText("TransportList");
		toolBar.add(btnTransport);
		btnTransport.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/resources/trader_1.png")));
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				showMarketData();
			}
		});
		btnNewButton.setToolTipText("Show Marketlist");
		btnNewButton.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/resources/trader_1.png")));
		toolBar.add(btnNewButton);
		
		JPanel panel = new JPanel();
		frmManagetTest.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(106dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(111dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(41dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("62px:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(25dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(289dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		progressBar = new JProgressBar();
		panel.add(progressBar, "2, 2, 3, 1");
		

		JLabel lblNewLabel = new JLabel("LoopCounter : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel, "6, 2, left, default");
		
		text_Loops = new JTextField();
		text_Loops.setToolTipText("Running Loops");
		panel.add(text_Loops, "8, 2, left, default");
		text_Loops.setColumns(10);
		
		txtListtitel = new JTextField();
		txtListtitel.setHorizontalAlignment(SwingConstants.LEFT);
		txtListtitel.setText("ListTitel");
		panel.add(txtListtitel, "12, 2, fill, default");
		txtListtitel.setColumns(10);
		
		
//		TextArea 
		textArea = new TextArea();
		panel.add(textArea, "2, 4, 4, 1, fill, fill");
		textArea.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent arg0) {
				setText();
			}
		});
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 10));
		textArea.setText("new line\r\nzeile 2\r\nZeil 3");
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, "12, 4, fill, fill");
		
		table = new JTable();
		table.setFont(new Font("Courier New", Font.PLAIN, 9));
		table.setModel(new DefaultTableModel(
			dataRows,
			columnHeader
		));
		scrollPane_1.setViewportView(table);
		
	}

	//The following codes set where the text get redirected. In this case, jTextArea1    
	  private static void updateTextArea(final String text) {
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
//			textArea.append(text);
	      }
	    });
	  }
	  
	private void paintSome(Canvas canvas)
	{
		Graphics2D graphics = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
		graphics.setColor(Color.red);
		graphics.fillRect(10, 10, 110, 110);
		canvas.getBufferStrategy().show();
		
	}


	private void showMarketData()
	{
		try
		{
			txtListtitel.setText("Trade Market");
			columnHeader = new String[] {"ID", "Von", "Nach", "Material", "Amount","Price"};
			int maxRow = managerTest.rModel.getTradeMarket().size();
			if (maxRow > 0)
			{
				dataRows = new  String[maxRow][columnHeader.length];
				int row = 0;
				for (Integer orderId : managerTest.rModel.getTradeMarket().sortInteger())
				{
					TradeMarketOrder order = managerTest.rModel.getTradeMarket().getOrder(orderId);
					if ((order.getSettleID() == 9) || (order.getSettleID() == 4)
						|| (order.getTargetId()== 9) || (order.getTargetId()== 4)
							)
					{
						dataRows[row][0]= ConfigBasis.setStrright(order.getId(),3);
						dataRows[row][1]= ConfigBasis.setStrright(order.getSettleID(),3); 
						dataRows[row][2]= ConfigBasis.setStrright(order.getTargetId() ,3); 
						dataRows[row][3]= order.ItemRef();
						dataRows[row][4]= ConfigBasis.setStrright(order.value(),5); 
						dataRows[row][5]= ConfigBasis.setStrformat2(order.getBasePrice(),9); 
						row++;
					}
				}
				table.setModel(new DefaultTableModel(dataRows,columnHeader));
				table.getColumnModel().getColumn(0).setPreferredWidth(10);
				table.getColumnModel().getColumn(1).setPreferredWidth(20);
				table.getColumnModel().getColumn(2).setPreferredWidth(20);
				table.setFont(new Font("Courier New", Font.PLAIN, 10));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void showTransportData()
	{
		try
		{
			
			columnHeader = new String[] {"ID", "Von", "Nach", "Material", "Price","Tick"};
			int maxRow = managerTest.rModel.getTradeTransport().size();
			if (maxRow > 0)
			{	
				dataRows = new  String[maxRow][6];
	//			table = new JTable();
				int row = 0;
				for ( TradeMarketOrder order : managerTest.rModel.getTradeTransport().values())
				{
					dataRows[row][0]= ConfigBasis.setStrright(order.getId(),3);
					dataRows[row][1]= ConfigBasis.setStrright(order.getSettleID(),3); 
					dataRows[row][2]= ConfigBasis.setStrright(order.getTargetId() ,3); 
					dataRows[row][3]= order.ItemRef();
					dataRows[row][4]= ConfigBasis.setStrright(order.value(),3); 
					dataRows[row][5]= ConfigBasis.setStrformat2(order.getBasePrice(),7); 
					row++;
				}
				DefaultTableModel tableModel = new DefaultTableModel(dataRows,columnHeader);
				table.setModel(tableModel);
				table.getColumnModel().getColumn(0).setPreferredWidth(10);
				table.getColumnModel().getColumn(1).setPreferredWidth(20);
				table.getColumnModel().getColumn(2).setPreferredWidth(20);
				table.setFont(new Font("Courier New", Font.PLAIN, 10));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void showSettleData()
	{
		
		try
		{
			
			columnHeader = new String[] {"ID", "Name", "Type", "Bank", "Betten","Siedler"};
			int maxRow = managerTest.data.getSettlements().size();
			dataRows = new  String[maxRow][columnHeader.length];
//			Class[] colTypes = new Class[] { String.class, String.class, String.class, String.class, String.class,, String.class };
			OverviewList dialog = new OverviewList();
			dialog.table.setModel(new DefaultTableModel(
				dataRows,
				columnHeader
			));
			dialog.table.getColumnModel().getColumn(0).setPreferredWidth(10);
			dialog.table.getColumnModel().getColumn(1).setPreferredWidth(100);
			dialog.table.getColumnModel().getColumn(2).setPreferredWidth(30);
			dialog.table.getColumnModel().getColumn(3).setPreferredWidth(45);
//			table.getColumnModel().getColumn(3).getCellRenderer().
			dialog.table.getColumnModel().getColumn(4).setPreferredWidth(45);
			dialog.table.getColumnModel().getColumn(5).setPreferredWidth(45);
			int row = 0;
			for (Integer id : managerTest.data.getSettlements().sortIntegerList(managerTest.data.getSettlements().keySet()))
			{
				Settlement settle = managerTest.data.getSettlements().getSettlement(id);
				dialog.table.getModel().setValueAt(String.valueOf(settle.getId()), row, 0);
				dialog.table.getModel().setValueAt(settle.getName(),row,1);; 
				dialog.table.getModel().setValueAt(settle.getSettleType().name(),row,2);
				dialog.table.getModel().setValueAt(ConfigBasis.setStrformat2(settle.getBank().getKonto(),11),row,3);
				dialog.table.getModel().setValueAt(ConfigBasis.setStrright(settle.getResident().getSettlerMax(),4),row,4); 
				dialog.table.getModel().setValueAt(ConfigBasis.setStrright(settle.getResident().getSettlerCount(),4),row,5);
//				table.getModel().setValueAt(ConfigBasis.setStrright(settle.getBuildingList().size(),3),row,6); 
				row++;
			}
			dialog.setDetailClass(Settlement.class);
			dialog.rModel = managerTest.rModel;
			dialog.setVisible(true);

			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void showLehenData()
	{
		try
		{
			
			columnHeader = new String[] {"ID", "Name", "Type", "Bank", "Betten","Siedler"};
			int maxRow = managerTest.data.getLehen().size();
			dataRows = new  String[maxRow][columnHeader.length];
//			table = new JTable();
			table.setModel(new DefaultTableModel(
				dataRows,
				columnHeader
			));
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(30);
			table.getColumnModel().getColumn(3).setPreferredWidth(45);
//			table.getColumnModel().getColumn(3).getCellRenderer().
			table.getColumnModel().getColumn(4).setPreferredWidth(45);
			table.getColumnModel().getColumn(5).setPreferredWidth(45);
			int row = 0;
			for (Lehen settle : managerTest.data.getLehen().values())
			{
				table.getModel().setValueAt(ConfigBasis.setStrright(settle.getId(),3), row, 0);
				table.getModel().setValueAt(settle.getName(),row,1);; 
				table.getModel().setValueAt(settle.getSettleType().name(),row,2);
				table.getModel().setValueAt(ConfigBasis.setStrformat2(settle.getBank().getKonto(),11),row,3);
				table.getModel().setValueAt(ConfigBasis.setStrright(settle.getResident().getSettlerMax(),4),row,4); 
				table.getModel().setValueAt(ConfigBasis.setStrright(settle.getResident().getSettlerCount(),4),row,5);
//				table.getModel().setValueAt(ConfigBasis.setStrright(settle.getBuildingList().size(),3),row,6); 
				row++;
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void showOwnerData()
	{
		try
		{
			
			columnHeader = new String[] {"ID", "Name", "Type", "Bank", "NobleLevel"," "};
			int maxRow = managerTest.data.getOwners().size();
			dataRows = new  String[maxRow][columnHeader.length];
//			table = new JTable();
			table.setModel(new DefaultTableModel(
				dataRows,
				columnHeader
			));
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(30);
			table.getColumnModel().getColumn(3).setPreferredWidth(45);
			table.getColumnModel().getColumn(4).setPreferredWidth(45);
			table.getColumnModel().getColumn(5).setPreferredWidth(45);
			int row = 0;
			for (Owner owner : managerTest.data.getOwners().values())
			{
				table.getModel().setValueAt(ConfigBasis.setStrright(owner.getId(),3), row, 0);
				table.getModel().setValueAt(owner.getPlayerName() ,row,1);; 
				table.getModel().setValueAt(owner.getIsNPC().toString(),row,2);
				table.getModel().setValueAt(ConfigBasis.setStrformat2(owner.getBank(),11),row,3);
				table.getModel().setValueAt(ConfigBasis.setStrleft(owner.getNobleLevel().name(),8) ,row,4);
				int lastAchiv = owner.getAchivList().size();
				for (Achivement item :  owner.getAchivList().values())
				{
					table.getModel().setValueAt(item.getName() ,row,5);
				}
				row++;
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void showBuildingList()
	{
		try
		{
			
			columnHeader = new String[] {"ID", "TYPE", "Region", "Settler", "Worker","Settle","Lehen"};
			int maxRow = managerTest.data.getBuildings().size();
			System.out.println("Buildings ["+maxRow+"]");
			dataRows = new  String[maxRow][columnHeader.length];
//			table = new JTable();
			table.setModel(new DefaultTableModel(
				dataRows,
				columnHeader
			));
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
			table.getColumnModel().getColumn(1).setPreferredWidth(60);
			table.getColumnModel().getColumn(2).setPreferredWidth(35);
			table.getColumnModel().getColumn(3).setPreferredWidth(35);
			table.getColumnModel().getColumn(4).setPreferredWidth(35);
			table.getColumnModel().getColumn(5).setPreferredWidth(35);
			table.getColumnModel().getColumn(6).setPreferredWidth(35);
			int row = 0;
			for (Integer index: managerTest.data.getBuildings().sortIntegerList(managerTest.data.getBuildings().keySet()))
			{
				Building building = managerTest.data.getBuildings().getBuilding(index);
				table.getModel().setValueAt(ConfigBasis.setStrright(building.getId(),3), row, 0);
				table.getModel().setValueAt(building.getBuildingType().name() ,row,1);; 
				table.getModel().setValueAt(ConfigBasis.setStrright(building.getHsRegion(),4),row,2);
				table.getModel().setValueAt(ConfigBasis.setStrright(building.getSettler(),2),row,3);
				table.getModel().setValueAt(ConfigBasis.setStrright(building.getWorkerNeeded() ,2) ,row,4);
				table.getModel().setValueAt(ConfigBasis.setStrright(building.getSettleId() ,3) ,row,5);
				table.getModel().setValueAt(ConfigBasis.setStrright(building.getLehenId() ,3) ,row,6);
				row++;
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void showNPCList()
	{
		try
		{
			
			columnHeader = new String[] {"ID", "Name", "NPCType", "UnitType","Settle","Lehen", "Money"};
			int maxRow = managerTest.data.getNpcs().size();
			System.out.println("NPC ["+maxRow+"]");
			dataRows = new  String[maxRow][columnHeader.length];
//			table = new JTable();
			table.setModel(new DefaultTableModel(
				dataRows,
				columnHeader
			));
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
			table.getColumnModel().getColumn(1).setPreferredWidth(60);
			table.getColumnModel().getColumn(2).setPreferredWidth(35);
			table.getColumnModel().getColumn(3).setPreferredWidth(35);
			table.getColumnModel().getColumn(4).setPreferredWidth(35);
			table.getColumnModel().getColumn(5).setPreferredWidth(35);
			table.getColumnModel().getColumn(6).setPreferredWidth(35);
			int row = 0;
			for (Integer index: managerTest.data.getNpcs().sortIntegerList(managerTest.data.getNpcs().keySet()))
			{
				NpcData npc = managerTest.data.getNpcs().get(index);
				table.getModel().setValueAt(ConfigBasis.setStrright(npc.getId(),5), row, 0);
				table.getModel().setValueAt(npc.getName() ,row,1);; 
				table.getModel().setValueAt(npc.getNpcType().name() ,row,2);; 
				table.getModel().setValueAt(npc.getUnitType().name() ,row,3);; 
				table.getModel().setValueAt(ConfigBasis.setStrright(npc.getSettleId() ,4),row,4);
				table.getModel().setValueAt(ConfigBasis.setStrright(npc.getLehenId() ,2),row,5);
				table.getModel().setValueAt(ConfigBasis.setStrformat2(npc.getMoney() ,9) ,row,6);
				row++;
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void showRegimentList(JTable  table)
	{
		try
		{
			
			columnHeader = new String[] {"ID", "Name", "Type", "Status","Barrack","Units", "Bank"};
			int maxRow = managerTest.data.getRegiments().size();
			System.out.println("NPC ["+maxRow+"]");
			dataRows = new  String[maxRow][columnHeader.length];
//			table = new JTable();
			table.setModel(new DefaultTableModel(
				dataRows,
				columnHeader
			));
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
			table.getColumnModel().getColumn(1).setPreferredWidth(60);
			table.getColumnModel().getColumn(2).setPreferredWidth(35);
			table.getColumnModel().getColumn(3).setPreferredWidth(35);
			table.getColumnModel().getColumn(4).setPreferredWidth(35);
			table.getColumnModel().getColumn(5).setPreferredWidth(35);
			table.getColumnModel().getColumn(6).setPreferredWidth(35);
			int row = 0;
			for (Integer index: managerTest.data.getRegiments().sortIntegerList(managerTest.data.getRegiments().keySet()))
			{
				Regiment regiment = managerTest.data.getRegiments().getRegiment(index);
				table.getModel().setValueAt(ConfigBasis.setStrright(regiment.getId(),5), row, 0);
				table.getModel().setValueAt(regiment.getName() ,row,1);; 
				table.getModel().setValueAt(regiment.getRegimentType().name() ,row,2);; 
				table.getModel().setValueAt(regiment.getRegStatus().name()  ,row,3);; 
				table.getModel().setValueAt(ConfigBasis.setStrright(regiment.getBarrack().getUnitMax() ,4),row,4);
				table.getModel().setValueAt(ConfigBasis.setStrright(regiment.getBarrack().getUnitList().size() ,4),row,5);
				table.getModel().setValueAt(ConfigBasis.setStrformat2(regiment.getBank().getKonto() ,9) ,row,6);
				row++;
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void showPriceList(JTable  table)
	{
		try
		{
			
			columnHeader = new String[] {"Material", "Price", "Group"};
			int maxRow = managerTest.data.getPriceList().size();
			System.out.println("NPC ["+maxRow+"]");
			dataRows = new  String[maxRow][columnHeader.length];
//			table = new JTable();
			table.setModel(new DefaultTableModel(
				dataRows,
				columnHeader
			));
			table.getColumnModel().getColumn(0).setPreferredWidth(60);
			table.getColumnModel().getColumn(1).setPreferredWidth(35);
			table.getColumnModel().getColumn(2).setPreferredWidth(35);
			int row = 0;
			for (String itemRef: managerTest.data.getPriceList().sortItems())
			{
				double price = managerTest.data.getPriceList().getBasePrice(itemRef);
				table.getModel().setValueAt(itemRef, row, 0);
				table.getModel().setValueAt(ConfigBasis.setStrformat2(price ,9) ,row,1);
				row++;
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private  String[][] initDataRow()
	{
		 String[][] rows = new  String[1][5];
		 if (managerTest != null)
		 {
		 }
		 return rows;
	}
	
	
    private class AreYouSure extends WindowAdapter {  
  	  public void windowClosing( WindowEvent e ) 
  	  {  
  		closeDialog();
//	            int option = JOptionPane.showOptionDialog(  
//	            		frmManagetTest,  
//	                    "Are you sure you want to quit?",  
//	                    "Exit Dialog", JOptionPane.YES_NO_OPTION,  
//	                    JOptionPane.WARNING_MESSAGE, null, null,  
//	                    null );  
//	            if( option == JOptionPane.YES_OPTION ) {  
//	                System.exit( 0 );  
//	            }  
  	   }  
    	@Override
    	public void windowActivated(WindowEvent arg0) {
    		setText();
    	}
  }  

	private void closeDialog()
	{
        int option = JOptionPane.showOptionDialog(  
        		frmManagetTest,  
                "Are you sure you want to quit?",  
                "Exit Dialog", JOptionPane.YES_NO_OPTION,  
                JOptionPane.WARNING_MESSAGE, null, null,  
                null );  
        if( option == JOptionPane.YES_OPTION ) {  
//    		this.dispose();
            System.exit( 0 );  
        }  
	}

	private void setText()
	{
		text_Loops.setText(calendar.getCalendarDateGer());
	}

	
	
}
