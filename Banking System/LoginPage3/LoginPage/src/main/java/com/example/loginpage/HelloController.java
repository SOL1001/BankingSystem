package com.example.loginpage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class HelloController implements Initializable {
    @FXML
    private TableView<Account> accountTable=new TableView<>();

    @FXML
    private TableColumn<Account, String> idcol=new TableColumn<>();

    @FXML
    private TableColumn<Account, String>namecol=new TableColumn<>();

    @FXML
    private TableColumn<Account, String> passcol=new TableColumn<>();

    @FXML
    private TableColumn<Account, String> rolecol=new TableColumn<>();

    @FXML
    private TableColumn<Account, String> usernamecol=new TableColumn<>();

    @FXML
    Label userNameerr;
    @FXML
    Label label;
    @FXML
    Label passError;
    @FXML
    private Label nameerror;
    @FXML
    private Label idError;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtsearch;
    @FXML
    private TextField txtbalance;
    @FXML
    private TextField txtaccnumber;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtpassword;
    @FXML
    private TextField txtdeposit;

    @FXML
    private ComboBox<String> role=new ComboBox<>();
    @FXML
    private ComboBox<String> roleempcu=new ComboBox<>();

    String rolee="superadmin";
    String rolead="employee";
    ArrayList<String> drop=new ArrayList<>();
    ArrayList<String> dropad=new ArrayList<>();
    @FXML
    private Button txtregister;
    @FXML
     AnchorPane diplay;
    ObservableList<Account> data = FXCollections.observableArrayList();
    DataBaseConnection connection=new DataBaseConnection();
    Connection con=connection.getConnection();
    private boolean t=false;
    private String user="";
    private String userId;
    @FXML
    private Label superlabel=new Label();
    @FXML
    private TextField txtaddress;
    private String logedPerson;
    @FXML
    private TextField txtamount;
    @FXML
    private TextField txtto;

    public HelloController() throws SQLException, ClassNotFoundException {
    }




    @FXML
 void Back(ActionEvent event) throws IOException {
     FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
     Scene scene = new Scene(fxmlLoader.load());
     HelloApplication.stage.setTitle("Login Page!");
     HelloApplication.stage.setScene(scene);
 }
    @FXML
    void register(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Create.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Create Account!");
        HelloApplication.stage.setScene(scene);
        drop.add("admin");
        drop.add("customer");
        drop.add("superadmin");
        drop.add("employee");
        dropad.add("customer");
        dropad.add("employee");

        role.getItems().addAll(drop);
        roleempcu.getItems().addAll(dropad);

        System.out.println(role.getValue());

    }
    public  void  CreateAccount(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateAccount.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Hello!");
        HelloApplication.stage.setScene(scene);    }
    public  void  CreateAccountempcu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateAccountForEmployeAndUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Hello!");
        HelloApplication.stage.setScene(scene);    }
    public  void  backToSuper(ActionEvent event) throws IOException, SQLException {
        String sql2 = "select role from admin where id =?";
        String c2 = readLogedAccount();
        c2=c2.trim();
        PreparedStatement st2 = con.prepareStatement(sql2);
        st2.setString(1, c2);
        ResultSet rs2 = st2.executeQuery();
        while (rs2.next()) {
            System.out.println("employee");

            if (rs2.getString("role").equals("superadmin")) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuperAdmin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                HelloApplication.stage.setTitle("Login Successfully!");
                HelloApplication.stage.setScene(scene);
            } else if (rs2.getString("role").equals("admin")) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                HelloApplication.stage.setTitle("Hello!");
                HelloApplication.stage.setScene(scene);
            }
        }

        //.....................................
        }
    public  void  backToAdmin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Hello!");
        HelloApplication.stage.setScene(scene);    }

    public  void updateAccount(ActionEvent event) throws SQLException {
        String sql="UPDATE admin SET password=?,name=?,address=? where id='"+ txtsearch.getText()+"'";
        PreparedStatement prep = con.prepareStatement(sql);
        prep.setString(1, txtpassword.getText());
        prep.setString(2, txtname.getText());
        prep.setString(3, txtaddress.getText());
        prep.executeUpdate();
        String sql1="UPDATE employee SET password=?,name=?,address=? where id='"+ txtsearch.getText()+"'";
        PreparedStatement prep2 = con.prepareStatement(sql1);
        prep2.setString(1, txtpassword.getText());
        prep2.setString(2, txtname.getText());
        prep2.setString(3, txtaddress.getText());
        prep2.executeUpdate();
        txtpassword.clear();
        txtname.clear();
        txtaddress.clear();
        txtID.clear();
    }
    public  void deleteAccount(ActionEvent event) throws SQLException {


        String sql="delete from super_admin where id=?";

        PreparedStatement prep = con.prepareStatement(sql);
        prep.setString(1, txtsearch.getText());
        prep.executeUpdate();

        String sql2="delete from admin where id=?";

        PreparedStatement prep2 = con.prepareStatement(sql2);
        prep2.setString(1, txtsearch.getText());
        prep2.executeUpdate();
        String sql3="delete from employee where id=?";

        PreparedStatement prep3= con.prepareStatement(sql3);
        prep3.setString(1, txtsearch.getText());
        prep3.executeUpdate();
        txtID.setText("");
        txtpassword.setText("");
        txtaddress.setText("");
        txtname.setText("");
    }

    public  void transfer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Transfer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Hello!");
        HelloApplication.stage.setScene(scene);

    }

    public  void search(ActionEvent event) throws SQLException {

        String id = "",name = null,address = null,password = null,rolew = null;
        String sql="select *from super_admin where id=?";
        PreparedStatement stmt=con.prepareStatement(sql);
        stmt.setString(1,txtsearch.getText());
        ResultSet re=stmt.executeQuery();
        while (re.next()){
                  id =re.getString("id");
                  name=re.getString("name");
                   address=re.getString("address");
                  password=re.getString("password");
                  rolew=re.getString("role");

             }

        String sql2="select *from admin where id=?";
        PreparedStatement stmt2=con.prepareStatement(sql2);
        stmt2.setString(1,txtsearch.getText());
        ResultSet re2=stmt2.executeQuery();
        while (re2.next()){
             id =re2.getString("id");
             name=re2.getString("name");
              address=re2.getString("address");
             password=re2.getString("password");
            rolew=re2.getString("role");

        }  String sql3="select *from employee where id=?";
        PreparedStatement stmt3=con.prepareStatement(sql3);
        stmt3.setString(1,txtsearch.getText());
        ResultSet re3=stmt.executeQuery();
        while (re3.next()){
             id =re3.getString("id");
             name=re3.getString("name");
              address=re3.getString("address");
             password=re3.getString("password");
            rolew=re3.getString("role");

        }

                 txtID.setText(id);
                 txtpassword.setText(password);
                 txtaddress.setText(address);
                 txtname.setText(name);

    }

    @FXML
    void  createAccount() throws SQLException, ClassNotFoundException, IOException {
        boolean n,d,p,e,s;
        e=n=p=d=s=true;
        String sql1="select *from FXsql ";
        Statement st=con.prepareStatement(sql1);
        ResultSet res1=st.executeQuery(sql1);
//        while (res1.next()){
//            if (txtUsername.getText().equals(res1.getString("userName"))){
//                System.out.println("get");
//                userNameerr.setText("Already taken");
//                s=false;
//
//            }
//        }

//         if(txtUsername.getText().isBlank()){
//             userNameerr.setText("userName is required");
//             e=false;
//         }


         if (txtpassword.getText().isBlank()){
             p=false;
             passError.setText("Password is Required");

         }
//        if (!txtUsername.getText().isBlank() && !s){
//            e=false;
//            userNameerr.setText("The userName is already available");
//
//        }

         if (!txtpassword.getText().isBlank() && txtpassword.getText().length()<8){
             p=false;
             passError.setText("password should at least 8 words");

         }
//        if(!txtUsername.getText().isBlank()){
//            userNameerr.setText("");
//            e=true;
//
//        }

        if(txtname.getText().isBlank()){
            nameerror.setText("Name is required");
            n=false;
        }


        if (txtID.getText().isBlank()){
            d=false;
            idError.setText("Department is Required");

        }


//        if(!txtUsername.getText().isBlank()){
//            userNameerr.setText("");
//           e=true;
//        }

        if(!txtname.getText().isBlank()){
            nameerror.setText("");
            n=true;
        }
        if(!txtID.getText().isBlank()){
            idError.setText("");
            d=true;
        }

        if (!txtpassword.getText().isBlank() && txtpassword.getText().length()>8){
            p=true;
            passError.setText("");

        }

        String sql2="select *from admin where role=?";
        PreparedStatement pst=con.prepareStatement(sql2);
        pst.setString(1,"superadmin");
        ResultSet re1=pst.executeQuery();
        boolean superadmin=false;
        while (re1.next()){
            if (re1.getString("role").equals("superadmin")){
                superadmin=true;
                System.out.println(re1.getString("role").equals("superadmin"));
            }
        }
        if (superadmin) {
            if (e && n && d && p && rolee != "superadmin") {
                if (rolee.equals("customer")) {
                    String sql = "INSERT INTO customer  (id,name,address,accountNumber,deposit,password,role) VALUES(?,?,?,?,?,?,?)";
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.setString(1, txtID.getText());
                    prep.setString(2, txtname.getText());
                    prep.setString(3, txtaddress.getText());
                    prep.setString(4, txtaccnumber.getText());
                    prep.setString(5, txtdeposit.getText());
                    prep.setString(6, txtpassword.getText());
                    prep.setString(7, rolee);
                    prep.executeUpdate();
                }
                else{
                    String sql = "INSERT INTO admin (id,name,address,password,role) VALUES(?,?,?,?,?)";
                    PreparedStatement prep = con.prepareStatement(sql);
                    prep.setString(1, txtID.getText());
                    prep.setString(2, txtname.getText());
                    prep.setString(3, txtaddress.getText());
                    prep.setString(4, txtpassword.getText());
                    prep.setString(5, rolee);
                    prep.executeUpdate();
                }
//                if (rolee.equals("employee")) {
//                    String sql = "INSERT INTO employee  (id,name,address,password,role) VALUES(?,?,?,?,?)";
//                    PreparedStatement prep = con.prepareStatement(sql);
//                    prep.setString(1, txtID.getText());
//                    prep.setString(2, txtname.getText());
//                    prep.setString(3, txtaddress.getText());
//                    prep.setString(4, txtpassword.getText());
//                    prep.setString(5, rolee);
//                    prep.executeUpdate();
//                }


//                String sql11 = "INSERT INTO user (name,id) VALUES(?,?)";
//                PreparedStatement prep11 = con.prepareStatement(sql11);
//                prep11.setString(1, txtname.getText());
//                prep11.setString(2, txtID.getText());
//                prep11.executeUpdate();
                Date now = new Date(System.currentTimeMillis());

                // Print the current date and time
                System.out.println(now);

                // Format the date and time using a SimpleDateFormat
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String formattedDate = formatter.format(now);
                String sqltr = "INSERT INTO transaction (user_id,devent,tevent) VALUES(?,?,?)";
                PreparedStatement preptr = con.prepareStatement(sqltr);
                preptr.setString(1, txtID.getText());
                preptr.setString(2, formattedDate);
                preptr.setString(3, "create account");
                preptr.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Login Page");
                alert.setContentText("You successfully registered !!!");
                alert.showAndWait();
                System.out.println(user == "superadmin");
                System.out.println(user.equals("superadmin"));

                write_read();
                txtname.clear();
                txtID.clear();
                txtpassword.clear();



            }
        }
        else {
            String sql = "INSERT INTO admin  (id,name,address,password,role) VALUES(?,?,?,?,?)";
            PreparedStatement prep = con.prepareStatement(sql);
            prep.setString(1, txtID.getText());
            prep.setString(2, txtname.getText());
            prep.setString(3, txtaddress.getText());
            prep.setString(4, txtpassword.getText());
            prep.setString(5, rolee);
            prep.executeUpdate();

            Date now = new Date(System.currentTimeMillis());

            // Print the current date and time
            System.out.println(now);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String formattedDate = formatter.format(now);
            String sqltr = "INSERT INTO transaction (user_id,devent,tevent) VALUES(?,?,?)";
            PreparedStatement preptr = con.prepareStatement(sqltr);
            preptr.setString(1, txtID.getText());
            preptr.setString(2, formattedDate);
            preptr.setString(3, "create account");
            preptr.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Login Page");
            alert.setContentText("You successfully registered !!!");
            alert.showAndWait();
            write_read();
            txtname.clear();
            txtID.clear();
            txtpassword.clear();
            txtaddress.clear();


        }

 }

    @FXML
    void login(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        boolean n,d,p,e;
        e=n=p=d=true;

        if(txtUsername.getText().isBlank()){
            userNameerr.setText("userName is required");
            e=false;
        }


        if (txtpassword.getText().isBlank()){
            p=false;
            passError.setText("Password is Required");

        }
        if (!txtpassword.getText().isBlank() && txtpassword.getText().length()<8){
            p=false;
            passError.setText("Password should contian at least 8 words");

        }
        if(!txtUsername.getText().isBlank()){
            userNameerr.setText("");
            e=true;

        }


        if(!txtUsername.getText().isBlank()){
            userNameerr.setText("");
            e=true;
        }



        if (!txtpassword.getText().isBlank() && txtpassword.getText().length()>8){
            p=true;
            passError.setText("");

        }

//        String sql = "SELECT COUNT(1) FROM FXsql where password =? and userName=?";
//        PreparedStatement statement=con.prepareStatement(sql);
//        statement.setString(1,txtpassword.getText());
//        statement.setString(2,txtUsername.getText());
//        ResultSet res = statement.executeQuery();
        String sql2="select *from admin where role=?";
        PreparedStatement pst=con.prepareStatement(sql2);
        pst.setString(1,"superadmin");
        ResultSet re1=pst.executeQuery();
        boolean superadmin=false;
        while (re1.next()){
            if (re1.getString("role").equals("superadmin")){
                superadmin=true;
                System.out.println(re1.getString("role").equals("superadmin"));
            }
        }
       if (superadmin){
//           String sql3="select *from super_admin where password =? and name=?";
//           PreparedStatement statement3=con.prepareStatement(sql3);
//           statement3.setString(1,txtpassword.getText());
//           statement3.setString(2,txtUsername.getText());
//           ResultSet re3=statement3.executeQuery();
//           while (re3.next()) {
//               if (!re3.getString("role").isBlank())
//                   logedPerson = re3.getString("role");
//               userId=re3.getString("id");
//           }

           String sql4="select *from admin where password =? and name=?";
           PreparedStatement statement4=con.prepareStatement(sql4);
           statement4.setString(1,txtpassword.getText());
           statement4.setString(2,txtUsername.getText());
           ResultSet re4=statement4.executeQuery();
           while (re4.next()) {
               if (!re4.getString("role").isBlank())
                   logedPerson = re4.getString("role");
                   userId=re4.getString("id");

           }
           String sql5="select *from customer where password =? and name=?";
           PreparedStatement statement5=con.prepareStatement(sql5);
           statement5.setString(1,txtpassword.getText());
           statement5.setString(2,txtUsername.getText());
           ResultSet re5=statement5.executeQuery();
           while (re5.next()) {
               if (!re5.getString("role").isBlank())
                   logedPerson = re5.getString("role");
               userId=re5.getString("id");

           }

//           while (res.next()) {
//            if (res.getInt(1) ==1) {
//                String sql1 = "SELECT *FROM FXsql where password =? and userName=?";
//                PreparedStatement statement1=con.prepareStatement(sql1);
//                statement1.setString(1,txtpassword.getText());
//                statement1.setString(2,txtUsername.getText());
//                ResultSet res1 = statement1.executeQuery();
//
//                while (res1.next()) {
//                    userId=res1.getString("account_id");
                    if (logedPerson.equals("admin")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        HelloApplication.stage.setTitle("Login Successfully!");
                        HelloApplication.stage.setScene(scene);
                        user=logedPerson;
                        System.out.println("user l"+user);
                        scene.getRoot().layout();
                    } else if (logedPerson.equals("superadmin")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuperAdmin.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        HelloApplication.stage.setTitle("Login Successfully!");
                        HelloApplication.stage.setScene(scene);
                        user=logedPerson;
                        System.out.println("user l"+user);
                        scene.getRoot().layout();

                    }
                    else if (logedPerson.equals("employee")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Empoyee.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        HelloApplication.stage.setTitle("Login Successfully!");
                        HelloApplication.stage.setScene(scene);
                        user=logedPerson;
                        scene.getRoot().layout();
                    }
                    else if (logedPerson.equals("customer")) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Customer.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        HelloApplication.stage.setTitle("Login Successfully!");
                        HelloApplication.stage.setScene(scene);
                        user=logedPerson;
                        scene.getRoot().layout();
                    }
                    BufferedWriter writer=new BufferedWriter(new FileWriter("account.txt"));
                    writer.write(userId);
                    writer.close();
           Date now = new Date(System.currentTimeMillis());

           SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
           String formattedDate = formatter.format(now);
           String sqltr= "INSERT INTO transaction (user_id,devent,tevent) VALUES(?,?,?)";
           PreparedStatement preptr = con.prepareStatement(sqltr);
           preptr.setString(1, userId);
           preptr.setString(2, formattedDate);
           preptr.setString(3, "log in");
           preptr.executeUpdate();

                }









        else {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information Dialog");
           alert.setHeaderText("You don't have a super Admin ");
           alert.setContentText("please create super admin first !!!");
           alert.showAndWait();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateSuperAdmin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            HelloApplication.stage.setTitle("Create Account!");
            HelloApplication.stage.setScene(scene);
        }
        write_read();
    }


    public void update(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Update.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Login Successfully!");
        HelloApplication.stage.setScene(scene);
    }
    public void delete(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Delete.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Login Successfully!");
        HelloApplication.stage.setScene(scene);
    }

    public void information(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Login Successfully!");
        HelloApplication.stage.setScene(scene);
    }

    public void transferbtn(ActionEvent event) throws SQLException, IOException {
        int t=Integer.parseInt(txtamount.getText());

        String sql="select *from customer where id=?";
        PreparedStatement stm=con.prepareStatement(sql);
        stm.setString(1,txtsearch.getText());
        ResultSet rt=stm.executeQuery();
        while (rt.next()) {
            int x=rt.getInt("deposit")-t;
            String sql1="update customer set deposit ='"+x+"' where id=?";
            PreparedStatement st=con.prepareStatement(sql1);
            st.setString(1,rt.getString("id"));
            st.executeUpdate();
        }
        String sql2="select *from customer where id=?";
        PreparedStatement stm2=con.prepareStatement(sql2);
        stm2.setString(1,txtto.getText());
        ResultSet rt2=stm2.executeQuery();
        while (rt2.next()) {
            int x=rt2.getInt("deposit")+t;
            System.out.println(rt2.getInt("deposit")-Integer.parseInt(txtamount.getText()));
            String sql1="update customer set deposit ='"+x+"' where id=?";
            PreparedStatement st=con.prepareStatement(sql1);
            st.setString(1,txtto.getText());
            st.executeUpdate();
        }
        Date now = new Date(System.currentTimeMillis());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formattedDate = formatter.format(now);
        System.out.println("logout "+readLogedAccount());
        String idd=readLogedAccount();
        String id= idd.trim();
        System.out.println("iddd  "+id);
        System.out.println("e=id  "+id.equals("e"));
        String sqltr= "INSERT INTO transactionTwo (cust_id,date,event,amount) VALUES(?,?,?,?)";
        PreparedStatement preptr = con.prepareStatement(sqltr);
        preptr.setString(1, txtsearch.getText());
        preptr.setString(2, formattedDate);
        preptr.setString(3, "transfer");
        preptr.setString(4, txtamount.getText());
        preptr.executeUpdate();


        write_read();
    }
    @FXML
    public void withdrawal(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("withdrawal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Login Successfully!");
        HelloApplication.stage.setScene(scene);
    }
    public void addInfo(ActionEvent event) {
    }

    public void balance(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("balance.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Login Successfully!");
        HelloApplication.stage.setScene(scene);
    }

    public void mobilePackage(ActionEvent event) {
    }

    public void customer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CreateAccountForEmployeAndUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Login Successfully!");
        HelloApplication.stage.setScene(scene);
    }

    public void employee(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Empoyee.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Login Successfully!");
        HelloApplication.stage.setScene(scene);

    }

    public void calaculateBalance(ActionEvent event) {
    }
    public void logOut(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Login Successfully!");
        HelloApplication.stage.setScene(scene);
        Date now = new Date(System.currentTimeMillis());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formattedDate = formatter.format(now);
        System.out.println("logout "+readLogedAccount());
        String idd=readLogedAccount();
         String id= idd.trim();
        System.out.println("iddd  "+id);
        System.out.println("e=id  "+id.equals("e"));
        String sqltr= "INSERT INTO transaction (user_id,devent,tevent) VALUES(?,?,?)";
        PreparedStatement preptr = con.prepareStatement(sqltr);
        preptr.setString(1, id);
        preptr.setString(2, formattedDate);
        preptr.setString(3, "log out");
        preptr.executeUpdate();


        write_read();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernamecol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passcol.setCellValueFactory(new PropertyValueFactory<>("password"));
        rolecol.setCellValueFactory(new PropertyValueFactory<>("role"));
        ObservableList<String> list1 = FXCollections.observableArrayList( "employee", "customer");
        roleempcu.setItems(list1);
        roleempcu.getSelectionModel().select(0);
            ObservableList<String> list = FXCollections.observableArrayList("superadmin","admin", "employee", "customer");
            role.setItems(list);
            role.getSelectionModel().select(0);


    }
    @FXML
    void refresh(ActionEvent event) throws SQLException, IOException {
        String sql = "select *from FXsql join user on FXsql.account_id=user.id";
        PreparedStatement pt = con.prepareStatement(sql);
        ResultSet res = pt.executeQuery();
        BufferedWriter writer=new BufferedWriter(new FileWriter("output.txt"));
        writer.write(String.format("%-10s %-20s  %-20s %-20s %-10s\n", "Name","ID", "Username", "Password", "Role"));
        data.clear();
        while (res.next()) {
            data.add(new Account(res.getString("name"), res.getString("id"), res.getString("userName"), res.getString("password"), res.getString("role")));
            accountTable.setItems(data);
            StringBuilder tableData = new StringBuilder();
            tableData.append(String.format("%-10s %-20s %-20s %-20s %-10s\n",res.getString("name"), res.getString("id"), res.getString("userName"), res.getString("password"), res.getString("role")));
            writer.write(tableData.toString());

        }
        writer.close();
    }
    public  void write_read() throws SQLException, IOException {
        String sqlw = "SELECT user.name, user.id, FXsql.userName, FXsql.password, FXsql.role, " +
                "transaction.devent, transaction.tevent FROM user JOIN fxsql ON " +
                "user.id = fxsql.account_id JOIN transaction ON transaction.user_id = fxsql.account_id;";
        PreparedStatement pt = con.prepareStatement(sqlw);
        ResultSet res0 = pt.executeQuery();
        String sqlwo = "SELECT *FROM user JOIN fxsql ON user.id  = fxsql.account_id;";
        PreparedStatement pto = con.prepareStatement(sqlwo);
        ResultSet res1 = pto.executeQuery();

        BufferedWriter writer=new BufferedWriter(new FileWriter("output.txt"));
        BufferedWriter writetran=new BufferedWriter(new FileWriter("transaction.txt"));
        BufferedWriter writetranAll=new BufferedWriter(new FileWriter("transactionALL.txt"));

        writer.write(String.format("%-10s %-20s  %-20s %-20s %-10s\n", "Name","ID", "Username", "Password", "Role"));
        writetran.write(String.format("%-10s %-20s %-20s %-10s\n", "Name","date","tyepe transacion", "Role"));
        writetranAll.write(String.format("%-10s %-20s %-20s %-10s\n", "Name","date","tyepe transacion", "Role"));

        while (res1.next()) {
            StringBuilder tableData = new StringBuilder();
            tableData.append(String.format("%-10s %-20s %-20s %-20s %-10s\n",res1.getString("name"), res1.getString("id"), res1.getString("userName"), res1.getString("password"), res1.getString("role")));
            writer.write(tableData.toString());

        }
        while (res0.next()) {

            StringBuilder tableData1 = new StringBuilder();
            if (readLogedAccount().equals(res0.getString("id"))) {
                tableData1.append(String.format("%-10s %-20s %-20s %-10s\n", res0.getString("name"), res0.getString("devent"), res0.getString("tevent"), res0.getString("role")));
                writetran.write(tableData1.toString());
            }
        }
        String sqlwos = "SELECT user.name, user.id, FXsql.userName, FXsql.password, FXsql.role, " +
                "transaction.devent, transaction.tevent FROM user JOIN fxsql ON " +
                "user.id = fxsql.account_id JOIN transaction ON transaction.user_id = fxsql.account_id;";
        PreparedStatement ptos = con.prepareStatement(sqlwos);
        ResultSet r = ptos.executeQuery();
        while (r.next()) {

                StringBuilder tableData2 = new StringBuilder();
                tableData2.append(String.format("%-10s %-20s %-20s %-10s\n", r.getString("name"), r.getString("devent"), r.getString("tevent"), r.getString("role")));
                writetranAll.write(tableData2.toString());
                System.out.println(tableData2 + "output");

        }

        writer.close();
        writetran.close();
        writetranAll.close();
    }

    public void select(ActionEvent event) {
        rolee=role.getSelectionModel().getSelectedItem().toString();
        System.out.println(rolee);

    }
    public void selectad(ActionEvent event) {

        rolee=roleempcu.getSelectionModel().getSelectedItem().toString();
        System.out.println(rolee);
    }
    public  void readfile(ActionEvent event) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\code block\\LoginPage3\\LoginPage\\transaction.txt"));
            String line;
            System.out.println("read");
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                System.out.println("re2");
            }

            System.out.println(content);
            reader.close();
            label.setText(content.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void  transactionbtn(ActionEvent event) throws SQLException {
        String sql="select *from customer join transactionTwo on customer.id=transactionTwo.cust_id";
        PreparedStatement st=con.prepareStatement(sql);
        ResultSet rs=st.executeQuery();
        StringBuilder content=new StringBuilder();
        System.out.println("Event       amount           date \n");
        content.append("Name        Event       amount           date");
        while (rs.next()){
            content.append("\n"+rs.getString("name")+"         " +rs.getString("event")+"         " +rs.getString("amount")+"   " +rs.getString("date")).append("\n");

        }
        superlabel.setText(String.valueOf(content));
    }
    public  void logininfo(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginInformation.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Login Successfully!");
        HelloApplication.stage.setScene(scene);
    }
    public  void readfileSuper(ActionEvent event) throws IOException {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\code block\\LoginPage3\\LoginPage\\transactionALL.txt"));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            superlabel.setText(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readLogedAccount() {
        String line = "logout";
        StringBuilder content = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\code block\\LoginPage3\\LoginPage\\account.txt"));
            System.out.println("read");
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                System.out.println("re2");
            }
       line= String.valueOf(content);
            line=line.trim();
            System.out.println("lineeeeeeeeeeeeeeee    "+line);
            reader.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
    @FXML
    void BackToCustmor(ActionEvent event) throws IOException, SQLException {
        String sql = "select role from customer where id =?";
        String c = readLogedAccount();
        c=c.trim();
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, c);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("role").equals("customer"));
            if (rs.getString("role").equals("customer")) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Customer.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                HelloApplication.stage.setTitle("Login Successfully!");
                HelloApplication.stage.setScene(scene);
            }
        }

        String sql2 = "select role from admin where id =?";
        String c2 = readLogedAccount();
        c2=c2.trim();
        PreparedStatement st2 = con.prepareStatement(sql2);
        st2.setString(1, c2);
        ResultSet rs2 = st2.executeQuery();
        while (rs2.next()) {
            System.out.println("employee");

            if (rs2.getString("role").equals("employee")) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Empoyee.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                HelloApplication.stage.setTitle("Login Successfully!");
                HelloApplication.stage.setScene(scene);
            }
        }
    }



@FXML
void  transacton(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("transaction.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    HelloApplication.stage.setTitle("Login Successfully!");
    HelloApplication.stage.setScene(scene);
}


    @FXML
    void balanceSearch(ActionEvent event) throws SQLException {
    String sql="select *from customer where id=?";
    PreparedStatement stm=con.prepareStatement(sql);
    stm.setString(1,txtsearch.getText());
    ResultSet rt=stm.executeQuery();
    while (rt.next()) {
        txtname.setText(rt.getString("name"));
        txtbalance.setText(rt.getString("deposit"));
        txtaccnumber.setText(rt.getString("accountNumber"));
    }
    }

    @FXML
    void withdrawalbtn (ActionEvent event) throws SQLException, IOException {
        String sql="select *from customer where id=?";
        PreparedStatement stm=con.prepareStatement(sql);
        stm.setString(1,txtsearch.getText());
        ResultSet rt=stm.executeQuery();
        while (rt.next()) {
        int x=rt.getInt("deposit")-Integer.parseInt(txtamount.getText());
            String sql1="update customer set deposit ='"+x+"' where id=?";
            PreparedStatement st=con.prepareStatement(sql1);
            st.setString(1,rt.getString("id"));
           st.executeUpdate();
            System.out.println(rt.getInt("deposit")-Integer.parseInt(txtamount.getText()));
        }
        Date now = new Date(System.currentTimeMillis());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formattedDate = formatter.format(now);
        System.out.println("logout "+readLogedAccount());
        String idd=readLogedAccount();
        String id= idd.trim();
        System.out.println("iddd  "+id);
        System.out.println("e=id  "+id.equals("e"));
        String sqltr= "INSERT INTO transactionTwo (cust_id,date,event,amount) VALUES(?,?,?,?)";
        PreparedStatement preptr = con.prepareStatement(sqltr);
        preptr.setString(1, txtsearch.getText());
        preptr.setString(2, formattedDate);
        preptr.setString(3, "withdrawal");
        preptr.setString(4, txtamount.getText());
        preptr.executeUpdate();


        write_read();
        }

    @FXML
    void depositbtn (ActionEvent event) throws SQLException, IOException {
        String sql="select *from customer where id=?";
        PreparedStatement stm=con.prepareStatement(sql);
        stm.setString(1,txtsearch.getText());
        ResultSet rt=stm.executeQuery();
        while (rt.next()) {
            int x=rt.getInt("deposit")+Integer.parseInt(txtamount.getText());
            String sql1="update customer set deposit ='"+x+"' where id=?";
            PreparedStatement st=con.prepareStatement(sql1);
            st.setString(1,rt.getString("id"));
            st.executeUpdate();
            System.out.println(rt.getInt("deposit")-Integer.parseInt(txtamount.getText()));
        }
        Date now = new Date(System.currentTimeMillis());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formattedDate = formatter.format(now);
        System.out.println("logout "+readLogedAccount());
        String idd=readLogedAccount();
        String id= idd.trim();
        System.out.println("iddd  "+id);
        System.out.println("e=id  "+id.equals("e"));
        String sqltr= "INSERT INTO transactionTwo (cust_id,date,event,amount) VALUES(?,?,?,?)";
        PreparedStatement preptr = con.prepareStatement(sqltr);
        preptr.setString(1, txtsearch.getText());
        preptr.setString(2, formattedDate);
        preptr.setString(3, "deposit");
        preptr.setString(4, txtamount.getText());
        preptr.executeUpdate();


        write_read();
    }

@FXML
    public void deposit(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Deposit.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    HelloApplication.stage.setTitle("Login Successfully!");
    HelloApplication.stage.setScene(scene);
    }
    @FXML
    public void airtime(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Package.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("Login Successfully!");
        HelloApplication.stage.setScene(scene);
    }
}


