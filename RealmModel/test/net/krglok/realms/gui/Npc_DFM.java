package net.krglok.realms.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.krglok.realms.core.ConfigBasis;
import net.krglok.realms.model.RealmModel;
import net.krglok.realms.npc.NpcData;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Npc_DFM extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtNpctype;
	private JTextField txtUnittype;
	private JTextField txtIsalive;
	private JTextField txtIsmaried;
	private JTextField txtIsimmortal;
	private JTextField txtNoblelevel;
	private JTextField txtSettleid;
	private JTextField txtLehenid;
	private JTextField txtRegimentid;
	private JTextField txtWorkbuilding;
	private JTextField txtNpchusband;
	private JTextField txtPchusaband;
	private JTextField txtMother;
	private JTextField txtFather;
	private JTextField txtIsPregnant;

	public NpcData npc;
	public RealmModel rModel;
	private JTextField txtIschild;
	private JTextField txtMoney;
	private JTextField txtInhand;
	private JTextField txtAge;
	private JTextField txtSpawnid;
	private JTextField txtHomebuilding;
	private JTextField txtHappiness;
	private JTextField txtIsSpawned;
	
	/**
	 * Launch the application.
	 */
	public static void showMe(NpcData npc, RealmModel rModel)
	{
		try
		{
			Npc_DFM dialog = new Npc_DFM();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.npc = npc;
			dialog.rModel = rModel;
			dialog.updateForm();
			dialog.setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Npc_DFM()
	{
		setBounds(100, 100, 582, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(59dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(111dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(53dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				RowSpec.decode("max(10dlu;default)"),
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
			contentPanel.add(lblId, "1, 1, right, default");
		}
		{
			txtId = new JTextField();
			txtId.setText("Id");
			contentPanel.add(txtId, "3, 1, fill, default");
			txtId.setColumns(10);
		}
		{
			JLabel lblAge = new JLabel("Age");
			contentPanel.add(lblAge, "7, 1, right, default");
		}
		{
			txtAge = new JTextField();
			txtAge.setText("Age");
			contentPanel.add(txtAge, "9, 1, fill, default");
			txtAge.setColumns(10);
		}
		{
			JLabel lblSpawnid = new JLabel("SpawnId");
			contentPanel.add(lblSpawnid, "1, 3, right, default");
		}
		{
			txtSpawnid = new JTextField();
			txtSpawnid.setText("SpawnId");
			contentPanel.add(txtSpawnid, "3, 3, fill, default");
			txtSpawnid.setColumns(10);
		}
		{
			JLabel lblIsmaried = new JLabel("isMaried");
			contentPanel.add(lblIsmaried, "7, 3, right, default");
		}
		{
			txtIsmaried = new JTextField();
			txtIsmaried.setText("isMaried");
			contentPanel.add(txtIsmaried, "9, 3, fill, default");
			txtIsmaried.setColumns(10);
		}
		{
			JLabel lblName = new JLabel("Name");
			contentPanel.add(lblName, "1, 5, right, default");
		}
		{
			txtName = new JTextField();
			txtName.setText("Name");
			contentPanel.add(txtName, "3, 5, fill, default");
			txtName.setColumns(10);
		}
		{
			JLabel lblNphusband = new JLabel("NpHusband");
			contentPanel.add(lblNphusband, "7, 5, right, default");
		}
		{
			txtNpchusband = new JTextField();
			txtNpchusband.setText("NpcHusband");
			contentPanel.add(txtNpchusband, "9, 5, fill, default");
			txtNpchusband.setColumns(10);
		}
		{
			JLabel lblNpctype = new JLabel("NpcType");
			contentPanel.add(lblNpctype, "1, 7, right, default");
		}
		{
			txtNpctype = new JTextField();
			txtNpctype.setText("NpcType");
			contentPanel.add(txtNpctype, "3, 7, fill, default");
			txtNpctype.setColumns(10);
		}
		{
			JLabel lblPchusband = new JLabel("PCHusband");
			contentPanel.add(lblPchusband, "7, 7, right, default");
		}
		{
			txtPchusaband = new JTextField();
			txtPchusaband.setText("PCHusaband");
			contentPanel.add(txtPchusaband, "9, 7, fill, default");
			txtPchusaband.setColumns(10);
		}
		{
			JLabel lblUnittype = new JLabel("UnitType");
			contentPanel.add(lblUnittype, "1, 9, right, default");
		}
		{
			txtUnittype = new JTextField();
			txtUnittype.setText("UnitType");
			contentPanel.add(txtUnittype, "3, 9, fill, default");
			txtUnittype.setColumns(10);
		}
		{
			JLabel lblMother = new JLabel("Mother");
			contentPanel.add(lblMother, "7, 9, right, default");
		}
		{
			txtMother = new JTextField();
			txtMother.setText("Mother");
			contentPanel.add(txtMother, "9, 9, fill, default");
			txtMother.setColumns(10);
		}
		{
			JLabel lblNoblelevel = new JLabel("NobleLevel");
			contentPanel.add(lblNoblelevel, "1, 11, right, default");
		}
		{
			txtNoblelevel = new JTextField();
			txtNoblelevel.setText("NobleLevel");
			contentPanel.add(txtNoblelevel, "3, 11, fill, default");
			txtNoblelevel.setColumns(10);
		}
		{
			JLabel lblFather = new JLabel("Father");
			contentPanel.add(lblFather, "7, 11, right, default");
		}
		{
			txtFather = new JTextField();
			txtFather.setText("Father");
			contentPanel.add(txtFather, "9, 11, fill, default");
			txtFather.setColumns(10);
		}
		{
			JLabel lblIsalive = new JLabel("isAlive");
			contentPanel.add(lblIsalive, "1, 13, right, default");
		}
		{
			txtIsalive = new JTextField();
			txtIsalive.setText("isAlive");
			contentPanel.add(txtIsalive, "3, 13, fill, default");
			txtIsalive.setColumns(10);
		}
		{
			JLabel lblPregnant = new JLabel("isPregnant");
			contentPanel.add(lblPregnant, "7, 13, right, default");
		}
		{
			txtIsPregnant = new JTextField();
			txtIsPregnant.setText("IsPregnant");
			contentPanel.add(txtIsPregnant, "9, 13, fill, default");
			txtIsPregnant.setColumns(10);
		}
		{
			JLabel lblIsimmortal = new JLabel("Immortal");
			contentPanel.add(lblIsimmortal, "1, 15, right, default");
		}
		{
			txtIsimmortal = new JTextField();
			txtIsimmortal.setText("Immortal");
			contentPanel.add(txtIsimmortal, "3, 15, fill, default");
			txtIsimmortal.setColumns(10);
		}
		{
			JLabel lblIschild = new JLabel("isChild");
			contentPanel.add(lblIschild, "7, 15, right, default");
		}
		{
			txtIschild = new JTextField();
			txtIschild.setText("IsChild");
			contentPanel.add(txtIschild, "9, 15, fill, default");
			txtIschild.setColumns(10);
		}
		{
			JLabel lblIsSpawned = new JLabel("IsSpawned");
			contentPanel.add(lblIsSpawned, "1, 17, right, default");
		}
		{
			txtIsSpawned = new JTextField();
			txtIsSpawned.setText("IsSpawned");
			contentPanel.add(txtIsSpawned, "3, 17, fill, default");
			txtIsSpawned.setColumns(10);
		}
		{
			JLabel lblHappiness = new JLabel("Happiness");
			contentPanel.add(lblHappiness, "7, 17, right, default");
		}
		{
			txtHappiness = new JTextField();
			txtHappiness.setText("Happiness");
			contentPanel.add(txtHappiness, "9, 17, fill, default");
			txtHappiness.setColumns(10);
		}
		{
			JLabel lblSettleid = new JLabel("SettleId");
			contentPanel.add(lblSettleid, "1, 19, right, default");
		}
		{
			txtSettleid = new JTextField();
			txtSettleid.setText("SettleId");
			contentPanel.add(txtSettleid, "3, 19, fill, default");
			txtSettleid.setColumns(10);
		}
		{
			JLabel lblMoney = new JLabel("Money");
			contentPanel.add(lblMoney, "7, 19, right, default");
		}
		{
			txtMoney = new JTextField();
			txtMoney.setText("Money");
			contentPanel.add(txtMoney, "9, 19, fill, default");
			txtMoney.setColumns(10);
		}
		{
			JLabel lblLehenid = new JLabel("LehenId");
			contentPanel.add(lblLehenid, "1, 21, right, default");
		}
		{
			txtLehenid = new JTextField();
			txtLehenid.setText("LehenId");
			contentPanel.add(txtLehenid, "3, 21, fill, default");
			txtLehenid.setColumns(10);
		}
		{
			JLabel lblInhand = new JLabel("InHand");
			contentPanel.add(lblInhand, "7, 21, right, default");
		}
		{
			txtInhand = new JTextField();
			txtInhand.setText("InHand");
			contentPanel.add(txtInhand, "9, 21, fill, default");
			txtInhand.setColumns(10);
		}
		{
			JLabel lblRegimentid = new JLabel("RegimentId");
			contentPanel.add(lblRegimentid, "1, 23, right, default");
		}
		{
			txtRegimentid = new JTextField();
			txtRegimentid.setText("RegimentId");
			contentPanel.add(txtRegimentid, "3, 23, fill, default");
			txtRegimentid.setColumns(10);
		}
		{
			JLabel lblWorkbuilding = new JLabel("WorkBuilding");
			contentPanel.add(lblWorkbuilding, "1, 25, right, default");
		}
		{
			txtWorkbuilding = new JTextField();
			txtWorkbuilding.setText("WorkBuilding");
			contentPanel.add(txtWorkbuilding, "3, 25, fill, default");
			txtWorkbuilding.setColumns(10);
		}
		{
			JLabel lblHomebuilding = new JLabel("HomeBuilding");
			contentPanel.add(lblHomebuilding, "1, 27, right, default");
		}
		{
			txtHomebuilding = new JTextField();
			txtHomebuilding.setText("HomeBuilding");
			contentPanel.add(txtHomebuilding, "3, 27, fill, default");
			txtHomebuilding.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeWindow();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void updateForm()
	{
		txtId.setText(String.valueOf(npc.getId()));
		txtName.setText(npc.getName());
		txtNpctype.setText(npc.getNpcType().name());
		txtUnittype.setText(npc.getUnitType().name());
		txtNoblelevel.setText(npc.getNoble().name());
		txtIsalive.setText(String.valueOf(npc.isAlive()));
		txtIsimmortal.setText(String.valueOf(npc.isImmortal()));
		txtIsmaried.setText(String.valueOf(npc.isMaried()));
		txtSettleid.setText(String.valueOf(npc.getSettleId()));
		txtLehenid.setText(String.valueOf(npc.getLehenId()));
		txtRegimentid.setText(String.valueOf(npc.getRegimentId()));
		txtWorkbuilding.setText(String.valueOf(npc.getWorkBuilding()));
		txtNpchusband.setText(String.valueOf(npc.getNpcHusband()));
		txtPchusaband.setText(String.valueOf(npc.getPcHusband()));
		txtMother.setText(String.valueOf(npc.getMother()));
		txtFather.setText(String.valueOf(npc.getFather()));
		txtIsPregnant.setText(String.valueOf(npc.isSchwanger()));
		txtSpawnid.setText(String.valueOf(npc.getId()));
		txtAge.setText(String.valueOf(npc.getAgeDay()/360));
		txtInhand.setText(String.valueOf(npc.getItemInHand().ItemRef()));
		txtMoney.setText(ConfigBasis.setStrformat2(npc.getMoney(),7));
		txtHomebuilding.setText(String.valueOf(npc.getHomeBuilding()));
		txtHappiness.setText(String.valueOf(npc.getHappiness()));
		txtIsSpawned.setText(String.valueOf(npc.isSpawned));
		txtIschild.setText(String.valueOf(npc.isChild()));
	}
	
	private void closeWindow()
	{
		this.dispose();
	}
	
}
