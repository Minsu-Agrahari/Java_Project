
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class Bank_Management {

    private static final int NULL = 0;
    private static final Connection con = DBConnection.getConnection();

    // Create a new bank account
    public static boolean createAccount(String name, int passCode) {
        if (name.isEmpty() || passCode == NULL) {
            System.out.println("-- All fields are required!");
            return false;
        }

        try {
            String sql = "INSERT INTO customer (cname, balance, pass_code) VALUES (?, 100000, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, passCode);

            int rows = ps.executeUpdate();
            if (rows == 1) {
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username already exists! Try another one.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Login to existing account
    public static boolean loginAccount(String name, int passCode) {
        if (name.isEmpty() || passCode == NULL) {
            System.out.println("-- All fields are required!");
            return false;
        }

        try {
            String sql = "SELECT * FROM customer WHERE cname = ? AND pass_code = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, passCode);
            ResultSet rs = ps.executeQuery();

            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

            if (rs.next()) {
                int senderAc = rs.getInt("ac_no");
                int choice;

                while (true) {
                    System.out.println("\nHello, " + rs.getString("cname") + "! What would you like to do?");
                    System.out.println("1) Transfer Money");
                    System.out.println("2) View Balance");
                    System.out.println("3) Logout");
                    System.out.print("Enter Choice: ");

                    choice = Integer.parseInt(sc.readLine());

                    if (choice == 1) {
                        System.out.print("Enter Receiver A/c No: ");
                        int receiverAc = Integer.parseInt(sc.readLine());
                        System.out.print("Enter Amount: ");
                        int amt = Integer.parseInt(sc.readLine());

                        if (transferMoney(senderAc, receiverAc, amt)) {
                            System.out.println("Transaction successful!");
                        } else {
                            System.out.println("Transaction failed! Please try again.");
                        }

                    } else if (choice == 2) {
                        getBalance(senderAc);
                    } else if (choice == 3) {
                        System.out.println("Logged out successfully. Returning to main menu.");
                        break;
                    } else {
                        System.out.println("Invalid choice! Try again.");
                    }
                }
                return true;
            } else {
                System.out.println("Invalid username or password!");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // View account balance
    public static void getBalance(int acNo) {
        try {
            String sql = "SELECT * FROM customer WHERE ac_no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, acNo);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n------------------------------------------------------");
            System.out.printf("%12s %15s %10s\n", "Account No", "Customer Name", "Balance");

            while (rs.next()) {
                System.out.printf("%12d %15s %10d.00\n",
                        rs.getInt("ac_no"),
                        rs.getString("cname"),
                        rs.getInt("balance"));
            }
            System.out.println("------------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Transfer money from one account to another
    public static boolean transferMoney(int sender_ac, int receiver_ac, int amount) {
        if (receiver_ac == NULL || amount <= 0) {
            System.out.println("All fields are required and amount must be positive.");
            return false;
        }

        try {
            con.setAutoCommit(false);

            // Check sender balance
            String checkBalance = "SELECT balance FROM customer WHERE ac_no = ?";
            PreparedStatement ps = con.prepareStatement(checkBalance);
            ps.setInt(1, sender_ac);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt("balance") < amount) {
                System.out.println("Insufficient Balance!");
                return false;
            }

            // Debit sender
            String debit = "UPDATE customer SET balance = balance - ? WHERE ac_no = ?";
            PreparedStatement psDebit = con.prepareStatement(debit);
            psDebit.setInt(1, amount);
            psDebit.setInt(2, sender_ac);
            psDebit.executeUpdate();

            // Credit receiver
            String credit = "UPDATE customer SET balance = balance + ? WHERE ac_no = ?";
            PreparedStatement psCredit = con.prepareStatement(credit);
            psCredit.setInt(1, amount);
            psCredit.setInt(2, receiver_ac);
            psCredit.executeUpdate();

            con.commit();
            return true;

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
