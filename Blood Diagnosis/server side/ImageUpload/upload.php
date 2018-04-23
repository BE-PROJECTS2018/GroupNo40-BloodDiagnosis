<?php
 
// Path to move uploaded files
$target_path = dirname(__FILE__).'/uploads/';

if (isset($_FILES['image']['name'])) {

    $target_path = $target_path . basename($_FILES['image']['name']);
	//echo $target_path
    try {
        // Throws exception incase file is not being moved
        if (!move_uploaded_file($_FILES['image']['tmp_name'], $target_path)) {
            // make error flag true
            echo json_encode(array('status'=>'fail', 'message'=>'could not move file'));
        }
 
        // File successfully uploaded
        //echo json_encode(array('status'=>'success', 'message'=>'File Uploaded'));
		
		ini_set('max_execution_time', 300); 
		$python = "C:\\Users\\krishna\\AppData\\Local\\Programs\\Python\\Python35\\python.exe";
		$file = "C:\\xampp\\htdocs\\ImageUpload\\python\\test.py";
		$output=exec($python . " " . $file);
		echo json_encode(array('status'=>'success', 'message'=>$output));
		
    } catch (Exception $e) {
        // Exception occurred. Make error flag true
        echo json_encode(array('status'=>'fail', 'message'=>$e->getMessage()));
    }
} else {
    // File parameter is missing
    echo json_encode(array('status'=>'fail', 'message'=>'Not received any file'));
}

?>