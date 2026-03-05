package controller;

import dao.UserDAO;
import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/users/*")
public class UserServlet extends HttpServlet {

    private final UserDAO dao = new UserDAO();

    // ── GET: load trang + load user nếu có username trên URL ─────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); // /edit?username=admin  hoặc /list
        String tab = req.getParameter("tab"); // "edit" | "list"
        if (tab == null) tab = "edit";

        try {
            // Load danh sách users cho tab USER LIST
            List<User> userList = dao.getAll();
            req.setAttribute("userList", userList);

            // Nếu có username thì load user đó vào form
            String username = req.getParameter("username");
            if (username != null && !username.isEmpty()) {
                User u = dao.getByUsername(username);
                req.setAttribute("editUser", u);
                tab = "edit";
            }

            req.setAttribute("activeTab", tab);
            req.getRequestDispatcher("/user.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/user.jsp").forward(req, resp);
        }
    }

    // ── POST: xử lý create / update / delete ─────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action   = req.getParameter("action");
        String username = req.getParameter("username");
        String fullname = req.getParameter("fullname");
        String password = req.getParameter("password");
        String email    = req.getParameter("email");

        String message = "";
        String tab     = "edit";

        try {
            switch (action == null ? "" : action) {
                case "create":
                    if (dao.exists(username)) {
                        message = "ERROR: Username '" + username + "' already exists!";
                    } else {
                        dao.insert(new User(username, fullname, password, email));
                        message = "User created successfully.";
                    }
                    break;

                case "update":
                    if (!dao.exists(username)) {
                        message = "ERROR: Username '" + username + "' does not exist!";
                    } else {
                        dao.update(new User(username, fullname, password, email));
                        message = "User updated successfully.";
                    }
                    break;

                case "delete":
                    if (!dao.exists(username)) {
                        message = "ERROR: Username '" + username + "' does not exist!";
                    } else {
                        dao.delete(username);
                        message = "User deleted successfully.";
                        username = ""; fullname = ""; password = ""; email = "";
                    }
                    break;

                default:
                    message = "Unknown action.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Database error: " + e.getMessage();
        }

        // Đẩy lại List & thông báo để render trên cùng trang
        try {
            req.setAttribute("userList", dao.getAll());
        } catch (Exception ignored) {}

        User formUser = new User(username, fullname, password, email);
        req.setAttribute("editUser",  formUser);
        req.setAttribute("message",   message);
        req.setAttribute("activeTab", tab);
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }
}