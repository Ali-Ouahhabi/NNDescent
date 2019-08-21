import NNDescent.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IrisSet {

    public List<Profile> Set = new ArrayList();

    public IrisSet(File in) throws FileNotFoundException {
        Scanner sc = new Scanner(in);
        while (sc.hasNextLine()){
            Iris i = new Iris(sc.nextLine());
            this.Set.add(i);
        }
    }

    public void printAll(){
        for (Profile d : Set){
            System.out.println(d.toString());
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }
    public static void main(String[] args) {
        try {
            IrisSet dataset = new IrisSet(new File("src/datasets/iris_csv.csv"));
            dataset.printAll();
            NNDescent knn = new NNDescent(dataset.Set,5);
            knn.getKNN();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
