package de.kugihan.dictionaryformids.hmi_java_se;

/** 
* @author Benjamin Sigg 
* @version 1.0 
*/ 
public interface SortedTableListener { 
   /** 
    * Wird aufgerufen, falls eine Column neu sortiert wurde. 
    * @param table Das Table, in welchem das Ereigniss stattfand 
    * @param column Die column, im Koordinatensystem des TableModels 
    * @param ascending Die Richtung, true = ascending, false = descending 
    */ 
   public void sorted( SortedTable table, int column, boolean ascending ); 
    
   /** 
    * Wird aufgerufen, falls keine Column mehr sortiert ist. 
    * @param table Das Table, in welchem das Ereigniss stattfand 
    */ 
   public void desorted( SortedTable table ); 
}