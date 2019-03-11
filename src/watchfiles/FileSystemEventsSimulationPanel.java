package watchfiles;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;

class FileSystemEventsSimulationPanel extends javax.swing.JComponent
{
  private File testFile;
  private File testDirectory;
  private static final String RENAME = "Rename";
  private static final String DELETE = "Delete";
  private static final String CREATE = "Create";
  private static final String DIRECTORY_IS_NOT_CREATED_YET = "Test directory is not created yet";
  private static final String FILE_IS_NOT_CREATED_YET = "Test file is not created yet";
  
  public FileSystemEventsSimulationPanel(final FileWatch demo)
  {
    setLayout(new java.awt.GridBagLayout());
    setBorder(null);
    
    JButton deleteFile = new JButton(new AbstractAction("Delete") {
      public void actionPerformed(ActionEvent e) {
        if (testFile == null) {
          demo.showError("Test file is not created yet");
          return;
        }
        if (!testFile.delete()) {
          demo.showError("Could not delete test file");
        }
      }
    });
    deleteFile.setToolTipText("This action deletes a test file");
    
    JButton modifyFile = new JButton(new AbstractAction("Modify") {
      public void actionPerformed(ActionEvent e) {
        if (testFile == null) {
          demo.showError("Test file is not created yet");
          return;
        }
        try {
          FileOutputStream fos = new FileOutputStream(testFile, true);
          fos.write(new byte[] { 1, 2, 3 });
          fos.close();
        } catch (Exception e1) {
          demo.showError("Could not modify test file: " + e1.getMessage());
        }
      }
    });
    modifyFile.setToolTipText("This action modifies a test file");
    
    JButton renameFile = new JButton(new AbstractAction("Rename") {
      public void actionPerformed(ActionEvent e) {
        if (testFile == null) {
          demo.showError("Test file is not created yet");
          return;
        }
        String folder = demo.getSelectedFolder();
        File dest = new File(folder, "RenamedTestFile.tmp");
        dest.deleteOnExit();
        if (!testFile.renameTo(dest)) {
          demo.showError("Could not rename test file");
        } else {
          testFile = dest;
        }
      }
    });
    renameFile.setToolTipText("This action renames a test file to 'RenamedTestFile.tmp'");
    
    JButton createFile = new JButton(new AbstractAction("Create") {
      public void actionPerformed(ActionEvent e) {
        String folder = demo.getSelectedFolder();
        testFile = new File(folder, "TestFile.tmp");
        testFile.deleteOnExit();
        if (testFile.exists()) {
          demo.showMessage("A test file has already been created");
          return;
        }
        try {
          if ((!testFile.createNewFile()) && 
            (!testFile.exists())) {
            demo.showError("Could not create test file");
            testFile = null;
          }
        }
        catch (IOException e1) {
          demo.showError("Could not create test file: " + e1.getMessage());
        }
      }
    });
    createFile.setToolTipText("This action creates the 'TestFile.tmp' file in a watching folder");
    
    add(new javax.swing.JLabel("File events:"), new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 14, 0, 5), 0, 0));
    
    add(createFile, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 5), 0, 0));
    
    add(renameFile, new GridBagConstraints(2, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 5), 0, 0));
    
    add(modifyFile, new GridBagConstraints(3, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 5), 0, 0));
    
    add(deleteFile, new GridBagConstraints(4, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 5), 0, 0));
    
    add(Box.createHorizontalStrut(2), new GridBagConstraints(5, 0, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
    

    JButton createDirectory = new JButton(new AbstractAction("Create") {
      public void actionPerformed(ActionEvent e) {
        String folder = demo.getSelectedFolder();
        testDirectory = new File(folder, "TestDirectory");
        testDirectory.deleteOnExit();
        if (testDirectory.exists()) {
          demo.showMessage("A test directory has already been created");
          return;
        }
        try {
          if (!testDirectory.mkdir()) {
            demo.showError("Could not create test directory");
            testDirectory = null;
          }
        } catch (Exception e1) {
          demo.showError(e1.getMessage());
        }
      }
    });
    createDirectory.setToolTipText("This action creates 'TestDirectory' directory in a watching folder");
    
    JButton renameDirectory = new JButton(new AbstractAction("Rename") {
      public void actionPerformed(ActionEvent e) {
        if (testDirectory == null) {
          demo.showError("Test directory is not created yet");
          return;
        }
        String folder = demo.getSelectedFolder();
        File dest = new File(folder, "RenamedDirectory");
        dest.deleteOnExit();
        if (!testDirectory.renameTo(dest)) {
          demo.showError("Could not rename test directory");
        } else {
          testDirectory = dest;
        }
      }
    });
    renameDirectory.setToolTipText("This action renames a test directory in a watching folder");
    
    JButton deleteDirectory = new JButton(new AbstractAction("Delete") {
      public void actionPerformed(ActionEvent e) {
        if (testDirectory == null) {
          demo.showError("Test directory is not created yet");
          return;
        }
        if (!testDirectory.delete()) {
          demo.showError("Could not delete test directory");
        }
      }
    });
    deleteDirectory.setToolTipText("This action deletes a test directory from a watching folder");
    
    add(new javax.swing.JLabel("Folder events:"), new GridBagConstraints(0, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 5), 0, 0));
    

    add(createDirectory, new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 5), 0, 0));
    

    add(renameDirectory, new GridBagConstraints(2, 1, 1, 1, 0.0D, 1.0D, 17, 0, new Insets(0, 0, 0, 5), 0, 0));
    

    add(deleteDirectory, new GridBagConstraints(3, 1, 1, 1, 0.0D, 1.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    

    add(Box.createGlue(), new GridBagConstraints(0, 2, 5, 1, 1.0D, 1.0D, 17, 1, new Insets(0, 0, 0, 0), 0, 0));
  }
}
