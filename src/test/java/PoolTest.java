import com.smith.sdb.dbpool.*;
import com.smith.sdb.dbpool.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.CountDownLatch;


/**
 * Created by Smith on 2017/4/9.
 */
public class PoolTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1000);
        for (int i=0; i<1000; i++) {
            new Thread(() -> {
                Connection con = null;
                try {
                    con = DatabaseHelper.getConnection();
                    PreparedStatement ps = con.prepareStatement("INSERT INTO `sdb` (`thread`) VALUE (?)");
                    ps.setString(1,Thread.currentThread().getName());
                    ps.execute();
                    DatabaseHelper.returnConnection(con);
                    latch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println((end-start)/1000);
    }

}
