package framework.utils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

/**
 * BÀI 3 - ExcelReader: Đọc test data từ file Excel .xlsx.
 *
 * CÁCH DÙNG:
 *   Object[][] data = ExcelReader.getData("src/test/resources/testdata/login_data.xlsx", "SmokeCases");
 *
 * QUY TẮC:
 *   - Dòng 0 (header) tự động bị bỏ qua
 *   - Xử lý đủ 4 kiểu cell: STRING, NUMERIC, BOOLEAN, FORMULA
 *   - Cell null → chuỗi rỗng "", không NullPointerException
 *   - Thêm dòng vào Excel → test tự chạy thêm, KHÔNG sửa Java
 */
public class ExcelReader {
    private ExcelReader() {}

    /**
     * Đọc toàn bộ data từ 1 sheet (bỏ dòng header đầu tiên).
     * @param filePath  đường dẫn file Excel
     * @param sheetName tên sheet cần đọc
     * @return Object[][] tương thích với @DataProvider TestNG
     */
    public static Object[][] getData(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook wb = new XSSFWorkbook(fis)) {

            Sheet sheet = wb.getSheet(sheetName);
            if (sheet == null)
                throw new RuntimeException("[ExcelReader] Không tìm thấy sheet: " + sheetName);

            int lastRow = sheet.getLastRowNum();
            int lastCol = sheet.getRow(0).getLastCellNum();
            Object[][] data = new Object[lastRow][lastCol];

            for (int r = 1; r <= lastRow; r++) {   // bắt đầu từ 1: bỏ header
                Row row = sheet.getRow(r);
                if (row == null) continue;
                for (int c = 0; c < lastCol; c++)
                    data[r-1][c] = getCellValue(row.getCell(c));
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException("[ExcelReader] Lỗi đọc file: " + filePath, e);
        }
    }

    /** Chuyển Cell thành String — xử lý đủ 4 kiểu + null */
    private static String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING  -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                double v = cell.getNumericCellValue();
                yield (v == Math.floor(v) && !Double.isInfinite(v))
                    ? String.valueOf((long)v) : String.valueOf(v);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> {
                CellType t = cell.getCachedFormulaResultType();
                yield (t == CellType.NUMERIC)
                    ? String.valueOf((long)cell.getNumericCellValue())
                    : (t == CellType.BOOLEAN)
                        ? String.valueOf(cell.getBooleanCellValue())
                        : cell.getStringCellValue().trim();
            }
            default -> "";
        };
    }
}
