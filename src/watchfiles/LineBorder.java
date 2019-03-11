package watchfiles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;


class LineBorder
  extends AbstractBorder
{
  public static final int HORISONTAL = 0;
  public static final int VERTICAL = 1;
  private int _type = 0;
  
  public LineBorder() {}
  
  public LineBorder(int type)
  {
    _type = type;
  }
  
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    g.translate(x, y);
    
    if (_type == 0) {
      int w = width;
      int h = height - 1;
      
      g.setColor(Color.lightGray);
      g.drawLine(0, h - 1, w - 1, h - 1);
      
      g.setColor(Color.white);
      g.drawLine(1, h, w, h);
    } else if (_type == 1) {
      int h = height;
      
      g.setColor(Color.lightGray);
      g.drawLine(0, 0, 0, h - 1);
      
      g.setColor(Color.white);
      g.drawLine(1, 1, 1, h);
    }
    
    g.translate(-x, -y);
  }
  
  public Insets getBorderInsets(Component c) {
    return new Insets(0, 0, 0, 0);
  }
  
  public boolean isBorderOpaque() {
    return true;
  }
}
