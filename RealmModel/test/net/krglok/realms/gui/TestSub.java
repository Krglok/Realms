package net.krglok.realms.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Canvas;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestSub extends JFrame
{

	private JPanel contentPane;
	private Canvas canvas;
	private int scale = 2;

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
					TestSub frame = new TestSub();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestSub()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 924, 616);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		canvas = new Canvas();
		contentPane.add(canvas, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setMaximumSize(new Dimension(600, 2));
		toolBar.setMinimumSize(new Dimension(230, 2));
		toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(toolBar);
		
		JButton btnClose = new JButton("Close");
		btnClose.setIcon(new ImageIcon(TestSub.class.getResource("/net/krglok/realms/gui/delete2.png")));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 System.exit( 0 );
			}
		});
		toolBar.add(btnClose);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setIcon(new ImageIcon(TestSub.class.getResource("/net/krglok/realms/gui/folder.png")));
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showImage();
			}
		});
		toolBar.add(btnLoad);
		
		JButton btnHamletframe = new JButton("HamletFrame");
		btnHamletframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paintHamletFrame(1, 1);
			}
		});
		btnHamletframe.setIcon(new ImageIcon(TestSub.class.getResource("/net/krglok/realms/gui/achiev_1.png")));
		toolBar.add(btnHamletframe);
		
		JButton btnTownframe = new JButton("TownFrame");
		btnTownframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paintTownFrame(1,1);
			}
		});
		btnTownframe.setIcon(new ImageIcon(TestSub.class.getResource("/net/krglok/realms/gui/achiev_1.png")));
		toolBar.add(btnTownframe);
		
		JButton btnCityframe = new JButton("CityFrame");
		btnCityframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paintCityFrame(1, 1);
			}
		});
		btnCityframe.setIcon(new ImageIcon(TestSub.class.getResource("/net/krglok/realms/gui/achiev_1.png")));
		toolBar.add(btnCityframe);
		
		JButton btnMetroframe = new JButton("MetroFrame");
		btnMetroframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paintMetroFrame(1, 1);
			}
		});
		btnMetroframe.setIcon(new ImageIcon(TestSub.class.getResource("/net/krglok/realms/gui/achiev_1.png")));
		panel.add(btnMetroframe);
		
		JButton btnRef = new JButton("Ref");
		btnRef.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paintScaledFrame(1,1, 40);
				
			}
		});
		btnRef.setIcon(new ImageIcon(TestSub.class.getResource("/net/krglok/realms/gui/achiev_2.png")));
		panel.add(btnRef);
		
		
		
	}

	private void showImage()
	{
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("D:\\GIT\\OwnPlugins\\Realms\\plugins\\Realms\\buildplan\\mc_tileset.png"));
		} catch (IOException e) {
		}	
		if (img != null)
		{
			canvas.getGraphics().drawImage(img, 1, 1, null);
		}
	}

	private void paintScaledFrame(int posX, int posY, int r)
	{
		int edge = ((r *2)-1)*scale;
		canvas.getGraphics().setColor(Color.BLACK);
		canvas.getGraphics().fillRect(posX, posY, edge, edge);
		canvas.getGraphics().setColor(Color.WHITE);
//		canvas.getGraphics().fillRect(posX+1, posY+1, edge-2, edge-2);
		canvas.getGraphics().clearRect(posX+1, posY+1, edge-2, edge-2);
	}
	
	private void paintCenterFrame(int posX, int posY, int r)
	{
		int left = posX-((r-1)*scale);
		int top  = posY-((r-1)*scale);
		paintScaledFrame(left, top, r);
	}
	
	private void paintRadiusFrame(int posX, int posY, int r)
	{
		int edge = ((r *2)-1);
		canvas.getGraphics().drawRect(posX, posY, edge, edge);
	}
	
	private void paintHamletFrame(int posX, int posY)
	{
		paintCenterFrame(posX, posY, 40);
	}
	

	private void paintTownFrame(int posX, int posY)
	{
		
		paintCenterFrame(posX, posY, 70);
	}

	private void paintCityFrame(int posX, int posY)
	{
		paintCenterFrame(posX, posY, 110);

	}

	private void paintMetroFrame(int posX, int posY)
	{
		paintCenterFrame(posX, posY, 220);

	}
	
}
