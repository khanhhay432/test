package DAO;
import DB.DBConnection;
import Model.Organization;
import java.sql.*;

public class OrganizationDAO {

    public java.util.List<Organization> getAllOrganizations() throws Exception {
        java.util.List<Organization> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM ORGANIZATION ORDER BY OrgId DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Organization org = new Organization();
                org.setOrgId(rs.getInt("OrgId"));
                org.setOrgName(rs.getString("OrgName"));
                org.setAddress(rs.getString("Address"));
                org.setPhone(rs.getString("Phone"));
                org.setEmail(rs.getString("Email"));
                org.setCreatedDate(rs.getTimestamp("CreatedDate"));
                list.add(org);
            }
        }
        return list;
    }

    public boolean isOrgNameExists(String name) throws Exception {
        String sql = "SELECT COUNT(*) FROM ORGANIZATION WHERE UPPER(OrgName)=UPPER(?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    public int insertOrganization(Organization org) throws Exception {
        String sql = "INSERT INTO ORGANIZATION(OrgName,Address,Phone,Email) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, org.getOrgName());
            ps.setString(2, org.getAddress());
            ps.setString(3, org.getPhone());
            ps.setString(4, org.getEmail());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }

    public Organization getOrganizationById(int orgId) throws Exception {
        String sql = "SELECT * FROM ORGANIZATION WHERE OrgId=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orgId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Organization org = new Organization();
                org.setOrgId(rs.getInt("OrgId"));
                org.setOrgName(rs.getString("OrgName"));
                org.setAddress(rs.getString("Address"));
                org.setPhone(rs.getString("Phone"));
                org.setEmail(rs.getString("Email"));
                org.setCreatedDate(rs.getTimestamp("CreatedDate"));
                return org;
            }
        }
        return null;
    }

    public boolean updateOrganization(Organization org) throws Exception {
        String sql = "UPDATE ORGANIZATION SET OrgName=?, Address=?, Phone=?, Email=? WHERE OrgId=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, org.getOrgName());
            ps.setString(2, org.getAddress());
            ps.setString(3, org.getPhone());
            ps.setString(4, org.getEmail());
            ps.setInt(5, org.getOrgId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteOrganization(int orgId) throws Exception {
        String sql = "DELETE FROM ORGANIZATION WHERE OrgId=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orgId);
            return ps.executeUpdate() > 0;
        }
    }
}
