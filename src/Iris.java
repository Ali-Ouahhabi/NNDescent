import java.util.ArrayList;
import java.util.List;
import java.lang.Double;
import java.util.ListIterator;

public class Iris {

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
    public String toString() {
        String tmp = "";
        for (Double d : this.profile ){
            tmp += d+" : ";
        }
        tmp += this.label;
        return tmp;
    }

}
