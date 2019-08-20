import java.util.List;

/*
 * A\iOuahhabi.
 */

/**
 * @author Admin
 */
public class Cosine<T> implements Similarity<T> {

    @Override
    public <T> Double similarityComputing(List<T> profile1, List<T> profile2) {
        //System.out.println("!!!!!!!!! Calling Cosine Similarity");
        if( profile1.stream().allMatch(v-> v instanceof Double) && profile2.stream().allMatch(v-> v instanceof Double)){
            if (profile1.size() == profile2.size()) {
            Double pscalaire = 0.0;
            Double normp1 = 0.0;
            Double normp2 = 0.0;
            for (int i = 0; i < profile1.size(); i++) {
                pscalaire += (Double) profile1.get(i) * (Double) profile2.get(i);
                normp1 += Math.pow((Double)profile1.get(i), 2);
                normp2 += Math.pow((Double)profile2.get(i), 2);
            }
            normp1 += Math.sqrt(normp1);
            normp2 += Math.sqrt(normp2);

            return (pscalaire / (normp1 * normp2));
           } else throw new IllegalArgumentException("Cosine similarity need vectors of the same size");
        }else throw new IllegalArgumentException ("Cosine similarity need vector of Double ");
    }


}
