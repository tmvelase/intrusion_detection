package watchfiles;

import com.teamdev.filewatch.WatchingAttributes;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;

class OptionsChooserField extends javax.swing.JComponent
{
  private JTextField textField;
  private JButton button;
  private JWindow optionsWindow;
  private OptionsPanel optionsPanel;
  private boolean optionsPanelVisible = false;
  
  private java.awt.event.AWTEventListener awtEventListener;
  
  public OptionsChooserField(java.util.Set<WatchingAttributes> options)
  {
    optionsPanel = new OptionsPanel(options);
    optionsPanel.setBackground(java.awt.Color.WHITE);
    
    addAncestorListener(new javax.swing.event.AncestorListener()
    {
      public void ancestorAdded(AncestorEvent event) {}
      
      public void ancestorRemoved(AncestorEvent event) {}
      
      public void ancestorMoved(AncestorEvent event)
      {
        OptionsChooserField.this.hideOptionsWindow();
      }
      
    });
    textField = new JTextField();
    textField.setEditable(false);
    textField.setFocusable(false);
    textField.setText(options.toString());
    
    button = new JButton("...");
    java.awt.Dimension dimension = new java.awt.Dimension(21, 21);
    button.setPreferredSize(dimension);
    button.setMaximumSize(dimension);
    button.setToolTipText("Select Watching Options");
    
    setLayout(new java.awt.GridBagLayout());
    add(textField, new GridBagConstraints(0, 0, 1, 1, 1.0D, 0.0D, 17, 2, new java.awt.Insets(0, 0, 0, 3), 0, 0));
    
    add(button, new GridBagConstraints(1, 0, 1, 1, 0.0D, 0.0D, 17, 2, new java.awt.Insets(0, 0, 0, 0), 0, 0));
    

    button.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            OptionsChooserField.this.showOptionsWindow();
          }
        });
      }
    });
    textField.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            if (textField.isEnabled()) {
              OptionsChooserField.this.showOptionsWindow();
            }
            
          }
        });
      }
    });
    setCursor(new java.awt.Cursor(12));
  }
  
  private void hideOptionsWindow() {
    if ((optionsWindow != null) && (optionsPanelVisible)) {
      optionsWindow.setVisible(false);
      optionsPanelVisible = false;
      textField.setText(optionsPanel.getOptions().toString());
      button.requestFocus();
    }
  }
  
  private void showOptionsWindow() {
    if (optionsWindow == null) {
      createPopup();
    }
    
    java.awt.Point location = textField.getLocationOnScreen();
   // y += textField.getHeight();
    optionsWindow.setLocation(location);
    
    if (!optionsPanelVisible) {
      optionsWindow.setVisible(true);
      Toolkit.getDefaultToolkit().addAWTEventListener(awtEventListener, 24L);
      optionsPanelVisible = true;
    } else {
      Toolkit.getDefaultToolkit().removeAWTEventListener(awtEventListener);
      hideOptionsWindow();
    }
  }
  
  private void createPopup() {
    java.awt.Window ancestor = SwingUtilities.getWindowAncestor(this);
    optionsWindow = new JWindow(ancestor);
    optionsWindow.setLayout(new java.awt.BorderLayout());
    optionsWindow.setContentPane(optionsPanel);
    optionsWindow.pack();
    
    awtEventListener = new java.awt.event.AWTEventListener() {
      public void eventDispatched(AWTEvent event) {
        if ((event instanceof MouseEvent)) {
          MouseEvent mouseEvent = (MouseEvent)event;
          if (mouseEvent.getID() == 500) {
            Object source = mouseEvent.getSource();
            MouseEvent convertedMouseEvent1 = SwingUtilities.convertMouseEvent((Component)source, mouseEvent, textField);
            MouseEvent convertedMouseEvent2 = SwingUtilities.convertMouseEvent((Component)source, mouseEvent, optionsWindow);
            MouseEvent convertedMouseEvent3 = SwingUtilities.convertMouseEvent((Component)source, mouseEvent, button);
            boolean clickOnOptionsWindow = optionsWindow.contains(convertedMouseEvent1.getPoint()) | textField.contains(convertedMouseEvent2.getPoint()) | button.contains(convertedMouseEvent3.getPoint());
            


            if ((source != null) && (optionsPanelVisible) && (!clickOnOptionsWindow)) {
              OptionsChooserField.this.hideOptionsWindow();
            }
          }
        } else if ((event instanceof KeyEvent)) {
          KeyEvent keyEvent = (KeyEvent)event;
          if ((keyEvent.getID() == 401) && (optionsPanelVisible)) {
            switch (keyEvent.getKeyCode()) {
            case 10: 
            case 27: 
              OptionsChooserField.this.hideOptionsWindow();
            }
            
          }
        }
      }
    };
  }
  
  public void setEnabled(boolean enabled)
  {
    super.setEnabled(enabled);
    button.setEnabled(enabled);
    textField.setEnabled(enabled);
  }
}
