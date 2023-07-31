package lab7;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class testHTTP {
    public static void main(String[] args) {
        String outputFilePath = "/home/manh/Documents/output.txt"; // Đường dẫn đến file muốn lưu nội dung phản hồi

        try {
            // Tạo kết nối tới server (HTTPS)
            String oldURL = "https://www.babla.vn/tieng-anh-tieng-viet/h";
            System.out.println("Old URL: " + oldURL);
            HttpsURLConnection connection = (HttpsURLConnection) new URL(oldURL).openConnection();
            connection.setRequestMethod("GET");

            // Đọc nội dung phản hồi và ghi vào file
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            FileWriter fileWriter = new FileWriter(outputFilePath);
            String line;
            while ((line = reader.readLine()) != null) {
            	System.out.println("line : " + line);
                System.out.println(line); // Đồng thời hiển thị nội dung trên màn hình (tuỳ chọn)
                fileWriter.write("line : " + line);
                fileWriter.write("\n"); // Thêm dấu xuống dòng sau mỗi dòng dữ liệu
            }

            // Đóng luồng và kết nối
            fileWriter.close();
            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
