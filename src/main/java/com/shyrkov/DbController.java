package com.shyrkov;

import com.shyrkov.model.Pipeline;
import com.shyrkov.model.PointSet;
import com.shyrkov.model.ResultModel;

import java.sql.*;
import java.util.List;

public class DbController {

    private static final String DB_URL = "jdbc:h2:/c:/Users/windo/pipeline-system/db/pipelineSystem";
    private static final String DB_Driver = "org.h2.Driver";
    private Connection connection = null;
    private Statement statement = null;

    public DbController() {
        try {
            Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД
            connection = DriverManager.getConnection(DB_URL);//соединениесБД
            System.out.println("Соединение с СУБД выполнено.");
            statement = connection.createStatement();

        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // обработка ошибки  Class.forName
            System.out.println("JDBC драйвер для СУБД не найден!");
        } catch (
                SQLException e) {
            e.printStackTrace(); // обработка ошибок  DriverManager.getConnection
            System.out.println("Ошибка SQL !");
        }
    }

    public void loadPipelineSystem(String path, List<Pipeline> pipelines) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS pipelines(ID INT PRIMARY KEY AUTO_INCREMENT, xId INT, yId INT, length INT)";
            statement.execute(query);
            for (Pipeline pipeline : pipelines) {
                query = "INSERT INTO pipelines(xId, yId, length) VALUES(" + pipeline.getStartPointId() + "," + pipeline
                        .getEndPointId() + "," + pipeline.getLength() + ")";
                statement.execute(query);
            }
            String sql = "SELECT ID, xId, yId, length FROM pipelines";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.print("id " + rs.getInt("ID"));
                System.out.print("xId " + rs.getInt("xId"));
                System.out.print("yId " + rs.getInt("yId"));
                System.out.println("length " + rs.getInt("length"));
            }

            System.out.println("Pipeline system uploaded");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadPointSet(String path, List<PointSet> points) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS point_set(ID INT PRIMARY KEY AUTO_INCREMENT, pointA INT, pointB INT)";
            statement.execute(query);
            for (PointSet pointSet : points) {
                query = "INSERT INTO point_set(pointA, pointB) VALUES(" + pointSet.getPointA() + "," + pointSet
                        .getPointB() + ")";
                statement.execute(query);
            }
            String sql = "SELECT ID, pointA, pointB FROM point_set";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.print("id " + rs.getInt("ID"));
                System.out.print("pointA " + rs.getInt("pointA"));
                System.out.println("pointB " + rs.getInt("pointB"));
            }

            System.out.println("Point set uploaded");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
