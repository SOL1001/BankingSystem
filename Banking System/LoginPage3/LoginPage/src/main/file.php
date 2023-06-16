

<!DOCTYPE html>
<html>
<head>
    <title>File Upload</title>
</head>
<body style="background-color: powderblue; text-align:center;" >
<?php

// Create a new file
$file = fopen("example.txt", "w");
fwrite($file, "HI this is solomon <br>");
fclose($file);


// Open the file again for reading
$file = fopen("example.txt", "r");
$contents = fread($file, filesize("example.txt"));
echo $contents;
fclose($file);

// Open the file again for appending
$file = fopen("example.txt", "a");
fwrite($file, "  This is some additional text.");
fclose($file);
echo readfile("example.txt");
// // Open the file again for reading
// $file = fopen("example.txt", "r");
// $contents = fread($file, filesize("example.txt"));
// echo $contents;
// fclose($file);
?>
    <h2>Upload a Text File</h2>
    <form action="" method="POST" enctype="multipart/form-data">
        <input type="file" name="fil" required  >
        <input type="submit" value="Upload"  >
    </form>
    <?php

// Check if a file was uploaded
if (isset($_FILES['fil']['name'])) {
    $file_name = $_FILES['fil']['name'];
    $file_tmp = $_FILES['fil']['tmp_name'];
    $file_size = $_FILES['fil']['size'];
    $file_type = $_FILES['fil']['type'];

    // Define the directory to save the uploaded file
    $upload_dir = 'uploads/';

    // Check if the upload directory exists, if not, create it
    if (!is_dir($upload_dir)) {
        mkdir($upload_dir, 0755, true);
    }

    // Move the uploaded file to the upload directory
    $destination = $upload_dir . $file_name;
    move_uploaded_file($file_tmp, $destination);

    // Display a success message
    echo "File uploaded successfully: $file_name <br>";
}
?>
</body>
</html>
