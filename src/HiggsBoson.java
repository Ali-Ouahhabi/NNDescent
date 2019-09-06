import NNDescent.Profile;

import java.util.ArrayList;
import java.util.List;

public class HiggsBoson extends Profile {

    public int id;
    public List<Double> profile = new ArrayList<>();
    public String label;

    public HiggsBoson(String row){
        String[] tmp = row.split(",");
        this.id = Integer.parseInt(tmp[0]);
        for (int i=1;i<tmp.length-1;i++){
            profile.add(Double.parseDouble(tmp[i]));
        }
        this.label = tmp[tmp.length-1];
    }

    @Override
    public Double similarity(Profile profile) {
        Double tmp=0.0;
        for(int i=0; i<this.profile.size(); i++){
            tmp+=Math.abs(this.profile.get(i)-((HiggsBoson) profile).profile.get(i));
        }
        return tmp;
    }
}
