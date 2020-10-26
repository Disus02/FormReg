package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class Controller {
    Connection conn;

    public Controller() throws ClassNotFoundException {
        conn = ControllerUtil.conDB();
    }

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private TextField txtAge;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label status;
    String name;
    String pass;

    @FXML
    public void pressInsert(ActionEvent event) throws SQLException {
        String sql = "INSERT INTO user(firstName, lastname, age, email, password)VALUES(?, ?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, txtFirstname.getText());
        statement.setString(2, txtLastname.getText());
        statement.setInt(3, Integer.parseInt(txtAge.getText()));
        statement.setString(4, txtEmail.getText());
        statement.setInt(5, Integer.parseInt(txtPassword.getText()));
        int result = statement.executeUpdate();


        if (result == 1) {
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                status.setText(txtFirstname.getText() + " " + txtLastname.getText() + " зарегистрирован id = " + id);
            }

        }

    }

    @FXML
    public void pressUpdate(ActionEvent event) throws SQLException {
        String sql = "UPDATE user SET firstName=?, lastname=?, age=?, email=?, password=? WHERE id_user=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, txtFirstname.getText());
        statement.setString(2, txtLastname.getText());
        statement.setInt(3, Integer.parseInt(txtAge.getText()));
        statement.setString(4, txtEmail.getText());
        statement.setInt(5, Integer.parseInt(txtPassword.getText()));
        statement.setInt(6, Integer.parseInt(txtId.getText()));
        int res = statement.executeUpdate();
        if (res == 1) {
            status.setText("Данные Обновлены");
        }
    }

    @FXML
    public void pressDelete(ActionEvent event) throws SQLException {
        String sql = "DELETE FROM user WHERE id_user=?";
        PreparedStatement statement1 = conn.prepareStatement(sql);
        statement1.setInt(1, Integer.parseInt(txtId.getText()));
        int res = statement1.executeUpdate();
        if (res == 1) {
            status.setText("Данные удалены");
        }
    }

    @FXML
    public void pressCheks(ActionEvent event) throws SQLException {
        int id = Integer.parseInt(txtId.getText());
        String sql = "SELECT * FROM user WHERE id_user= ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
            if (id == result.getInt(1)) {
                txtFirstname.setText(result.getString("firstName"));
                txtLastname.setText(result.getString("lastname"));
                txtAge.setText(result.getString("age"));
                txtEmail.setText(result.getString("email"));
                txtPassword.setText(result.getString("password"));
            }
        }
    }

    @FXML
    public void pressClear(ActionEvent event) {
        txtFirstname.setText("");
        txtLastname.setText("");
        txtEmail.setText("");
        txtAge.setText("");
        txtPassword.setText("");
        txtId.setText("");
    }

    @FXML
    public void pressOpen(ActionEvent event) throws SQLException {
        String sql = "SELECT * FROM user WHERE email=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1,txtEmail.getText());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            pass = result.getString("password");
            name=result.getString("email");
        }
        if (pass.equals(txtPassword.getText())&& name.equals(txtEmail.getText())){
        status.setText("Вы вошли");
        }
        if (!pass.equals(txtPassword.getText())){
            status.setText("Неверный пароль");
        }
        if (!name.equals((txtEmail.getText()))){
            status.setText("неверный логин");
        }
        if (!pass.equals(txtPassword.getText())&& !name.equals(txtEmail.getText())){
            status.setText("зарегистрируйтесь");
        }
    }
}
