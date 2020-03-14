package info.kgeorgiy.java.advanced.implementor.basic.interfaces.standard;

import javax.management.RuntimeOperationsException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Copy of {@link javax.management.Descriptor}
 */
public interface Descriptor extends Serializable, Cloneable
{

    /**
     * Returns the value for a specific field name, or null if no value
     * is present for that name.
     *
     * @param fieldName the field name.
     *
     * @return the corresponding value, or null if the field is not present.
     *
     * @exception RuntimeOperationsException if the field name is illegal.
     */
    public Object getFieldValue(String fieldName)
            throws RuntimeOperationsException;

    /**
     * <p>Sets the value for a specific field name. This will
     * modify an existing field or add a new field.</p>
     *
     * <p>The field value will be validated before it is set.
     * If it is not valid, then an exception will be thrown.
     * The meaning of validity is dependent on the descriptor
     * implementation.</p>
     *
     * @param fieldName The field name to be set. Cannot be null or empty.
     * @param fieldValue The field value to be set for the field
     * name. Can be null if that is a valid value for the field.
     *
     * @exception RuntimeOperationsException if the field name or field value
     * is illegal (wrapped exception is {@link IllegalArgumentException}); or
     * if the descriptor is immutable (wrapped exception is
     * {@link UnsupportedOperationException}).
     */
    public void setField(String fieldName, Object fieldValue)
            throws RuntimeOperationsException;


    /**
     * Returns all of the fields contained in this descriptor as a string array.
     *
     * @return String array of fields in the format <i>fieldName=fieldValue</i>
     * <br>If the value of a field is not a String, then the toString() method
     * will be called on it and the returned value, enclosed in parentheses,
     * used as the value for the field in the returned array. If the value
     * of a field is null, then the value of the field in the returned array
     * will be empty.  If the descriptor is empty, you will get
     * an empty array.
     *
     * @see #setFields
     */
    public String[] getFields();


    /**
     * Returns all the field names in the descriptor.
     *
     * @return String array of field names. If the descriptor is empty,
     * you will get an empty array.
     */
    public String[] getFieldNames();

    /**
     * Returns all the field values in the descriptor as an array of Objects. The
     * returned values are in the same order as the {@code fieldNames} String array parameter.
     *
     * @param fieldNames String array of the names of the fields that
     * the values should be returned for.  If the array is empty then
     * an empty array will be returned.  If the array is null then all
     * values will be returned, as if the parameter were the array
     * returned by {@link #getFieldNames()}.  If a field name in the
     * array does not exist, including the case where it is null or
     * the empty string, then null is returned for the matching array
     * element being returned.
     *
     * @return Object array of field values. If the list of {@code fieldNames}
     * is empty, you will get an empty array.
     */
    public Object[] getFieldValues(String... fieldNames);

    /**
     * Removes a field from the descriptor.
     *
     * @param fieldName String name of the field to be removed.
     * If the field name is illegal or the field is not found,
     * no exception is thrown.
     *
     * @exception RuntimeOperationsException if a field of the given name
     * exists and the descriptor is immutable.  The wrapped exception will
     * be an {@link UnsupportedOperationException}.
     */
    public void removeField(String fieldName);

    /**
     * <p>Sets all fields in the field names array to the new value with
     * the same index in the field values array. Array sizes must match.</p>
     *
     * <p>The field value will be validated before it is set.
     * If it is not valid, then an exception will be thrown.
     * If the arrays are empty, then no change will take effect.</p>
     *
     * @param fieldNames String array of field names. The array and array
     * elements cannot be null.
     * @param fieldValues Object array of the corresponding field values.
     * The array cannot be null. Elements of the array can be null.
     *
     * @throws RuntimeOperationsException if the change fails for any reason.
     * Wrapped exception is {@link IllegalArgumentException} if
     * {@code fieldNames} or {@code fieldValues} is null, or if
     * the arrays are of different lengths, or if there is an
     * illegal value in one of them.
     * Wrapped exception is {@link UnsupportedOperationException}
     * if the descriptor is immutable, and the call would change
     * its contents.
     *
     * @see #getFields
     */
    public void setFields(String[] fieldNames, Object[] fieldValues)
            throws RuntimeOperationsException;


    /**
     * <p>Returns a descriptor which is equal to this descriptor.
     * Changes to the returned descriptor will have no effect on this
     * descriptor, and vice versa.  If this descriptor is immutable,
     * it may fulfill this condition by returning itself.</p>
     * @exception RuntimeOperationsException for illegal value for field names
     * or field values.
     * If the descriptor construction fails for any reason, this exception will
     * be thrown.
     * @return A descriptor which is equal to this descriptor.
     */
    public Object clone() throws RuntimeOperationsException;


    /**
     * Returns true if all of the fields have legal values given their
     * names.
     *
     * @return true if the values are legal.
     *
     * @exception RuntimeOperationsException If the validity checking fails for
     * any reason, this exception will be thrown.
     * The method returns false if the descriptor is not valid, but throws
     * this exception if the attempt to determine validity fails.
     */
    public boolean isValid() throws RuntimeOperationsException;

    /**
     * <p>Compares this descriptor to the given object.  The objects are equal if
     * the given object is also a Descriptor, and if the two Descriptors have
     * the same field names (possibly differing in case) and the same
     * associated values.  The respective values for a field in the two
     * Descriptors are equal if the following conditions hold:</p>
     *
     * <ul>
     * <li>If one value is null then the other must be too.</li>
     * <li>If one value is a primitive array then the other must be a primitive
     * array of the same type with the same elements.</li>
     * <li>If one value is an object array then the other must be too and
     * {@link Arrays#deepEquals(Object[],Object[])} must return true.</li>
     * <li>Otherwise {@link Object#equals(Object)} must return true.</li>
     * </ul>
     *
     * @param obj the object to compare with.
     *
     * @return {@code true} if the objects are the same; {@code false}
     * otherwise.
     *
     * @since 1.6
     */
    public boolean equals(Object obj);

    /**
     * <p>Returns the hash code value for this descriptor.  The hash
     * code is computed as the sum of the hash codes for each field in
     * the descriptor.  The hash code of a field with name {@code n}
     * and value {@code v} is {@code n.toLowerCase().hashCode() ^ h}.
     * Here {@code h} is the hash code of {@code v}, computed as
     * follows:</p>
     *
     * <ul>
     * <li>If {@code v} is null then {@code h} is 0.</li>
     * <li>If {@code v} is a primitive array then {@code h} is computed using
     * the appropriate overloading of {@code java.util.Arrays.hashCode}.</li>
     * <li>If {@code v} is an object array then {@code h} is computed using
     * {@link Arrays#deepHashCode(Object[])}.</li>
     * <li>Otherwise {@code h} is {@code v.hashCode()}.</li>
     * </ul>
     *
     * @return A hash code value for this object.
     *
     * @since 1.6
     */
    public int hashCode();
}
