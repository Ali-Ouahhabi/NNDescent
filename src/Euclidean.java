import java.util.List;

public class Euclidean implements Similarity {

    @Override
    public Double similarityComputing(List<Double> profile1, List<Double> profile2) {
        //System.out.println("Calling Euclidien Similarity");
        if (profile1.size() == profile2.size()) {
            Double sum = 0.0;
            Double tmp = 0.0;
            for (int i = 0; i < profile1.size(); i++) {
                tmp = Math.pow(profile1.get(i) - profile2.get(i), 2);
                sum += tmp;
            }

            return Math.sqrt(sum);
        } else {
            //throw exception 
            System.err.println("Euclidean similarity need vectors of the same size");
        }
        return 0.0;
    }


}
