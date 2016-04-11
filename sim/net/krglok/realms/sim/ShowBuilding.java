package net.krglok.realms.sim;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JCheckBox;

import net.krglok.realms.Common.LocationData;
import net.krglok.realms.core.Building;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShowBuilding extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtBuildingid;
	private JTextField txtBuildingtype;
	private JTextField txtPosx;
	private JTextField txtPosy;
	private JTextField txtPosz;
	private JTextField txtWorld;
	private JTextField txtSettler;
	private JTextField txtWorker;
	private JTextField txtSlot;
	private JTextField txtSlot_1;
	private JTextField txtSlot_2;
	private JTextField txtTraintype;
	private JTextField txtTraintime;
	private JTextField txtTraincount;
	private JTextField txtMaxproduction;
	private JTextField txtSale;
	private JTextField txtWorkerneeded;

	protected Building building;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(Building building)
	{
		try
		{
			ShowBuilding dialog = new ShowBuilding(building);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ShowBuilding(Building building)
	{
		this.building = building;
		setTitle("Building");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ShowBuilding.class.getResource("/net/krglok/realms/resources/build_2.png")));
		setBounds(100, 100, 517, 354);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{75, 75, 75, 75, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{20, 25, 25, 0, 0, 0, 0, 29, 28, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel label = new JLabel("");
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.fill = GridBagConstraints.BOTH;
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 1;
			gbc_label.gridy = 0;
			contentPanel.add(label, gbc_label);
		}
		{
			JLabel lblBuildingid = new JLabel("buildingId");
			GridBagConstraints gbc_lblBuildingid = new GridBagConstraints();
			gbc_lblBuildingid.insets = new Insets(0, 0, 5, 5);
			gbc_lblBuildingid.anchor = GridBagConstraints.EAST;
			gbc_lblBuildingid.gridx = 0;
			gbc_lblBuildingid.gridy = 1;
			contentPanel.add(lblBuildingid, gbc_lblBuildingid);
		}
		{
			txtBuildingid = new JTextField();
			txtBuildingid.setText("BuildingId");
			GridBagConstraints gbc_txtBuildingid = new GridBagConstraints();
			gbc_txtBuildingid.insets = new Insets(0, 0, 5, 5);
			gbc_txtBuildingid.fill = GridBagConstraints.BOTH;
			gbc_txtBuildingid.gridx = 1;
			gbc_txtBuildingid.gridy = 1;
			contentPanel.add(txtBuildingid, gbc_txtBuildingid);
			txtBuildingid.setColumns(10);
			txtBuildingid.setText(String.valueOf(building.getId()));
		}
		{
			JLabel lblBuildingtype = new JLabel("buildingType");
			GridBagConstraints gbc_lblBuildingtype = new GridBagConstraints();
			gbc_lblBuildingtype.anchor = GridBagConstraints.EAST;
			gbc_lblBuildingtype.insets = new Insets(0, 0, 5, 5);
			gbc_lblBuildingtype.gridx = 2;
			gbc_lblBuildingtype.gridy = 1;
			contentPanel.add(lblBuildingtype, gbc_lblBuildingtype);
		}
		{
			txtBuildingtype = new JTextField();
			txtBuildingtype.setText("buildingType");
			GridBagConstraints gbc_txtBuildingtype = new GridBagConstraints();
			gbc_txtBuildingtype.insets = new Insets(0, 0, 5, 5);
			gbc_txtBuildingtype.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtBuildingtype.gridx = 3;
			gbc_txtBuildingtype.gridy = 1;
			contentPanel.add(txtBuildingtype, gbc_txtBuildingtype);
			txtBuildingtype.setColumns(10);
			txtBuildingtype.setText(building.getBuildingType().name());
		}
		{
			JLabel lblPosition = new JLabel("position");
			GridBagConstraints gbc_lblPosition = new GridBagConstraints();
			gbc_lblPosition.anchor = GridBagConstraints.EAST;
			gbc_lblPosition.insets = new Insets(0, 0, 5, 5);
			gbc_lblPosition.gridx = 0;
			gbc_lblPosition.gridy = 2;
			contentPanel.add(lblPosition, gbc_lblPosition);
		}
		{
			txtPosx = new JTextField();
			txtPosx.setText("posX");
			GridBagConstraints gbc_txtPosx = new GridBagConstraints();
			gbc_txtPosx.insets = new Insets(0, 0, 5, 5);
			gbc_txtPosx.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPosx.gridx = 1;
			gbc_txtPosx.gridy = 2;
			contentPanel.add(txtPosx, gbc_txtPosx);
			txtPosx.setColumns(10);
			txtPosx.setText(String.valueOf(Math.round(building.getPosition().getX())));
		}
		{
			txtPosy = new JTextField();
			txtPosy.setText("posY");
			GridBagConstraints gbc_txtPosy = new GridBagConstraints();
			gbc_txtPosy.insets = new Insets(0, 0, 5, 5);
			gbc_txtPosy.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPosy.gridx = 2;
			gbc_txtPosy.gridy = 2;
			contentPanel.add(txtPosy, gbc_txtPosy);
			txtPosy.setColumns(10);
			txtPosy.setText(String.valueOf(Math.round(building.getPosition().getY())));
		}
		{
			txtPosz = new JTextField();
			txtPosz.setText("posZ");
			GridBagConstraints gbc_txtPosz = new GridBagConstraints();
			gbc_txtPosz.insets = new Insets(0, 0, 5, 5);
			gbc_txtPosz.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPosz.gridx = 3;
			gbc_txtPosz.gridy = 2;
			contentPanel.add(txtPosz, gbc_txtPosz);
			txtPosz.setColumns(10);
			txtPosz.setText(String.valueOf(Math.round(building.getPosition().getZ())));
		}
		{
			txtWorld = new JTextField();
			txtWorld.setText("world");
			GridBagConstraints gbc_txtWorld = new GridBagConstraints();
			gbc_txtWorld.insets = new Insets(0, 0, 5, 0);
			gbc_txtWorld.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWorld.gridx = 4;
			gbc_txtWorld.gridy = 2;
			contentPanel.add(txtWorld, gbc_txtWorld);
			txtWorld.setColumns(10);
			txtWorld.setText(String.valueOf(building.getPosition().getWorld()));
		}
		{
			JLabel lblSettler = new JLabel("Settler");
			GridBagConstraints gbc_lblSettler = new GridBagConstraints();
			gbc_lblSettler.anchor = GridBagConstraints.EAST;
			gbc_lblSettler.insets = new Insets(0, 0, 5, 5);
			gbc_lblSettler.gridx = 0;
			gbc_lblSettler.gridy = 3;
			contentPanel.add(lblSettler, gbc_lblSettler);
		}
		{
			txtSettler = new JTextField();
			txtSettler.setText("settler");
			GridBagConstraints gbc_txtSettler = new GridBagConstraints();
			gbc_txtSettler.insets = new Insets(0, 0, 5, 5);
			gbc_txtSettler.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtSettler.gridx = 1;
			gbc_txtSettler.gridy = 3;
			contentPanel.add(txtSettler, gbc_txtSettler);
			txtSettler.setColumns(10);
			txtSettler.setText(String.valueOf(building.getSettler()));
		}
		{
			JLabel lblWorker = new JLabel("Worker");
			GridBagConstraints gbc_lblWorker = new GridBagConstraints();
			gbc_lblWorker.anchor = GridBagConstraints.EAST;
			gbc_lblWorker.insets = new Insets(0, 0, 5, 5);
			gbc_lblWorker.gridx = 2;
			gbc_lblWorker.gridy = 3;
			contentPanel.add(lblWorker, gbc_lblWorker);
		}
		{
			txtWorker = new JTextField();
			txtWorker.setText("worker");
			GridBagConstraints gbc_txtWorker = new GridBagConstraints();
			gbc_txtWorker.insets = new Insets(0, 0, 5, 5);
			gbc_txtWorker.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWorker.gridx = 3;
			gbc_txtWorker.gridy = 3;
			contentPanel.add(txtWorker, gbc_txtWorker);
			txtWorker.setColumns(10);
			txtWorker.setText(String.valueOf(building.getWorkerInstalled()));
		}
		{
			txtWorkerneeded = new JTextField();
			txtWorkerneeded.setText("workerNeeded");
			GridBagConstraints gbc_txtWorkerneeded = new GridBagConstraints();
			gbc_txtWorkerneeded.insets = new Insets(0, 0, 5, 0);
			gbc_txtWorkerneeded.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWorkerneeded.gridx = 4;
			gbc_txtWorkerneeded.gridy = 3;
			contentPanel.add(txtWorkerneeded, gbc_txtWorkerneeded);
			txtWorkerneeded.setColumns(10);
			txtWorkerneeded.setText(String.valueOf(building.getWorkerNeeded()));
		}
		{
			JLabel lblSlot = new JLabel("Slot");
			GridBagConstraints gbc_lblSlot = new GridBagConstraints();
			gbc_lblSlot.anchor = GridBagConstraints.EAST;
			gbc_lblSlot.insets = new Insets(0, 0, 5, 5);
			gbc_lblSlot.gridx = 0;
			gbc_lblSlot.gridy = 4;
			contentPanel.add(lblSlot, gbc_lblSlot);
		}
		{
			txtSlot = new JTextField();
			txtSlot.setText("x");
			GridBagConstraints gbc_txtSlot = new GridBagConstraints();
			gbc_txtSlot.insets = new Insets(0, 0, 5, 5);
			gbc_txtSlot.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtSlot.gridx = 1;
			gbc_txtSlot.gridy = 4;
			contentPanel.add(txtSlot, gbc_txtSlot);
			txtSlot.setColumns(10);
			if (building.isSlot())
			{
				txtSlot.setText(String.valueOf(building.getSlots()[0].ItemRef()));
			}
		}
		{
			txtSlot_1 = new JTextField();
			txtSlot_1.setText("x");
			GridBagConstraints gbc_txtSlot_1 = new GridBagConstraints();
			gbc_txtSlot_1.insets = new Insets(0, 0, 5, 5);
			gbc_txtSlot_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtSlot_1.gridx = 2;
			gbc_txtSlot_1.gridy = 4;
			contentPanel.add(txtSlot_1, gbc_txtSlot_1);
			txtSlot_1.setColumns(10);
			if (building.isSlot())
			{
				txtSlot_1.setText(String.valueOf(building.getSlots()[1].ItemRef()));
			}
		}
		{
			txtSlot_2 = new JTextField();
			txtSlot_2.setText("x");
			GridBagConstraints gbc_txtSlot_2 = new GridBagConstraints();
			gbc_txtSlot_2.insets = new Insets(0, 0, 5, 5);
			gbc_txtSlot_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtSlot_2.gridx = 3;
			gbc_txtSlot_2.gridy = 4;
			contentPanel.add(txtSlot_2, gbc_txtSlot_2);
			txtSlot_2.setColumns(10);
			if (building.isSlot())
			{
				txtSlot_2.setText(String.valueOf(building.getSlots()[2].ItemRef()));
			}
		}
		{
			JCheckBox chckbxIsenaled = new JCheckBox("isEnaled");
			GridBagConstraints gbc_chckbxIsenaled = new GridBagConstraints();
			gbc_chckbxIsenaled.anchor = GridBagConstraints.WEST;
			gbc_chckbxIsenaled.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxIsenaled.gridx = 4;
			gbc_chckbxIsenaled.gridy = 4;
			contentPanel.add(chckbxIsenaled, gbc_chckbxIsenaled);
			chckbxIsenaled.setSelected(building.isEnabled());
		}
		{
			JCheckBox chckbxIsactive = new JCheckBox("isActive");
			GridBagConstraints gbc_chckbxIsactive = new GridBagConstraints();
			gbc_chckbxIsactive.anchor = GridBagConstraints.WEST;
			gbc_chckbxIsactive.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxIsactive.gridx = 4;
			gbc_chckbxIsactive.gridy = 5;
			contentPanel.add(chckbxIsactive, gbc_chckbxIsactive);
			chckbxIsactive.setSelected(building.isActive());
		}
		{
			JLabel lblTrain = new JLabel("Train");
			GridBagConstraints gbc_lblTrain = new GridBagConstraints();
			gbc_lblTrain.anchor = GridBagConstraints.EAST;
			gbc_lblTrain.insets = new Insets(0, 0, 5, 5);
			gbc_lblTrain.gridx = 0;
			gbc_lblTrain.gridy = 6;
			contentPanel.add(lblTrain, gbc_lblTrain);
		}
		{
			txtTraintype = new JTextField();
			txtTraintype.setText("trainType");
			GridBagConstraints gbc_txtTraintype = new GridBagConstraints();
			gbc_txtTraintype.insets = new Insets(0, 0, 5, 5);
			gbc_txtTraintype.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtTraintype.gridx = 1;
			gbc_txtTraintype.gridy = 6;
			contentPanel.add(txtTraintype, gbc_txtTraintype);
			txtTraintype.setColumns(10);
			txtTraintype.setText(building.getTrainType().name());
		}
		{
			txtTraintime = new JTextField();
			txtTraintime.setText("x");
			GridBagConstraints gbc_txtTraintime = new GridBagConstraints();
			gbc_txtTraintime.insets = new Insets(0, 0, 5, 5);
			gbc_txtTraintime.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtTraintime.gridx = 2;
			gbc_txtTraintime.gridy = 6;
			contentPanel.add(txtTraintime, gbc_txtTraintime);
			txtTraintime.setColumns(10);
			txtTraintime.setText(String.valueOf(building.getTrainTime()));
		}
		{
			txtTraincount = new JTextField();
			txtTraincount.setText("x");
			GridBagConstraints gbc_txtTraincount = new GridBagConstraints();
			gbc_txtTraincount.insets = new Insets(0, 0, 5, 5);
			gbc_txtTraincount.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtTraincount.gridx = 3;
			gbc_txtTraincount.gridy = 6;
			contentPanel.add(txtTraincount, gbc_txtTraincount);
			txtTraincount.setColumns(10);
			txtTraincount.setText(String.valueOf(building.getTrainCounter()));
		}
		{
			JCheckBox chckbxIsslot = new JCheckBox("isSlot");
			GridBagConstraints gbc_chckbxIsslot = new GridBagConstraints();
			gbc_chckbxIsslot.anchor = GridBagConstraints.WEST;
			gbc_chckbxIsslot.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxIsslot.gridx = 4;
			gbc_chckbxIsslot.gridy = 6;
			contentPanel.add(chckbxIsslot, gbc_chckbxIsslot);
			chckbxIsslot.setSelected(building.isSlot());
		}
		{
			JLabel lblProduction = new JLabel("Production");
			GridBagConstraints gbc_lblProduction = new GridBagConstraints();
			gbc_lblProduction.anchor = GridBagConstraints.EAST;
			gbc_lblProduction.insets = new Insets(0, 0, 5, 5);
			gbc_lblProduction.gridx = 0;
			gbc_lblProduction.gridy = 7;
			contentPanel.add(lblProduction, gbc_lblProduction);
			
		}
		{
			txtMaxproduction = new JTextField();
			txtMaxproduction.setText("x");
			GridBagConstraints gbc_txtMaxproduction = new GridBagConstraints();
			gbc_txtMaxproduction.insets = new Insets(0, 0, 5, 5);
			gbc_txtMaxproduction.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtMaxproduction.gridx = 1;
			gbc_txtMaxproduction.gridy = 7;
			contentPanel.add(txtMaxproduction, gbc_txtMaxproduction);
			txtMaxproduction.setColumns(10);
		}
		{
			JLabel lblSale = new JLabel("Sale");
			GridBagConstraints gbc_lblSale = new GridBagConstraints();
			gbc_lblSale.anchor = GridBagConstraints.EAST;
			gbc_lblSale.insets = new Insets(0, 0, 5, 5);
			gbc_lblSale.gridx = 0;
			gbc_lblSale.gridy = 8;
			contentPanel.add(lblSale, gbc_lblSale);
		}
		{
			txtSale = new JTextField();
			txtSale.setText("x");
			GridBagConstraints gbc_txtSale = new GridBagConstraints();
			gbc_txtSale.insets = new Insets(0, 0, 5, 5);
			gbc_txtSale.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtSale.gridx = 1;
			gbc_txtSale.gridy = 8;
			contentPanel.add(txtSale, gbc_txtSale);
			txtSale.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						doCancel();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	
	private void doCancel()
	{
		this.dispose();
	}
	
}
