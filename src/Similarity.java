/*
 * A\iOuahhabi.
 */

import java.util.List;

/**
 * @author Admin
 */
public interface Similarity<T extends Object> {
    //interface to reference the object with the similarity algorithm  
    public <T> Double similarityComputing(List<T> p1, List<T> p2);
}
