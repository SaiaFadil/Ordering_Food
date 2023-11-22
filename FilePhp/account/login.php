<?php
require('../connection/connect.php'); // Ensure the connection to the database

header("Content-Type: application/json");

// Check if the request method is POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Sanitize and retrieve user input
    $email = mysqli_real_escape_string($konek, $_POST['email']);
    $password = $_POST['password'];

    // Prepare and execute the SQL query
    $sql = "SELECT * FROM users WHERE email = '$email' LIMIT 1";
    $result = $konek->query($sql);
$response = null;
    // Check if the query was executed successfully
    if ($result) {
        // Check if user with given email exists
        if ($result->num_rows == 1) {
            $user = $result->fetch_assoc();
            $hashedPasswordFromDatabase = $user['password'];

            // Verify password
            if (password_verify($password, $hashedPasswordFromDatabase)) {
                // Fetch user role
                $role_db = $user['role'];

                // Prepare response based on user role
                if ($role_db == 'admin') {
                    $response = array("status" => "admin", "message" => "login admin success", "data" => $user);
                } else if ($role_db == 'user') {
                    $response = array("status" => "user", "message" => "login user success", "data" => $user);
                }
            } else {
                // Invalid password
                $response = array("status" => "failed", "message" => "Username or password is wrong");
            }
        } else {
            // No user found with the provided email
            $response = array("status" => "failed", "message" => "Email or password is wrong");
        }
    } else {
        // Query execution failed
        $response = array("status" => "failed", "message" => "Query execution error: " . mysqli_error($konek));
    }
} else {
    // Invalid request method
    $response = array("status" => "failed", "message" => "Your method is not POST");
}

// Output JSON response
echo json_encode($response);

// Close the database connection
mysqli_close($konek);
?>
