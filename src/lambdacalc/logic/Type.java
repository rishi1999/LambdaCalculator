/*
 * Type.java
 *
 * Created on May 30, 2006, 10:37 AM
 */

package lambdacalc.logic;

import java.util.*;

/**
 * Represents a semantic type, an AtomicType like e and t,
 * a CompositeType like &lt;e,t&gt;, or a ProductType like (e x e).
 */
public abstract class Type implements Comparable, java.io.Serializable {
    
    public static final char LEFTBRACKET    = '<';
    public static final char RIGHTBRACKET   = '>';
    public static final char SEPARATOR      = ',';
    
    /**
     * The type of entities, e.
     */
    public static final Type E = new AtomicType('e');

     /**
     * The type of truth values, t.
     */
    public static final Type T = new AtomicType('t');

    /**
     * The type of one place predicates, &lt;e,t&gt;.
     */
    public static final Type ET = new CompositeType(new AtomicType('e'), new AtomicType('t'));

    /**
     * The type of two place predicates, &lt;e x e , t&gt;.
     */
    public static final Type ExET = new CompositeType(
            new ProductType(new Type[] { new AtomicType('e'), new AtomicType('e') }),
            new AtomicType('t'));
    
    /**
     * Tests two types for equality (as you would expect).
     */
    public boolean equals(Object obj) {
        if (obj instanceof Type)
            return equals((Type)obj);
        else
            return false;
    }
    
    /**
     * Implemented by subclasses to test for equality between types.
     */
    protected abstract boolean equals(Type t);

    /**
     * Compares two types.  The natural ordering of types is as follows:
     *   First, atomic types, ordered by their character names.
     *   Then product types, ordered by arity.
     *   Finally composite types, ordered first according to domain, then range.
     */
    public int compareTo(Object other) {
        if (this instanceof AtomicType) {
            if (other instanceof AtomicType) {
                char x = ((AtomicType)this).getSymbol();
                char y = ((AtomicType)other).getSymbol();
                if (x < y) return -1;
                if (x > y) return 1;
                return 0;
            }
            return -1;
        } else if (this instanceof ProductType) {
            if (other instanceof AtomicType)
                return 1;
            if (other instanceof ProductType) {
                int a = ((ProductType)this).getArity();
                int b = ((ProductType)other).getArity();
                if (a < b) return -1;
                if (a > b) return 1;
                return 0;
            }
            return -1;
            
        } else if (this instanceof CompositeType) {
            if (other instanceof AtomicType || other instanceof ProductType)
                return 1;
            Type al = ((CompositeType)this).getLeft();
            Type ar = ((CompositeType)this).getRight();
            Type bl = ((CompositeType)other).getLeft();
            Type br = ((CompositeType)other).getRight();
            
            int c = al.compareTo(bl);
            if (c != 0) return c;
            return ar.compareTo(br);
        }
        throw new RuntimeException(); // unreachable
    }
}