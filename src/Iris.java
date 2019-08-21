import NNDescent.Profile;

import java.util.ArrayList;
import java.util.List;
import java.lang.Double;
import java.util.ListIterator;

public class Iris extends Profile {

    public List<Double> profile = new ArrayList<>();
    public String label;

    public  Iris(String row){
        String[] tmp = row.split(",");
        this.profile.add(Double.parseDouble(tmp[0]));
        this.profile.add(Double.parseDouble(tmp[1]));
        this.profile.add(Double.parseDouble(tmp[2]));
        this.profile.add(Double.parseDouble(tmp[3]));

        this.label =  tmp[4];
    }

    @Override
    public Double similarity(Profile profile){
        Double tmp=0.0;
        //manhattan distance
        for(int i=0; i<this.profile.size(); i++){
            tmp+=Math.abs(this.profile.get(i)-((Iris) profile).profile.get(i));
        }

        return tmp;
    }


    public String toString() {
        String tmp = "";
       /* for (Double d : this.profile ){
            tmp += d+" : ";
        }*/
        tmp += this.label;
        return tmp;
    }

}
