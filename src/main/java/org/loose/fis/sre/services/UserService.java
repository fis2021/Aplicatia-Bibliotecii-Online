package org.loose.fis.sre.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dizitart.no2.filters.Filters;
import static org.loose.fis.sre.services.FileSystemService.getPathToFile;

public class UserService {

    public static ObjectRepository<User> userRepository;

    private static Nitrite database;
    public static void initDatabase() {
        FileSystemService.initDirectory();
         database = Nitrite.builder()
                .filePath(getPathToFile("clients.db").toFile())
                .openOrCreate("biblioteca", "biblioteca");

        userRepository = database.getRepository(User.class);
    }


    public static void addUser(String username,String password,String name,String email,String address,String phone) throws UsernameAlreadyExistsException,NoUpperCaseException,UncompletedFieldsException {
        AllFieldsCompleted(username,password,name,email,address,phone);
        checkUserDoesNotAlreadyExist(username);
        UpperCaseExists(password);
        userRepository.insert(new User(username, encodePassword(username, password), name, email, address, phone));

    }


    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        Cursor<User> cursor = userRepository.find();
        for (User user : cursor) {
//            if (Objects.equals(username, user.getUsername()))
            if (username.equals(user.getUsername()))
            {   throw new UsernameAlreadyExistsException(username);

            }
        }
    }
    public static boolean checkIsAdmin(String username)  {
        Cursor<User> cursor = userRepository.find();
        for (User user : cursor) {
         if (username.equals(user.getUsername())) {
             if (user.isAdmin() == true) {
                 return true;
             }
         }
        }
        return false;
    }
    public static boolean UpperCaseExists(String password) throws NoUpperCaseException
    {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(password);
        boolean matchFound = matcher.find();
        if(!matchFound) throw new NoUpperCaseException("Your password must contain at least one upper case!");
        else
            return true;

    }
    public static boolean AllFieldsCompleted(String username,String password,String name,String email,String address,String phone) throws UncompletedFieldsException
    {
        Pattern pattern = Pattern.compile("[\\S+]");
        Matcher matcher1 = pattern.matcher(username);
        Matcher matcher2 = pattern.matcher(password);
        Matcher matcher3 = pattern.matcher(email);
        Matcher matcher4= pattern.matcher(address);
        Matcher matcher5=pattern.matcher(phone);
        Matcher matcher6=pattern.matcher(name);
        boolean matchFound1 = matcher1.find();
        boolean matchFound2 = matcher2.find();
        boolean matchFound3 = matcher3.find();
        boolean matchFound4 = matcher4.find();
        boolean matchFound5 = matcher5.find();
        boolean matchFound6 = matcher6.find();
        if(!matchFound1 ) throw new UncompletedFieldsException("Your must complete all the fields!");
        if(!matchFound2 ) throw new UncompletedFieldsException("Your must complete all the fields!");
        if(!matchFound3 ) throw new UncompletedFieldsException("Your must complete all the fields!");
        if(!matchFound4 ) throw new UncompletedFieldsException("Your must complete all the fields!");
        if(!matchFound5 ) throw new UncompletedFieldsException("Your must complete all the fields!");
        if(!matchFound6) throw new UncompletedFieldsException("Your must complete all the fields!");

        return true;

    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }
    public static User userExists(String username,String password) throws InvalidUsernameException, IncorrectPasswordException {
        int ok=0,ok2=0;
        for(User user :userRepository.find())
        {
            if (Objects.equals(username, user.getUsername()))
            {   ok=1;
                if(encodePassword(username,password).equals(user.getPassword())) {
                    ok2=1;
                    return user;
                }
            }
        }
        if(ok==0)
            throw new InvalidUsernameException("Introduced username is incorrect");
        if(ok2==0)
            throw new IncorrectPasswordException("Introduced password is incorrect");
        return null;
    }
    public static void addAdmin(String username, String password,String name,String email,String address,String phone) throws UsernameAlreadyExistsException,NoUpperCaseException,UncompletedFieldsException
    {    AllFieldsCompleted(username,password,name,email,address,phone);
        checkUserDoesNotAlreadyExist(username);
        UpperCaseExists(password);
        User u=new User(username,encodePassword(username,password),name,email,address,phone);
         u.setisAdmin();
        userRepository.insert(u);
    }
    public static ArrayList<User> AllUsers()
    {   Cursor<User> u=userRepository.find();
        ArrayList<User> au = new ArrayList<User>();
        for(User us:u)
        {   if(us.isAdmin()==false)
        {au.add(us);}
        }
        return au;
    }
    public static List<User> getAllUsers()
    {
        return userRepository.find().toList();
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }
  public static void deleteRecord(String t) {
        Cursor<User> cursor = userRepository.find();
        for (User user : cursor) {
           if(user.getUsername().equals(t))
           {
               userRepository.remove(user);
           }

        }
    }
    public static void closeDatabase()
    {
        database.close();
    }


}
