package de.kugihan.dictionaryformids.hmi_java_se;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
* Ein einfacher Header-Renderer f�r das SortedJTable
*
* @author Benjamin Sigg
* @version 1.0
*/
public class DefaultSortTableHeaderRenderer extends JPanel
      implements SortedTableListener, TableCellRenderer{


   private TableCellRenderer renderer;

   private int column = -1;
   private boolean ascending = false;

   private Component image;

   private Icon ascendingIcon, descendingIcon;
   private Icon icon;

   /**
    * Konstruktor des Renderers
    * @param renderer Der Originalrenderer, welcher dargestellt, und evtl. um
    * einen Pfeil erweitert wird.
    */
   public DefaultSortTableHeaderRenderer( TableCellRenderer renderer ){
      this.renderer = renderer;

      ascendingIcon = createAscendingIcon();
      descendingIcon = createDescendingIcon();

      setLayout( null );

      setOpaque( false );
   }

   /**
    * Eine Implementation des DefaultSortTableHeaderRenderers, der
    * das Interface {@link javax.swing.plaf.UIResource UIResrouce}
    * implementiert, und somit durch LookAndFeel's ersetzt werden kann.
    * @author Benjamin Sigg
    */
   public static class UIResource extends DefaultSortTableHeaderRenderer
      implements javax.swing.plaf.UIResource{

      public UIResource( TableCellRenderer renderer ){
         super( renderer );
      }
   }

   public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
      image = renderer.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );

      removeAll();
      add( image );

      /**
       * Workaround
       * Eigentlich sollte man diese Einstellung irgendwie aus dem LookAndFeel
       * ziehen k�nnen.
       */
      if( image instanceof JComponent )
         ((JComponent)image).setOpaque( false );

      if( this.column == table.convertColumnIndexToModel( column )){
         if( ascending )
            icon = ascendingIcon;
         else
            icon = descendingIcon;
      }
      else
         icon = null;

      return this;
   }

   public void paint( Graphics g ){
      image.paint( g );
      if( icon != null ){
         int iw = icon.getIconWidth();
         int ih = icon.getIconHeight();

         int w = getWidth();
         int h = getHeight();

         Insets insets = new Insets( 2, 2, 2, 2 );

         if( image instanceof Container ){
            insets = ((Container)image).getInsets();
         }

         if( getComponentOrientation() == ComponentOrientation.LEFT_TO_RIGHT )
            icon.paintIcon( this, g, w - iw - insets.right - 2, (h - ih)/2 );
         else if( getComponentOrientation() == ComponentOrientation.RIGHT_TO_LEFT )
            icon.paintIcon( this, g, insets.left, (h - ih)/2 );
         else
            icon.paintIcon( this, g, w - iw - insets.right - 2, (h - ih)/2 );

      }
   }

   public void setBounds(int x,int y,int w,int h){
      super.setBounds( x, y, w, h );
      image.setBounds( 0, 0, w, h );
   }

   public void update( Graphics g ) {
      image.update( g );
   }

   public Dimension getMinimumSize() {
      return image.getMinimumSize();
   }

   public Dimension getMaximumSize() {
      return image.getMaximumSize();
   }

   public Dimension getPreferredSize() {
      return image.getPreferredSize();
   }

   public void desorted( SortedTable table ) {
      column = -1;
   }

   public void sorted( SortedTable table, int column, boolean ascending) {
      this.column = column;
      this.ascending = ascending;
   }

   protected Icon createAscendingIcon(){
      return new Icon(){
         public int getIconHeight() {
            return 3;
         }

         public int getIconWidth() {
            return 5;
         }

         public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor( Color.BLACK );
            g.drawLine( x, y+2, x+4, y+2 );
            g.drawLine( x+1, y+1, x+3, y+1 );
            g.drawLine( x+2, y, x+2, y );
         }
      };
   }
   protected Icon createDescendingIcon(){
      return new Icon(){
         public int getIconHeight() {
            return 3;
         }

         public int getIconWidth() {
            return 5;
         }

         public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor( Color.BLACK );
            g.drawLine( x, y, x+4, y );
            g.drawLine( x+1, y+1, x+3, y+1 );
            g.drawLine( x+2, y+2, x+2, y+2 );
         }
      };
   }
}