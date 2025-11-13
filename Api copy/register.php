<?php

$con=mysqli_connect("localhost","root","","furniture");

if(isset($_POST['user_name']) &&
isset($_POST['user_password']) &&
isset($_POST['user_email']) &&
isset($_POST['user_phone']) 
){

    $username=$_POST['user_name'];
    $password=md5($_POST['user_password']);
    $email=$_POST['user_email'];
    $phone=$_POST['user_phone'];
    

    $query="INSERT INTO tbl_user (user_id,user_name,user_password,user_email,user_phone) 
    VALUES (NULL, '$username', '$password', '$email', '$phone')";

    $res=mysqli_query($con,$query);
    if($res){
        $last_id = mysqli_insert_id($con);
        $qry="select * from tbl_user where user_id=$last_id";
        $r=mysqli_query($con,$qry);

        $row=mysqli_fetch_assoc($r);
        $arr=array("status"=>true,"message"=>"Registered....");
        $arr['user']=$row;
        echo json_encode($arr);

    }else{
        $arr=array("status"=>false,"message"=>"Not Registered...");
        $arr['user']=null;
        echo json_encode($arr);
    }


}else{
    $arr=array("status"=>false,"message"=>"Insufficient Parameter","user"=>null);

    echo json_encode($arr);
}
?>