package Service;
import DAO.OrganizationDAO;
import Model.Organization;
import java.util.regex.Pattern;

public class OrganizationService {

    private OrganizationDAO dao = new OrganizationDAO();

    public java.util.List<Organization> getAllOrganizations() throws Exception {
        return dao.getAllOrganizations();
    }

    public String validate(Organization org) throws Exception {

        if (org.getOrgName() == null || org.getOrgName().trim().isEmpty())
            return "Organization Name is required";

        if (org.getOrgName().length() < 3 || org.getOrgName().length() > 255)
            return "Organization Name length must be 3-255";

        if (dao.isOrgNameExists(org.getOrgName()))
            return "Organization Name already exists";

        if (org.getEmail() != null && !org.getEmail().isEmpty()) {
            String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!Pattern.matches(regex, org.getEmail()))
                return "Invalid Email format";
        }

        if (org.getPhone() != null && !org.getPhone().isEmpty()) {
            if (!org.getPhone().matches("\\d{9,12}"))
                return "Phone must be 9-12 digits";
        }

        return null;
    }

    public int saveOrganization(Organization org) throws Exception {
        return dao.insertOrganization(org);
    }

    public Organization getOrganization(int orgId) throws Exception {
        return dao.getOrganizationById(orgId);
    }

    public String validateForUpdate(Organization org) throws Exception {
        if (org.getOrgName() == null || org.getOrgName().trim().isEmpty())
            return "Organization Name is required";

        if (org.getOrgName().length() < 3 || org.getOrgName().length() > 255)
            return "Organization Name length must be 3-255";

        // Skip duplicate check for update
        
        if (org.getEmail() != null && !org.getEmail().isEmpty()) {
            String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            if (!Pattern.matches(regex, org.getEmail()))
                return "Invalid Email format";
        }

        if (org.getPhone() != null && !org.getPhone().isEmpty()) {
            if (!org.getPhone().matches("\\d{9,12}"))
                return "Phone must be 9-12 digits";
        }

        return null;
    }

    public boolean updateOrganization(Organization org) throws Exception {
        return dao.updateOrganization(org);
    }

    public boolean deleteOrganization(int orgId) throws Exception {
        return dao.deleteOrganization(orgId);
    }
}
