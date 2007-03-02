package de.kugihan.dictionaryformids.hmi_java_se;

import javax.swing.table.TableModel; 

/** 
* Ein erweitertes TableModel 
*/ 
public interface SortableTableModel extends TableModel { 
   /** 
    * Gibt an, ob die Rows anhand dieser Column sortierbar sind. 
    * @param column Die Column 
    * @return true, falls die Daten sortierbar sind 
    */ 
   public boolean isColumnSortable( int column ); 
}