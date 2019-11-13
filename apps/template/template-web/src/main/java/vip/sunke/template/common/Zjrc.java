package vip.sunke.template.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import vip.sunke.common.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author sunke
 * @Date 2019-05-21 13:53
 * @description
 */

public class Zjrc {


    static Map<String, Future<String>> futureResultMap = new HashMap<String, Future<String>>();

    static String srcDir = "/upload/zjrc/json/";
    static String targetDir = "/upload/zjrc/target";
    static int maxZipFileCount = 10000;
    static int currentZipFileCount = 0;
    static int fileName = 0;


    public static String getId(int id) {

        return new String(Base64.encode((id + "").getBytes()));

    }


    public static void main(String[] args) throws Exception {


        int lastNum = 2800001;
        int startNum = 1000000;
        Map<String, String> headMap = new HashMap<>();
        headMap.put("X-Requested-With", "XMLHttpRequest");

        int round = 50;


        while (startNum < lastNum) {


            for (int i = 0; i < round; i++) {
                if (startNum >= lastNum)
                    break;
                send(startNum++, headMap);

            }

            write();


        }


        ZipUtils.toZip(srcDir, targetDir, fileName + ".zip", true);
        FileUtil.deleteDirectory(srcDir);









       /* for (int i = startNum; i < lastNum; i++) {

            try {
                byte[] a = Base64.encode((i + "").getBytes());
                String fileName = new String(a);
                Map map = new HashMap<>();
                map.put("id", fileName);




                resultStr = HttpClientUtil.doHttpRequest("http://service.zjrc.com/rcsc/showTalentDetail", map, "utf-8", headMap);
                if (StringUtil.isEmpty(resultStr))
                    continue;
                resultArray = JSONArray.parseArray(resultStr);
                if (StringUtil.isEmpty(resultArray.getJSONObject(0).getString("personname")))
                    continue;
                resultPerson = resultArray.getJSONObject(0);
                resultJsonStr = resultPerson.toJSONString();
                System.out.println("json:" + resultJsonStr);
                count++;

                FileUtil.writeInFile(srcDir + i + ".json", resultJsonStr);

                if (count % maxZipFileCount == 0) {

                    endOne = i;
                    System.out.println("zip:" + startOne + "-" + endOne + ".zip");
                    ZipUtils.toZip(srcDir, targetDir, startOne + "-" + endOne + ".zip", true);
                    FileUtil.deleteDirectory(srcDir);
                    FileUtil.mkdirs(srcDir);
                    startOne = i + 1;
                    count = 0;
                } else {
                    endOne = i;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //  headMap.clear();
            }

        }


        ZipUtils.toZip(srcDir, targetDir, startOne + "-" + endOne + ".zip", true);
        FileUtil.deleteDirectory(srcDir);*/

    }


    public static void write() {

        String result = null;
        JSONArray resultArray;
        JSONObject resultPerson;
        String resultJsonStr;

        for (Map.Entry<String, Future<String>> entry : futureResultMap.entrySet()) {
            try {
                result = entry.getValue().get();
                if (result == null || "".equalsIgnoreCase(result))
                    continue;
                resultArray = JSONArray.parseArray(result);
                if (StringUtil.isEmpty(resultArray.getJSONObject(0).getString("personname")))
                    continue;
                resultPerson = resultArray.getJSONObject(0);
                resultJsonStr = resultPerson.toJSONString();
                FileUtil.writeInFile(srcDir, entry.getKey() + ".json", resultJsonStr);
                currentZipFileCount++;

                if (currentZipFileCount % maxZipFileCount == 0) {

                    ZipUtils.toZip(srcDir, targetDir, fileName + ".zip", true);
                    fileName++;
                    FileUtil.deleteDirectory(srcDir);
                    currentZipFileCount = 0;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        futureResultMap.clear();


    }


    public static void send(int id, Map<String, String> headMap) {

        try {
            Map<String, String> idMap = new HashMap<String, String>();

            idMap.put("id", getId(id));
            FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return HttpTookit.doPost("http://service.zjrc.com/rcsc/showTalentDetail", idMap, headMap);
                }
            });

            new Thread(task).start();


            futureResultMap.put(id + "", task);


        } catch (Exception e) {

        }


    }


}
