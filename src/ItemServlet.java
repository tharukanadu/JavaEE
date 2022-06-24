

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            String allRecords ="";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kade", "root", "1234");
            ResultSet rst = connection.prepareStatement("select * from item").executeQuery();
            while (rst.next()){
                String id = rst.getString(1);
                String name = rst.getString(2);
                int qty = rst.getInt(3);
                double price = rst.getDouble(4);
                System.out.println(id+" "+name+" "+qty+" "+price);

                String item = "{\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"qty\":\"" + qty + "\",\"price\":" + price + "},";
                allRecords = allRecords + item;

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
        String itemCode = req.getParameter("itemCode");
        String itemName = req.getParameter("itemName");
        String itemQty = req.getParameter("itemQty");
        String itemPrice = req.getParameter("itemPrice");
        System.out.println(itemCode+" "+itemName+ " "+itemQty+" "+itemPrice);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/kade", "root","1234");

            PreparedStatement pstm = connection.prepareStatement("Insert into item values(?,?,?,?) ");
            pstm.setObject(1,itemCode);
            pstm.setObject(2,itemName);
            pstm.setObject(3,itemQty);
            pstm.setObject(4,itemPrice);

            boolean b= pstm.executeUpdate()>0;
            PrintWriter writer=resp.getWriter();

            if (b){
                writer.write("Item Added");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Request received for delete");
        String itemCode = req.getParameter("itemCode");
        System.out.println(itemCode);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/kade", "root","1234");

            PreparedStatement pstm = connection.prepareStatement("Delete from item where id=?");
            pstm.setObject(1,itemCode);


            boolean b= pstm.executeUpdate()>0;
            PrintWriter writer=resp.getWriter();

            if (b){
                writer.write("Item Deleted");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        String itemName = req.getParameter("itemName");
        String itemQty = req.getParameter("itemQty");
        String itemPrice = req.getParameter("itemPrice");
        System.out.println(itemCode+" "+itemName+ " "+itemQty+" "+itemPrice);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/kade", "root","1234");

            PreparedStatement pstm = connection.prepareStatement("update item set name=?,qty=?,price=? where id=?");
            pstm.setObject(4,itemCode);
            pstm.setObject(1,itemName);
            pstm.setObject(2,itemQty);
            pstm.setObject(3,itemPrice);



            boolean b= pstm.executeUpdate()>0;
            PrintWriter writer=resp.getWriter();

            if (b){
                writer.write("Item Updated");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resp.sendError(500, e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.sendError(500, throwables.getMessage());
        }

    }


}
