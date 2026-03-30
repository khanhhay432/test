package lab4;

/**
 * BÀI 4 - Hàm tinhPhiShip: Tính phí ship hàng hóa
 *
 * CFG ANALYSIS:
 *   Decision nodes: D1, D2, D3, D4, D5, D6, D7 = 7 decisions
 *   CC = 7 + 1 = 8  (cách nhanh: số decision + 1)
 *   Cần thiết kế 8 Basis Path test case
 *
 * BASIS PATH (8 đường cơ sở):
 *   P1: D1-True → Exception
 *   P2: D1-F, D2-True, D3-F(≤5kg), D7-F
 *   P3: D1-F, D2-True, D3-True(>5kg), D7-F
 *   P4: D1-F, D2-F, D4-True, D5-F(≤3kg), D7-F
 *   P5: D1-F, D2-F, D4-True, D5-True(>3kg), D7-F
 *   P6: D1-F, D2-F, D4-F(tinh_khac), D6-F(≤2kg), D7-F
 *   P7: D1-F, D2-F, D4-F, D6-True(>2kg), D7-F
 *   P8: Any path + D7-True (laMember=true)
 */
public class PhiShip {

    public static double tinhPhiShip(double trongLuong, String vung, boolean laMember) {
        if (trongLuong <= 0) {                          // D1
            throw new IllegalArgumentException("Trong luong phai > 0");
        }
        double phi = 0;
        if (vung.equals("noi_thanh")) {                 // D2
            phi = 15000;
            if (trongLuong > 5) {                       // D3
                phi += (trongLuong - 5) * 2000;
            }
        } else if (vung.equals("ngoai_thanh")) {        // D4
            phi = 25000;
            if (trongLuong > 3) {                       // D5
                phi += (trongLuong - 3) * 3000;
            }
        } else {  // tinh_khac
            phi = 50000;
            if (trongLuong > 2) {                       // D6
                phi += (trongLuong - 2) * 5000;
            }
        }
        if (laMember) {                                 // D7
            phi = phi * 0.9;  // giảm 10%
        }
        return phi;
    }
}
