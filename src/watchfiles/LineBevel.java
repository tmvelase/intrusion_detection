package watchfiles;

import java.awt.Dimension;
import javax.swing.JComponent;



class LineBevel
  extends JComponent
{
  public LineBevel(int type)
  {
    init(type);
    setOpaque(false);
  }
  
  private void init(int type) {
    setBorder(new LineBorder(type));
    
    if (type == 0) {
      setPreferredSize(new Dimension(1, 2));
    } else
      setPreferredSize(new Dimension(2, 1));
  }
  
  public LineBevel() {
    init(0);
  }
}
