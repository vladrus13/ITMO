package info.kgeorgiy.java.advanced.implementor.full.interfaces.standard;

import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetMetaData;
import javax.sql.rowset.Joinable;
import javax.sql.rowset.RowSetWarning;
import javax.sql.rowset.spi.SyncProvider;
import javax.sql.rowset.spi.SyncProviderException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Collection;

/**
 * Copy of {@link javax.sql.rowset.CachedRowSet}
 */
public interface CachedRowSet extends RowSet, Joinable {

    /**
     * Populates this <code>CachedRowSet</code> object with data from
     * the given <code>ResultSet</code> object.
     * <P>
     * This method can be used as an alternative to the <code>execute</code> method when an
     * application has a connection to an open <code>ResultSet</code> object.
     * Using the method <code>populate</code> can be more efficient than using
     * the version of the <code>execute</code> method that takes no parameters
     * because it does not open a new connection and re-execute this
     * <code>CachedRowSet</code> object's command. Using the <code>populate</code>
     * method is more a matter of convenience when compared to using the version
     * of <code>execute</code> that takes a <code>ResultSet</code> object.
     *
     * @param data the <code>ResultSet</code> object containing the data
     * to be read into this <code>CachedRowSet</code> object
     * @throws SQLException if a null <code>ResultSet</code> object is supplied
     * or this <code>CachedRowSet</code> object cannot
     * retrieve the associated <code>ResultSetMetaData</code> object
     * @see #execute
     * @see java.sql.ResultSet
     * @see java.sql.ResultSetMetaData
     */
    public void populate(ResultSet data) throws SQLException;

    /**
     * Populates this <code>CachedRowSet</code> object with data, using the
     * given connection to produce the result set from which the data will be read.
     * This method should close any database connections that it creates to
     * ensure that this <code>CachedRowSet</code> object is disconnected except when
     * it is reading data from its data source or writing data to its data source.
     * <P>
     * The reader for this <code>CachedRowSet</code> object
     * will use <i>conn</i> to establish a connection to the data source
     * so that it can execute the rowset's command and read data from the
     * the resulting <code>ResultSet</code> object into this
     * <code>CachedRowSet</code> object. This method also closes <i>conn</i>
     * after it has populated this <code>CachedRowSet</code> object.
     * <P>
     * If this method is called when an implementation has already been
     * populated, the contents and the metadata are (re)set. Also, if this method is
     * called before the method <code>acceptChanges</code> has been called
     * to commit outstanding updates, those updates are lost.
     *
     * @param conn a standard JDBC <code>Connection</code> object with valid
     * properties
     * @throws SQLException if an invalid <code>Connection</code> object is supplied
     * or an error occurs in establishing the connection to the
     * data source
     * @see #populate
     * @see java.sql.Connection
     */
    public void execute(Connection conn) throws SQLException;

    /**
     * Propagates row update, insert and delete changes made to this
     * <code>CachedRowSet</code> object to the underlying data source.
     * <P>
     * This method calls on this <code>CachedRowSet</code> object's writer
     * to do the work behind the scenes.
     * Standard <code>CachedRowSet</code> implementations should use the
     * <code>SyncFactory</code> singleton
     * to obtain a <code>SyncProvider</code> instance providing a
     * <code>RowSetWriter</code> object (writer).  The writer will attempt
     * to propagate changes made in this <code>CachedRowSet</code> object
     * back to the data source.
     * <P>
     * When the method <code>acceptChanges</code> executes successfully, in
     * addition to writing changes to the data source, it
     * makes the values in the current row be the values in the original row.
     * <P>
     * Depending on the synchronization level of the <code>SyncProvider</code>
     * implementation being used, the writer will compare the original values
     * with those in the data source to check for conflicts. When there is a conflict,
     * the <code>RIOptimisticProvider</code> implementation, for example, throws a
     * <code>SyncProviderException</code> and does not write anything to the
     * data source.
     * <P>
     * An application may choose to catch the <code>SyncProviderException</code>
     * object and retrieve the <code>SyncResolver</code> object it contains.
     * The <code>SyncResolver</code> object lists the conflicts row by row and
     * sets a lock on the data source to avoid further conflicts while the
     * current conflicts are being resolved.
     * Further, for each conflict, it provides methods for examining the conflict
     * and setting the value that should be persisted in the data source.
     * After all conflicts have been resolved, an application must call the
     * <code>acceptChanges</code> method again to write resolved values to the
     * data source.  If all of the values in the data source are already the
     * values to be persisted, the method <code>acceptChanges</code> does nothing.
     * <P>
     * Some provider implementations may use locks to ensure that there are no
     * conflicts.  In such cases, it is guaranteed that the writer will succeed in
     * writing changes to the data source when the method <code>acceptChanges</code>
     * is called.  This method may be called immediately after the methods
     * <code>updateRow</code>, <code>insertRow</code>, or <code>deleteRow</code>
     * have been called, but it is more efficient to call it only once after
     * all changes have been made so that only one connection needs to be
     * established.
     * <P>
     * Note: The <code>acceptChanges()</code> method will determine if the
     * <code>COMMIT_ON_ACCEPT_CHANGES</code> is set to true or not. If it is set
     * to true, all updates in the synchronization are committed to the data
     * source. Otherwise, the application <b>must</b> explicitly call the
     * <code>commit()</code> or <code>rollback()</code> methods as appropriate.
     *
     * @throws SyncProviderException if the underlying
     * synchronization provider's writer fails to write the updates
     * back to the data source
     * @see #acceptChanges(java.sql.Connection)
     * @see javax.sql.RowSetWriter
     * @see javax.sql.rowset.spi.SyncFactory
     * @see javax.sql.rowset.spi.SyncProvider
     * @see javax.sql.rowset.spi.SyncProviderException
     * @see javax.sql.rowset.spi.SyncResolver
     */
    public void acceptChanges() throws SyncProviderException;

    /**
     * Propagates all row update, insert and delete changes to the
     * data source backing this <code>CachedRowSet</code> object
     * using the specified <code>Connection</code> object to establish a
     * connection to the data source.
     * <P>
     * The other version of the <code>acceptChanges</code> method is not passed
     * a connection because it uses
     * the <code>Connection</code> object already defined within the <code>RowSet</code>
     * object, which is the connection used for populating it initially.
     * <P>
     * This form of the method <code>acceptChanges</code> is similar to the
     * form that takes no arguments; however, unlike the other form, this form
     * can be used only when the underlying data source is a JDBC data source.
     * The updated <code>Connection</code> properties must be used by the
     * <code>SyncProvider</code> to reset the <code>RowSetWriter</code>
     * configuration to ensure that the contents of the <code>CachedRowSet</code>
     * object are synchronized correctly.
     * <P>
     * When the method <code>acceptChanges</code> executes successfully, in
     * addition to writing changes to the data source, it
     * makes the values in the current row be the values in the original row.
     * <P>
     * Depending on the synchronization level of the <code>SyncProvider</code>
     * implementation being used, the writer will compare the original values
     * with those in the data source to check for conflicts. When there is a conflict,
     * the <code>RIOptimisticProvider</code> implementation, for example, throws a
     * <code>SyncProviderException</code> and does not write anything to the
     * data source.
     * <P>
     * An application may choose to catch the <code>SyncProviderException</code>
     * object and retrieve the <code>SyncResolver</code> object it contains.
     * The <code>SyncResolver</code> object lists the conflicts row by row and
     * sets a lock on the data source to avoid further conflicts while the
     * current conflicts are being resolved.
     * Further, for each conflict, it provides methods for examining the conflict
     * and setting the value that should be persisted in the data source.
     * After all conflicts have been resolved, an application must call the
     * <code>acceptChanges</code> method again to write resolved values to the
     * data source.  If all of the values in the data source are already the
     * values to be persisted, the method <code>acceptChanges</code> does nothing.
     * <P>
     * Some provider implementations may use locks to ensure that there are no
     * conflicts.  In such cases, it is guaranteed that the writer will succeed in
     * writing changes to the data source when the method <code>acceptChanges</code>
     * is called.  This method may be called immediately after the methods
     * <code>updateRow</code>, <code>insertRow</code>, or <code>deleteRow</code>
     * have been called, but it is more efficient to call it only once after
     * all changes have been made so that only one connection needs to be
     * established.
     * <P>
     * Note: The <code>acceptChanges()</code> method will determine if the
     * <code>COMMIT_ON_ACCEPT_CHANGES</code> is set to true or not. If it is set
     * to true, all updates in the synchronization are committed to the data
     * source. Otherwise, the application <b>must</b> explicitly call the
     * <code>commit</code> or <code>rollback</code> methods as appropriate.
     *
     * @param con a standard JDBC <code>Connection</code> object
     * @throws SyncProviderException if the underlying
     * synchronization provider's writer fails to write the updates
     * back to the data source
     * @see #acceptChanges()
     * @see javax.sql.RowSetWriter
     * @see javax.sql.rowset.spi.SyncFactory
     * @see javax.sql.rowset.spi.SyncProvider
     * @see javax.sql.rowset.spi.SyncProviderException
     * @see javax.sql.rowset.spi.SyncResolver
     */
    public void acceptChanges(Connection con) throws SyncProviderException;

    /**
     * Restores this <code>CachedRowSet</code> object to its original
     * value, that is, its value before the last set of changes. If there
     * have been no changes to the rowset or only one set of changes,
     * the original value is the value with which this <code>CachedRowSet</code> object
     * was populated; otherwise, the original value is
     * the value it had immediately before its current value.
     * <P>
     * When this method is called, a <code>CachedRowSet</code> implementation
     * must ensure that all updates, inserts, and deletes to the current
     * rowset instance are replaced by the previous values. In addition,
     * the cursor should be
     * reset to the first row and a <code>rowSetChanged</code> event
     * should be fired to notify all registered listeners.
     *
     * @throws SQLException if an error occurs rolling back the current value of
     *       this <code>CachedRowSet</code> object to its previous value
     * @see javax.sql.RowSetListener#rowSetChanged
     */
    public void restoreOriginal() throws SQLException;

    /**
     * Releases the current contents of this <code>CachedRowSet</code>
     * object and sends a <code>rowSetChanged</code> event to all
     * registered listeners. Any outstanding updates are discarded and
     * the rowset contains no rows after this method is called. There
     * are no interactions with the underlying data source, and any rowset
     * content, metadata, and content updates should be non-recoverable.
     * <P>
     * This <code>CachedRowSet</code> object should lock until its contents and
     * associated updates are fully cleared, thus preventing 'dirty' reads by
     * other components that hold a reference to this <code>RowSet</code> object.
     * In addition, the contents cannot be released
     * until all components reading this <code>CachedRowSet</code> object
     * have completed their reads. This <code>CachedRowSet</code> object
     * should be returned to normal behavior after firing the
     * <code>rowSetChanged</code> event.
     * <P>
     * The metadata, including JDBC properties and Synchronization SPI
     * properties, are maintained for future use. It is important that
     * properties such as the <code>command</code> property be
     * relevant to the originating data source from which this <code>CachedRowSet</code>
     * object was originally established.
     * <P>
     * This method empties a rowset, as opposed to the <code>close</code> method,
     * which marks the entire rowset as recoverable to allow the garbage collector
     * the rowset's Java VM resources.
     *
     * @throws SQLException if an error occurs flushing the contents of this
     * <code>CachedRowSet</code> object
     * @see javax.sql.RowSetListener#rowSetChanged
     * @see java.sql.ResultSet#close
     */
    public void release() throws SQLException;

    /**
     * Cancels the deletion of the current row and notifies listeners that
     * a row has changed. After this method is called, the current row is
     * no longer marked for deletion. This method can be called at any
     * time during the lifetime of the rowset.
     * <P>
     * In addition, multiple cancellations of row deletions can be made
     * by adjusting the position of the cursor using any of the cursor
     * position control methods such as:
     * <ul>
     * <li><code>CachedRowSet.absolute</code>
     * <li><code>CachedRowSet.first</code>
     * <li><code>CachedRowSet.last</code>
     * </ul>
     *
     * @throws SQLException if (1) the current row has not been deleted or
     * (2) the cursor is on the insert row, before the first row, or
     * after the last row
     * @see javax.sql.rowset.CachedRowSet#undoInsert
     * @see java.sql.ResultSet#cancelRowUpdates
     */
    public void undoDelete() throws SQLException;

    /**
     * Immediately removes the current row from this <code>CachedRowSet</code>
     * object if the row has been inserted, and also notifies listeners that a
     * row has changed. This method can be called at any time during the
     * lifetime of a rowset and assuming the current row is within
     * the exception limitations (see below), it cancels the row insertion
     * of the current row.
     * <P>
     * In addition, multiple cancellations of row insertions can be made
     * by adjusting the position of the cursor using any of the cursor
     * position control methods such as:
     * <ul>
     * <li><code>CachedRowSet.absolute</code>
     * <li><code>CachedRowSet.first</code>
     * <li><code>CachedRowSet.last</code>
     * </ul>
     *
     * @throws SQLException if (1) the current row has not been inserted or (2)
     * the cursor is before the first row, after the last row, or on the
     * insert row
     * @see javax.sql.rowset.CachedRowSet#undoDelete
     * @see java.sql.ResultSet#cancelRowUpdates
     */
    public void undoInsert() throws SQLException;


    /**
     * Immediately reverses the last update operation if the
     * row has been modified. This method can be
     * called to reverse updates on all columns until all updates in a row have
     * been rolled back to their state just prior to the last synchronization
     * (<code>acceptChanges</code>) or population. This method may also be called
     * while performing updates to the insert row.
     * <P>
     * <code>undoUpdate</code> may be called at any time during the lifetime of a
     * rowset; however, after a synchronization has occurred, this method has no
     * effect until further modification to the rowset data has occurred.
     *
     * @throws SQLException if the cursor is before the first row or after the last
     *     row in this <code>CachedRowSet</code> object
     * @see #undoDelete
     * @see #undoInsert
     * @see java.sql.ResultSet#cancelRowUpdates
     */
    public void undoUpdate() throws SQLException;

    /**
     * Indicates whether the designated column in the current row of this
     * <code>CachedRowSet</code> object has been updated.
     *
     * @param idx an <code>int</code> identifying the column to be checked for updates
     * @return <code>true</code> if the designated column has been visibly updated;
     * <code>false</code> otherwise
     * @throws SQLException if the cursor is on the insert row, before the first row,
     *     or after the last row
     * @see java.sql.DatabaseMetaData#updatesAreDetected
     */
    public boolean columnUpdated(int idx) throws SQLException;


    /**
     * Indicates whether the designated column in the current row of this
     * <code>CachedRowSet</code> object has been updated.
     *
     * @param columnName a <code>String</code> object giving the name of the
     *        column to be checked for updates
     * @return <code>true</code> if the column has been visibly updated;
     * <code>false</code> otherwise
     * @throws SQLException if the cursor is on the insert row, before the first row,
     *      or after the last row
     * @see java.sql.DatabaseMetaData#updatesAreDetected
     */
    public boolean columnUpdated(String columnName) throws SQLException;

    /**
     * Converts this <code>CachedRowSet</code> object to a <code>Collection</code>
     * object that contains all of this <code>CachedRowSet</code> object's data.
     * Implementations have some latitude in
     * how they can represent this <code>Collection</code> object because of the
     * abstract nature of the <code>Collection</code> framework.
     * Each row must be fully represented in either a
     * general purpose <code>Collection</code> implementation or a specialized
     * <code>Collection</code> implementation, such as a <code>TreeMap</code>
     * object or a <code>Vector</code> object.
     * An SQL <code>NULL</code> column value must be represented as a <code>null</code>
     * in the Java programming language.
     * <P>
     * The standard reference implementation for the <code>CachedRowSet</code>
     * interface uses a <code>TreeMap</code> object for the rowset, with the
     * values in each row being contained in  <code>Vector</code> objects. It is
     * expected that most implementations will do the same.
     * <P>
     * The <code>TreeMap</code> type of collection guarantees that the map will be in
     * ascending key order, sorted according to the natural order for the
     * key's class.
     * Each key references a <code>Vector</code> object that corresponds to one
     * row of a <code>RowSet</code> object. Therefore, the size of each
     * <code>Vector</code> object  must be exactly equal to the number of
     * columns in the <code>RowSet</code> object.
     * The key used by the <code>TreeMap</code> collection is determined by the
     * implementation, which may choose to leverage a set key that is
     * available within the internal <code>RowSet</code> tabular structure by
     * virtue of a key already set either on the <code>RowSet</code> object
     * itself or on the underlying SQL data.
     *
     * @return a <code>Collection</code> object that contains the values in
     * each row in this <code>CachedRowSet</code> object
     * @throws SQLException if an error occurs generating the collection
     * @see #toCollection(int)
     * @see #toCollection(String)
     */
    public Collection<?> toCollection() throws SQLException;

    /**
     * Converts the designated column in this <code>CachedRowSet</code> object
     * to a <code>Collection</code> object. Implementations have some latitude in
     * how they can represent this <code>Collection</code> object because of the
     * abstract nature of the <code>Collection</code> framework.
     * Each column value should be fully represented in either a
     * general purpose <code>Collection</code> implementation or a specialized
     * <code>Collection</code> implementation, such as a <code>Vector</code> object.
     * An SQL <code>NULL</code> column value must be represented as a <code>null</code>
     * in the Java programming language.
     * <P>
     * The standard reference implementation uses a <code>Vector</code> object
     * to contain the column values, and it is expected
     * that most implementations will do the same. If a <code>Vector</code> object
     * is used, it size must be exactly equal to the number of rows
     * in this <code>CachedRowSet</code> object.
     *
     * @param column an <code>int</code> indicating the column whose values
     *        are to be represented in a <code>Collection</code> object
     * @return a <code>Collection</code> object that contains the values
     * stored in the specified column of this <code>CachedRowSet</code>
     * object
     * @throws SQLException if an error occurs generating the collection or
     * an invalid column id is provided
     * @see #toCollection
     * @see #toCollection(String)
     */
    public Collection<?> toCollection(int column) throws SQLException;

    /**
     * Converts the designated column in this <code>CachedRowSet</code> object
     * to a <code>Collection</code> object. Implementations have some latitude in
     * how they can represent this <code>Collection</code> object because of the
     * abstract nature of the <code>Collection</code> framework.
     * Each column value should be fully represented in either a
     * general purpose <code>Collection</code> implementation or a specialized
     * <code>Collection</code> implementation, such as a <code>Vector</code> object.
     * An SQL <code>NULL</code> column value must be represented as a <code>null</code>
     * in the Java programming language.
     * <P>
     * The standard reference implementation uses a <code>Vector</code> object
     * to contain the column values, and it is expected
     * that most implementations will do the same. If a <code>Vector</code> object
     * is used, it size must be exactly equal to the number of rows
     * in this <code>CachedRowSet</code> object.
     *
     * @param column a <code>String</code> object giving the name of the
     *        column whose values are to be represented in a collection
     * @return a <code>Collection</code> object that contains the values
     * stored in the specified column of this <code>CachedRowSet</code>
     * object
     * @throws SQLException if an error occurs generating the collection or
     * an invalid column id is provided
     * @see #toCollection
     * @see #toCollection(int)
     */
    public Collection<?> toCollection(String column) throws SQLException;

    /**
     * Retrieves the <code>SyncProvider</code> implementation for this
     * <code>CachedRowSet</code> object. Internally, this method is used by a rowset
     * to trigger read or write actions between the rowset
     * and the data source. For example, a rowset may need to get a handle
     * on the rowset reader (<code>RowSetReader</code> object) from the
     * <code>SyncProvider</code> to allow the rowset to be populated.
     * <pre>
     *     RowSetReader rowsetReader = null;
     *     SyncProvider provider =
     *         SyncFactory.getInstance("javax.sql.rowset.provider.RIOptimisticProvider");
     *         if (provider instanceof RIOptimisticProvider) {
     *             rowsetReader = provider.getRowSetReader();
     *         }
     * </pre>
     * Assuming <i>rowsetReader</i> is a private, accessible field within
     * the rowset implementation, when an application calls the <code>execute</code>
     * method, it in turn calls on the reader's <code>readData</code> method
     * to populate the <code>RowSet</code> object.
     *<pre>
     *     rowsetReader.readData((RowSetInternal)this);
     * </pre>
     * <P>
     * In addition, an application can use the <code>SyncProvider</code> object
     * returned by this method to call methods that return information about the
     * <code>SyncProvider</code> object, including information about the
     * vendor, version, provider identification, synchronization grade, and locks
     * it currently has set.
     *
     * @return the <code>SyncProvider</code> object that was set when the rowset
     *      was instantiated, or if none was set, the default provider
     * @throws SQLException if an error occurs while returning the
     * <code>SyncProvider</code> object
     * @see #setSyncProvider
     */
    public SyncProvider getSyncProvider() throws SQLException;

    /**
     * Sets the <code>SyncProvider</code> object for this <code>CachedRowSet</code>
     * object to the one specified.  This method
     * allows the <code>SyncProvider</code> object to be reset.
     * <P>
     * A <code>CachedRowSet</code> implementation should always be instantiated
     * with an available <code>SyncProvider</code> mechanism, but there are
     * cases where resetting the <code>SyncProvider</code> object is desirable
     * or necessary. For example, an application might want to use the default
     * <code>SyncProvider</code> object for a time and then choose to use a provider
     * that has more recently become available and better fits its needs.
     * <P>
     * Resetting the <code>SyncProvider</code> object causes the
     * <code>RowSet</code> object to request a new <code>SyncProvider</code> implementation
     * from the <code>SyncFactory</code>. This has the effect of resetting
     * all previous connections and relationships with the originating
     * data source and can potentially drastically change the synchronization
     * behavior of a disconnected rowset.
     *
     * @param provider a <code>String</code> object giving the fully qualified class
     *        name of a <code>SyncProvider</code> implementation
     * @throws SQLException if an error occurs while attempting to reset the
     * <code>SyncProvider</code> implementation
     * @see #getSyncProvider
     */
    public void setSyncProvider(String provider) throws SQLException;

    /**
     * Returns the number of rows in this <code>CachedRowSet</code>
     * object.
     *
     * @return number of rows in the rowset
     */
    public int size();

    /**
     * Sets the metadata for this <code>CachedRowSet</code> object with
     * the given <code>RowSetMetaData</code> object. When a
     * <code>RowSetReader</code> object is reading the contents of a rowset,
     * it creates a <code>RowSetMetaData</code> object and initializes
     * it using the methods in the <code>RowSetMetaData</code> implementation.
     * The reference implementation uses the <code>RowSetMetaDataImpl</code>
     * class. When the reader has completed reading the rowset contents,
     * this method is called internally to pass the <code>RowSetMetaData</code>
     * object to the rowset.
     *
     * @param md a <code>RowSetMetaData</code> object containing
     * metadata about the columns in this <code>CachedRowSet</code> object
     * @throws SQLException if invalid metadata is supplied to the
     * rowset
     */
    public void setMetaData(RowSetMetaData md) throws SQLException;

    /**
     * Returns a <code>ResultSet</code> object containing the original value of this
     * <code>CachedRowSet</code> object.
     * <P>
     * The cursor for the <code>ResultSet</code>
     * object should be positioned before the first row.
     * In addition, the returned <code>ResultSet</code> object should have the following
     * properties:
     * <UL>
     * <LI>ResultSet.TYPE_SCROLL_INSENSITIVE
     * <LI>ResultSet.CONCUR_UPDATABLE
     * </UL>
     * <P>
     * The original value for a <code>RowSet</code> object is the value it had before
     * the last synchronization with the underlying data source.  If there have been
     * no synchronizations, the original value will be the value with which the
     * <code>RowSet</code> object was populated.  This method is called internally
     * when an application calls the method <code>acceptChanges</code> and the
     * <code>SyncProvider</code> object has been implemented to check for conflicts.
     * If this is the case, the writer compares the original value with the value
     * currently in the data source to check for conflicts.
     *
     * @return a <code>ResultSet</code> object that contains the original value for
     *         this <code>CachedRowSet</code> object
     * @throws SQLException if an error occurs producing the
     * <code>ResultSet</code> object
     */
    public ResultSet getOriginal() throws SQLException;

    /**
     * Returns a <code>ResultSet</code> object containing the original value for the
     * current row only of this <code>CachedRowSet</code> object.
     * <P>
     * The cursor for the <code>ResultSet</code>
     * object should be positioned before the first row.
     * In addition, the returned <code>ResultSet</code> object should have the following
     * properties:
     * <UL>
     * <LI>ResultSet.TYPE_SCROLL_INSENSITIVE
     * <LI>ResultSet.CONCUR_UPDATABLE
     * </UL>
     *
     * @return the original result set of the row
     * @throws SQLException if there is no current row
     * @see #setOriginalRow
     */
    public ResultSet getOriginalRow() throws SQLException;

    /**
     * Sets the current row in this <code>CachedRowSet</code> object as the original
     * row.
     * <P>
     * This method is called internally after the any modified values in the current
     * row have been synchronized with the data source. The current row must be tagged
     * as no longer inserted, deleted or updated.
     * <P>
     * A call to <code>setOriginalRow</code> is irreversible.
     *
     * @throws SQLException if there is no current row or an error is
     * encountered resetting the contents of the original row
     * @see #getOriginalRow
     */
    public void setOriginalRow() throws SQLException;

    /**
     * Returns an identifier for the object (table) that was used to
     * create this <code>CachedRowSet</code> object. This name may be set on multiple occasions,
     * and the specification imposes no limits on how many times this
     * may occur or whether standard implementations should keep track
     * of previous table names.
     *
     * @return a <code>String</code> object giving the name of the table that is the
     *         source of data for this <code>CachedRowSet</code> object or <code>null</code>
     *         if no name has been set for the table
     * @throws SQLException if an error is encountered returning the table name
     * @see javax.sql.RowSetMetaData#getTableName
     */
    public String getTableName() throws SQLException;

    /**
     * Sets the identifier for the table from which this <code>CachedRowSet</code>
     * object was derived to the given table name. The writer uses this name to
     * determine which table to use when comparing the values in the data source with the
     * <code>CachedRowSet</code> object's values during a synchronization attempt.
     * The table identifier also indicates where modified values from this
     * <code>CachedRowSet</code> object should be written.
     * <P>
     * The implementation of this <code>CachedRowSet</code> object may obtain the
     * the name internally from the <code>RowSetMetaDataImpl</code> object.
     *
     * @param tabName a <code>String</code> object identifying the table from which this
    <code>CachedRowSet</code> object was derived; cannot be <code>null</code>
     *         but may be an empty string
     * @throws SQLException if an error is encountered naming the table or
     *     <i>tabName</i> is <code>null</code>
     * @see javax.sql.RowSetMetaData#setTableName
     * @see javax.sql.RowSetWriter
     * @see javax.sql.rowset.spi.SyncProvider
     */
    public void setTableName(String tabName) throws SQLException;

    /**
     * Returns an array containing one or more column numbers indicating the columns
     * that form a key that uniquely
     * identifies a row in this <code>CachedRowSet</code> object.
     *
     * @return an array containing the column number or numbers that indicate which columns
     *       constitute a primary key
     *       for a row in this <code>CachedRowSet</code> object. This array should be
     *       empty if no columns are representative of a primary key.
     * @throws SQLException if this <code>CachedRowSet</code> object is empty
     * @see #setKeyColumns
     * @see Joinable#getMatchColumnIndexes
     * @see Joinable#getMatchColumnNames
     */
    public int[] getKeyColumns() throws SQLException;

    /**
     * Sets this <code>CachedRowSet</code> object's <code>keyCols</code>
     * field with the given array of column numbers, which forms a key
     * for uniquely identifying a row in this <code>CachedRowSet</code> object.
     * <p>
     * If a <code>CachedRowSet</code> object becomes part of a <code>JoinRowSet</code>
     * object, the keys defined by this method and the resulting constraints are
     * maintained if the columns designated as key columns also become match
     * columns.
     *
     * @param keys an array of <code>int</code> indicating the columns that form
     *        a primary key for this <code>CachedRowSet</code> object; every
     *        element in the array must be greater than <code>0</code> and
     *        less than or equal to the number of columns in this rowset
     * @throws SQLException if any of the numbers in the given array
     *            are not valid for this rowset
     * @see #getKeyColumns
     * @see Joinable#setMatchColumn(String)
     * @see Joinable#setMatchColumn(int)

     */
    public void setKeyColumns(int[] keys) throws SQLException;


    /**
     * Returns a new <code>RowSet</code> object backed by the same data as
     * that of this <code>CachedRowSet</code> object. In effect, both
     * <code>CachedRowSet</code> objects have a cursor over the same data.
     * As a result, any changes made by a duplicate are visible to the original
     * and to any other duplicates, just as a change made by the original is visible
     * to all of its duplicates. If a duplicate calls a method that changes the
     * underlying data, the method it calls notifies all registered listeners
     * just as it would when it is called by the original <code>CachedRowSet</code>
     * object.
     * <P>
     * In addition, any <code>RowSet</code> object
     * created by this method will have the same properties as this
     * <code>CachedRowSet</code> object. For example, if this <code>CachedRowSet</code>
     * object is read-only, all of its duplicates will also be read-only. If it is
     * changed to be updatable, the duplicates also become updatable.
     * <P>
     * NOTE: If multiple threads access <code>RowSet</code> objects created from
     * the <code>createShared()</code> method, the following behavior is specified
     * to preserve shared data integrity: reads and writes of all
     * shared <code>RowSet</code> objects should be made serially between each
     * object and the single underlying tabular structure.
     *
     * @return a new shared <code>RowSet</code> object that has the same properties
     *         as this <code>CachedRowSet</code> object and that has a cursor over
     *         the same data
     * @throws SQLException if an error occurs or cloning is not
     * supported in the underlying platform
     * @see javax.sql.RowSetEvent
     * @see javax.sql.RowSetListener
     */
    public RowSet createShared() throws SQLException;

    /**
     * Creates a <code>RowSet</code> object that is a deep copy of the data in
     * this <code>CachedRowSet</code> object. In contrast to
     * the <code>RowSet</code> object generated from a <code>createShared</code>
     * call, updates made to the copy of the original <code>RowSet</code> object
     * must not be visible to the original <code>RowSet</code> object. Also, any
     * event listeners that are registered with the original
     * <code>RowSet</code> must not have scope over the new
     * <code>RowSet</code> copies. In addition, any constraint restrictions
     * established must be maintained.
     *
     * @return a new <code>RowSet</code> object that is a deep copy
     * of this <code>CachedRowSet</code> object and is
     * completely independent of this <code>CachedRowSet</code> object
     * @throws SQLException if an error occurs in generating the copy of
     * the of this <code>CachedRowSet</code> object
     * @see #createShared
     * @see #createCopySchema
     * @see #createCopyNoConstraints
     * @see javax.sql.RowSetEvent
     * @see javax.sql.RowSetListener
     */
    public javax.sql.rowset.CachedRowSet createCopy() throws SQLException;

    /**
     * Creates a <code>CachedRowSet</code> object that is an empty copy of this
     * <code>CachedRowSet</code> object.  The copy
     * must not contain any contents but only represent the table
     * structure of the original <code>CachedRowSet</code> object. In addition, primary
     * or foreign key constraints set in the originating <code>CachedRowSet</code> object must
     * be equally enforced in the new empty <code>CachedRowSet</code> object.
     * In contrast to
     * the <code>RowSet</code> object generated from a <code>createShared</code> method
     * call, updates made to a copy of this <code>CachedRowSet</code> object with the
     * <code>createCopySchema</code> method must not be visible to it.
     * <P>
     * Applications can form a <code>WebRowSet</code> object from the <code>CachedRowSet</code>
     * object returned by this method in order
     * to export the <code>RowSet</code> schema definition to XML for future use.
     * @return An empty copy of this {@code CachedRowSet} object
     * @throws SQLException if an error occurs in cloning the structure of this
     *         <code>CachedRowSet</code> object
     * @see #createShared
     * @see #createCopySchema
     * @see #createCopyNoConstraints
     * @see javax.sql.RowSetEvent
     * @see javax.sql.RowSetListener
     */
    public javax.sql.rowset.CachedRowSet createCopySchema() throws SQLException;

    /**
     * Creates a <code>CachedRowSet</code> object that is a deep copy of
     * this <code>CachedRowSet</code> object's data but is independent of it.
     * In contrast to
     * the <code>RowSet</code> object generated from a <code>createShared</code>
     * method call, updates made to a copy of this <code>CachedRowSet</code> object
     * must not be visible to it. Also, any
     * event listeners that are registered with this
     * <code>CachedRowSet</code> object must not have scope over the new
     * <code>RowSet</code> object. In addition, any constraint restrictions
     * established for this <code>CachedRowSet</code> object must <b>not</b> be maintained
     * in the copy.
     *
     * @return a new <code>CachedRowSet</code> object that is a deep copy
     *     of this <code>CachedRowSet</code> object and is
     *     completely independent of this  <code>CachedRowSet</code> object
     * @throws SQLException if an error occurs in generating the copy of
     *     the of this <code>CachedRowSet</code> object
     * @see #createCopy
     * @see #createShared
     * @see #createCopySchema
     * @see javax.sql.RowSetEvent
     * @see javax.sql.RowSetListener
     */
    public javax.sql.rowset.CachedRowSet createCopyNoConstraints() throws SQLException;

    /**
     * Retrieves the first warning reported by calls on this <code>RowSet</code> object.
     * Subsequent warnings on this <code>RowSet</code> object will be chained to the
     * <code>RowSetWarning</code> object that this method returns.
     *
     * The warning chain is automatically cleared each time a new row is read.
     * This method may not be called on a RowSet object that has been closed;
     * doing so will cause a <code>SQLException</code> to be thrown.
     *
     * @return RowSetWarning the first <code>RowSetWarning</code>
     * object reported or null if there are none
     * @throws SQLException if this method is called on a closed RowSet
     * @see RowSetWarning
     */
    public RowSetWarning getRowSetWarnings() throws SQLException;

    /**
     * Retrieves a <code>boolean</code> indicating whether rows marked
     * for deletion appear in the set of current rows. If <code>true</code> is
     * returned, deleted rows are visible with the current rows. If
     * <code>false</code> is returned, rows are not visible with the set of
     * current rows. The default value is <code>false</code>.
     * <P>
     * Standard rowset implementations may choose to restrict this behavior
     * due to security considerations or to better fit certain deployment
     * scenarios. This is left as implementation defined and does not
     * represent standard behavior.
     * <P>
     * Note: Allowing deleted rows to remain visible complicates the behavior
     * of some standard JDBC <code>RowSet</code> Implementations methods.
     * However, most rowset users can simply ignore this extra detail because
     * only very specialized applications will likely want to take advantage of
     * this feature.
     *
     * @return <code>true</code> if deleted rows are visible;
     *         <code>false</code> otherwise
     * @throws SQLException if a rowset implementation is unable to
     * to determine whether rows marked for deletion are visible
     * @see #setShowDeleted
     */
    public boolean getShowDeleted() throws SQLException;

    /**
     * Sets the property <code>showDeleted</code> to the given
     * <code>boolean</code> value, which determines whether
     * rows marked for deletion appear in the set of current rows.
     * If the value is set to <code>true</code>, deleted rows are immediately
     * visible with the set of current rows. If the value is set to
     * <code>false</code>, the deleted rows are set as invisible with the
     * current set of rows.
     * <P>
     * Standard rowset implementations may choose to restrict this behavior
     * due to security considerations or to better fit certain deployment
     * scenarios. This is left as implementations defined and does not
     * represent standard behavior.
     *
     * @param b <code>true</code> if deleted rows should be shown;
     *              <code>false</code> otherwise
     * @exception SQLException if a rowset implementation is unable to
     * to reset whether deleted rows should be visible
     * @see #getShowDeleted
     */
    public void setShowDeleted(boolean b) throws SQLException;

    /**
     * Each <code>CachedRowSet</code> object's <code>SyncProvider</code> contains
     * a <code>Connection</code> object from the <code>ResultSet</code> or JDBC
     * properties passed to it's constructors. This method wraps the
     * <code>Connection</code> commit method to allow flexible
     * auto commit or non auto commit transactional control support.
     * <p>
     * Makes all changes that are performed by the <code>acceptChanges()</code>
     * method since the previous commit/rollback permanent. This method should
     * be used only when auto-commit mode has been disabled.
     *
     * @throws SQLException if a database access error occurs or this
     * Connection object within this <code>CachedRowSet</code> is in auto-commit mode
     * @see java.sql.Connection#setAutoCommit
     */
    public void commit() throws SQLException;

    /**
     * Each <code>CachedRowSet</code> object's <code>SyncProvider</code> contains
     * a <code>Connection</code> object from the original <code>ResultSet</code>
     * or JDBC properties passed to it.
     * <p>
     * Undoes all changes made in the current transaction.  This method
     * should be used only when auto-commit mode has been disabled.
     *
     * @throws SQLException if a database access error occurs or this Connection
     * object within this <code>CachedRowSet</code> is in auto-commit mode.
     */
    public void rollback() throws SQLException;

    /**
     * Each <code>CachedRowSet</code> object's <code>SyncProvider</code> contains
     * a <code>Connection</code> object from the original <code>ResultSet</code>
     * or JDBC properties passed to it.
     * <p>
     * Undoes all changes made in the current transaction back to the last
     * <code>Savepoint</code> transaction marker. This method should be used only
     * when auto-commit mode has been disabled.
     *
     * @param s A <code>Savepoint</code> transaction marker
     * @throws SQLException if a database access error occurs or this Connection
     * object within this <code>CachedRowSet</code> is in auto-commit mode.
     */
    public void rollback(Savepoint s) throws SQLException;

    /**
     * Causes the <code>CachedRowSet</code> object's <code>SyncProvider</code>
     * to commit the changes when <code>acceptChanges()</code> is called. If
     * set to false, the changes will <b>not</b> be committed until one of the
     * <code>CachedRowSet</code> interface transaction methods is called.
     *
     * @deprecated Because this field is final (it is part of an interface),
     *  its value cannot be changed.
     * @see #commit
     * @see #rollback
     */
    @Deprecated
    public static final boolean COMMIT_ON_ACCEPT_CHANGES = true;

    /**
     * Notifies registered listeners that a RowSet object in the given RowSetEvent
     * object has populated a number of additional rows. The <code>numRows</code> parameter
     * ensures that this event will only be fired every <code>numRow</code>.
     * <p>
     * The source of the event can be retrieved with the method event.getSource.
     *
     * @param event a <code>RowSetEvent</code> object that contains the
     *     <code>RowSet</code> object that is the source of the events
     * @param numRows when populating, the number of rows interval on which the
     *     <code>CachedRowSet</code> populated should fire; the default value
     *     is zero; cannot be less than <code>fetchSize</code> or zero
     * @throws SQLException {@code numRows < 0 or numRows < getFetchSize() }
     */
    public void rowSetPopulated(RowSetEvent event, int numRows) throws SQLException;

    /**
     * Populates this <code>CachedRowSet</code> object with data from
     * the given <code>ResultSet</code> object. While related to the <code>populate(ResultSet)</code>
     * method, an additional parameter is provided to allow starting position within
     * the <code>ResultSet</code> from where to populate the CachedRowSet
     * instance.
     * <P>
     * This method can be used as an alternative to the <code>execute</code> method when an
     * application has a connection to an open <code>ResultSet</code> object.
     * Using the method <code>populate</code> can be more efficient than using
     * the version of the <code>execute</code> method that takes no parameters
     * because it does not open a new connection and re-execute this
     * <code>CachedRowSet</code> object's command. Using the <code>populate</code>
     *  method is more a matter of convenience when compared to using the version
     * of <code>execute</code> that takes a <code>ResultSet</code> object.
     *
     * @param startRow the position in the <code>ResultSet</code> from where to start
     *                populating the records in this <code>CachedRowSet</code>
     * @param rs the <code>ResultSet</code> object containing the data
     * to be read into this <code>CachedRowSet</code> object
     * @throws SQLException if a null <code>ResultSet</code> object is supplied
     * or this <code>CachedRowSet</code> object cannot
     * retrieve the associated <code>ResultSetMetaData</code> object
     * @see #execute
     * @see #populate(ResultSet)
     * @see java.sql.ResultSet
     * @see java.sql.ResultSetMetaData
     */
    public void populate(ResultSet rs, int startRow) throws SQLException;

    /**
     * Sets the <code>CachedRowSet</code> object's page-size. A <code>CachedRowSet</code>
     * may be configured to populate itself in page-size sized batches of rows. When
     * either <code>populate()</code> or <code>execute()</code> are called, the
     * <code>CachedRowSet</code> fetches an additional page according to the
     * original SQL query used to populate the RowSet.
     *
     * @param size the page-size of the <code>CachedRowSet</code>
     * @throws SQLException if an error occurs setting the <code>CachedRowSet</code>
     *      page size or if the page size is less than 0.
     */
    public void setPageSize(int size) throws SQLException;

    /**
     * Returns the page-size for the <code>CachedRowSet</code> object
     *
     * @return an <code>int</code> page size
     */
    public int getPageSize();

    /**
     * Increments the current page of the <code>CachedRowSet</code>. This causes
     * the <code>CachedRowSet</code> implementation to fetch the next page-size
     * rows and populate the RowSet, if remaining rows remain within scope of the
     * original SQL query used to populated the RowSet.
     *
     * @return true if more pages exist; false if this is the last page
     * @throws SQLException if an error occurs fetching the next page, or if this
     *     method is called prematurely before populate or execute.
     */
    public boolean nextPage() throws SQLException;

    /**
     * Decrements the current page of the <code>CachedRowSet</code>. This causes
     * the <code>CachedRowSet</code> implementation to fetch the previous page-size
     * rows and populate the RowSet. The amount of rows returned in the previous
     * page must always remain within scope of the original SQL query used to
     * populate the RowSet.
     *
     * @return true if the previous page is successfully retrieved; false if this
     *     is the first page.
     * @throws SQLException if an error occurs fetching the previous page, or if
     *     this method is called prematurely before populate or execute.
     */
    public boolean previousPage() throws SQLException;
}
