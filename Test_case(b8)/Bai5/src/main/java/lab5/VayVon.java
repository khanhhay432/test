package lab5;

/**
 * BÀI 5 - Hàm duDieuKienVay: Kiểm tra điều kiện vay vốn ngân hàng
 *
 * PHÂN TÍCH ĐIỀU KIỆN ĐƠN:
 *   dieuKienCoBan = (tuoi >= 22) && (thuNhap >= 10_000_000)
 *     → A = (tuoi >= 22)
 *     → B = (thuNhap >= 10_000_000)
 *
 *   dieuKienBaoDam = coTaiSanBaoLanh || (dienTinDung >= 700)
 *     → C = coTaiSanBaoLanh
 *     → D = (dienTinDung >= 700)
 *
 *   Kết quả = (A && B) && (C || D)
 *   → 4 điều kiện đơn: A, B, C, D
 *
 * MC/DC ANALYSIS:
 *   Cần n+1 = 5 test case tối thiểu
 *   Mỗi điều kiện đơn phải chứng minh ảnh hưởng độc lập đến kết quả.
 */
public class VayVon {

    public static boolean duDieuKienVay(int tuoi, double thuNhap,
                                         boolean coTaiSanBaoLanh, int dienTinDung) {
        // A && B
        boolean dieuKienCoBan = (tuoi >= 22) && (thuNhap >= 10_000_000);
        // C || D
        boolean dieuKienBaoDam = coTaiSanBaoLanh || (dienTinDung >= 700);
        // (A&&B) && (C||D)
        return dieuKienCoBan && dieuKienBaoDam;
    }
}
