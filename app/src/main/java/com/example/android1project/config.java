package com.example.android1project;

public class config {

        static String ip_value="http://192.168.14.48/android1project/api/";
        static String selectitem=ip_value+"register.php";
        static String selecttype=ip_value+"itemtype.php";

        static String apilogin=ip_value+"apilogin.php?apicall=";
        static String signup=apilogin+"signup";
        static String login=apilogin+"login";

        static String apidelete=ip_value+"api.php?apicall=";
        static String delete=apidelete+"delete";
        static String change=apidelete+"change";
        static String itemsave=apidelete+"itemsave";
        static String additem_save=apidelete+"additem_save";
        static String updateitem_sum=apidelete+"updateitem_sum";
}
