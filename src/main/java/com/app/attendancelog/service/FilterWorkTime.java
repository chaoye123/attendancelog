package com.app.attendancelog.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import com.app.attendancelog.util.DateUtils;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
/**
 * Created by jacky on 2017/7/5.
 */
public class FilterWorkTime {

    private static String url = "http://10.86.92.69/";
    private static String yyyy_mm_dd = "%d/%d/%d";


    /**
     * 分页请求才好，否则分页不好处理,
     * 因为分页的时候，下一页不知道怎么获取下一页的参数去请求下一页的数据
     *
     * @param workerNo
     * @return
     */
    public static List<String> getAllDateList(String workerNo, int year, int month){
        try {

            int month_days = DateUtils.getMonthDays(year,month);
            List<String> datestrList = getRequestListByThreadPool(workerNo, year,month, month_days);
                //getRequestList(workerNo, year,month, 1, month_days);
            List<String> result = new ArrayList<>(datestrList.size());
            if(! CollectionUtils.isEmpty(datestrList)) {
                for(String date : datestrList) {
                    result.add(date.replaceAll("/", "-"));
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return Collections.emptyList();
    }


    //优化成线程池来处理
    private static List<String> getRequestListByThreadPool(
        final String workerNo,
        final int year,
        final int month, int month_day){

        ExecutorService service = Executors.newFixedThreadPool(20);
        List<Callable<List<String>>> results = new ArrayList<>();
        try {

            AtomicInteger atomicInteger = new AtomicInteger(1);
            for(int i = 1; i<= month_day; i++){

               int days = atomicInteger.getAndIncrement();
                results.add(new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        return getRequestList(workerNo, year, month,days,days);
                    }
                });
            }
            List<Future<List<String>>> list = service.invokeAll(results);

            List<String> stringList = new ArrayList<>();
            for(Future<List<String>> future : list){
                stringList.addAll(future.get());
            }
            return stringList;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
        return null;
    }

    private static List<String> getRequestList(String workerNo, int year, int month, int start, int end) throws Exception {
        List<String> datestrList = new ArrayList<>();
//        System.out.println("======================================>" +start + "," + end);
        for(int i = start; i <= end; i++) {
            String day = String.format(yyyy_mm_dd, year, month, i);
            String dataString = submittingForm(workerNo, day, day);
            List<String> dataList = getFilterDate(dataString);
            if(dataList.size() == 0){
                continue;
            }
            datestrList.addAll(dataList);
        }
        return datestrList;
    }

    private static List<String> getFilterDate(String text){
        int index = text.indexOf("考勤位置");
        int start = index + "考勤位置".length();
        String result = text.substring(start);
        List<String> dateList = new ArrayList<>();
        if(StringUtils.isNotBlank(result)){
            String[] array = result.split("\r\n");
            for(String str : array){
                if(StringUtils.isNotBlank(str)){
                    String filterStr = filterDateString(str);
                    if(StringUtils.isNotBlank(filterStr)) {
                        dateList.add(filterDateString(str));
                    }
                }
            }
        }
        return dateList;
    }

    /**
     * 获取页面中的打卡时间
     *
     * @param str
     * @return
     */
    private static String filterDateString(String str){
        String dateString = "";
        if(StringUtils.isNotBlank(str)){
            String[] list = str.split("\t");
            if(list != null && list.length == 4){
                dateString = list[2];
            }
            return dateString.trim();
        }
        return str;
    }

    /**
     *
     * @param workNo "010xxxx"
     * @param beginDate "2017/6/12"
     * @param endDate "2017/6/12"
     * @throws Exception
     */
    private static String submittingForm(String workNo, String beginDate, String endDate) throws Exception {
        try (final WebClient webClient = new WebClient()) {

            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);

            // Get the first page
            final HtmlPage page1 = webClient.getPage(url);

            // Get the form that we are dealing with and within that form,
            // find the submit button and the field that we want to change.
            HtmlForm form=page1.getForms().get(0);
            final HtmlSubmitInput button = form.getInputByName("ctl07");

            final HtmlTextInput textField = form.getInputByName("UserId");
            textField.setValueAttribute(workNo);

            final HtmlTextInput textField1 = form.getInputByName("StartDate");
            textField1.setValueAttribute(beginDate);
            final HtmlTextInput textField2 = form.getInputByName("EndDate");
            textField2.setValueAttribute(endDate);

            final HtmlPage nextPage = button.click();
            // 我把结果转成String
            String text = nextPage.asText();
            return text;
        }
    }

//    public static void main(String[] args) {
//
//        long start = System.currentTimeMillis();
//        List<String> result = FilterWorkTime.getAllDateList("工号", 2017, 6);
//        System.out.println(result);
//        System.out.println(System.currentTimeMillis() - start);
//        System.out.println(Runtime.getRuntime().availableProcessors());
//    }
}
