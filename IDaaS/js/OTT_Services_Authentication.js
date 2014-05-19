


 
 function facebook_auth(userid)
{

      window.fbAsyncInit = function() {
        FB.init({
          appId      : '1509102699311592',
          xfbml      : true,
          version    : 'v2.0'
        });
      };

      (function(d, s, id){
         var js, fjs = d.getElementsByTagName(s)[0];
         if (d.getElementById(id)) {return;}
         js = d.createElement(s); js.id = id;
         js.src = "//connect.facebook.net/en_US/sdk.js";
         fjs.parentNode.insertBefore(js, fjs);
       }(document, 'script', 'facebook-jssdk'));

FB.login(function(response) {
   if (response.authResponse) {
     var access_token =   FB.getAuthResponse()['accessToken'];
     console.log('Access Token = '+ access_token);
     
   } else {
     console.log('User cancelled login or did not fully authorize.');
   }
 }, {scope: 'user_likes,user_videos'});



}