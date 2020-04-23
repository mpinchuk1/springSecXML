package ua.kiev.prog;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement
@XmlType(name = "userList")
@Data
@NoArgsConstructor
public class CustomUserList {

    private List<CustomUser> list = new ArrayList<>();


}
