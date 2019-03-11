package watchfiles;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

class FileChooserField extends JComponent
{
  private JComboBox comboBox;
  private JButton button;
  
  public FileChooserField()
  {
    comboBox = new JComboBox();
    comboBox.setEditable(false);
    
    setFolder(System.getProperty("user.home"));
    comboBox.addItem(System.getProperty("user.dir"));
    comboBox.addItem(System.getProperty("java.io.tmpdir"));
    
    button = new JButton("...");
    button.setPreferredSize(new java.awt.Dimension(21, 21));
    button.setToolTipText("Open Folder");
    
    setLayout(new GridBagLayout());
    add(comboBox, new GridBagConstraints(0, 0, 1, 1, 2.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 3), 0, 0));
    
    add(button, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    

    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String oldFolder = getFolder();
        
        JFileChooser fileDialog = new JFileChooser(oldFolder);
        fileDialog.setDialogType(0);
        fileDialog.setFileSelectionMode(1);
        if (fileDialog.showOpenDialog(SwingUtilities.getWindowAncestor(FileChooserField.this)) == 0) {
          String newFolder = fileDialog.getSelectedFile().getAbsolutePath();
          setFolder(newFolder);
        }
      }
    });
  }
  
  public void setFolder(String folder) {
    comboBox.addItem(folder);
    comboBox.setSelectedItem(folder);
  }
  
  public String getFolder() {
    Object selectedItem = comboBox.getSelectedItem();
    return selectedItem == null ? "" : selectedItem.toString();
  }
  
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    comboBox.setEnabled(enabled);
    button.setEnabled(enabled);
  }
}
