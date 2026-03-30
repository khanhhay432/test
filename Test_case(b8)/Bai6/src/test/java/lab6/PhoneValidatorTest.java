package lab6;

import org.testng.Assert;
import org.testng.annotations.*;

/**
 * BÀI 6 - Bài tập 6.1: TDD PhoneValidator
 *
 * THỨ TỰ TDD:
 *   1. RED:   Viết test này trước khi có PhoneValidator → FAIL
 *   2. GREEN: Viết PhoneValidator.java → test PASS
 *   3. REFACTOR: Cải thiện code → test vẫn PASS
 *
 * BASIS PATH (CC=6 → 6 test case cơ sở):
 *   P1: null/blank → false
 *   P2: Độ dài sai (<10 hoặc >10) → false
 *   P3: Không bắt đầu bằng 0 → false
 *   P4: Ký tự thứ 2 không hợp lệ (vd: 01, 02, 04, 06) → false
 *   P5: Số hợp lệ dạng 0xxxxxxxxx → true
 *   P6: Số hợp lệ dạng +84xxxxxxxxx → true (chuẩn hóa)
 */
public class PhoneValidatorTest {

    // ===== BASIS PATH TESTS =====

    @Test(description = "P1-Basis: So null → false")
    public void testP1_Null() {
        Assert.assertFalse(PhoneValidator.isValid(null),
            "So null phai tra ve false");
    }

    @Test(description = "P1-Basis: So rong → false")
    public void testP1_Blank() {
        Assert.assertFalse(PhoneValidator.isValid("   "),
            "So trong phai tra ve false");
    }

    @Test(description = "P2-Basis: Do dai < 10 → false")
    public void testP2_TooShort() {
        Assert.assertFalse(PhoneValidator.isValid("09123"),
            "So qua ngan phai tra ve false");
    }

    @Test(description = "P2-Basis: Do dai > 10 → false")
    public void testP2_TooLong() {
        Assert.assertFalse(PhoneValidator.isValid("091234567890"),
            "So qua dai phai tra ve false");
    }

    @Test(description = "P3-Basis: Khong bat dau bang 0 → false")
    public void testP3_NotStartWith0() {
        Assert.assertFalse(PhoneValidator.isValid("1234567890"),
            "So khong bat dau bang 0 phai false");
    }

    @Test(description = "P4-Basis: Ky tu thu 2 khong hop le (01, 02, 04) → false")
    public void testP4_InvalidSecondDigit() {
        Assert.assertFalse(PhoneValidator.isValid("0123456789"),
            "01x khong hop le (ky tu thu 2 = 1)");
        Assert.assertFalse(PhoneValidator.isValid("0223456789"),
            "02x khong hop le");
        Assert.assertFalse(PhoneValidator.isValid("0423456789"),
            "04x khong hop le");
        Assert.assertFalse(PhoneValidator.isValid("0623456789"),
            "06x khong hop le");
    }

    @Test(description = "P5-Basis: So hop le dang 0xxxxxxxxx → true")
    public void testP5_ValidFormat_0() {
        Assert.assertTrue(PhoneValidator.isValid("0912345678"),  "09x hop le");
        Assert.assertTrue(PhoneValidator.isValid("0812345678"),  "08x hop le");
        Assert.assertTrue(PhoneValidator.isValid("0712345678"),  "07x hop le");
        Assert.assertTrue(PhoneValidator.isValid("0512345678"),  "05x hop le");
        Assert.assertTrue(PhoneValidator.isValid("0312345678"),  "03x hop le");
    }

    @Test(description = "P6-Basis: So hop le dang +84 → true (chuan hoa)")
    public void testP6_ValidFormat_Plus84() {
        Assert.assertTrue(PhoneValidator.isValid("+84912345678"), "+84 9xx hop le");
        Assert.assertTrue(PhoneValidator.isValid("+84812345678"), "+84 8xx hop le");
    }

    // ===== BOUNDARY TESTS =====

    @Test(description = "Boundary: So co khoang trang → xu ly dung")
    public void testBoundary_WithSpaces() {
        Assert.assertTrue(PhoneValidator.isValid("0912 345 678"),
            "So co space phai duoc xu ly dung");
    }

    @Test(description = "Boundary: Chuoi co ky tu dac biet → false")
    public void testBoundary_SpecialChars() {
        Assert.assertFalse(PhoneValidator.isValid("091234567a"),
            "So chua chu cai phai false");
        Assert.assertFalse(PhoneValidator.isValid("0912-345678"),
            "So chua dau gach phai false");
    }
}
