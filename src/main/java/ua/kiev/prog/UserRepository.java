package ua.kiev.prog;


import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserRepository {

    private final File f = new File("D:\\Max\\projects\\SecTestHomeworkFile\\src\\main\\resources\\databaseFile\\database.txt");
    private final CustomUserList customUserList = new CustomUserList();

    public CustomUser findByLogin(String login){
        CustomUser user = null;
        for(CustomUser u: customUserList.getList()){
            if(login.equals(u.getLogin())){
                user = u;
            }
        }
        return user;
    }


    public boolean existsByLogin(String login){
        for(CustomUser u: customUserList.getList()){
            if(login.equals(u.getLogin())) return true;
        }
         return false;
    }

    public List<CustomUser> findAll() {
        return customUserList.getList();
    }

    public Optional<CustomUser> findById(String id) {
        Optional<CustomUser> user = Optional.empty();
        for(CustomUser u: customUserList.getList()){
            if(id.equals(u.getId())){
                user = Optional.of(u);
            }
        }
        return user;
    }

    public void deleteById(String id) {

        customUserList.getList().removeIf(u -> u.getId().equals(id));
        toXMLToFile(customUserList);

    }

    public void save(CustomUser user) {
        user.setId(UUID.randomUUID().toString());
        customUserList.getList().add(user);
        toXMLToFile(customUserList);
        System.out.println(customUserList);
    }

    public void toXMLToFile(CustomUserList userList){

        try(FileOutputStream outputStream = new FileOutputStream(f))
        {
            JAXBContext jaxbContext = JAXBContext.newInstance(CustomUserList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(userList, sw);
            String xmlUserList = sw.toString();

            byte[] strToBytes = xmlUserList.getBytes();
            outputStream.write(strToBytes);

        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }

    }

    public void getUsersFromFile(){
        try {
            InputStream  is = new FileInputStream(f);
            JAXBContext context = JAXBContext.newInstance(CustomUserList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            CustomUserList userListTemp = (CustomUserList) unmarshaller.unmarshal(is);
            customUserList.setList(userListTemp.getList());
        } catch (FileNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
    }

    public void makeModer(String id) {
        for(CustomUser user: customUserList.getList()){
            if(user.getId().equals(id))
                user.addRole(UserRole.MODERATOR);
        }
        toXMLToFile(customUserList);
    }
}
