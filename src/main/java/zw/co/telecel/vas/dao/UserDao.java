package zw.co.telecel.vas.dao;

import zw.co.telecel.vas.model.ServiceCommandRequest;
import zw.co.telecel.vas.model.User;
import zw.co.telecel.vas.model.UserAction;
import zw.co.telecel.vas.util.legacy.DatabaseException;
import zw.co.telecel.vas.util.legacy.UserNotFoundException;

import java.io.InputStream;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * david@ebridgevas.com
 *
 */
public class UserDao {

    private static Connection connection;

    static {
        try {
            connection = DataBaseConnectionPool.getConnection();
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    /**
     * findUserByMobileNumber
     *
     * @param user
     * @return
     * @throws UserNotFoundException
     * @throws DatabaseException
     */
    public static Boolean persist(User user) throws DatabaseException {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        try {
            findUser( user.getMobileNumber() );
            throw new DatabaseException(" mobile number : " + user.getMobileNumber() + " already registered.");
        } catch (UserNotFoundException e) {
        }

        String sql =
                " INSERT INTO usr ( email_address, mobile_number, password, first_name, surname, role, " +
                        "                   notification_agent, status, narrative, activation_code, " +
                        "                   security_question, security_answer ) " +
                        "   VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = connection.prepareStatement( sql );
            ps.setString(1, user.getEmailAddress());
            ps.setString(2, user.getMobileNumber() );
            ps.setString(3, user.getPassword() );
            ps.setString(4, user.getFirstName() );
            ps.setString(5, user.getSurname() );
            ps.setString(6, user.getRole() );
            ps.setString(7, user.getNotificationAgent() );
            ps.setString(8, user.getStatus());
            ps.setString(9, user.getNarrative());
            ps.setString(10, user.getActivationCode());
            ps.setString(11, user.getSecurityQuestion());
            ps.setString(12, user.getSecurityAnswer());
//            ps.setString(13, user.getUsrPhotoFileType());
//            ps.setInt(   14, user.getUsrPhotoFileLength() );
//            ps.setBinaryStream(15, user.getUserPhotoStream() );

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException( e.getMessage() );
        } finally {
            try {rs.close();} catch (Exception e){}
            try {ps.close();} catch (Exception e){}
        }
    }

    /**
     * findUserByMobileNumber
     *
     * @param user
     * @return
     * @throws UserNotFoundException
     * @throws DatabaseException
     */
    public static Boolean update(User user) throws DatabaseException {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        try {
            findUser( user.getMobileNumber() );
        } catch (UserNotFoundException e) {
            throw new DatabaseException(" mobile number : " + user.getMobileNumber() + " does not exists.");
        }

        String sql =
                " UPDATE usr " +
                        " SET email_address = '" + user.getEmailAddress() + "'," +
                        "        first_name = '" + user.getFirstName() + "'," +
                        "           surname = '" + user.getSurname() + "'," +
                        "              role = '" + user.getRole() + "', " +
                        "notification_agent = '" + user.getNotificationAgent() + "', " +
                        " security_question = '" + user.getSecurityQuestion() + "', " +
                        "   security_answer = '" + user.getSecurityAnswer() + "'" +
                        (user.getPassword().equals("*") ? "" : ", password = '" + user.getPassword() + "'") +
                        " WHERE mobile_number = '" + user.getMobileNumber() + "'";

        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = connection.createStatement();
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new DatabaseException( e.getMessage() );
        } finally {
            try {rs.close();} catch (Exception e){}
            try {stmt.close();} catch (Exception e){}
        }
    }

    public static Boolean updateUserPhoto( String mobileNumber,
                                           String userPhotoFilename) throws DatabaseException {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        try {
            findUser( mobileNumber );
        } catch (UserNotFoundException e) {
            throw new DatabaseException(" mobile number : " + mobileNumber + " does not exists.");
        }

        String sql =
                " UPDATE usr " +
                        " SET user_photo_file_name = ? " +
                        " WHERE     mobile_number = ? ";

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement( sql );
            ps.setString(1, userPhotoFilename );
            ps.setString(2, mobileNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException( e.getMessage() );
        } finally {
            try {ps.close();} catch (Exception e){}
        }
    }

    public static UserAction updateWrongPasswordCounter( User user, String mobileNumber) throws DatabaseException {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

            PreparedStatement ps = null;

            String sql = null;

            try {

//                Date date = new Date(new SimpleDateFormat("yyyy-MM-dd").fonew java.util.Date().getTime());
//                System.out.println("today : " + date + ", wrong password date : " + user.getWrongPasswordCounterDate());
//                System.out.println("today : " +  date.getTime() + ", wrong password date : " + user.getWrongPasswordCounterDate().getTime());
//                if (user.getWrongPasswordCounterDate().getTime() == date.getTime()) {
//                    System.out.println("############# wrong counter today ");
                Integer count = user.getWrongPasswordCounter();
                if (count == null) count = 0;

                if ( count > 4) {

                    sql = " UPDATE usr " +
                            " SET status = ? " +
                            " WHERE     mobile_number = ? ";

                    System.out.println(sql + " mobileNumber  " + mobileNumber);
                    ps = connection.prepareStatement( sql );
                    ps.setString(1, "locked");
                    ps.setString(2, mobileNumber);
                    ps.executeUpdate();
                    return UserAction.ACCOUNT_LOCKED;
                } else {

                    System.out.println("############# counter : " + (count + 1) );
                    sql = " UPDATE usr " +
                            " SET wrong_password_counter = ?, " +
                            "    wrong_password_counter_date  = ? " +
                            " WHERE     mobile_number = ? ";
                    ps = connection.prepareStatement( sql );
                    ps.setInt(1, ( count + 1) );
                    ps.setDate(2, new Date( new java.util.Date().getTime()));
                    ps.setString(3, mobileNumber);

                    System.out.println(sql + ", counter : " + (count + 1)
                            + " wrong_password_counter_date : " +
                            new Date( new java.util.Date().getTime()) + " mobileNumber  " + mobileNumber);

                    ps.executeUpdate();
                    return UserAction.INVALID_PASSWORD;
                }
//                } else {

//                    System.out.println("############# wrong counter not today ");
//
//                    sql = " UPDATE usr " +
//                            " SET wrong_password_counter = ?, " +
//                            "    wrong_password_counter_date  = ? " +
//                            " WHERE     mobile_number = ? ";
//                    ps = connection.prepareStatement( sql );
//                    ps.setInt(1, 1 );
//                    ps.setDate(2, new Date( new java.util.Date().getTime()));
//                    ps.setString(3, mobileNumber);
//
//                    System.out.println(sql + ", wrong_password_counter_date : " +
//                            new Date(new java.util.Date().getTime()) + " mobileNumber  " + mobileNumber);
//
//                    ps.executeUpdate();
//                    return UserAction.INVALID_PASSWORD;
//                }
           } catch (SQLException e) {
                e.printStackTrace();
                throw new DatabaseException( e.getMessage() );
            } finally {
                try {ps.close();} catch (Exception e){}
            }
    }


    /**
     * findUserByMobileNumber
     */
    public static User findUser(String mobileNumber) throws UserNotFoundException, DatabaseException {

        try {
            if ( (connection == null) || (connection.isClosed())) {
                try {
                    connection = DataBaseConnectionPool.getConnection();
                } catch (Exception e) {
                    // TODO handle exception
                    e.printStackTrace();
                }
            }
        } catch(SQLException e ) {
            e.printStackTrace();
            return null;
        }

        String sql = " SELECT email_address, mobile_number, password, first_name, surname, role, " +
                "          notification_agent, status, narrative, activation_code, " +
                "          security_question, security_answer, user_photo_file_name," +
                "          wrong_password_counter, wrong_password_counter_date "+
                "   FROM usr " +
                "  WHERE mobile_number = '" + mobileNumber + "'";
        System.out.println( sql );
        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            if ( rs.next() ) {
                User user =
                        new User(
                                rs.getString("email_address"),
                                rs.getString("mobile_number"),
                                rs.getString("password"),
                                rs.getString("first_name"),
                                rs.getString("surname"),
                                rs.getString("role"),
                                rs.getString("notification_agent"),
                                rs.getString("status"),
                                rs.getString("narrative"),
                                rs.getString("activation_code"),
                                rs.getString("security_question"),
                                rs.getString("security_answer"));


                user.setUserPhotoFileName(
                        rs.getString("user_photo_file_name") != null ?
                                rs.getString("user_photo_file_name") : "default-photo.jpg");

                user.setWrongPasswordCounter(rs.getInt("wrong_password_counter"));
                user.setWrongPasswordCounterDate(rs.getDate("wrong_password_counter_date"));
                return user;
            } else {
                throw new UserNotFoundException("Mobile number " + mobileNumber + " is not registered.");
            }
        } catch (SQLException e) {
            throw new DatabaseException( e.getMessage() );
        } finally {
            try {rs.close();} catch (Exception e){}
            try {stmt.close();} catch (Exception e){}
        }
    }

    public static List<User> findAll(Integer start, Integer pageSize )
            throws UserNotFoundException, DatabaseException {

        return findAll(start, pageSize, Boolean.FALSE);
    }

    /**
     * findAll
     *
     * @return
     * @throws UserNotFoundException
     * @throws DatabaseException
     */
    public static List<User> findAll(Integer start, Integer pageSize, Boolean chronologicalOrder )
            throws UserNotFoundException, DatabaseException {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql = " SELECT email_address, mobile_number, password, first_name, surname, role, " +
                "          notification_agent, status, narrative, activation_code, security_question,  " +
                "          security_answer " +
                "   FROM usr " +

                "  ORDER BY " + ( chronologicalOrder ? " id DESC " : " surname, first_name " ) +
                "  LIMIT " + pageSize + " OFFSET " + start;
        System.out.println("#### " + sql);
        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            List<User> users = new ArrayList<User>();
            while ( rs.next() ) {
                users.add(
                        new User(
                                rs.getString("email_address"),
                                rs.getString("mobile_number"),
                                rs.getString("password"),
                                rs.getString("first_name"),
                                rs.getString("surname"),
                                rs.getString("role"),
                                rs.getString("notification_agent"),
                                rs.getString("status"),
                                rs.getString("narrative"),
                                rs.getString("activation_code"),
                                rs.getString("security_question"),
                                rs.getString("security_answer")));
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseException( e.getMessage() );
        } finally {
            try {rs.close();} catch (Exception e){}
            try {stmt.close();} catch (Exception e){}
        }
    }

    /**
     * authenticateUserByMobileNumber
     */
    public static UserAction authenticateUser(String userId, String password) {

        User user = null;
        try {

            user = findUser( userId );

            return user.getPassword().equals( password)  ?
                    UserAction.GRANT_ACCESS :
                    UserAction.SET_PASSWORD_RETRY;

        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return UserAction.REGISTER_USER;
        } catch (DatabaseException e) {
            return UserAction.RETRY;
        }
    }

//    /**
//     * toUserAuthenticationResponse
//     *
//     * @param userId
//     * @param idType
//     * @param e
//     * @return
//     */
//    protected static UserAuthenticationResponse
//                toUserAuthenticationResponse(String userId, IdType idType, Exception e) {
//        return new UserAuthenticationResponse(userId, idType, Boolean.FALSE, e.getMessage(), null);
//    }

    public static Boolean update ( ServiceCommandRequest request ) throws DatabaseException {

        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                throw new DatabaseException( e.getMessage() );
            }
        }

        String sql = "";
        switch(request.getServiceCommand()) {

            case GENERATE_ACTIVATION_CODE:
                sql = " UPDATE usr SET password = '*', " +
                        "                status = 'enterActivationCode', " +
                        "                activation_code = '" + request.getPayload() + "'";
                break;

            case ACTIVATE_USER:
                sql = " UPDATE usr SET status = 'active'";
                break;
            case RESET_USER_PASSWORD:
                sql = " UPDATE usr SET status = 'forcePasswordChange'";
                break;
            case CHANGE_USER_PASSWORD:
                sql = " UPDATE usr SET password = '" + request.getPayload().trim() + "'," +
                        " status = 'active', activation_code = '' ";
                break;
            case DEACTIVATE_USER:
                sql = " UPDATE usr SET status = 'inactive'";
                break;

            case DELETE_USER:
                sql = " DELETE FROM usr ";
                break;
        }
        sql += "  WHERE mobile_number = '" + request.getUserId() + "'";

        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = connection.createStatement();
            return stmt.executeUpdate(sql) > 0;
        } catch (SQLException e) {
            throw new DatabaseException( e.getMessage() );
        } finally {
            try {rs.close();} catch (Exception e){}
            try {stmt.close();} catch (Exception e){}
        }
    }
}
