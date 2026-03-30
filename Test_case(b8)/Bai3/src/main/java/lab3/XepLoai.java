package lab3;

/**
 * BÀI 3 - Hàm xepLoai: Tính điểm xếp loại học lực
 *
 * CFG (Control Flow Graph):
 *
 *  Entry
 *    ↓
 *  N1: diemTB < 0 || diemTB > 10 ?
 *    ├─[TRUE]→  return "Diem khong hop le"
 *    └─[FALSE]→ N2: diemTB >= 8.5 ?
 *                 ├─[TRUE]→  return "Gioi"
 *                 └─[FALSE]→ N3: diemTB >= 7.0 ?
 *                              ├─[TRUE]→  return "Kha"
 *                              └─[FALSE]→ N4: diemTB >= 5.5 ?
 *                                           ├─[TRUE]→  return "Trung Binh"
 *                                           └─[FALSE]→ N5: coThiLai ?
 *                                                        ├─[TRUE]→  return "Thi lai"
 *                                                        └─[FALSE]→ return "Yeu - Hoc lai"
 *
 * Số decision nodes: 5 → CC = 5 + 1 = 6
 * Tổng nhánh: 10 (mỗi decision 2 nhánh T/F)
 */
public class XepLoai {

    public static String xepLoai(double diemTB, boolean coThiLai) {
        if (diemTB < 0 || diemTB > 10) {   // N1 - Điều kiện 1
            return "Diem khong hop le";
        }
        if (diemTB >= 8.5) {                // N2 - Điều kiện 2
            return "Gioi";
        } else if (diemTB >= 7.0) {         // N3 - Điều kiện 3
            return "Kha";
        } else if (diemTB >= 5.5) {         // N4 - Điều kiện 4
            return "Trung Binh";
        } else {
            if (coThiLai) {                 // N5 - Điều kiện 5
                return "Thi lai";
            }
            return "Yeu - Hoc lai";
        }
    }
}
