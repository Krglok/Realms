package net.krglok.realms.sim;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.Component;
import javax.swing.JFormattedTextField;

import net.krglok.realms.data.ConfigData;
import net.krglok.realms.simdata.ConfigSim;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SimConfigDlg extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDPath;
	private JTextField txtTextstrongholdpath;
	private JTextField txtPluginname;
	private JTextField txtPluginver;
	private JTextField txtSettlementcounter;
	private JTextField txtBuildingcounter;
	private JTextField txtSequencestep;

	private JLabel lblErrormsg; // = new JLabel("errorMsg");

	private ConfigData configData;
	
	private ConfigSim configSim = null;
	
	private ConsoleDebug console;
	
	/**
	 * Launch the application.
	 */
	public static void main(ConfigSim configSim, ConfigData configData,ConsoleDebug console)
	{
		try
		{
			SimConfigDlg dialog = new SimConfigDlg(configSim,configData, console);
			// init dialog
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public ConsoleDebug getConsole()
	{
		return console;
	}


	public void setConsole(ConsoleDebug console)
	{
		this.console = console;
	}


	/**
	 * Create the dialog.
	 */
	public SimConfigDlg(ConfigSim configSim, ConfigData configData, ConsoleDebug console)
	{
		// debug console setzen
		this.setConsole(console);
		console.debug("Set Config objects");
		this.configSim = configSim;
		this.configData = configData;
		
		console.debug("Start dialog init");
		setTitle("Configuration");
		setModal(true);
		setBounds(100, 100, 539, 540);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("121px"),
				new ColumnSpec(ColumnSpec.FILL, Sizes.bounded(Sizes.PREFERRED, Sizes.constant("207dlu", true), Sizes.constant("250dlu", true)), 1),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
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
			JLabel lblSimulation = new JLabel("Simulation");
			lblSimulation.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPanel.add(lblSimulation, "2, 2");
		}
		{
			JCheckBox chckbxSimIsinit = new JCheckBox("isInit");
			chckbxSimIsinit.setSelected(configSim.isInit);
			chckbxSimIsinit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					doChckbxSimIsinit();
				}
			});
			chckbxSimIsinit.setSelected(configSim.isInit);
			contentPanel.add(chckbxSimIsinit, "3, 2");
		}
		{
			JLabel lblDataPath = new JLabel("DataPath");
			contentPanel.add(lblDataPath, "2, 4, right, top");
		}
		{
			txtDPath = new JTextField();
			txtDPath.setText(configSim.getDataPath());
			contentPanel.add(txtDPath, "3, 4, 2, 1, fill, top");
			txtDPath.setColumns(10);
		}
		{
			JLabel lblStrongholdpath = new JLabel("StrongholdPath");
			contentPanel.add(lblStrongholdpath, "2, 6, right, default");
		}
		{
			txtTextstrongholdpath = new JTextField();
			txtTextstrongholdpath.setText(configSim.getStrongholdPath());
			contentPanel.add(txtTextstrongholdpath, "3, 6, fill, default");
			txtTextstrongholdpath.setColumns(10);
		}
		{
			JLabel lblSinglestep = new JLabel("Singlestep");
			contentPanel.add(lblSinglestep, "2, 8, right, default");
		}
		{
			JCheckBox chckbxSinglestep = new JCheckBox("Singlestep");
			chckbxSinglestep.setSelected(configSim.isSingleStep());
			contentPanel.add(chckbxSinglestep, "3, 8");
		}
		{
			JLabel lblSequencestep = new JLabel("SequenceStep");
			contentPanel.add(lblSequencestep, "2, 10, right, default");
		}
		{
			txtSequencestep = new JTextField();
			txtSequencestep.setText(configSim.getSequenceStepString());
			contentPanel.add(txtSequencestep, "3, 10, fill, default");
			txtSequencestep.setColumns(10);
		}
		{
			JLabel lblPlugin = new JLabel("Plugin");
			lblPlugin.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPanel.add(lblPlugin, "2, 12");
		}
		{
			JCheckBox chckbxIsplugininit = new JCheckBox("isInit");
			chckbxIsplugininit.setSelected(configData.isLoaded());
			contentPanel.add(chckbxIsplugininit, "3, 12");
		}
		{
			JLabel lblUpdatecheck = new JLabel("UpdateCheck");
			contentPanel.add(lblUpdatecheck, "2, 14, right, default");
		}
		{
			JCheckBox chckbxUpdatecheck = new JCheckBox("updateCheck");
			contentPanel.add(chckbxUpdatecheck, "3, 14");
		}
		{
			JLabel lblAutoupdate = new JLabel("autoupdate");
			contentPanel.add(lblAutoupdate, "2, 16, right, default");
		}
		{
			JCheckBox chckbxAutoupdate = new JCheckBox("autoupdate");
			contentPanel.add(chckbxAutoupdate, "3, 16");
		}
		{
			JLabel lblPluginname = new JLabel("plugin_name");
			contentPanel.add(lblPluginname, "2, 18, right, default");
		}
		{
			txtPluginname = new JTextField();
			txtPluginname.setText("plugin_name");
			contentPanel.add(txtPluginname, "3, 18, fill, default");
			txtPluginname.setColumns(10);
		}
		{
			JLabel lblPluginver = new JLabel("plugin_ver");
			contentPanel.add(lblPluginver, "2, 20, right, default");
		}
		{
			txtPluginver = new JTextField();
			txtPluginver.setText("plugin_ver");
			contentPanel.add(txtPluginver, "3, 20, fill, default");
			txtPluginver.setColumns(10);
		}
		{
			JLabel lblRealmcounter = new JLabel("realmCounter");
			contentPanel.add(lblRealmcounter, "2, 22, right, default");
		}
		{
			JFormattedTextField frmtdtxtfldRealmcounter = new JFormattedTextField();
			frmtdtxtfldRealmcounter.setText("0");
			contentPanel.add(frmtdtxtfldRealmcounter, "3, 22, fill, default");
		}
		{
			JLabel lblSettlementcounter = new JLabel("settlementCounter");
			contentPanel.add(lblSettlementcounter, "2, 24, right, default");
		}
		{
			txtSettlementcounter = new JTextField();
			txtSettlementcounter.setText("0");
			contentPanel.add(txtSettlementcounter, "3, 24, fill, default");
			txtSettlementcounter.setColumns(10);
		}
		{
			JLabel lblBuildingcounter = new JLabel("buildingCounter");
			contentPanel.add(lblBuildingcounter, "2, 26, right, default");
		}
		{
			txtBuildingcounter = new JTextField();
			txtBuildingcounter.setText("buildingCounter");
			contentPanel.add(txtBuildingcounter, "3, 26, fill, default");
			txtBuildingcounter.setColumns(10);
		}
		{
			JLabel lblSupersettletypes = new JLabel("superSettleTypes");
			contentPanel.add(lblSupersettletypes, "2, 28, right, default");
		}
		{
			JButton btnSupersettletypes = new JButton("superSettleTypes");
			contentPanel.add(btnSupersettletypes, "3, 28");
		}
		{
			JLabel lblRegionbuildingtypes = new JLabel("regionBuildingTypes");
			contentPanel.add(lblRegionbuildingtypes, "2, 30, right, default");
		}
		{
			JButton btnRegionbuildingtypes = new JButton("regionBuildingTypes");
			contentPanel.add(btnRegionbuildingtypes, "3, 30");
		}
		{
			lblErrormsg = new JLabel("errorMsg");
			contentPanel.add(lblErrormsg, "3, 34");
			lblErrormsg.setText("");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// save config data
						doOK();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// close window, no save
						doCancel();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		console.debug("End dialog init");
	}
	
	/**
	 *  fuehrt die OK aktionen aus
	 *  - save simulation config
	 *  - save plugin config
	 */
	private void doOK()
	{
		
		this.dispose();
	}
	
	/**
	 * fuehrt ie CancelBtn action aus.
	 * beendet das Fenster
	 * 
	 */
	private void doCancel()
	{
		this.dispose();
	}

	private void doChckbxSimIsinit()
	{
		if (configSim.isInit )
		{
			String errorMsg = configSim.writeConfig();
			lblErrormsg.setText(errorMsg);
			console.debug(errorMsg);
			configSim.isInit = false;
		} else
		{
			String errorMsg = configSim.readConfig();
			lblErrormsg.setText(errorMsg);
			console.debug(errorMsg);
			configSim.isInit = true;
		}
	}
}
