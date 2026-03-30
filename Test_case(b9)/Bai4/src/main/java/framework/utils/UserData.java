package framework.utils;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BÀI 4 - UserData: POJO mapping từ users.json.
 * @JsonProperty: map tên field JSON → Java field.
 * Jackson cần constructor mặc định (no-arg) để deserialize.
 */
public class UserData {
    @JsonProperty("username")       public String  username;
    @JsonProperty("password")       public String  password;
    @JsonProperty("role")           public String  role;
    @JsonProperty("expect_success") public boolean expectSuccess;
    @JsonProperty("description")    public String  description;
    public UserData() {}
    @Override public String toString() {
        return "UserData{user='" + username + "', role='" + role
             + "', expectSuccess=" + expectSuccess + ", desc='" + description + "'}";
    }
}
