package dao;

import model.User;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases cho UserDAO – bao phủ đầy đủ các trường hợp CRUD.
 *
 * Yêu cầu:
 *  - MySQL đang chạy tại localhost:3306
 *  - Database testdb đã được tạo (chạy schema.sql)
 *  - Bảng users tồn tại
 *
 * Kịch bản được đánh số theo bảng báo cáo kiểm thử.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDAOTest {

    private static UserDAO dao;

    // ─── username dùng riêng cho test, không đụng data seed ──────────────────
    private static final String TEST_USER = "test_junit";

    @BeforeAll
    static void setup() {
        dao = new UserDAO();
    }

    @AfterAll
    static void cleanup() throws Exception {
        // Dọn dẹp record test nếu còn tồn tại
        if (dao.exists(TEST_USER)) {
            dao.delete(TEST_USER);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-01  Insert user mới hợp lệ → thành công
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(1)
    @DisplayName("TC-01: Insert user hợp lệ – thành công")
    void tc01_insertValidUser() throws Exception {
        User u = new User(TEST_USER, "JUnit Tester", "test123", "junit@test.com");
        boolean result = dao.insert(u);
        assertTrue(result, "Insert phải trả về true khi thêm user thành công");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-02  Insert user trùng username → ném Exception (duplicate PK)
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(2)
    @DisplayName("TC-02: Insert user trùng username – phải ném Exception")
    void tc02_insertDuplicateUsername() {
        User u = new User(TEST_USER, "Duplicate", "dup", "dup@test.com");
        assertThrows(Exception.class, () -> dao.insert(u),
                "Insert username trùng phải ném Exception (duplicate key)");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-03  getByUsername với username tồn tại → trả User đúng
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(3)
    @DisplayName("TC-03: getByUsername – username tồn tại → trả về đúng User")
    void tc03_getByUsernameExists() throws Exception {
        User u = dao.getByUsername(TEST_USER);
        assertNotNull(u, "Kết quả không được null khi username tồn tại");
        assertEquals(TEST_USER,       u.getUsername(), "Username phải khớp");
        assertEquals("junit@test.com", u.getEmail(),   "Email phải khớp");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-04  getByUsername với username không tồn tại → trả null
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(4)
    @DisplayName("TC-04: getByUsername – username không tồn tại → trả null")
    void tc04_getByUsernameNotFound() throws Exception {
        User u = dao.getByUsername("nonexistent_xyz_9999");
        assertNull(u, "Phải trả về null khi username không tồn tại");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-05  getAll → danh sách không rỗng, chứa ít nhất 1 phần tử
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(5)
    @DisplayName("TC-05: getAll – trả về danh sách không rỗng")
    void tc05_getAllReturnsNonEmpty() throws Exception {
        List<User> list = dao.getAll();
        assertNotNull(list, "Danh sách không được null");
        assertFalse(list.isEmpty(), "Danh sách phải có ít nhất 1 user (seed data)");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-06  getAll → kết quả chứa user vừa thêm ở TC-01
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(6)
    @DisplayName("TC-06: getAll – chứa user mới vừa thêm")
    void tc06_getAllContainsNewUser() throws Exception {
        List<User> list = dao.getAll();
        boolean found = list.stream().anyMatch(u -> TEST_USER.equals(u.getUsername()));
        assertTrue(found, "Danh sách phải chứa '" + TEST_USER + "' sau khi insert");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-07  exists với username hợp lệ → true
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(7)
    @DisplayName("TC-07: exists – username tồn tại → true")
    void tc07_existsTrue() throws Exception {
        assertTrue(dao.exists(TEST_USER), "exists() phải trả true khi user tồn tại");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-08  exists với username không tồn tại → false
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(8)
    @DisplayName("TC-08: exists – username không tồn tại → false")
    void tc08_existsFalse() throws Exception {
        assertFalse(dao.exists("ghost_user_000"), "exists() phải trả false khi user không tồn tại");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-09  Update user tồn tại → cập nhật thành công
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(9)
    @DisplayName("TC-09: Update – user tồn tại → cập nhật thành công")
    void tc09_updateExistingUser() throws Exception {
        User updated = new User(TEST_USER, "JUnit Updated", "newpass", "updated@test.com");
        boolean result = dao.update(updated);
        assertTrue(result, "Update phải trả về true");

        User u = dao.getByUsername(TEST_USER);
        assertEquals("JUnit Updated",   u.getFullname(), "Fullname phải được cập nhật");
        assertEquals("newpass",         u.getPassword(), "Password phải được cập nhật");
        assertEquals("updated@test.com", u.getEmail(),   "Email phải được cập nhật");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-10  Update user không tồn tại → affected rows = 0 (false)
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(10)
    @DisplayName("TC-10: Update – username không tồn tại → false (0 rows affected)")
    void tc10_updateNonExistent() throws Exception {
        User ghost = new User("ghost_user_000", "Ghost", "xxx", "ghost@x.com");
        boolean result = dao.update(ghost);
        assertFalse(result, "Update user không tồn tại phải trả về false");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-11  Delete user tồn tại → xoá thành công
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(11)
    @DisplayName("TC-11: Delete – user tồn tại → xoá thành công")
    void tc11_deleteExistingUser() throws Exception {
        boolean result = dao.delete(TEST_USER);
        assertTrue(result, "Delete phải trả về true khi user tồn tại");
        assertFalse(dao.exists(TEST_USER), "User phải không còn tồn tại sau khi xoá");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-12  Delete user không tồn tại → affected rows = 0 (false)
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(12)
    @DisplayName("TC-12: Delete – username không tồn tại → false")
    void tc12_deleteNonExistent() throws Exception {
        boolean result = dao.delete("ghost_user_000");
        assertFalse(result, "Delete user không tồn tại phải trả về false");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-13  Insert user với email trùng → ném Exception (duplicate UNIQUE)
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(13)
    @DisplayName("TC-13: Insert email trùng – phải ném Exception (UNIQUE constraint)")
    void tc13_insertDuplicateEmail() {
        // 'admin@gmail.com' đã tồn tại trong seed data
        User u = new User("new_admin_2", "New Admin", "pass", "admin@gmail.com");
        assertThrows(Exception.class, () -> dao.insert(u),
                "Insert email trùng phải ném Exception (UNIQUE constraint)");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-14  Insert user với username rỗng → ném Exception (NOT NULL / PK)
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(14)
    @DisplayName("TC-14: Insert username rỗng/null – phải ném Exception")
    void tc14_insertEmptyUsername() {
        User u = new User("", "No Name", "pass", "noname@test.com");
        assertThrows(Exception.class, () -> dao.insert(u),
                "Username rỗng phải ném Exception");
    }

    // ══════════════════════════════════════════════════════════════════════════
    // TC-15  Seed data: Admin user tồn tại trong DB
    // ══════════════════════════════════════════════════════════════════════════
    @Test
    @Order(15)
    @DisplayName("TC-15: Seed data – admin tồn tại trong database")
    void tc15_seedAdminExists() throws Exception {
        // Chỉ chạy nếu seed data đã được import
        User admin = dao.getByUsername("admin");
        // Không fail nếu chưa seed – ta dùng assumeTrue
        org.junit.jupiter.api.Assumptions.assumeTrue(admin != null,
                "Seed data chưa được import – bỏ qua TC-15");
        assertEquals("admin@gmail.com", admin.getEmail(), "Email admin phải đúng");
    }
}
