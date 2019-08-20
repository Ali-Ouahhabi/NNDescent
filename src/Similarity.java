/*
 * A\iOuahhabi.
 */

import java.util.List;

/**
 * @author Admin
 */
public interface Similarity<T> {
    //interface to reference the object with the similarity algorithm  
    public Double similarityComputing(List<T> p1, List<T> p2);
}
