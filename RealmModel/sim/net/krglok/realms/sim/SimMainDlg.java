package net.krglok.realms.sim;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import javax.swing.JSeparator;
import javax.swing.border.CompoundBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import java.awt.Component;
import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JSplitPane;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.krglok.realms.builder.BuildPlanType;
import net.krglok.realms.core.ItemPrice;
import net.krglok.realms.core.SettleType;
import net.krglok.realms.core.Settlement;
import net.krglok.realms.core.SettlementList;
import net.krglok.realms.data.ConfigData;
import net.krglok.realms.data.LogList;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.simdata.ConfigSim;

import java.awt.Button;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;

public class SimMainDlg
{

	private JFrame frmRealmsSimulation;

	private JScrollPane scrollPane; // = new JScrollPane();
	private JTextPane txtpnConsole = new JTextPane();
	private ConfigSim configSim ; 
	private ConfigData configData;
	private ConsoleDebug console; // = new ConsoleDebug(txtpnConsole);
	private JTextField txtStatus;
	private JProgressBar progressBar;
	private SimServer simServer;
	private SimMessage simMessage;
	private SimTask simTask;
	private LogList logList;
	private RealmModel realmModel;
	
	
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
					SimMainDlg window = new SimMainDlg();
					window.frmRealmsSimulation.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimMainDlg()
	{
		initialize();
		startModel();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmRealmsSimulation = new JFrame();
		frmRealmsSimulation.setMaximumSize(new Dimension(1640, 1024));
		frmRealmsSimulation.setMinimumSize(new Dimension(970, 340));
		frmRealmsSimulation.setTitle("Realms Simultion");
		frmRealmsSimulation.setIconImage(Toolkit.getDefaultToolkit().getImage(SimMainDlg.class.getResource("/net/krglok/realms/resources/server-icon.png")));
		frmRealmsSimulation.setBounds(100, 100, 973, 338);
		frmRealmsSimulation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{957, 0};
		gridBagLayout.rowHeights = new int[]{37, 44, 0, 100, 12, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 12.0, 0.0, Double.MIN_VALUE};
		frmRealmsSimulation.getContentPane().setLayout(gridBagLayout);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMaximumSize(new Dimension(0, 21));
		menuBar.setMinimumSize(new Dimension(0, 20));
		menuBar.setAlignmentY(Component.CENTER_ALIGNMENT);
		GridBagConstraints gbc_menuBar = new GridBagConstraints();
		gbc_menuBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_menuBar.insets = new Insets(0, 0, 5, 0);
		gbc_menuBar.gridx = 0;
		gbc_menuBar.gridy = 0;
		frmRealmsSimulation.getContentPane().add(menuBar, gbc_menuBar);
		
		JMenu mnDatei = new JMenu("Datei");
		mnDatei.setVerticalAlignment(SwingConstants.TOP);
		menuBar.add(mnDatei);
		
		JMenuItem mntmConfig = new JMenuItem("Config");
		mntmConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// configuration Dialog
				doConfiguration();
			}
		});
		mnDatei.add(mntmConfig);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnDatei.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//end program
				closeMainDlg();
			}
		});
		mnDatei.add(mntmExit);
		
		JMenu mnLoad = new JMenu("Info");
		menuBar.add(mnLoad);
		
		JMenuItem mntmSettlements = new JMenuItem("Settlements");
		mntmSettlements.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doLoadSettlements();
			}
		});
		mntmSettlements.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/settle_1ss.png")));
		mnLoad.add(mntmSettlements);
		
		JMenuItem mntmBuildingtype = new JMenuItem("BuildingType");
		mntmBuildingtype.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doLoadBuildingType();
			}
		});
		mntmBuildingtype.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/gui/_text.gif")));
		mnLoad.add(mntmBuildingtype);
		
		JMenuItem mntmSuperregion = new JMenuItem("SuperRegion");
		mntmSuperregion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doLoadSuperRegion();
			}
		});
		mntmSuperregion.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/map_1.png")));
		mnLoad.add(mntmSuperregion);
		
		JMenuItem mntmPricelist = new JMenuItem("Pricelist");
		mntmPricelist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doPricelist();
			}
		});
		mntmPricelist.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/gui/_text.gif")));
		mnLoad.add(mntmPricelist);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.fill = GridBagConstraints.BOTH;
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 1;
		frmRealmsSimulation.getContentPane().add(toolBar, gbc_toolBar);
		
		JButton btnSimStart = new JButton("");
		btnSimStart.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnSimStart.setToolTipText("Start");
		btnSimStart.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/btn_start.png")));
		btnSimStart.setSelectedIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/btn_start.png")));
		btnSimStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		toolBar.add(btnSimStart);
		
		JButton btnBtnstep = new JButton("");
		btnBtnstep.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBtnstep.setToolTipText("Step");
		btnBtnstep.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/btn_step.png")));
		toolBar.add(btnBtnstep);
		
		JButton btnBtnsequence = new JButton("");
		btnBtnsequence.setToolTipText("Sequenze");
		btnBtnsequence.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/btn_run1.png")));
		btnBtnsequence.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.add(btnBtnsequence);
		
		JButton btnBtnrun = new JButton("");
		btnBtnrun.setToolTipText("Run");
		btnBtnrun.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/btn_run2.png")));
		btnBtnrun.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.add(btnBtnrun);
		
		JButton btnBtnstop = new JButton("");
		btnBtnstop.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBtnstop.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/btn_stop.png")));
		btnBtnstop.setToolTipText("Stop");
		toolBar.add(btnBtnstop);
		
		JButton btnBtninfo = new JButton("");
		btnBtninfo.setToolTipText("Info");
		btnBtninfo.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/btn_info.png")));
		btnBtninfo.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.add(btnBtninfo);
		
		Button button = new Button("");
		toolBar.add(button);
		
		JButton btnCmdRealms = new JButton("");
		btnCmdRealms.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/realms.png")));
		btnCmdRealms.setToolTipText("Realms");
		btnCmdRealms.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.add(btnCmdRealms);
		
		JButton btnCmdSettle = new JButton("");
		btnCmdSettle.setToolTipText("Realms Commands");
		btnCmdSettle.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/settle_1.png")));
		toolBar.add(btnCmdSettle);
		btnCmdSettle.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		JButton btnBtncmdcolonist = new JButton("");
		btnBtncmdcolonist.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBtncmdcolonist.setToolTipText("Colonist Commands");
		btnBtncmdcolonist.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/colonist_1.png")));
		toolBar.add(btnBtncmdcolonist);
		
		JButton btnBtncmdregiment = new JButton("");
		btnBtncmdregiment.setToolTipText("Regiment Commands");
		btnBtncmdregiment.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/regiment_1.png")));
		btnBtncmdregiment.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		toolBar.add(btnBtncmdregiment);
		
		JButton btnBtncmdkingdom = new JButton("");
		btnBtncmdkingdom.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/kingdom_1.png")));
		btnBtncmdkingdom.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBtncmdkingdom.setToolTipText("KIngdom Commands");
		toolBar.add(btnBtncmdkingdom);
		
		JButton btnBtncmdlehen = new JButton("");
		btnBtncmdlehen.setIcon(new ImageIcon(SimMainDlg.class.getResource("/net/krglok/realms/resources/lehen_1.png")));
		btnBtncmdlehen.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnBtncmdlehen.setToolTipText("Lehen Commands");
		toolBar.add(btnBtncmdlehen);
		
		Button button_1 = new Button("");
		toolBar.add(button_1);
		
		progressBar = new JProgressBar();
		progressBar.setValue(10);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridx = 0;
		gbc_progressBar.gridy = 2;
		frmRealmsSimulation.getContentPane().add(progressBar, gbc_progressBar);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		frmRealmsSimulation.getContentPane().add(scrollPane, gbc_scrollPane);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.setName("scrollConsole");
		scrollPane.setToolTipText("console");
		scrollPane.setViewportBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtpnConsole.setEditable(false);
		txtpnConsole.setName("pconsole");
		txtpnConsole.setToolTipText("Console");
		txtpnConsole.setText("Console \r\nline 1\r\nline 2\r\nline 3\r\nline 4\r\nline 5\r\nline 6\r\nline 7\r\nline 8\r\nline 10\r\n");
		scrollPane.setViewportView(txtpnConsole);
		console = new ConsoleDebug(txtpnConsole);
		
		txtStatus = new JTextField();
		txtStatus.setText("Status");
		GridBagConstraints gbc_txtStatus = new GridBagConstraints();
		gbc_txtStatus.anchor = GridBagConstraints.SOUTH;
		gbc_txtStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStatus.gridx = 0;
		gbc_txtStatus.gridy = 4;
		frmRealmsSimulation.getContentPane().add(txtStatus, gbc_txtStatus);
		txtStatus.setColumns(10);

		simTask = new SimTask(progressBar,realmModel);

		final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleWithFixedDelay(
			simTask, 
			0, 
			1, 
			TimeUnit.SECONDS
		);

	}

	private void startModel()
	{
		configSim = new ConfigSim("");
		configData = getConfigFile("");
		simServer = new SimServer(configSim.getDataPath());
		simServer.getDataStorage().initData();
		simMessage = new SimMessage(console);
		logList = new LogList(configSim.dataPath);
		

//		realmModel = new RealmModel(config.getRealmCounter(), config.getSettlementCounter(), server, config, data, messageData, logList);
		
		realmModel = new RealmModel(
				simServer.getDataStorage().initKingdoms().size(), 
				simServer.getDataStorage().initSettlements().size(), 
				simServer, 
				configData, 
				simServer.getDataStorage(), 
				simMessage, 
				logList
				);
		
	}
	
	
	private void closeMainDlg()
	{
		console.info("Programm beenden !");
		int option = JOptionPane.showOptionDialog(  
        		frmRealmsSimulation,  
                "Are you sure you want to quit?",  
                "Exit Dialog", JOptionPane.YES_NO_OPTION,  
                JOptionPane.WARNING_MESSAGE, null, null,  
                null );  
        if( option == JOptionPane.YES_OPTION ) {  
//    		this.dispose();
            System.exit( 0 );  
        }  
	}
	
	/**
	 * wickelt den Configurations Dialog ab
	 */
	private void doConfiguration()
	{
		try
		{
			console.info("Start configurationDlg");
			SimConfigDlg.main(configSim,configData,console);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * liest die Daten fuer die Config verwaltung aus der Datei
	 * 
	 * @param path
	 */
	private ConfigData getConfigFile(String path)
	{
		FileConfiguration configFile = new YamlConfiguration();
		String fileName = "config.yml";
		if (path == "")
		{
			path = ConfigSim.defaultPath;
		}
		try
		{
            File dataFile = new File(path, fileName);
            if (dataFile.exists() == false) 
            {
            	dataFile.createNewFile();
    			console.info("Write default config file");
            }
            
            configFile.load(dataFile);
            console.info("Read "+fileName);
    		return new ConfigData(configFile); 
            
		} catch (IOException e)
		{
//			e.printStackTrace();
			console.error(e.getMessage());
			return null;
		} catch (InvalidConfigurationException e)
		{
//			e.printStackTrace();
			console.error(e.getMessage());
			return null;
		} catch (Exception e)
		{
			console.error("read "+fileName+" :: "+ e.getMessage());
			return null;
		}
		
	}

	private void doLoadSettlements()
	{
		if (simServer.getDataStorage().initSettlements() != null)
		{
			int id = 1;
			SettlementList settlements = simServer.getDataStorage().initSettlements();
			Object[][] dataRows = new Object[settlements.size()][3];
			int index = 0;
			for (Settlement settle : settlements.values())
			{
//				if (index <100)
				{
					dataRows[index][0] = settle.getId();
					dataRows[index][1] = settle.getName();
					dataRows[index][2] = settle.getSettleType().name();
				}
				index ++;
			}
			String[] colHeader = new String[] {	"Id","Name", "Type"};		
			int[] colSize = new int[] { 35,200,45 };
			
			BrowseList.showMe(dataRows, colHeader, colSize, "Settlement List");
			if (BrowseList.isSelected())
			{
				id = BrowseList.getSelctedId();
				// reset selection in Browser
				BrowseList.setSelected(false);
				BrowseList.setSelctedId(0);
				// show selected Data
				Settlement settlement = simServer.getDataStorage().initSettlements().getSettlement(id );
				ShowSettle.showMe(settlement);
			}
		} else
		{
			console.error("Settlements not loaded !");
		}
	}
	
	private void doLoadBuildingType()
	{
//		BuildPlanType.values()
		Object[][] dataRows = new Object[BuildPlanType.values().length][2];
		int index = 0;
		for (BuildPlanType bType : BuildPlanType.values())
		{
			dataRows[index][0] = bType.getValue();
			dataRows[index][1] = bType.name();
			index ++;
		}
		String[] colHeader = new String[] {	"Id","Name"};
		int[] colSize = new int[] { 35,200 };
		
		BrowseList.showMe(dataRows, colHeader, colSize, "BuildingTypes List");
	}
	
	
	private void doLoadSuperRegion()
	{
		Object[][] dataRows = new Object[SettleType.values().length][1];
		int index = 0;
		for (SettleType bType : SettleType.values())
		{
			dataRows[index][0] = bType.name();
			index ++;
		}
		String[] colHeader = new String[] {	"Name"};
		int [] colSize = new int[] { 200 };
		BrowseList.showMe(dataRows, colHeader, colSize, "SettleTypes List");
	}

	private void doPricelist()
	{
		Object[][] dataRows = new Object[simServer.getDataStorage().getPriceList().size()][3];
		int index = 0;
		
		for (String ref : simServer.getDataStorage().getPriceList().sortItems())
		{
			ItemPrice item = simServer.getDataStorage().getPriceList().get(ref);
			dataRows[index][0] = index;
			dataRows[index][1] = item.ItemRef();
			dataRows[index][2] = item.getBasePrice();
			index ++;
		}
		String[] colHeader = new String[] {	"Name","Price",""};
		int[] colSize = new int[] { 45,150,40 };
		
		BrowseList.showMe(dataRows, colHeader, colSize, "Price List");
		
	}
	
	
}
