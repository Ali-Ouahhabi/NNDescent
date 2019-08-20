import java.util.List;

/*
 * A\iOuahhabi.
 */

/**
 * @author Admin
 */
public class Cosine implements Similarity {

    @Override
    public Double similarityComputing(List<Double> profile1, List<Double> profile2) {
        //System.out.println("!!!!!!!!! Calling Cosine Similarity");

        if (profile1.size() == profile2.size()) {
            Double pscalaire = 0.0;
            Double normp1 = 0.0;
            Double normp2 = 0.0;
            for (int i = 0; i < profile1.size(); i++) {
                pscalaire += profile1.get(i) * profile2.get(i);
                normp1 += Math.pow(profile1.get(i), 2);
                normp2 += Math.pow(profile2.get(i), 2);
            }
            normp1 += Math.sqrt(normp1);
            normp2 += Math.sqrt(normp2);

            return (pscalaire / (normp1 * normp2));
        } else {
            //throw exception 
            System.err.println("cosine similarity need vectors of the same size");
        }
        return 0.0;
    }


}
