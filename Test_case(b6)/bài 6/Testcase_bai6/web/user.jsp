<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User, java.util.List" %>
<%
    // Lấy dữ liệu từ Servlet
    String activeTab = (String) request.getAttribute("activeTab");
    if (activeTab == null) activeTab = "edit";

    User editUser = (User) request.getAttribute("editUser");
    String uUsername = (editUser != null && editUser.getUsername() != null) ? editUser.getUsername() : "";
    String uFullname = (editUser != null && editUser.getFullname() != null) ? editUser.getFullname() : "";
    String uPassword = (editUser != null && editUser.getPassword() != null) ? editUser.getPassword() : "";
    String uEmail    = (editUser != null && editUser.getEmail()    != null) ? editUser.getEmail()    : "";

    String message  = (String) request.getAttribute("message");
    List<User> userList = (List<User>) request.getAttribute("userList");

    // Base URL của servlet
    String baseUrl = request.getContextPath() + "/admin/users";
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management – NGHIENPHIM</title>
    <style>
        /* ── Reset & Base ───────────────────────────────────────── */
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #0f1117;
            color: #cdd6f4;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        /* ── Top Navbar ─────────────────────────────────────────── */
        .navbar {
            background: #181c2e;
            height: 46px;
            display: flex;
            align-items: center;
            padding: 0 18px;
            border-bottom: 1px solid #2a2f4a;
            gap: 12px;
            position: fixed;
            top: 0; left: 0; right: 0;
            z-index: 100;
        }
        .navbar .brand {
            display: flex;
            align-items: center;
            gap: 8px;
            font-size: 13px;
            font-weight: 700;
            color: #cdd6f4;
            letter-spacing: 1px;
        }
        .navbar .brand svg {
            color: #7289da;
        }
        .navbar .search {
            flex: 1;
            max-width: 300px;
            background: #0f1117;
            border: 1px solid #2a2f4a;
            border-radius: 4px;
            color: #888;
            font-size: 12px;
            padding: 5px 10px;
            outline: none;
        }
        .navbar .nav-actions {
            margin-left: auto;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .navbar .nav-actions .icon {
            width: 28px; height: 28px;
            border-radius: 50%;
            background: #2a2f4a;
            display: flex; align-items: center; justify-content: center;
            font-size: 13px;
            cursor: pointer;
        }

        /* ── Layout ─────────────────────────────────────────────── */
        .layout {
            display: flex;
            margin-top: 46px;
            flex: 1;
        }

        /* ── Sidebar ─────────────────────────────────────────────── */
        .sidebar {
            width: 185px;
            background: #181c2e;
            border-right: 1px solid #2a2f4a;
            padding: 18px 0;
            min-height: calc(100vh - 46px);
            position: fixed;
            top: 46px;
            left: 0;
            bottom: 0;
        }
        .sidebar .section-label {
            font-size: 10px;
            font-weight: 600;
            color: #555f7e;
            text-transform: uppercase;
            letter-spacing: 0.8px;
            padding: 8px 16px 4px;
        }
        .sidebar a {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 9px 18px;
            font-size: 13px;
            color: #9099b7;
            text-decoration: none;
            transition: background 0.15s, color 0.15s;
        }
        .sidebar a:hover {
            background: #1e2340;
            color: #cdd6f4;
        }
        .sidebar a.active {
            background: #1e2340;
            color: #7289da;
            border-left: 3px solid #7289da;
        }
        .sidebar a .icon { font-size: 14px; width: 18px; text-align: center; }
        .sidebar .logout {
            position: absolute;
            bottom: 0;
            left: 0; right: 0;
        }

        /* ── Main Content ───────────────────────────────────────── */
        .main {
            margin-left: 185px;
            flex: 1;
            padding: 24px 30px;
            max-width: 900px;
        }

        /* ── Tab Strip ──────────────────────────────────────────── */
        .tab-strip {
            display: flex;
            border-bottom: 1px solid #2a2f4a;
            margin-bottom: 24px;
        }
        .tab-strip .tab {
            padding: 9px 18px;
            font-size: 13px;
            color: #9099b7;
            cursor: pointer;
            border-bottom: 2px solid transparent;
            text-decoration: none;
            transition: color 0.15s;
        }
        .tab-strip .tab:hover { color: #cdd6f4; }
        .tab-strip .tab.active {
            color: #7289da;
            border-bottom: 2px solid #7289da;
            font-weight: 600;
        }

        /* ── Message Banner ─────────────────────────────────────── */
        .msg-banner {
            padding: 10px 16px;
            border-radius: 5px;
            font-size: 13px;
            margin-bottom: 18px;
        }
        .msg-banner.success { background: #1a3a27; color: #4ade80; border-left: 4px solid #4ade80; }
        .msg-banner.error   { background: #3a1a1a; color: #f87171; border-left: 4px solid #f87171; }

        /* ── Form Grid ──────────────────────────────────────────── */
        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 22px 40px;
            margin-bottom: 26px;
        }
        .form-group label {
            display: block;
            font-size: 12px;
            color: #9099b7;
            margin-bottom: 8px;
        }
        .form-group input {
            width: 100%;
            background: transparent;
            border: none;
            border-bottom: 1px solid #2a2f4a;
            color: #cdd6f4;
            font-size: 14px;
            padding: 4px 2px 6px;
            outline: none;
            transition: border-color 0.2s;
        }
        .form-group input:focus {
            border-bottom-color: #7289da;
        }

        /* ── Action Buttons ─────────────────────────────────────── */
        .btn-row {
            display: flex;
            gap: 10px;
            margin-top: 6px;
        }
        .btn {
            padding: 7px 22px;
            border: none;
            border-radius: 4px;
            font-size: 13px;
            font-weight: 600;
            cursor: pointer;
            transition: opacity 0.2s, transform 0.1s;
        }
        .btn:hover  { opacity: 0.85; }
        .btn:active { transform: scale(0.97); }
        .btn-create { background: #5562ea; color: #fff; }
        .btn-update { background: #7289da; color: #fff; }
        .btn-delete { background: #ed4245; color: #fff; }
        .btn-reset  { background: #f5a623; color: #fff; }

        /* ── User List Table ────────────────────────────────────── */
        .user-table-wrap {
            overflow-x: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 13px;
        }
        thead tr {
            background: #1e2340;
        }
        thead th {
            padding: 10px 14px;
            text-align: left;
            color: #9099b7;
            font-weight: 600;
            font-size: 11px;
            text-transform: uppercase;
            letter-spacing: 0.7px;
            border-bottom: 1px solid #2a2f4a;
        }
        tbody tr {
            border-bottom: 1px solid #1e2340;
            transition: background 0.12s;
        }
        tbody tr:hover { background: #1c2135; }
        tbody td {
            padding: 9px 14px;
            color: #cdd6f4;
        }
        .action-link {
            color: #7289da;
            text-decoration: none;
            font-size: 12px;
        }
        .action-link:hover { text-decoration: underline; }

        /* ── Footer ─────────────────────────────────────────────── */
        footer {
            text-align: center;
            padding: 16px;
            font-size: 11px;
            color: #555f7e;
            border-top: 1px solid #1e2340;
            margin-left: 185px;
        }
    </style>
</head>
<body>

<!-- ═══ TOP NAVBAR ═══════════════════════════════════════════════════════════ -->
<nav class="navbar">
    <div class="brand">
        <svg width="16" height="16" fill="currentColor" viewBox="0 0 24 24"><path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-1 14H9V8h2v8zm4 0h-2V8h2v8z"/></svg>
        NGHIENPHIM
    </div>
    <input class="search" type="text" placeholder="Tìm kiếm phim...">
    <div class="nav-actions">
        <div class="icon">⚙</div>
        <div class="icon" style="background:#2c3a5e;color:#7289da;font-weight:700;font-size:11px;">N</div>
    </div>
</nav>

<!-- ═══ LAYOUT ════════════════════════════════════════════════════════════════ -->
<div class="layout">

    <!-- ── SIDEBAR ────────────────────────────────────────────────────────── -->
    <aside class="sidebar">
        <div class="section-label">Management</div>

        <a href="#" class="icon">🏠</a>
        <a href="#" style="padding-left:18px">
            <span class="icon">📹</span> Video Management
        </a>
        <a href="<%= baseUrl %>?tab=edit" class="active">
            <span class="icon">👤</span> User Management
        </a>
        <a href="#">
            <span class="icon">📋</span> Report Management
        </a>

        <div class="logout">
            <a href="#">
                <span class="icon">⏻</span> Logout
            </a>
        </div>
    </aside>

    <!-- ── MAIN ───────────────────────────────────────────────────────────── -->
    <main class="main">

        <!-- Tab Strip -->
        <div class="tab-strip">
            <a class="tab <%= "edit".equals(activeTab) ? "active" : "" %>"
               href="<%= baseUrl %>?tab=edit">USER EDITION</a>
            <a class="tab <%= "list".equals(activeTab) ? "active" : "" %>"
               href="<%= baseUrl %>?tab=list">USER LIST</a>
        </div>

        <!-- Flash message -->
        <% if (message != null && !message.isEmpty()) { %>
            <div class="msg-banner <%= message.startsWith("ERROR") ? "error" : "success" %>">
                <%= message %>
            </div>
        <% } %>

        <!-- ╔══════════════════════════════╗ -->
        <!-- ║  TAB: USER EDITION           ║ -->
        <!-- ╚══════════════════════════════╝ -->
        <% if ("edit".equals(activeTab)) { %>
        <form action="<%= baseUrl %>" method="post" id="userForm">

            <div class="form-grid">
                <!-- Username -->
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username"
                           value="<%= uUsername %>" placeholder="vd: admin">
                </div>

                <!-- Password -->
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="text" id="password" name="password"
                           value="<%= uPassword %>" placeholder="vd: 123">
                </div>

                <!-- Fullname -->
                <div class="form-group">
                    <label for="fullname">Fullname</label>
                    <input type="text" id="fullname" name="fullname"
                           value="<%= uFullname %>" placeholder="vd: Văn Tèo">
                </div>

                <!-- Email -->
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email"
                           value="<%= uEmail %>" placeholder="vd: admin@gmail.com">
                </div>
            </div>

            <!-- Buttons -->
            <div class="btn-row">
                <button type="submit" name="action" value="create" class="btn btn-create">Create</button>
                <button type="submit" name="action" value="update" class="btn btn-update">Update</button>
                <button type="submit" name="action" value="delete" class="btn btn-delete">Delete</button>
                <button type="reset"  class="btn btn-reset">Reset</button>
            </div>
        </form>
        <% } %>

        <!-- ╔══════════════════════════════╗ -->
        <!-- ║  TAB: USER LIST              ║ -->
        <!-- ╚══════════════════════════════╝ -->
        <% if ("list".equals(activeTab)) { %>
        <div class="user-table-wrap">
            <table id="userTable">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Username</th>
                        <th>Fullname</th>
                        <th>Email</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <% if (userList == null || userList.isEmpty()) { %>
                    <tr>
                        <td colspan="5" style="text-align:center;color:#555f7e;padding:30px">
                            No users found.
                        </td>
                    </tr>
                <% } else {
                    int idx = 1;
                    for (User u : userList) { %>
                    <tr>
                        <td><%= idx++ %></td>
                        <td><%= u.getUsername() %></td>
                        <td><%= u.getFullname() %></td>
                        <td><%= u.getEmail() %></td>
                        <td>
                            <a class="action-link"
                               href="<%= baseUrl %>?username=<%= u.getUsername() %>">Edit</a>
                        </td>
                    </tr>
                <% }} %>
                </tbody>
            </table>
        </div>
        <% } %>

    </main>
</div>

<!-- ═══ FOOTER ════════════════════════════════════════════════════════════════ -->
<footer>Copyright © 2022 NGHIENPHIM. All rights reserved.</footer>

</body>
</html>