import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
/**
 * Created by wenchao.huang on 2017/7/12.
 */
public class JodaTest {

    public static void main(String[] args) {
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        //时间解析
        DateTime dateTime = DateTime.parse("2012-12-21 23:22:45", format);
        System.out.println(dateTime.toDate());
    }
}
