var express = require('express');
var router = express.Router();
var CryptoJS = require("crypto-js");
var mysql = require('mysql');
var md5 = require("md5");
console.log("enter index.js")

router.post('/login', function(req, res) {
    console.log("username=" + req.body.username);
    console.log("passw=" + req.body.password);
    if (req.body.password != "a0b923820dcc509a") { //wring password,备注：此处待从数据库取出
        var ans = {};
        ans.status = "-1";
        res.json(ans);
    } else {
        if (req.body.login_remember == "false") {
            var md5str = md5(req.body.username + 'salt1'); //加盐
            res.append('Set-Cookie', ['account=' + req.body.username, 'plot=' + md5str, 'HttpOnly']);
            console.log(md5str);
        } else {
            // cookie alive: 1 day
            var md5str = md5(req.body.username + 'salt21day');
            res.append('Set-Cookie', ['account=' + req.body.username, 'plot=' + md5str, 'maxAge=60*60*24*1000', 'HttpOnly']);
        }
        var ans = {};
        ans.status = 1;
        res.json(ans);
        // give access to homePage
        //前端跳转
    }
});

// // athentication
// router.use(function(req, res, next) {
//     var md5str1 = md5(req.cookies.account + 'salt1'); //加盐
//     var md5str2 = md5(req.cookies.account + 'salt21day');
//     if (req.cookies.account && (req.cookies.plot === md5str1 || req.cookies.plot === md5str2)) {
//         console.log(req.cookies);
//         console.log('cookie exists');
//         next();
//     } else {
//         // redirect to login.html
//         console.log("cookie not exists")
//         //res.redirect("/login.html");
//         res.redirect("/html/index1.html");
//     }
// });

/* Goto home page. */
router.get('/', function(req, res, next) {
    // res.render('index', { title: 'Express' });
    res.redirect('/home.html');
});

router.get("/Lot", function(req, res, err){
   var ans = new Array();
   var title = ["教师","教授","院士"];
   for(i=1; i<=3; i++){
       var item={};
       item.value = i;
       item.label = title[i-1];
       ans.push(item);
   }
   console.log(ans);
   res.json(ans);
});
module.exports = router;