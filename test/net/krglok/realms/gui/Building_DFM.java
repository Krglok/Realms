package net.krglok.realms.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

import net.krglok.realms.Common.Item;
import net.krglok.realms.Common.ItemList;
import net.krglok.realms.core.Building;
import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.core.LocationData;
import net.krglok.realms.model.RealmModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Building_DFM extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;
	private JTextField txtType;
	private JTextField txtHsregion;
	private JTextField txtIsactive;
	private JTextField txtIsenabled;
	private JTextField txtIsslot;
	private JTextField txtSettleid;
	private JTextField txtLehenid;
	private JTextField txtOwnerid;
	private JTextField txtSettler;
	private JTextField txtWorkerneed;
	private JTextField txtWorker;
	private JTextField txtSettlerinstalled;
	private JTextField txtTraintype;
	private JTextField txtTraintime;
	private JTextField txtTraincounter;
	private JTextField txtPosition;
	private JTextField txtSlot;
	private JTextField txtSlot_1;
	private JTextField txtSlot_2;
	private JTextField txtSlot_3;
	private JTextField txtSlot_4;
	private JTextField txtSale;

	public Building building;
	public RealmModel rModel;
	
	/**
	 * Launch the application.
	 */
	public static void showMe(Building building, RealmModel rModel)
	{
		try
		{
			Building_DFM dialog = new Building_DFM();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			dialog.building = building;
			dialog.rModel = rModel;
			dialog.updateForm();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Building_DFM()
	{
		setBounds(100, 100, 725, 539);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(43dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(41dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(89dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
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
			JLabel lblId = new JLabel("Id");
			contentPanel.add(lblId, "2, 3, right, default");
		}
		{
			txtId = new JTextField();
			txtId.setText("Id");
			contentPanel.add(txtId, "4, 3, fill, default");
			txtId.setColumns(10);
		}
		{
			JLabel lblIsslot = new JLabel("isSlot");
			contentPanel.add(lblIsslot, "12, 3, right, default");
		}
		{
			txtIsslot = new JTextField();
			txtIsslot.setText("isSlot");
			contentPanel.add(txtIsslot, "14, 3, fill, default");
			txtIsslot.setColumns(10);
		}
		{
			JLabel lblType = new JLabel("Type");
			contentPanel.add(lblType, "2, 5, right, default");
		}
		{
			txtType = new JTextField();
			txtType.setText("Type");
			contentPanel.add(txtType, "4, 5, 3, 1, fill, default");
			txtType.setColumns(10);
		}
		{
			txtSlot = new JTextField();
			txtSlot.setText("Slot0");
			contentPanel.add(txtSlot, "14, 5, fill, default");
			txtSlot.setColumns(10);
		}
		{
			JButton button_slot0 = new JButton("     Recipe");
			button_slot0.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			button_slot0.setIcon(new ImageIcon(Building_DFM.class.getResource("/net/krglok/realms/gui/_tinfo16.gif")));
			contentPanel.add(button_slot0, "16, 5");
		}
		{
			JLabel lblHsregion = new JLabel("hsRegion");
			contentPanel.add(lblHsregion, "2, 7, right, default");
		}
		{
			txtHsregion = new JTextField();
			txtHsregion.setText("hsRegion");
			contentPanel.add(txtHsregion, "4, 7, fill, default");
			txtHsregion.setColumns(10);
		}
		{
			txtSlot_1 = new JTextField();
			txtSlot_1.setText("Slot1");
			contentPanel.add(txtSlot_1, "14, 7, fill, default");
			txtSlot_1.setColumns(10);
		}
		{
			JButton button_slot1 = new JButton("     Recipe");
			button_slot1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			button_slot1.setIcon(new ImageIcon(Building_DFM.class.getResource("/net/krglok/realms/gui/_tinfo16.gif")));
			contentPanel.add(button_slot1, "16, 7");
		}
		{
			JLabel lblIsactive = new JLabel("isActive");
			contentPanel.add(lblIsactive, "2, 9, right, default");
		}
		{
			txtIsactive = new JTextField();
			txtIsactive.setText("isActive");
			contentPanel.add(txtIsactive, "4, 9, fill, default");
			txtIsactive.setColumns(10);
		}
		{
			txtSlot_2 = new JTextField();
			txtSlot_2.setText("Slot2");
			contentPanel.add(txtSlot_2, "14, 9, fill, default");
			txtSlot_2.setColumns(10);
		}
		{
			JButton button_slot2 = new JButton("     Recipe");
			button_slot2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			button_slot2.setIcon(new ImageIcon(Building_DFM.class.getResource("/net/krglok/realms/gui/_tinfo16.gif")));
			contentPanel.add(button_slot2, "16, 9");
		}
		{
			JLabel lblIsenabled = new JLabel("isEnabled");
			contentPanel.add(lblIsenabled, "2, 11, right, default");
		}
		{
			txtIsenabled = new JTextField();
			txtIsenabled.setText("isEnabled");
			contentPanel.add(txtIsenabled, "4, 11, fill, default");
			txtIsenabled.setColumns(10);
		}
		{
			txtSlot_3 = new JTextField();
			txtSlot_3.setText("Slot3");
			contentPanel.add(txtSlot_3, "14, 11, fill, default");
			txtSlot_3.setColumns(10);
		}
		{
			JButton button_slot3 = new JButton("     Recipe");
			button_slot3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			button_slot3.setIcon(new ImageIcon(Building_DFM.class.getResource("/net/krglok/realms/gui/_tinfo16.gif")));
			contentPanel.add(button_slot3, "16, 11");
		}
		{
			JLabel lblSale = new JLabel("Sale");
			contentPanel.add(lblSale, "2, 13, right, default");
		}
		{
			txtSale = new JTextField();
			txtSale.setText("Sale");
			contentPanel.add(txtSale, "4, 13, fill, default");
			txtSale.setColumns(10);
		}
		{
			txtSlot_4 = new JTextField();
			txtSlot_4.setText("Slot4");
			contentPanel.add(txtSlot_4, "14, 13, fill, default");
			txtSlot_4.setColumns(10);
		}
		{
			JButton button_slot4 = new JButton("     Recipe");
			button_slot4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			button_slot4.setIcon(new ImageIcon(Building_DFM.class.getResource("/net/krglok/realms/gui/_tinfo16.gif")));
			contentPanel.add(button_slot4, "16, 13");
		}
		{
			JLabel lblSettleid = new JLabel("settleId");
			contentPanel.add(lblSettleid, "2, 15, right, default");
		}
		{
			txtSettleid = new JTextField();
			txtSettleid.setText("settleID");
			contentPanel.add(txtSettleid, "4, 15, fill, default");
			txtSettleid.setColumns(10);
		}
		{
			JLabel lblRecipe = new JLabel("Recipe");
			contentPanel.add(lblRecipe, "12, 15, right, default");
		}
		{
			JButton btnRecipe = new JButton("     Recipe");
			btnRecipe.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showRecipe();
				}
			});
			btnRecipe.setIcon(new ImageIcon(Building_DFM.class.getResource("/net/krglok/realms/gui/_tinfo16.gif")));
			contentPanel.add(btnRecipe, "14, 15");
		}
		{
			JLabel lblLeheid = new JLabel("leheId");
			contentPanel.add(lblLeheid, "2, 17, right, default");
		}
		{
			txtLehenid = new JTextField();
			txtLehenid.setText("lehenId");
			contentPanel.add(txtLehenid, "4, 17, fill, default");
			txtLehenid.setColumns(10);
		}
		{
			JLabel lblMessage = new JLabel("Message");
			contentPanel.add(lblMessage, "12, 17, right, default");
		}
		{
			JButton btnMessage = new JButton("Message");
			btnMessage.setIcon(new ImageIcon(Building_DFM.class.getResource("/net/krglok/realms/gui/_tinfo16.gif")));
			btnMessage.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showMessage();
				}
			});
			contentPanel.add(btnMessage, "14, 17, fill, default");
		}
		{
			JLabel lblOwnerid = new JLabel("OwnerId");
			contentPanel.add(lblOwnerid, "2, 19, right, default");
		}
		{
			txtOwnerid = new JTextField();
			txtOwnerid.setText("OwnerId");
			contentPanel.add(txtOwnerid, "4, 19, fill, default");
			txtOwnerid.setColumns(10);
		}
		{
			JLabel lblSettler = new JLabel("SettlerMax");
			contentPanel.add(lblSettler, "2, 21, right, default");
		}
		{
			txtSettler = new JTextField();
			txtSettler.setText("Settler");
			contentPanel.add(txtSettler, "4, 21, fill, default");
			txtSettler.setColumns(10);
		}
		{
			JLabel lblSettlerinstalled = new JLabel("Settler");
			contentPanel.add(lblSettlerinstalled, "6, 21, right, default");
		}
		{
			txtSettlerinstalled = new JTextField();
			txtSettlerinstalled.setText("SettlerInstalled");
			contentPanel.add(txtSettlerinstalled, "8, 21, fill, default");
			txtSettlerinstalled.setColumns(10);
		}
		{
			JLabel lblWorkerneed = new JLabel("WorkerNeed");
			contentPanel.add(lblWorkerneed, "2, 23, right, default");
		}
		{
			txtWorkerneed = new JTextField();
			txtWorkerneed.setText("WorkerNeed");
			contentPanel.add(txtWorkerneed, "4, 23, fill, default");
			txtWorkerneed.setColumns(10);
		}
		{
			JLabel lblWorker = new JLabel("Worker");
			contentPanel.add(lblWorker, "6, 23, right, default");
		}
		{
			txtWorker = new JTextField();
			txtWorker.setText("Worker");
			contentPanel.add(txtWorker, "8, 23, fill, default");
			txtWorker.setColumns(10);
		}
		{
			JLabel lblTraintype = new JLabel("TrainType");
			contentPanel.add(lblTraintype, "2, 25, right, default");
		}
		{
			txtTraintype = new JTextField();
			txtTraintype.setText("TrainType");
			contentPanel.add(txtTraintype, "4, 25, fill, default");
			txtTraintype.setColumns(10);
		}
		{
			JLabel lblTraintime = new JLabel("TrainTime");
			contentPanel.add(lblTraintime, "2, 27, right, default");
		}
		{
			txtTraintime = new JTextField();
			txtTraintime.setText("TrainTime");
			contentPanel.add(txtTraintime, "4, 27, fill, default");
			txtTraintime.setColumns(10);
		}
		{
			JLabel lblTraincounter = new JLabel("TrainCounter");
			contentPanel.add(lblTraincounter, "6, 27, right, default");
		}
		{
			txtTraincounter = new JTextField();
			txtTraincounter.setText("TrainCounter");
			contentPanel.add(txtTraincounter, "8, 27, fill, default");
			txtTraincounter.setColumns(10);
		}
		{
			JLabel lblPosition = new JLabel("Position");
			contentPanel.add(lblPosition, "2, 29, right, default");
		}
		{
			txtPosition = new JTextField();
			txtPosition.setText("Position");
			contentPanel.add(txtPosition, "4, 29, 7, 1, fill, default");
			txtPosition.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setEnabled(false);
				okButton.setIcon(new ImageIcon(Building_DFM.class.getResource("/net/krglok/realms/gui/check.png")));
				okButton.setMnemonic(KeyEvent.VK_ENTER);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setIcon(new ImageIcon(Building_DFM.class.getResource("/net/krglok/realms/gui/error.png")));
				cancelButton.setMnemonic(KeyEvent.VK_ESCAPE);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialog();
					}
					
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void closeDialog()
	{
		this.dispose();
	}
	
	private void updateForm()
	{
		txtId.setText(String.valueOf(building.getId()));
		txtType.setText(building.getBuildingType().name());
		txtPosition.setText(LocationData.toString(building.getPosition()));
		txtHsregion.setText(String.valueOf(building.getHsRegion()));
		txtLehenid.setText(String.valueOf(building.getLehenId()));
		txtSale.setText(ConfigBasis.setStrformat2(building.getSales(), 9));
		txtIsactive.setText("True");
		txtIsenabled.setText(building.getIsEnabled().toString());
		txtIsslot.setText("?");
		txtSettleid.setText(String.valueOf(building.getSettleId()));
		txtOwnerid.setText(String.valueOf(building.getOwnerId()));
		txtSettler.setText(String.valueOf(building.getSettler()));
		txtWorkerneed.setText(String.valueOf(building.getWorkerNeeded()));
		txtWorker.setText(String.valueOf(building.getWorkerInstalled()));
		txtSettlerinstalled.setText(String.valueOf(building.getSettlerInstalled()));
		txtTraintype.setText(building.getTrainType().name());
		txtTraintime.setText(String.valueOf(building.getTrainTime()));
		txtTraincounter.setText(String.valueOf(building.getTrainCounter()));
		if (building.getSlots()[0] != null)
		{
			txtSlot.setText(building.getSlots()[0].ItemRef());
		} else
		if (building.getSlots()[1] != null)
		{
			txtSlot.setText(building.getSlots()[1].ItemRef());
		} else
		if (building.getSlots()[2] != null)
		{
			txtSlot.setText(building.getSlots()[2].ItemRef());
		} else
		if (building.getSlots()[3] != null)
		{
			txtSlot.setText(building.getSlots()[3].ItemRef());
		} else
		if (building.getSlots()[4] != null)
		{
			txtSlot.setText(building.getSlots()[4].ItemRef());
		} 
			
	}

	private void showRecipe()
	{
		
		ItemList iList = rModel.getServer().getRecipeProd("",building.getBuildingType().name());
//		rModel.getServer().get
		Object[][] dataRows = new Object[iList.size()][3];
		int index = 0;
		for (String itemRef : iList.sortItems())
		{
			Item item = iList.getItem(itemRef);
//			if (index <100)
			{
				dataRows[index][0] = item.ItemRef();
				dataRows[index][1] = item.value();
			}
			index ++;
		}
		for (Item item : iList.values())
		{
			for (int i = 0; i < dataRows.length; i++)
			{
				if (item.ItemRef().equalsIgnoreCase(String.valueOf(dataRows[i][0])))
				{
					dataRows[i][2] = item.value();
				}
			}
		}
		String[] colHeader = new String[] {	"Items", "Amount"};		
		@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] {String.class, Integer.class, String.class};
		Item_Browser.showMe(dataRows, columnTypes, colHeader, "Recipe");
		
	}

	private void showMessage()
	{
		MessageBrowser.showMe(building.getBuildingType().name(),building.getMsg());		
	}
}
