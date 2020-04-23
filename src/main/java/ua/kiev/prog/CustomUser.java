package ua.kiev.prog;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@Data @NoArgsConstructor
public class CustomUser {

    private String id;

    private String login;
    private String password;

    private List<UserRole> roles;

    private String email;
    private String phone;

    public CustomUser(String login, String password, UserRole role, String email, String phone) {
        this.login = login;
        this.password = password;
        this.roles = new ArrayList<>();
        this.roles.add(role);
        this.email = email;
        this.phone = phone;
    }

    public void addRole(UserRole role) {
        this.roles.add(role);
    }
}
