import NNDescent.NNDescent;
import NNDescent.Profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HiggsBosonSet {
    public List<Profile> Set = new ArrayList();

    public HiggsBosonSet(File in){
        Scanner sc = null;
        int j=0;
        try {
            sc = new Scanner(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.nextLine();
        while (sc.hasNextLine()&& j<2250){
            HiggsBoson i = new HiggsBoson(sc.nextLine());
            this.Set.add(i);
            j++;
        }
    }
    public static void main(String[] args) {
        HiggsBosonSet dataset = new HiggsBosonSet(new File("src/datasets/Higgs_Boson.csv"));
        NNDescent knn = new NNDescent(dataset.Set,10);
        knn.getKNN();
    }
}
