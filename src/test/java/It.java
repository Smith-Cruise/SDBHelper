import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Smith on 2017/4/10.
 */
public class It {
    public static void main(String[] args0) {
        ArrayList<Data> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            Data data = new Data();
            list.add(data);
        }

        Iterator<Data> iterator = list.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Data d = iterator.next();
            if (i == 5) {
                d.status = true;
            }
            i++;
        }

        for (Data data: list) {
            System.out.println(data.status);
        }
    }
}

class Data {
    boolean status = false;
}
