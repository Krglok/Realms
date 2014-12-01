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

import net.krglok.realms.core.TradeMarketOrder;
import net.krglok.realms.tool.SettleManagerTest;
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

public class TestMain
{

	private JFrame frmManagetTest;
	private static BufferedReader reader;
	private static PipedOutputStream pOut;
	private static  TextArea textArea;
	Image myImage;
	private  Object[][] dataRows = initDataRow();
	private String[] columnHeader = new String[] {"ID", "Sender", ">>", "Target", "Item", "amount", "price", "Status", "Count", "Max"};
	
	
	private TestManager managerTest = new TestManager();
	private JTextField text_Loops;
	private JTable table;
	
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
		initialize();
	
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
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open");
		mntmNewMenuItem_1.setIcon(new ImageIcon(TestMain.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		mnNewMenu.add(mntmNewMenuItem_1);
		
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
		btn_end.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/delete2.png")));
		btn_end.setMaximumSize(new Dimension(33, 33));
		btn_end.setMinimumSize(new Dimension(32, 32));
		toolBar.add(btn_end);
		
		JButton btn_Settle = new JButton("");
		btn_Settle.setToolTipText("Show Settle Data");
		btn_Settle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				showSettleData(1);
			}
		});
		
		JButton btn_Sell = new JButton("Sell   ");
		btn_Sell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				managerTest.doSellWheat(1);
			}
		});
		btn_Sell.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btn_Sell.setToolTipText("Command sell");
		btn_Sell.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_tcheck.gif")));
		toolBar.add(btn_Sell);
		
		JButton btnLoop = new JButton("Loop 35  ");
		btnLoop.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_tdb.gif")));
		btnLoop.addActionListener(
				new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						managerTest.doLoop35(1);
					}
				}
		);
		btnLoop.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.add(btnLoop);
		
		JSeparator separator = new JSeparator();
		toolBar.add(separator);
		btn_Settle.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btn_Settle.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_tinfo.gif")));
		toolBar.add(btn_Settle);
		
		JSeparator separator_1 = new JSeparator();
		toolBar.add(separator_1);
		
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
				FormFactory.GLUE_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
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
		
		JButton btnNewButton_1 = new JButton("Init Test SettleManager");
		btnNewButton_1.addActionListener(
				new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						managerTest.testSettleMgrModel(1);
						int l = e.getActionCommand().length();
						System.out.println("Action "+e.getActionCommand()+":"+l);
					}
				}
		);
		btnNewButton_1.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_tdb.gif")));
		panel.add(btnNewButton_1, "2, 2");
		
		JLabel lblNewLabel = new JLabel("LoopCounter : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel, "6, 2, left, default");
		
		text_Loops = new JTextField();
		text_Loops.setToolTipText("Running Loops");
		panel.add(text_Loops, "8, 2, left, default");
		text_Loops.setColumns(10);
		
		
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
		textArea.setText("new line");
		
		JButton btnNewButton = new JButton("Market");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doMarketList();
			}
		});
		btnNewButton.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_text.gif")));
		panel.add(btnNewButton, "6, 4");
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, "12, 4, fill, fill");
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			dataRows,
			columnHeader
		));
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
		
	}

	//The following codes set where the text get redirected. In this case, jTextArea1    
	  private static void updateTextArea(final String text) {
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
			textArea.append(text);
	      }
	    });
	  }

	private void doMarketList() 
	{
		//
		refreshDataRow();
		table.repaint();
	}
	  
	private void paintSome(Canvas canvas)
	{
		Graphics2D graphics = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
		graphics.setColor(Color.red);
		graphics.fillRect(10, 10, 110, 110);
		canvas.getBufferStrategy().show();
		
	}
	
	private void showSettleData(int settleId)
	{
		try
		{
//			ShowSettle dialog = new ShowSettle();
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
			 int index = 0;
			 if (managerTest.rModel.getTradeMarket().isEmpty() == false)
			 {
				 for (TradeMarketOrder order : managerTest.rModel.getTradeMarket().values() )
				 {
					 if (index < 200)
					 {
						 System.out.println(".");
						 dataRows[index][0] = order.getId();
						 dataRows[index][1] = order.getSettleID();
						 dataRows[index][2] = ">>";
						 dataRows[index][3] = order.getTargetId();
						 dataRows[index][4] = order.ItemRef();
						 dataRows[index][5] = order.value();
						 dataRows[index][6] = order.getBasePrice();
						 dataRows[index][7] = order.getStatus().name();
						 dataRows[index][8] = order.getTickCount();
						 dataRows[index][9] = order.getMaxTicks();
					 }
				 }
			 }
		 }
		 
	}

	
	private  Object[][] initDataRow()
	{
		 Object[][] rows = new  Object[200][10];
		 if (managerTest != null)
		 {
//			 int index = 0;
//			 if (managerTest.rModel.getTradeMarket().isEmpty() == false)
//			 {
//				 for (TradeMarketOrder order : managerTest.rModel.getTradeMarket().values() )
//				 {
//					 if (index < 200)
//					 {
//						 rows[index][0] = order.getId();
//						 rows[index][1] = order.getSettleID();
//						 rows[index][2] = ">>";
//						 rows[index][3] = order.getTargetId();
//						 rows[index][4] = order.ItemRef();
//						 rows[index][5] = order.value();
//						 rows[index][6] = order.getBasePrice();
//						 rows[index][7] = order.getStatus().name();
//						 rows[index][8] = order.getTickCount();
//						 rows[index][9] = order.getMaxTicks();
//					 }
//				 }
//			 }
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
		text_Loops.setText(String.valueOf(managerTest.dayCounter));
	}
}
