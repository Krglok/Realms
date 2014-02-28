package net.krglok.realms.tool.BreedTest;

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
import java.util.Vector;

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
import javax.swing.event.TableModelEvent;

import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.gui.ShowSettle;
import net.krglok.realms.tool.SettleManagerTest;
import net.krglok.realms.unittest.SettlementDataTest;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
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
import javax.swing.JProgressBar;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.SoftBevelBorder;

import org.bukkit.block.Biome;

public class BreedMain
{

	private JFrame frmManagetTest;
	private static BufferedReader reader;
	private static PipedOutputStream pOut;
	private static  TextArea textArea;
	private JProgressBar progressBar;
	private Image myImage;
	private JPanel panel_1;
	private JPanel panel;
	private  Object[][] dataRows; // = initDataRow();
	private  Object[][] dataRows1; // = initDataRow();
	private String[] columnHeader = new String[] {"ID", "Sender", ">>", "Target", "Item", "amount", "price", "Status", "Count", "Max"};
	private String[] columnHeader1 = new String[] {"ID", "Sender", ">>", "Target", "Item", "amount", "price", "Status", "Count", "Max"};
	
	
	private SettlementBreedTest managerTest = new SettlementBreedTest();
	private JTable table;
	private DefaultTableModel model;  //= new DefaultTableModel(dataRows,columnHeader );
	private DefaultTableModel model1;  //= new DefaultTableModel(dataRows,columnHeader );
	private int settleId = 1;
	private JTable table_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					BreedMain window = new BreedMain();

					window.frmManagetTest.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
		        redirectSystemStreams();
		        System.out.println("THIS IS A DEMO FOR REDIRECTING OUTPUT TO GUI");
			}
		});
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
	public BreedMain()
	{
		initialize();
	
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		dataRows = initDataRow();
		dataRows1 = initDataRow();
		model = new DefaultTableModel(dataRows,columnHeader );
		model1 = new DefaultTableModel(dataRows1,columnHeader1 );

		frmManagetTest = new JFrame();
		frmManagetTest.getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyCode());
				closeDialog();
			}
		});

		frmManagetTest.addWindowListener( new AreYouSure() );

		frmManagetTest.setTitle("Settlement Breeding");
		frmManagetTest.setIconImage(Toolkit.getDefaultToolkit().getImage(BreedMain.class.getResource("/net/krglok/realms/gui/star_blue.gif")));
		frmManagetTest.setBounds(100, 100, 1226, 630);
		frmManagetTest.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		addWindowListener( new AreYouSure() );
		
		JMenuBar menuBar = new JMenuBar();
		frmManagetTest.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open");
		mntmNewMenuItem_1.setIcon(new ImageIcon(BreedMain.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Close");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Exit");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeDialog();
			}
		});
		mntmNewMenuItem_2.setIcon(new ImageIcon(BreedMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		mntmNewMenuItem_2.setSelectedIcon(new ImageIcon(BreedMain.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenu mnSettlements = new JMenu("Settlements");
		menuBar.add(mnSettlements);
		
		JMenuItem mntmNewhaven = new JMenuItem("NewHaven");
		mntmNewhaven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//show settlement Data
				showSettleData(1);
			}
		});
		mnSettlements.add(mntmNewhaven);
		
		JMenuItem mntmHelnrauu = new JMenuItem("Helnrau");
		mntmHelnrauu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showSettleData(2);
			}
		});
		mnSettlements.add(mntmHelnrauu);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("SalicStadt");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSettleData(3);
			}
		});
		mnSettlements.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNetherhome = new JMenuItem("NetherHome");
		mntmNetherhome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSettleData(4);
			}
		});
		mnSettlements.add(mntmNetherhome);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(13, 33));
		toolBar.setMinimumSize(new Dimension(13, 33));
		toolBar.setMaximumSize(new Dimension(13, 33));
		frmManagetTest.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btn_end = new JButton("");
		btn_end.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btn_end.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});
		btn_end.setIcon(new ImageIcon(BreedMain.class.getResource("/net/krglok/realms/gui/delete2.png")));
		btn_end.setMaximumSize(new Dimension(33, 33));
		btn_end.setMinimumSize(new Dimension(32, 32));
		toolBar.add(btn_end);
		
		JButton btn_Settle = new JButton("");
		btn_Settle.setToolTipText("Show Settle Data");
		btn_Settle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				showSettleData(managerTest.settle.getId());
			}
		});
		
		JButton btnLoop = new JButton(" Loop 5 ");
		btnLoop.setIcon(new ImageIcon(BreedMain.class.getResource("/net/krglok/realms/gui/_tdb.gif")));
		btnLoop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBreeding(500);
				refreshDataRow();
			}
		});
		btnLoop.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.add(btnLoop);
		
		JButton btnNewButton_1 = new JButton("Init Test");
		btnNewButton_1.setBorder(new BevelBorder(BevelBorder.RAISED, Color.RED, null, null, null));
		toolBar.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				managerTest.SettlerBreedInit(settleId);
				managerTest.dayCounter = 0;
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(BreedMain.class.getResource("/net/krglok/realms/gui/_tdb.gif")));
		
		JButton btnBreedTest = new JButton("Breed Test");
		btnBreedTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doSettleBreedTest();
			}
		});
		btnBreedTest.setIcon(new ImageIcon(BreedMain.class.getResource("/net/krglok/realms/gui/_tcheck.gif")));
		btnBreedTest.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.add(btnBreedTest);
		
		JSeparator separator = new JSeparator();
		toolBar.add(separator);
		btn_Settle.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btn_Settle.setIcon(new ImageIcon(BreedMain.class.getResource("/net/krglok/realms/gui/_tinfo.gif")));
		toolBar.add(btn_Settle);
		
		JSeparator separator_1 = new JSeparator();
		toolBar.add(separator_1);
		
		panel = new JPanel();
		frmManagetTest.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:max(214dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(53dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(41dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.GLUE_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(212dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(201dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(237dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("LoopCounter : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel, "8, 2, left, default");
		
		progressBar = new JProgressBar();
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				progressBar.setToolTipText("Count : "+progressBar.getValue());
			}
		});
		progressBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
			}
		});
		panel.add(progressBar, "10, 2, 5, 1");
		
		JButton btnNewButton = new JButton("Market");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doMarketList();
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "4, 4, 3, 1, fill, fill");
		
		table_1 = new JTable();
		table_1.setModel(model1);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(28);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(46);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(26);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(45);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(112);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(48);
		table_1.getColumnModel().getColumn(7).setPreferredWidth(58);
		table_1.getColumnModel().getColumn(8).setPreferredWidth(57);
		table_1.getColumnModel().getColumn(9).setPreferredWidth(60);
		scrollPane.setViewportView(table_1);
		btnNewButton.setIcon(new ImageIcon(BreedMain.class.getResource("/net/krglok/realms/gui/_text.gif")));
		panel.add(btnNewButton, "8, 4");
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, "10, 4, 5, 1, fill, fill");
		
		table = new JTable();
		table.setModel(model);
//		(new DefaultTableModel(
//			dataRows,
//			columnHeader
//		));
		table.getColumnModel().getColumn(0).setPreferredWidth(28);
		table.getColumnModel().getColumn(1).setPreferredWidth(46);
		table.getColumnModel().getColumn(2).setPreferredWidth(26);
		table.getColumnModel().getColumn(3).setPreferredWidth(45);
		table.getColumnModel().getColumn(4).setPreferredWidth(112);
		table.getColumnModel().getColumn(5).setPreferredWidth(48);
		table.getColumnModel().getColumn(7).setPreferredWidth(58);
		table.getColumnModel().getColumn(8).setPreferredWidth(57);
		table.getColumnModel().getColumn(9).setPreferredWidth(60);
		scrollPane_1.setViewportView(table);
		
		panel_1 = new JPanel();
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panel_1.repaint();
			}
		});
		panel_1.setBackground(Color.black);
		panel.add(panel_1, "4, 6, 10, 1, fill, fill");
		
		
//		TextArea 
		textArea = new TextArea();
		panel.add(textArea, "14, 6, fill, fill");
		textArea.addTextListener(new TextListener() {
			public void textValueChanged(TextEvent arg0) {
				setText();
			}
		});
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 10));
		textArea.setText("new line");
		
	}

	//The following codes set where the text get redirected. In this case, jTextArea1    
	private static void updateTextArea(final String text) {
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
			textArea.append(text);
	      }
	    });
	}
	
	private void doSettleBreedTest()
	{
		paintSome(panel_1);
		paintHorLine(8, (100-managerTest.settle.getResident().getSettlerMax()) , panel_1,Color.magenta);
		managerTest.dayCounter = 0;
		for (int i = 0; i < 1250; i++)
		{
			managerTest.BreedingLoop(managerTest.settle);
			progressBar.setValue(managerTest.dayCounter);
//			if ((managerTest.dayCounter % 40) == 0)
			{
				paintBalken(managerTest.settle.getResident().getSettlerCount(), (managerTest.dayCounter ), panel_1, Color.green);
				paintBalken((int)managerTest.settle.getResident().getFertilityCounter(), (managerTest.dayCounter), panel_1, Color.yellow);
				paintBalken((int)(managerTest.settle.getResident().getHappiness()*10), (managerTest.dayCounter), panel_1, Color.red);
			}
			if (progressBar.getValue() >= 1500)
			{
				progressBar.setMaximum(managerTest.dayCounter+1500);
			}
			progressBar.repaint();
		}
	}

	private void doBreeding(int days)
	{
		paintSome(panel_1);
		days = 10;
		int maxLoops = days * 40;
		paintSome(panel_1);
		paintHorLine(8, (100-managerTest.settle.getResident().getSettlerMax()) , panel_1,Color.magenta);
		progressBar.setMaximum(1500);
		progressBar.setValue(0);
		for (int i = 0; i < maxLoops; i++)
		{
			managerTest.doBreeding();
			progressBar.setValue(managerTest.dayCounter);
			if ((managerTest.dayCounter % 40) == 0)
			{
				paintBalken(managerTest.settle.getResident().getSettlerCount(), (managerTest.dayCounter / 40), panel_1, Color.green);
				paintBalken((int)managerTest.settle.getResident().getFertilityCounter(), (managerTest.dayCounter / 40), panel_1, Color.yellow);
				paintBalken((int)(managerTest.settle.getResident().getHappiness()*10), (managerTest.dayCounter / 40), panel_1, Color.red);
				refreshDataRow();
				panel.setVisible(true);
				System.out.println(".");
				textArea.repaint();
			}
			if (progressBar.getValue() >= 1500)
			{
				progressBar.setMaximum(managerTest.dayCounter+1500);
			}
			progressBar.repaint();
			
		}
	}
	  
	private void doMarketList() 
	{
		//
		refreshDataRow();
		table.repaint();
		setText();
	}
	  
	
	private void showSettleData(int settleId)
	{
		try
		{
			ShowSettle.showMe(managerTest.rModel.getSettlements().getSettlement(settleId));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private  void refreshDataRow()
	{
		 if (managerTest != null)
		 {
//			 System.out.println("=");
			 int index = 0;
			 if (managerTest.rModel.getTradeMarket().isEmpty() == false)
			 {
				 model.setRowCount(managerTest.rModel.getTradeMarket().size()+1);
				 for (TradeMarketOrder order : managerTest.rModel.getTradeMarket().values() )
				 {
//						 System.out.println(".");
					 model.setValueAt(order.getId(), index, 0);
					 model.setValueAt(order.getSettleID(), index, 1);
					 model.setValueAt(">>", index, 2);
					 model.setValueAt(order.getTargetId(), index, 3);
					 model.setValueAt(order.ItemRef(), index, 4);
					 model.setValueAt(order.value(), index, 5);
					 model.setValueAt(order.getBasePrice(), index, 6);
					 model.setValueAt(order.getStatus().name(), index, 7);
					 model.setValueAt(order.getTickCount(), index, 8);
					 model.setValueAt(order.getMaxTicks(), index, 9);
					 index++;
				 }
			 }
			 index = 0;
			 if (managerTest.rModel.getTradeTransport().isEmpty() == false)
			 {
				 model1.setRowCount(managerTest.rModel.getTradeTransport().size()+1);
				 for (TradeMarketOrder order : managerTest.rModel.getTradeTransport().values() )
				 {
//						 System.out.println(".");
					 model1.setValueAt(order.getId(), index, 0);
					 model1.setValueAt(order.getSettleID(), index, 1);
					 model1.setValueAt(">>", index, 2);
					 model1.setValueAt(order.getTargetId(), index, 3);
					 model1.setValueAt(order.ItemRef(), index, 4);
					 model1.setValueAt(order.value(), index, 5);
					 model1.setValueAt(order.getBasePrice(), index, 6);
					 model1.setValueAt(order.getStatus().name(), index, 7);
					 model1.setValueAt(order.getTickCount(), index, 8);
					 model1.setValueAt(order.getMaxTicks(), index, 9);
					 index++;
				 }
			 }
			 table.repaint();
			 table_1.repaint();
		 }
		 
	}

	
	private  Object[][] initDataRow()
	{
		 Object[][] rows = new  Object[30][10];
		 return rows;
	}
	
	
    private class AreYouSure extends WindowAdapter {  
  	  public void windowClosing( WindowEvent e ) 
  	  {  
  		closeDialog();
  	   }  
    	@Override
    	public void windowActivated(WindowEvent arg0) {
//    		setText();
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

	private void paintBalken(int len, int step, JPanel panel_1, Color color)
	{
		Graphics2D graphics = (Graphics2D) panel_1.getGraphics();
		Color lastColor = graphics.getColor();
		graphics.setColor(color);
		int x1 = 10+step;
		int y1 = 100-len;
		int x2 = 10+step;
		int y2 = y1+1; //-len+2;
		graphics.drawLine(x1, y1, x2, y2);
		graphics.setColor(lastColor);
		
	}
	
	private void drawRasterX(int x1, int y1, Graphics2D graphics, int len)
	{
		int x2 = x1 ;
		int y2 = y1 + len;
		graphics.drawLine(x1, y1, x2, y2);
		
	}
	
	private void drawRasterY(int x1, int y1, Graphics2D graphics, int len)
	{
		int x2 = x1 - len;
		int y2 = y1 ;
		graphics.drawLine(x1, y1, x2, y2);
		
	}
	
	private void paintHorLine(int x1, int y1, JPanel panel_1, Color color)
	{
		Graphics2D graphics = (Graphics2D) panel_1.getGraphics();
		Color lastColor = graphics.getColor();
		graphics.setColor(color);
		int width = graphics.getDeviceConfiguration().getBounds().width -20;
		int x2 = x1 + width;
		int y2 = y1;
		graphics.drawLine(x1, y1, x2, y2);
		graphics.setColor(lastColor);
		
	}
	
	private void paintSome(JPanel panel_1)
	{
		Graphics2D graphics = (Graphics2D) panel_1.getGraphics();
		graphics.setColor(Color.lightGray);
		int width = graphics.getDeviceConfiguration().getBounds().width -20;
		int x1 = 10;
		int y1 = 101;
		int x2 = x1 + width;
		int y2 = 101;
		graphics.drawLine(x1, y1, x2, y2);
		x1 = 10;
		y1 = 51;
		x2 = x1 + width;
		y2 = 51;
		graphics.drawLine(x1, y1, x2, y2);
		x1 = 10;
		y1 = 101;
		x2 = 10;
		y2 = 10;
		graphics.drawLine(x1, y1, x2, y2);
		
		graphics.setColor(Color.lightGray);
		drawRasterX( x1,  y1,  graphics,3);
		drawRasterX( x1+10,  y1,  graphics,3);
		drawRasterX( x1+20,  y1,  graphics,3);
		drawRasterX( x1+30,  y1,  graphics,3);
		drawRasterX( x1+40,  y1,  graphics,3);
		drawRasterX( x1+50,  y1,  graphics,5);
		drawRasterX( x1+60,  y1,  graphics,3);
		drawRasterX( x1+70,  y1,  graphics,3);
		drawRasterX( x1+80,  y1,  graphics,3);
		drawRasterX( x1+90,  y1,  graphics,3);
		drawRasterX( x1+100,  y1,  graphics,5);
		drawRasterX( x1+110,  y1,  graphics,3);
		drawRasterX( x1+120,  y1,  graphics,3);
		drawRasterX( x1+130,  y1,  graphics,3);
		drawRasterX( x1+140,  y1,  graphics,3);
		drawRasterX( x1+150,  y1,  graphics,5);

		drawRasterY( x1,  y1,  graphics,3);
		drawRasterY( x1,  y1-10,  graphics,3);
		drawRasterY( x1,  y1-20,  graphics,3);
		drawRasterY( x1,  y1-30,  graphics,3);
		drawRasterY( x1,  y1-40,  graphics,3);
		drawRasterY( x1,  y1-50,  graphics,5);
		drawRasterY( x1,  y1-60,  graphics,3);
		drawRasterY( x1,  y1-70,  graphics,3);
		drawRasterY( x1,  y1-80,  graphics,3);
		drawRasterY( x1,  y1-90,  graphics,3);
		drawRasterY( x1,  y1-100,  graphics,35);
	}
	
	private void setText()
	{
		paintSome(panel_1);
	}
}
