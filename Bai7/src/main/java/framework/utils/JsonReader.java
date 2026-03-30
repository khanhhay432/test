package framework.utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.List;

/**
 * BÀI 4 - JsonReader: Đọc dữ liệu test từ file JSON dùng Jackson.
 * Phù hợp khi dữ liệu có cấu trúc phức tạp: boolean, nested object, v.v.
 */
public class JsonReader {
    private static final ObjectMapper mapper = new ObjectMapper();
    private JsonReader() {}

    /** Đọc danh sách UserData từ file JSON */
    public static List<UserData> readUsers(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) throw new IOException("[JsonReader] Không tìm thấy: " + filePath);
        return mapper.readValue(file, new TypeReference<List<UserData>>(){});
    }
}
