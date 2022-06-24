
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            String allRecords ="";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kade", "root", "1234");
            ResultSet rst = connection.prepareStatement("select * from customer").executeQuery();
            while (rst.next()){
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                double salary = rst.getDouble(4);
                System.out.println(id+" "+name+" "+address+" "+salary);

                String customer = "{\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"address\":\"" + address + "\",\"salary\":" + salary + "},";
                allRecords = allRecords + customer;

            }
            String finalJson = "[" + allRecords.substring(0,allRecords.length()-1) + "]";
            PrintWriter writer = resp.getWriter();
            writer.write(finalJson);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusId = req.getParameter("cusId");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");
        System.out.println(cusId+" "+cusName+ " "+cusAddress+" "+cusSalary);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/kade", "root","1234");

            PreparedStatement pstm = connection.prepareStatement("Insert into customer values(?,?,?,?) ");
            pstm.setObject(1,cusId);
            pstm.setObject(2,cusName);
            pstm.setObject(3,cusAddress);
            pstm.setObject(4,cusSalary);

            boolean b= pstm.executeUpdate()>0;
            PrintWriter writer=resp.getWriter();

            if (b){
                writer.write("Customer Added");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Request received for delete");
        String cusId = req.getParameter("cusId");
        System.out.println(cusId);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/kade", "root","1234");

            PreparedStatement pstm = connection.prepareStatement("Delete from customer where id=?");
            pstm.setObject(1,cusId);


            boolean b= pstm.executeUpdate()>0;
            PrintWriter writer=resp.getWriter();

            if (b){
                writer.write("Customer Deleted");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusId = req.getParameter("cusId");
        String cusName = req.getParameter("cusName");
        String cusAddress = req.getParameter("cusAddress");
        String cusSalary = req.getParameter("cusSalary");
        System.out.println(cusId+" "+cusName+ " "+cusAddress+" "+cusSalary);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/kade", "root","1234");

            PreparedStatement pstm = connection.prepareStatement("update customer set name=?,address=?,salary=? where id=?");
            pstm.setObject(4,cusId);
            pstm.setObject(1,cusName);
            pstm.setObject(2,cusAddress);
            pstm.setObject(3,cusSalary);



            boolean b= pstm.executeUpdate()>0;
            PrintWriter writer=resp.getWriter();

            if (b){
                writer.write("Customer Updated");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.sendError(500, throwables.getMessage());
        }

       /* try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kade", "root", "1234");

            PreparedStatement pstm = connection.prepareStatement("Update kade set name=?,address=?,salary=? where id=?");
            pstm.setObject(1, cusName);
            pstm.setObject(2, cusAddress);
            pstm.setObject(3, cusSalary);
            pstm.setObject(4, cusId);
            boolean b = pstm.executeUpdate() > 0;
            PrintWriter writer = resp.getWriter();

            if (b) {
                writer.write("Customer Updated");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.sendError(500, throwables.getMessage());
        }

*/
    }

}
