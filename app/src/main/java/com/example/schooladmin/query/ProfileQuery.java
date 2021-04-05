package com.example.schooladmin.query;import android.content.Context;import android.content.Intent;import android.os.AsyncTask;import com.example.schooladmin.connectdb.ConnectDB;import com.example.schooladmin.model.Admin;import java.sql.Connection;import java.sql.ResultSet;import java.sql.ResultSetMetaData;import java.sql.Statement;public class ProfileQuery {    private Context context;    private Admin admin = new Admin();    public ProfileQuery(Context context) {        this.context = context;    }    public void setAdminInfo(Admin admin) {        this.admin = admin;    }    public Admin getAdminInfo() {        return admin;    }    public void callTaskGetInfoProfileByID(int adminID) {        new TaskGetInfoProfileByID( adminID ).execute( "" );    }    //Task for get profile Info By id    private class TaskGetInfoProfileByID extends AsyncTask<String, String, Admin> {        String state = "";        boolean isSuccess = false;        int adminID;        public TaskGetInfoProfileByID(int adminID) {            this.adminID = adminID;        }        @Override        protected Admin doInBackground(String... strings) {            Admin admin = new Admin();            try {                ConnectDB connectDB = new ConnectDB();                Connection connection = connectDB.CONN();                if (connection == null) {                } else {                    String query = "SELECT admin.id , admin.name , admin.password \n" +                            "FROM admin \n" +                            "WHERE id='" + adminID + "'";                    Statement stmt = connection.createStatement();                    ResultSet resultSet = stmt.executeQuery( query );                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();                    while (resultSet.next()) {                        admin.setId( resultSet.getInt( 1 ) );                        admin.setName( resultSet.getString( 2 ) );                        admin.setPassword( resultSet.getString( 3 ) );                    }                    isSuccess = true;                }            } catch (Exception ex) {                isSuccess = false;                state = "Exceptions" + ex;            }            return admin;        }        @Override        protected void onPostExecute(Admin admin) {            Intent intent = new Intent( "adminInfo" );            context.sendBroadcast( intent );            if (isSuccess) {                setAdminInfo( admin );            }        }    }}