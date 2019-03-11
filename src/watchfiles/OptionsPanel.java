package watchfiles;

import com.teamdev.filewatch.WatchingAttributes;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

class OptionsPanel extends javax.swing.JPanel
{
  private final Set<WatchingAttributes> options;
  private static Font italic;
  
  static
  {
    JLabel label = new JLabel();
    Font font = label.getFont();
    italic = new Font(font.getName(), font.getStyle(), font.getSize() - 1);
  }
  
  private class OptionCheckBox extends JComponent
  {
    private JCheckBox checkBox;
    
    public OptionCheckBox(String description, final WatchingAttributes attribute) {
      checkBox = new JCheckBox(attribute.toString());
      checkBox.setSelected(options.contains(attribute));
      checkBox.setOpaque(false);
      checkBox.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (checkBox.isSelected()) {
            options.add(attribute);
          } else {
            options.remove(attribute);
          }
        }
      });
      JLabel text = new JLabel(description);
      text.setFont(OptionsPanel.italic);
      text.setForeground(Color.darkGray);
      
      setLayout(new java.awt.GridBagLayout());
      add(checkBox, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 18, 0, new Insets(0, 0, 0, 0), 0, 0));
      
      add(text, new GridBagConstraints(1, 0, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(5, 0, 0, 0), 0, 0));
    }
  }
  
  public OptionsPanel(Set<WatchingAttributes> options)
  {
    this.options = options;
    
    setLayout(new java.awt.GridBagLayout());
    
    JComponent subtree = new OptionCheckBox("- watch for the events in the specified directory only, or in its subdirectories", WatchingAttributes.Subtree);
    
    add(new TitledBevel("Watching scope "), new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(0, 0, 0, 0), 0, 0));
    

    add(subtree, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 18, 0, new Insets(0, 0, 0, 0), 0, 0));
    

    add(new TitledBevel("File/Directory name events "), new GridBagConstraints(0, 2, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(5, 0, 0, 0), 0, 0));
    

    JComponent changeFileName = new OptionCheckBox("- watch for the create/rename/delete file events", WatchingAttributes.FileName);
    JComponent changeDirectoryName = new OptionCheckBox("- watch for the create/rename/delete directory events", WatchingAttributes.DirectoryName);
    
    add(changeFileName, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 18, 0, new Insets(0, 0, 0, 0), 0, 0));
    

    add(changeDirectoryName, new GridBagConstraints(0, 4, 1, 1, 0.0D, 0.0D, 18, 0, new Insets(0, 0, 0, 0), 0, 0));
    

    add(new TitledBevel("File/Directory data events "), new GridBagConstraints(0, 5, 1, 1, 1.0D, 0.0D, 18, 2, new Insets(5, 0, 0, 0), 0, 0));
    


    JComponent changeAttributes = new OptionCheckBox("- watch for the attribute change events", WatchingAttributes.Attributes);
    JComponent changeSize = new OptionCheckBox("- watch for the size change events", WatchingAttributes.Size);
    JComponent changeLastWrite = new OptionCheckBox("- watch for the last modified time change events", WatchingAttributes.ModifiedDate);
    JComponent changeLastAccess = new OptionCheckBox("- watch for the last access time change events", WatchingAttributes.AccessDate);
    
    add(changeSize, new GridBagConstraints(0, 6, 1, 1, 0.0D, 0.0D, 18, 0, new Insets(0, 0, 0, 0), 0, 0));
    

    add(changeAttributes, new GridBagConstraints(0, 7, 1, 1, 0.0D, 0.0D, 18, 0, new Insets(0, 0, 0, 0), 0, 0));
    

    add(changeLastAccess, new GridBagConstraints(0, 8, 1, 1, 0.0D, 0.0D, 18, 0, new Insets(0, 0, 0, 0), 0, 0));
    

    add(changeLastWrite, new GridBagConstraints(0, 9, 1, 1, 0.0D, 0.0D, 18, 0, new Insets(0, 0, 0, 0), 0, 0));
    

    setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(3, 3, 3, 5)));
  }
  
  public Set<WatchingAttributes> getOptions()
  {
    return options;
  }
}
