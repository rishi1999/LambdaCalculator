/*
 * ProductType.java
 *
 * Created on May 30, 2006, 5:52 PM
 */

package lambdacalc.logic;

/**
 * Represents a cartesian product type.  This is the type
 * of vectors, i.e. the list of arguments to a predicate of
 * two or more arguments.  Product types are the types
 * of ArgLists.
 */
public class ProductType extends Type {
    /**
     * The unicode cross characte.r
     */
    public static final char SYMBOL = '\u00D7';
    
    Type[] subtypes;
    
    /**
     * Creates a new product type with the given sub-types.
     */
    public ProductType(Type[] subtypes) {
        this.subtypes = subtypes;
        if (subtypes.length == 0) throw new IllegalArgumentException();
    }
    
    /**
     * Gets the sub-types of this product type.
     */
    public Type[] getSubTypes() {
        return subtypes;
    }
    
    /**
     * Gets the number of sub-types in this type.
     */
    public int getArity() {
        return subtypes.length;
    }
    
    protected boolean equals(Type t) {
        if (t instanceof ProductType) {
            Type[] a1 = getSubTypes();
            Type[] a2 = ((ProductType)t).getSubTypes();
            if (a1.length != a2.length) return false;
            for (int i = 0; i < a1.length; i++)
                if (!a1[i].equals(a2[i]))
                    return false;
            return true;
        } else { 
            return false;
        }
    }
    
    public int hashCode() {
        int hc = 0;
        for (int i = 0; i < subtypes.length; i++)
            hc ^= subtypes[i].hashCode(); // XOR the hash codes
        return hc;
    }
    
    public String toString() {
        String ret = "";
        for (int i = 0; i < getSubTypes().length; i++) {
            if (i > 0)
                ret += " " + SYMBOL + " ";
            ret += getSubTypes()[i].toString();
        }
        return ret;
    }
    
}