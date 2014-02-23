package net.krglok.realms.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextField;
import javax.swing.border.BevelBorder;

import net.krglok.realms.unittest.SettlementDataTest;

import java.awt.TextArea;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.image.BufferStrategy;
import java.awt.Toolkit;
import java.awt.Canvas;

public class TestMain
{

	private JFrame frame;
	private static BufferedReader reader;
	private static PipedOutputStream pOut;
	private static  TextArea textArea;
	Image myImage;
	
	private SettlementDataTest settleDataTest = new SettlementDataTest();
	
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
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(TestMain.class.getResource("/net/krglok/realms/gui/star_blue - Kopie.gif")));
		frame.setBounds(100, 100, 805, 576);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
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
		
		JToolBar toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(13, 33));
		toolBar.setMinimumSize(new Dimension(13, 33));
		toolBar.setMaximumSize(new Dimension(13, 33));
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_tdelete - Kopie.gif")));
		btnNewButton.setMaximumSize(new Dimension(33, 33));
		btnNewButton.setMinimumSize(new Dimension(32, 32));
		toolBar.add(btnNewButton);
		
		JButton button = new JButton("");
		button.setToolTipText("Start the SettlementDataTest\r\nRead Settlement from File\r\nRead Pricelist from File\r\nMake some Basisc Analysis and printout the Results");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				writeInConsole();
			}
		});
		button.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		button.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_db - Kopie.gif")));
		toolBar.add(button);
		
		
//		TextArea 
		textArea = new TextArea();
		textArea.setFont(new Font("CourierPS", Font.PLAIN, 10));
		textArea.setText("new line");
		frame.getContentPane().add(textArea, BorderLayout.WEST);
		
		final Canvas canvas = new Canvas();
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		canvas.createBufferStrategy(1);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
				 paintSome(canvas);
			}
		});
		button_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		button_1.setIcon(new ImageIcon(TestMain.class.getResource("/net/krglok/realms/gui/_db - Kopie.gif")));
		toolBar.add(button_1);
		
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
	  
	private void writeInConsole()
	{
		System.out.println("TEST ");
		settleDataTest.testReadSettledata();
	}
}
