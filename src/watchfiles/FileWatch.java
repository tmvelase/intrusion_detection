package watchfiles;

import com.teamdev.filewatch.FileWatcher;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class FileWatch extends javax.swing.JFrame
{
  public static final java.text.SimpleDateFormat outputDateFormat = new java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.ENGLISH);
  
  private final JButton startButton;
  
  private final JButton stopButton;
  private final JButton closeButton;
  private final JTextArea console;
  private final FileChooserField selectFolderField;
  private final OptionsChooserField optionsChooserField;
  private final JComboBox filter;
  private final java.util.Set<com.teamdev.filewatch.WatchingAttributes> options;
  private FileWatcher watcher;
  
  public FileWatch()
  {
    super("Intrusion Detection System ");
    
    options = java.util.EnumSet.allOf(com.teamdev.filewatch.WatchingAttributes.class);
    console = new JTextArea();
    startButton = new JButton(new AbstractAction("  Start  ") {
      public void actionPerformed(ActionEvent e) {
        if (FileWatch.this.startWatching()) {
          startButton.setEnabled(false);
          selectFolderField.setEnabled(false);
          optionsChooserField.setEnabled(false);
          filter.setEnabled(false);
          stopButton.setEnabled(true);
          stopButton.requestFocus();
        }
      }
    });
    startButton.setMnemonic('S');
    
    stopButton = new JButton(new AbstractAction("  Stop  ") {
      public void actionPerformed(ActionEvent e) {
        if (FileWatch.this.stopWatching()) {
          FileWatch.this.processStopWatching();
        }
      }
    });
    stopButton.setMnemonic('p');
    
    stopButton.setEnabled(false);
    closeButton = new JButton(new AbstractAction("  Close  ") {
      public void actionPerformed(ActionEvent e) {
        FileWatch.this.stopWatching();
        dispose();
        System.exit(0);
      }
    });
    closeButton.setMnemonic('C');
    
    selectFolderField = new FileChooserField();
    optionsChooserField = new OptionsChooserField(options);
    
    filter = new JComboBox();
    filter.setEditable(true);
    filter.addItem("<None>");
    filter.addItem("All files (*.*)");
    filter.addItem("Text files (*.txt)");
    filter.addItem("executable files (*.exe)");
    filter.setToolTipText("Select one of the available values or specify  file mask filter, for example: *.doc");
    
    getRootPane().setDefaultButton(startButton);
    ImageIcon imageIcon = new ImageIcon(getClass().getResource("res/icon.png"));
    setIconImage(imageIcon.getImage());
    createUI();
  }
  
  private void processStopWatching() {
    startButton.setEnabled(true);
    selectFolderField.setEnabled(true);
    optionsChooserField.setEnabled(true);
    filter.setEnabled(true);
    stopButton.setEnabled(false);
    startButton.requestFocus();
  }
  
  private void createUI() {
    Container container = getContentPane();
    container.setLayout(new java.awt.BorderLayout());
    
    JPanel contentPanel = new JPanel(new java.awt.GridBagLayout());
    
    JLabel caption = new JLabel("Welcome to Anomaly based IDS");
    caption.setBorder(javax.swing.BorderFactory.createCompoundBorder(new LineBorder(), javax.swing.BorderFactory.createEmptyBorder(0, 0, 3, 0)));
    Font font = caption.getFont();
    Font boldFont = new Font(font.getName(), font.getStyle() | 0x1, font.getSize());
    caption.setFont(boldFont);
    
    contentPanel.add(caption, new GridBagConstraints(0, 0, 2, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 0, 10), 0, 0));
    

    JLabel text = new JLabel("");
    contentPanel.add(text, new GridBagConstraints(0, 1, 2, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 0, 10), 0, 0));
    

    final JPanel watchingOptionsPanel = createWathingOptionsPanel();
    TitledBevel watchingOptionsBevel = new TitledBevel("Watching options ", new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (e.getID() == 1102) {
          watchingOptionsPanel.setVisible(true);
        } else if (e.getID() == 1101)
          watchingOptionsPanel.setVisible(false); } }, false);
    



    contentPanel.add(watchingOptionsBevel, new GridBagConstraints(0, 2, 2, 1, 1.0D, 0.0D, 18, 2, new Insets(10, 0, 5, 10), 0, 0));
    

    contentPanel.add(watchingOptionsPanel, new GridBagConstraints(0, 3, 2, 1, 1.0D, 0.0D, 18, 2, new Insets(0, 0, 0, 10), 0, 0));
    

 /*   final JComponent eventsSimulationPanel = new FileSystemEventsSimulationPanel(this);
    eventsSimulationPanel.setVisible(false);
    TitledBevel eventsSimulationBevel = new TitledBevel("Events simulation ", new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (e.getID() == 1102) {
          eventsSimulationPanel.setVisible(true);
        } else if (e.getID() == 1101)
          eventsSimulationPanel.setVisible(false); } }, true);
    



    contentPanel.add(eventsSimulationBevel, new GridBagConstraints(0, 4, 2, 1, 1.0D, 0.0D, 18, 2, new Insets(0, 0, 0, 10), 0, 0));
   

    contentPanel.add(eventsSimulationPanel, new GridBagConstraints(0, 5, 2, 1, 1.0D, 1.0D, 18, 1, new Insets(0, 0, 0, 10), 0, 0));
    */

    JLabel fileSystemEventsLog = new JLabel("File System Events Log:");
    contentPanel.add(fileSystemEventsLog, new GridBagConstraints(0, 6, 2, 1, 0.0D, 0.0D, 18, 0, new Insets(5, 0, 0, 10), 0, 0));
    

    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(console);
    scrollPane.setMinimumSize(new java.awt.Dimension(60, 90));
    scrollPane.setPreferredSize(new java.awt.Dimension(60, 90));
    
    contentPanel.add(scrollPane, new GridBagConstraints(0, 7, 2, 1, 1.0D, 1.0D, 18, 1, new Insets(0, 0, 10, 10), 0, 0));
    
    fileSystemEventsLog.setLabelFor(console);
    fileSystemEventsLog.setDisplayedMnemonic('E');
    
    container.add(contentPanel, "Center");
    
    JLabel image = new JLabel(new ImageIcon(getClass().getResource("res/logo.png")));
    image.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
    container.add(image, "West");
    
    container.add(createButtonsPanel(), "South");
  }
  
  private JPanel createWathingOptionsPanel() {
    JPanel result = new JPanel(new java.awt.GridBagLayout());
    JLabel directoryLabel = new JLabel("Directory:");
    result.add(directoryLabel, new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 5, 3), 0, 0));
    
    result.add(selectFolderField, new GridBagConstraints(1, 1, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(0, 0, 5, 0), 0, 0));
    
    directoryLabel.setLabelFor(selectFolderField);
    directoryLabel.setDisplayedMnemonic('D');
    
    JLabel attributesLabel = new JLabel("Attributes:");
    result.add(attributesLabel, new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 10, 5, 3), 0, 0));
    
    result.add(optionsChooserField, new GridBagConstraints(1, 2, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(0, 0, 5, 0), 0, 0));
    
    attributesLabel.setLabelFor(optionsChooserField);
    attributesLabel.setDisplayedMnemonic('A');
    
    JLabel filterLabel = new JLabel("Filter:");
    result.add(filterLabel, new GridBagConstraints(0, 3, 1, 1, 0.0D, 0.0D, 13, 0, new Insets(0, 0, 5, 3), 0, 0));
    
    result.add(filter, new GridBagConstraints(1, 3, 1, 1, 1.0D, 0.0D, 17, 0, new Insets(0, 0, 5, 0), 0, 0));
    
    filterLabel.setLabelFor(filter);
    filterLabel.setDisplayedMnemonic('F');
    return result;
  }
  
  private JPanel createButtonsPanel() {
    JPanel result = new JPanel(new java.awt.BorderLayout());
    result.add(new LineBevel(), "North");
    JPanel buttons = new JPanel(new java.awt.FlowLayout(2));
    buttons.add(startButton);
    buttons.add(stopButton);
    buttons.add(javax.swing.Box.createHorizontalStrut(5));
    buttons.add(closeButton);
    result.add(buttons, "Center");
    return result;
  }
  
  private boolean startWatching() {
    console.setText("");
    String folder = selectFolderField.getFolder();
    try {
      watcher = FileWatcher.create(new java.io.File(folder));
    } catch (Exception e) {
      showError("Cannot create file watcher: " + e.getMessage());
      return false;
    }
    try {
      watcher.setOptions(options);
    } catch (Exception e) {
      showError("Specified options are not correct: " + e.getMessage());
      return false;
    }
    
    int index = this.filter.getSelectedIndex();
    switch (index) {
    case -1: 
      String filter = this.filter.getSelectedItem().toString();
      watcher.setFilter(new com.teamdev.filewatch.FileMaskFilter(filter));
      break;
    case 0: 
      watcher.setFilter(null);
      break;
    case 1: 
      watcher.setFilter(new com.teamdev.filewatch.FileMaskFilter("*.*"));
      break;
    case 2: 
      watcher.setFilter(new com.teamdev.filewatch.FileMaskFilter("*.txt"));
      break;
    case 3: 
      watcher.setFilter(new com.teamdev.filewatch.FileMaskFilter("*.exe"));
    }
    
    
    watcher.addFileEventsListener(new com.teamdev.filewatch.FileEventsListener() {
      public void fileAdded(com.teamdev.filewatch.FileEvent.Added e) {
        FileWatch.this.writeToConsole(e.toString());
      }
      
      public void fileDeleted(com.teamdev.filewatch.FileEvent.Deleted e) {
        FileWatch.this.writeToConsole(e.toString());
      }
      
      public void fileChanged(com.teamdev.filewatch.FileEvent.Changed e) {
        FileWatch.this.writeToConsole(e.toString());
      }
      
      public void fileRenamed(com.teamdev.filewatch.FileEvent.Renamed e) {
        FileWatch.this.writeToConsole(e.toString());
      }
      
    });
    watcher.addWatcherListener(new com.teamdev.filewatch.WatcherEventListener() {
      public void fileWatcherStopped(com.teamdev.filewatch.WatcherEvent.Stopped e) {
        FileWatch.this.writeToConsole(e.toString());
        FileWatch.this.processStopWatching();
      }
      
      public void fileWatcherStarted(com.teamdev.filewatch.WatcherEvent.Started e) {
        FileWatch.this.writeToConsole(e.toString());
      }
    });
    try
    {
      watcher.start();
    } catch (Exception e) {
      showError("Could not start file watcher: " + e.getCause().getMessage());
      return false;
    }
    return true;
  }
  
  private boolean stopWatching() {
    if ((watcher != null) && (watcher.isRunning())) {
      try {
        watcher.stop();
      } catch (Exception e) {
        showError("Could not stop file watcher: " + e.getCause().getMessage());
        return false;
      }
    }
    return true;
  }
  
  String getSelectedFolder() {
    return selectFolderField.getFolder();
  }
  
  void showError(String message) {
    javax.swing.JOptionPane.showMessageDialog(this, message, "Error", 2);
  }
  
  void showMessage(String message) {
    javax.swing.JOptionPane.showMessageDialog(this, message, "Message", 1);
  }
  
  private void writeToConsole(String message) {
    StringBuilder formatedLine = new StringBuilder(255);
    formatedLine.append(outputDateFormat.format(new java.util.Date()));
    formatedLine.append(':');
    formatedLine.append(' ');
    formatedLine.append(message);
    formatedLine.append('\n');
    
    console.append(formatedLine.toString());
    console.setCaretPosition(console.getText().length());
  }
  
  public static void main(String[] args) throws Exception {
    String osName = System.getProperty("os.name");
    if (osName.equals("Linux"))
    {

      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } else {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    
    Font tahoma = new Font("Tahoma", 0, 11);
    
    UIDefaults defaults = UIManager.getDefaults();
    defaults.put("Button.font", tahoma);
    defaults.put("CheckBox.font", tahoma);
    defaults.put("ToggleButton.font", tahoma);
    defaults.put("ComboBox.font", tahoma);
    defaults.put("Label.font", tahoma);
    defaults.put("Panel.font", tahoma);
    defaults.put("TextArea.font", tahoma);
    defaults.put("TextField.font", tahoma);
    
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        FileWatch watcherDemo = new FileWatch();
        watcherDemo.setSize(640, 426);
        watcherDemo.setDefaultCloseOperation(3);
        watcherDemo.setLocationRelativeTo(null);
        watcherDemo.setResizable(false);
        watcherDemo.setVisible(true);
      }
    });
  }
}
