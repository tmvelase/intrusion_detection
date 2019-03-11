package watchfiles;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

class TitledBevel extends JComponent
{
  public static final int ACTION_COLLAPSED = 1101;
  public static final int ACTION_EXPANDED = 1102;
  private final ImageIcon minusIcon = new ImageIcon(getClass().getResource("res/minus.gif"));
  private final ImageIcon plusIcon = new ImageIcon(getClass().getResource("res/plus.gif"));
  private JLabel label;
  private boolean collapsed = false;
  private ActionListener actionListener;
  
  public TitledBevel(String caption) {
    this(caption, null, false);
  }
  
  public TitledBevel(String caption, ActionListener actionListener, boolean collapsed) {
    label = new JLabel(caption);
    
    setActionListener(actionListener);
    
    setLayout(new GridBagLayout());
    add(label, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 17, 0, new Insets(0, 0, 0, 0), 0, 0));
    

    LineBevel bevel = new LineBevel();
    add(bevel, new GridBagConstraints(1, 0, 1, 1, 1.0D, 0.0D, 17, 2, new Insets(0, 0, 0, 0), 0, 0));
    

    if (actionListener != null) {
      label.setCursor(new Cursor(12));
      label.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          setCollapsed(!isCollapsed());
          getActionListener().actionPerformed(new ActionEvent(TitledBevel.this, isCollapsed() ? 1101 : 1102, ""));
        }
      });
    }
    

    setCollapsed(collapsed);
  }
  
  boolean isCollapsed() {
    return collapsed;
  }
  
  void setCollapsed(boolean collapsed) {
    this.collapsed = collapsed;
    if (getActionListener() != null) {
      if (collapsed) {
        label.setIcon(plusIcon);
      } else {
        label.setIcon(minusIcon);
      }
    }
  }
  
  ActionListener getActionListener() {
    return actionListener;
  }
  
  void setActionListener(ActionListener actionListener) {
    this.actionListener = actionListener;
  }
  
  public void setFont(Font font) {
    super.setFont(font);
    if (label != null) {
      label.setFont(font);
    }
  }
}
