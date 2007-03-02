package de.kugihan.dictionaryformids.hmi_java_se;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
* Ein JTable, das die Eintr�ge nach den Columns sortieren kann.
* <br><br>
* Wichtig: um eine Column zu sortieren muss ein entsprechender Comparator
* installiert sein <b>und</b> das TableModel muss für die Column die
* richtig Class liefern.
* <br><br>
* Die Methode "getSelectedRow" bezieht sich auf das sortierte Model, nicht
* auf die Originalanordnung. Es kann aber die Methode {@link #getOriginalSelectedRow()}
* benutzt werden, welche sich auf das unsortierte Model bezieht.
*
* @author Benjamin Sigg
* @version 1.0
*/

public class SortedTable extends JTable{
   /** Das TableModel mit den Originaldaten */
   private TableModel model;

   /** Ein TableModel das zwischengeschaltet wird, um die Sortierung vorzunehmen */
   private Model sortModel;

   private Listener listener = new Listener();

   private Vector listeners = new Vector();

   private Hashtable comparators = new Hashtable();

   private boolean reactionResort = true;

   private boolean doNotAlterSortingDirection = true;

   /**
    * Standardkonstruktor
    */
   public SortedTable(){
      sortModel = new Model();
      JTableHeader header = getTableHeader();

      header.addMouseListener( listener );

      TableCellRenderer renderer = header.getDefaultRenderer();
      if( renderer instanceof SortedTableListener )
         addSortedTableListener( (SortedTableListener)renderer );

      createDefaultComparators();
      super.setModel( sortModel );
   }

   /**
    * Konstruktor mit Model mit Daten.
    * @param model Das TableModel, welches Daten enthält.
    */
   public SortedTable(TableModel model){
      this();
      setModel( model );
   }

   /**
    * Konstruktor mit Model mit Daten.
    * @param model Das TableModel, welches Daten enthält
    */
   public SortedTable(SortableTableModel model){
      this();
      setModel( model );
   }

   /**
    * Setzt das TableModel mit den Daten.
    * @param model Das Model
    */
   public void setModel( TableModel model ) {
      if( model == null )
         throw new IllegalArgumentException( "Model must not be null" );

      if( sortModel == null )
         super.setModel( model );
      else{
         TableModel oldModel = this.model;
         this.model = model;
         sortModel.modelChange( oldModel, model );
      }
   }

   /**
    * Gibt das Model zurück, welches die Daten liefert.
    * @return Das Model
    */
   public TableModel getOriginalModel() {
      return model;
   }

   /**
    * Gibt den Index der selektierten Rows, im Koordinatensystem des
    * Original-Models zur�ck.
    * @return Der Index
    *
    */
   public int getOriginalSelectedRow() {
      int row = getSelectedRow();
      if( row != -1 )
         row = sortModel.convertSortToOriginal( row );

      return row;
   }

   /**
    * Gibt die Indices der selektierten Rows zurück.
    * @return Die Indices im Koordinatensystem des Original-Models
    */
   public int[] getOriginalSelectedRows() {
      int[] rows = getSelectedRows();

      for( int i = 0, n = rows.length; i < n; i++ )
         rows[i] = sortModel.convertSortToOriginal( rows[i] );

      return rows;
   }

   /**
    * F�gt die angegeben Rows des Original-Models der Selektion hinzu.
    * @param index0 Der erste Index, inklusive
    * @param index1 Der zweite Index, inklusive
    */
   public void addOriginalRowSelectionIntervall( int index0, int index1 ) {
      int min = Math.min( index0, index1 );
      int max = Math.max( index0, index1 );

      for( int i = min; i <= max; i++ ){
         int index = sortModel.convertSortToOriginal( i );
         addRowSelectionInterval( index, index );
      }
   }

   /**
    * Gibt an, ob Standardmässig neu sortiert werden soll, falls das Model
    * Veränderungen meldet. Ist dieser Wert falsch, kann das die Sortierung bei
    * jeder Veränderung des Models verloren gehen. Die ursprüngliche Sortierung
    * des Original-Models wird verwendet.
    * @param resort true, falls neu Sortiert werden soll, andernfalls false
    */
   public void setResortOnModelChange( boolean resort ) {
      reactionResort = resort;
   }

   /**
    * Setzt den Comparator für diese spezielle Klasse.
    * @param clazz Die Klasse
    * @param comparator Der zu benutzende Comparator
    */
   public void setDefaultComparator( Class clazz, Comparator comparator ) {
      comparators.put( clazz, comparator );
   }

   /**
    * Gibt den Comparator der angegebenen Column zurück.
    * @param column Index der Column
    * @return Der Comparator
    */
   public Comparator getComparator( int column ) {
      return getComparator( model.getColumnClass( column ) );
   }

   /**
    * Gibt den Comparator f�r eine Klasse zurück.
    * @param clazz Die Klasse
    * @return Der Comparator
    */
   public Comparator getComparator( Class clazz ) {
      Comparator comp = internalGetComparator( clazz );
      if( comp == null ){
         Class[] interfaces = clazz.getInterfaces();
         Class comparable = Comparable.class;
         for( int i = 0, n = interfaces.length; i < n; i++ ){
            if( interfaces[i].equals( comparable ) )
               return internalGetComparator( comparable );
         }
      }

      return comp;
   }

   private Comparator internalGetComparator( Class clazz ) {
      if( clazz != null ){
         Object value = comparators.get( clazz );
         if( value == null ){
            return internalGetComparator( clazz.getSuperclass() );
         }
         else
            return (Comparator)value;
      }
      else
         return null;
   }

   /**
    * Wird diese Option aktiviert, so wird bei dem Klick auf ein Header
    * die Sortierrichtung nur verändert, wenn dieser Header bereits
    * sortiert ist.
    * @param doNotAlterSortingDirection <code>true</code> wenn die
    * Sortierrichtung möglichst nicht verändert werden soll.
    */
   public void setDoNotAlterSortingDirection(
         boolean doNotAlterSortingDirection ) {
      this.doNotAlterSortingDirection = doNotAlterSortingDirection;
   }

   /**
    * Gibt an, ob die Sortierrichtung nicht verändert werden soll.
    * @return <code>true</code> wenn die Richtung möglichst nicht
    * verändert werden soll.
    * @see #setResortOnModelChange(boolean)
    */
   public boolean isDoNotAlterSortingDirection() {
      return doNotAlterSortingDirection;
   }

   /**
    * Gibt an, ob diese Column sortierbar ist. Sollte ein SortableTableModel
    * verwendet werden, wird dieses Model gefragt, andernfalls wird ein
    * Comparator zur columnClass gesucht, und falls einer gefunden wird, wird
    * true zurückgegeben.
    * @param column Der Index der Column, im TableModel-System
    * @return true, falls die Column sortierbar ist
    */
   public boolean isColumnSortable( int column ) {
      if( model != null ){
         if( model instanceof SortableTableModel )
            return ((SortableTableModel)model).isColumnSortable( column );
         else
            return getComparator( model.getColumnClass( column ) ) != null;
      }
      else
         return false;
   }

   /**
    * Setzt den JTableHeader.
    * @param tableHeader der Header
    */
   public void setTableHeader( JTableHeader tableHeader ) {
      if( tableHeader == null )
         throw new IllegalArgumentException( "Header must not be null" );

      JTableHeader oldHeader = getTableHeader();
      if( oldHeader != null ){
         oldHeader.removeMouseListener( listener );
         TableCellRenderer renderer = oldHeader.getDefaultRenderer();
         if( renderer instanceof SortedTableListener ){
            removeSortedTableListener( (SortedTableListener)renderer );
         }
      }

      TableCellRenderer renderer = tableHeader.getDefaultRenderer();
      if( renderer instanceof SortedTableListener ){
         removeSortedTableListener( (SortedTableListener)renderer );
      }

      super.setTableHeader( tableHeader );
      tableHeader.addMouseListener( listener );
   }

   /**
    * F�gt einen SortedTableListener hinzu.
    * @param listener Der Listener
    */
   public void addSortedTableListener( SortedTableListener listener ) {
      if( listeners != null )
         listeners.add( listener );
   }

   /**
    * Entfernt einen SortedTableListener.
    * @param listener Der Listener
    */
   public void removeSortedTableListener( SortedTableListener listener ) {
      if( listeners != null )
         listeners.remove( listener );
   }

   /**
    * Verschickt eine Nachricht an alle Listeners, dass das Table sortiert
    * wurde.
    * @param column Die Column
    * @param ascending true, falls aufsteigend sortiert wurde
    */
   protected void fireSorted( int column, boolean ascending ) {
      for( int i = 0, n = listeners.size(); i < n; i++ )
         ((SortedTableListener)listeners.get( i )).sorted( this, column, ascending );

      sortModel.fireTableDataChanged();
      getTableHeader().repaint();
   }

   /**
    * Verschickt eine Nachricht an alle Listeners, dass das Table nicht mehr
    * sortiert ist.
    */
   protected void fireDesorted() {
      for( int i = 0, n = listeners.size(); i < n; i++ )
         ((SortedTableListener)listeners.get( i )).desorted( this );

      getTableHeader().repaint();
   }

   /**
    * Hebt die Anzeige der Sortierung auf, und es gibt keine keine Reaktion,
    * falls etwas neues hinzugefügt wird.
    */
   public void desort() {
      sortModel.desort();
   }

   /**
    * Setzt alle Standardcomperatoren.
    */

   protected void createDefaultComparators() {
      setDefaultComparator( String.class, Collator.getInstance() );
      setDefaultComparator( Comparable.class, new ComparableComparator() );
   }

   /**
    * Stellt das normale JTableHeader her. Dieser Header benutzt ein
    * {@link DefaultSortTableHeaderRenderer}.<br>
    * Wann immer diesem Header ein anderer Renderer gesetzt wird, wird dieser
    * Renderer in ein DefaultSortTableHeaderRenderer verpackt.
    */
   protected JTableHeader createDefaultTableHeader() {
      JTableHeader header = new JTableHeader( super.getColumnModel() ){
         public void setDefaultRenderer( TableCellRenderer defaultRenderer ) {
            TableCellRenderer old = getDefaultRenderer();
            if( old instanceof SortedTableListener && SortedTable.this != null )
               removeSortedTableListener( (SortedTableListener)old );

            DefaultSortTableHeaderRenderer renderer =
               new DefaultSortTableHeaderRenderer.UIResource( defaultRenderer );

            if( SortedTable.this != null )
               addSortedTableListener( renderer );

            super.setDefaultRenderer( renderer );
         }
      };

      header.setColumnModel( this.getColumnModel() );
      return header;
   }

   /**
    * Sortiert den Inhalt des Tables nach der angegebenen Column.
    * @param column Der Index der zu sortierenden Column.
    * @param ascendent True, falls aufsteigend sortiert werden soll, f�r
    *            absteigendes Sortieren false
    */
   public void sort( int column, boolean ascendent ) {
      sortModel.sort( column, ascendent );
   }

   /**
    * Dieser MouseListener wird dem JTableHeader hinzugef�gt, um die zu
    * sortierende Column herauszufinden.
    */
   private class Listener extends MouseAdapter{
      public Listener(){

      }

      public void mouseClicked( MouseEvent e ) {
         JTableHeader header = (JTableHeader)e.getSource();
         int column = convertColumnIndexToModel( header.columnAtPoint( e
               .getPoint() ) );

         // System.out.println( column + " " + convertColumnIndexToModel(
         // column )
         // + " " + convertColumnIndexToView( column ));

         if( column >= 0 && isColumnSortable( column ) ){
            sortModel.sort( column );
         }
      }
   }

   /**
    * Dieses Model steht zwischen dem tats�chlichen Model, und der JTable. Es
    * sorgt f�r die Sortierung, indem es Rows vertauscht zur�ckgibt.
    */
   private class Model extends AbstractTableModel implements
         TableModelListener{
      private Row[] rows = new Row[0];

      private int rowCount = 0;

      private int column = -1;

      private boolean ascendent = false;

      /**
       * Standardkonstruktor
       */
      public Model(){
      }

      /**
       * Sortiert die angegebene Column.
       * @param column Der Index der Column
       */
      public void sort( int column ) {
         if( column == this.column ){
            ascendent = !ascendent;

            for( int i = 0, j = rowCount - 1; i < j; i++, j-- ){
               Row temp = rows[i];
               rows[i] = rows[j];
               rows[j] = temp;
            }

            fireTableDataChanged();
            fireSorted( column, ascendent );
         }
         else{
            if( doNotAlterSortingDirection )
               sort( column, ascendent );
            else
               sort( column, true );
         }
      }

      /**
       * Sortiert die aktuelle Column neu.
       */
      private void resort() {
         sort( column, ascendent );
      }

      /**
       * Sortiert die angegebene Column.
       * @param column Der Index der Column
       * @param ascendent true, falls aufsteigend sortiert werden soll, false
       *            sofern absteigend sortiert werden soll.
       */
      public synchronized void sort( int column, boolean ascendent ) {
         this.column = column;
         this.ascendent = ascendent;

         if( rowCount > 1 ){
            Arrays.sort( rows, 0, rowCount, new RowComparator() );
            fireTableChanged( new TableModelEvent( this, 0, rowCount - 1,
                  TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE ) );
         }
         fireSorted( column, ascendent );
      }

      /**
       * Hebt die Sortierung auf. Alle Rows bleiben an ihrer aktuellen
       * Position und werden nicht mehr weiterbewegt.
       */
      public void desort() {
         column = -1;
         fireDesorted();
      }

      /**
       * Wird aufgerufen, falls das TableModel mit den Daten ausgetauscht
       * wurde.
       * @param oldModel Das alte Model
       * @param newModel Das neue Model
       */
      public synchronized void modelChange( TableModel oldModel,
            TableModel newModel ) {
         if( oldModel != null )
            oldModel.removeTableModelListener( this );
         newModel.addTableModelListener( this );

         ensureAllInArray( newModel );

         fireTableStructureChanged();
         desort();
      }

      /**
       * �berpr�ft und versichert, dass alle Rows des Models gespeichert sind.
       * @param model Das Model, dessen Rows gespeichert sein sollen.
       */
      private void ensureAllInArray( TableModel model ) {
         int size = model.getRowCount();
         ensureRowArray( size );

         for( int i = 0; i < size; i++ ){
            if( rows[i] == null )
               rows[i] = new Row( i );
            else
               rows[i].setIndex( i );
         }

         rowCount = size;
      }

      /**
       * Versichert, dass der Row-Array genug lang f�r <code>capacity</code>
       * viele Elemente ist.
       * @param capacity Die minimale L�nge des Arrays
       */
      private void ensureRowArray( int capacity ) {
         if( rows.length < capacity ){
            Row[] array = new Row[Math.max( capacity, rows.length * 2 ) + 1];
            System.arraycopy( rows, 0, array, 0, rowCount );
            rows = array;
         }
      }

      /**
       * Wandelt den Row-Index des Original-Models in einen Row-Index dieses
       * Models um.
       * @param row Der originale Index
       * @return Der echte Index
       */
      public int convertSortToOriginal( int row ) {
         return rows[row].getIndex();
      }

      /**
       * Wandelt den aktuellen Row-Index in den Row-Index des Originals um.
       * @param row Der echte Index
       * @return Der originale Index
       */
      public int convertOriginalToSort( int row ) {
         for( int i = 0; i < rowCount; i++ )
            if( rows[i].getIndex() == row )
               return i;

         return -1;
      }

      public Class getColumnClass( int columnIndex ) {
         return model.getColumnClass( columnIndex );
      }

      public int getColumnCount() {
         if( model == null )
            return 0;
         else
            return model.getColumnCount();
      }

      public String getColumnName( int columnIndex ) {
         return model.getColumnName( columnIndex );
      }

      public int getRowCount() {
         if( model == null )
            return 0;
         else
            return model.getRowCount();
      }

      public Object getValueAt( int rowIndex, int columnIndex ) {
         return model.getValueAt( rows[rowIndex].getIndex(), columnIndex );
      }

      public boolean isCellEditable( int rowIndex, int columnIndex ) {
         return model
               .isCellEditable( rows[rowIndex].getIndex(), columnIndex );
      }

      public void setValueAt( Object aValue, int rowIndex, int columnIndex ) {
         model.setValueAt( aValue, rows[rowIndex].getIndex(), columnIndex );
      }

      /**
       * Wird aufgerufen, falls das Original-Model ver�ndert wurde.
       * @param e Das Event
       */
      public void tableChanged( TableModelEvent e ) {
         int columns = model.getColumnCount();

         if( e.getFirstRow() == TableModelEvent.HEADER_ROW ){
            ensureAllInArray( model );
            fireTableStructureChanged();
         }
         else if( e.getType() != TableModelEvent.UPDATE || rowCount != model.getRowCount()  ){
            int delta =  model.getRowCount() - rowCount;
            int oldRows = rowCount;
            ensureAllInArray( model );

            if( delta > 0 )
               fireTableRowsInserted( oldRows, model.getRowCount()-1 );
            else if( delta < 0 )
               fireTableRowsDeleted( model.getRowCount(), oldRows-1 );
         }

         if( reactionResort && column >= 0 && column < columns )
            resort();
         else if( column != -1 ){
            desort();
            if( rowCount > 0 )
               fireTableRowsUpdated( 0, rowCount-1 );
         }
      }

      /**
       * Eine "Wrapper"-Klasse f�r Rows. Jede Row enth�lt einen Index zu einer
       * Original-Row.
       */
      private class Row{
         private int index;

         public Row(int index){
            this.index = index;
         }

         public int getIndex() {
            return index;
         }

         public void setIndex( int index ) {
            this.index = index;
         }
      }

      /**
       * Vergleicht zwei Rows. Dabei wird wann immer m�glich eine Reihenfolge
       * erstellt.
       */
      private class RowComparator implements Comparator{
         private int columns;

         private Comparator[] comparators;

         public RowComparator(){
            columns = model.getColumnCount();
            comparators = new Comparator[columns];
         }

         public int compare( Object o1, Object o2 ) {

            Row a = (Row)o1;
            Row b = (Row)o2;

            if( comparators[column] == null )
               comparators[column] = getComparator( column );

            int result = compare( comparators[column], a, b, column );

            if( result == 0 ){
               int index = 0;

               while( index < columns && result == 0 ){
                  if( index != column ){
                     if( isColumnSortable( index ) ){
                        if( comparators[index] == null )
                           comparators[index] = getComparator( index );

                        result = compare( comparators[index], a, b,
                              index );
                     }
                  }
                  index++;
               }
            }

            return result;
         }

         private int compare( Comparator comparator, Row a, Row b, int column ) {
            if( ascendent )
               return comparator.compare( model.getValueAt( a.getIndex(),
                     column ), model.getValueAt( b.getIndex(), column ) );
            else
               return comparator.compare( model.getValueAt( b.getIndex(),
                     column ), model.getValueAt( a.getIndex(), column ) );
         }
      }
   }

   /**
    * Standardcomparator der zwei Comparables vergleicht, indem er ihre
    * <code>compareTo</code> Methoden benutzt.
    */
   private class ComparableComparator implements Comparator{
      public int compare( Object o1, Object o2 ) {
         return ((Comparable)o1).compareTo( o2 );
      }
   }
}