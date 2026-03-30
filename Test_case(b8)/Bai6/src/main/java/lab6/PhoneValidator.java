package lab6;

/**
 * BÀI 6 - Bài tập 6.1 GREEN: PhoneValidator sau khi viết test (TDD)
 *
 * QUY TẮC SỐ ĐIỆN THOẠI VIỆT NAM:
 *   - Bắt đầu bằng 0 hoặc +84
 *   - Sau chuẩn hóa: bắt đầu bằng 03x, 05x, 07x, 08x, 09x
 *   - Tổng 10 chữ số sau chuẩn hóa
 *   - Chỉ chứa 0-9, +, space trước khi chuẩn hóa
 *
 * CFG:
 *   N1: phone null/blank? → invalid
 *   N2: Chuẩn hóa (+84 → 0)
 *   N3: Độ dài == 10? → invalid nếu không
 *   N4: Bắt đầu bằng 0? → invalid nếu không
 *   N5: Ký tự thứ 2 ∈ {3,5,7,8,9}? → invalid nếu không
 *   → Valid
 *
 * CC = 5 + 1 = 6 (5 decision nodes)
 */
public class PhoneValidator {

    public static boolean isValid(String phone) {
        // N1: Kiểm tra null/blank
        if (phone == null || phone.isBlank()) return false;

        // Xóa khoảng trắng
        String cleaned = phone.replaceAll("\\s+", "");

        // Chuẩn hóa: +84 → 0
        if (cleaned.startsWith("+84")) {
            cleaned = "0" + cleaned.substring(3);
        }

        // N3: Phải đúng 10 chữ số
        if (cleaned.length() != 10) return false;

        // N4: Phải bắt đầu bằng 0
        if (!cleaned.startsWith("0")) return false;

        // N5: Ký tự thứ 2 phải là 3,5,7,8,9
        char second = cleaned.charAt(1);
        if (second != '3' && second != '5' && second != '7'
                && second != '8' && second != '9') return false;

        // N6: Tất cả phải là chữ số
        return cleaned.matches("\\d{10}");
    }
}
