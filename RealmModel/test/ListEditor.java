

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import net.krglok.realms.core.Item;
import net.krglok.realms.gui.InteractiveTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class ListEditor extends JDialog
{

	private final JPanel contentPanel = new JPanel();
    protected JTable table;
    protected JScrollPane scroller;
    protected InteractiveTableModel tableModel;
//    private JTable table_1;
	public static final String[] columnNames = {
        "0", "1", "2", ""
    };

	private Vector dataList;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			ListEditor dialog = new ListEditor();
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void showMe(Vector data)
	{
		try
		{
			ListEditor dialog = new ListEditor();
			dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dialog.setVisible(true);
			dialog.dataList = data;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public ListEditor()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(ListEditor.class.getResource("/net/krglok/realms/gui/achiev_1.png")));
		addWindowListener( new AreYouSure() );
		setBounds(100, 100, 566, 411);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
	        tableModel = new InteractiveTableModel(columnNames);
	        tableModel.addTableModelListener(new ListEditor.InteractiveTableModelListener());
	        table = new JTable();
	        table.setModel(tableModel);
	        table.setSurrendersFocusOnKeystroke(true);
	        if (!tableModel.hasEmptyRow()) {
	            tableModel.addEmptyRow();
	        }
	        scroller = new javax.swing.JScrollPane(table);
	        
	        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));
	        TableColumn hidden = table.getColumnModel().getColumn(InteractiveTableModel.HIDDEN_INDEX);
	        hidden.setMinWidth(2);
	        hidden.setPreferredWidth(2);
	        hidden.setMaxWidth(2);
	        hidden.setCellRenderer((TableCellRenderer) new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX));

			contentPanel.add(scroller);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setIcon(new ImageIcon(ListEditor.class.getResource("/net/krglok/realms/gui/check.png")));
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						doOK();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setIcon(new ImageIcon(ListEditor.class.getResource("/net/krglok/realms/gui/error.png")));
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						doCancel();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
    public void highlightLastRow(int row) {
        int lastrow = tableModel.getRowCount();
        if (row == lastrow - 1) {
            table.setRowSelectionInterval(lastrow - 1, lastrow - 1);
        } else {
            table.setRowSelectionInterval(row + 1, row + 1);
        }

        table.setColumnSelectionInterval(0, 0);
    }

    class InteractiveRenderer extends DefaultTableCellRenderer {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected int interactiveColumn;

        public InteractiveRenderer(int interactiveColumn) {
            this.interactiveColumn = interactiveColumn;
        }

        public Component getTableCellRendererComponent(JTable table,
           Object value, boolean isSelected, boolean hasFocus, int row,
           int column)
        {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == interactiveColumn && hasFocus) {
                if ((ListEditor.this.tableModel.getRowCount() - 1) == row &&
                   !ListEditor.this.tableModel.hasEmptyRow())
                {
                	ListEditor.this.tableModel.addEmptyRow();
                }

                highlightLastRow(row);
            }

            return c;
        }
    }

    private class AreYouSure extends WindowAdapter {  
    	  public void windowClosing( WindowEvent e ) 
    	  {  
	            int option = JOptionPane.showOptionDialog(  
	            		ListEditor.this,  
	                    "Are you sure you want to quit?",  
	                    "Exit Dialog", JOptionPane.YES_NO_OPTION,  
	                    JOptionPane.WARNING_MESSAGE, null, null,  
	                    null );  
	            if( option == JOptionPane.YES_OPTION ) {  
	                System.exit( 0 );  
	            }  
    	   }  
    	@Override
    	public void windowActivated(WindowEvent arg0) {
    		showData();
    	}
    }  
    
    public class InteractiveTableModelListener implements TableModelListener {
        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                table.setColumnSelectionInterval(column + 1, column + 1);
                table.setRowSelectionInterval(row, row);
            }
        }
    }

    private void doOK()
    {
		this.dispose();
    }

    private void doCancel()
    {
    	this.dispose();
    }
    
    private void showData()
    {
    	Item item = (Item) dataList.firstElement();
    	
    	table.setValueAt(item.ItemRef(), 0, 0);
    	table.setValueAt(item.value(), 0, 1);
    }
}
