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
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import net.krglok.realms.tool.SettleManagerTest;
import net.krglok.realms.unittest.SettlementDataTest;

public class TestMain
{

	private JFrame frmManagetTest;
	private static BufferedReader reader;
	private static PipedOutputStream pOut;
	private static  TextArea textArea;
	Image myImage;
	
	private TestManager managerTest = new TestManager();
	
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
					TestMain window = new TestMain();
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
	public TestMain()
	{
		initialize();
	
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmManagetTest = new JFrame();
		frmManagetTest.setTitle("Managet Test");
		frmManagetTest.setIconImage(Toolkit.getDefaultToolkit().getImage(TestMain.class.getResource("/net/krglok/realms/gui/star_blue - Kopie.gif")));
		frmManagetTest.setBounds(100, 100, 1060, 662);
		frmManagetTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
				System.exit(0);
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
			}
		});
		mnSettlements.add(mntmNewhaven);
		
		JMenuItem mntmHelnrauu = new JMenuItem("Helnrau");
		mnSettlements.add(mntmHelnrauu);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("SalicStadt");
		mnSettlements.add(mntmNewMenuItem_3);
		
		JMenuItem mntmNetherhome = new JMenuItem("NetherHome");
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
				System.exit(0);
			}
		});
		btn_end.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_tdelete - Kopie.gif")));
		btn_end.setMaximumSize(new Dimension(33, 33));
		btn_end.setMinimumSize(new Dimension(32, 32));
		toolBar.add(btn_end);
		
		JButton btn_alltest = new JButton("");
		btn_alltest.setToolTipText("Start the SettlementDataTest\r\nRead Settlement from File\r\nRead Pricelist from File\r\nMake some Basisc Analysis and printout the Results");
		btn_alltest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				writeInConsole();
			}
		});
		btn_alltest.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btn_alltest.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_db - Kopie.gif")));
		toolBar.add(btn_alltest);
		
		
//		TextArea 
		textArea = new TextArea();
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 10));
		textArea.setText("new line");
		frmManagetTest.getContentPane().add(textArea, BorderLayout.WEST);
		
		JButton btn_Settle = new JButton("");
		btn_Settle.setToolTipText("Show Settle Data");
		btn_Settle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				showSettleData();
			}
		});
		btn_Settle.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btn_Settle.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_db - Kopie.gif")));
		toolBar.add(btn_Settle);
		
	}

	//The following codes set where the text get redirected. In this case, jTextArea1    
	  private static void updateTextArea(final String text) {
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
			textArea.append(text);
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
	
	private void showSettleData()
	{
		try
		{
//			ShowSettle dialog = new ShowSettle();
			ShowSettle.showMe(managerTest.rModel.getSettlements().getSettlement(1));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void writeInConsole()
	{
		managerTest.testSettleMgrModel(1);
//		settleDataTest.testReadSettledata();
	}
}
