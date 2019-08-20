import java.util.List;

public class Euclidean<T> implements Similarity<T> {

    @Override
    public <T> Double similarityComputing(List<T> profile1, List<T> profile2) {

        //System.out.println("Calling Euclidien Similarity");
        if( profile1.stream().allMatch(v-> v instanceof Double) && profile2.stream().allMatch(v-> v instanceof Double)){
            if (profile1.size() == profile2.size()) {
                Double sum = 0.0;
                Double tmp = 0.0;
                for (int i = 0; i < profile1.size(); i++) {
                    tmp = Math.pow((Double) profile1.get(i) - (Double) profile2.get(i), 2);
                    sum += tmp;
                }
                return Math.sqrt(sum);
            } else throw new IllegalArgumentException("Euclidean similarity need vectors of the same size");

        } else throw new IllegalArgumentException ("Euclidean similarity need vector of Double ");
    }


}
